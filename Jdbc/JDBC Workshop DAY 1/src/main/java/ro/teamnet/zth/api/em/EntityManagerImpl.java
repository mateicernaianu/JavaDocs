package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImpl implements EntityManager{

    @Override
    public <T> T findById(Class<T> entityClass, Long id) {
        T instance = null;
        try {
            Connection c = DBManager.getConnection();
            String tableName = EntityUtils.getTableName(entityClass);
            List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);
            List<Field> idFields = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);
            List<Field> colFields = EntityUtils.getFieldsByAnnotations(entityClass, Column.class);

            Id idd = idFields.get(0).getAnnotation(Id.class);
            Condition cond = new Condition(idd.name(), id);
            QueryBuilder qb = new QueryBuilder();
            qb.setTableName(tableName);
            qb.addQueryColumns(columns);
            qb.setQueryType(QueryType.SELECT);
            qb.addCondition(cond);
            String s = qb.createQuery();

            instance = entityClass.newInstance();
            Statement state = c.createStatement();
            ResultSet resultSet = state.executeQuery(s);
            Object result = resultSet.next();
            if (result != null) {
                for (ColumnInfo ci : columns) {
                    Field f = instance.getClass().getDeclaredField(ci.getColumnName());
                    f.setAccessible(true);
                    f.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(ci.getDbName()), f.getType()));
                }
            }
        } catch (Exception ex) {}

        return instance;
    }


    @Override
    public Long getNextIdVal(String tableName, String columnIdName) throws SQLException, ClassNotFoundException {
        long returned = 0;
        Connection cn = DBManager.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT " + "max(" + columnIdName + ") from" + tableName);//select mx(id_column) + 1 from tableNme
            rs.next();
            returned = rs.getInt(1);
            return returned + 1;

    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) throws SQLException, ClassNotFoundException, NoSuchFieldException
    {
        List<T> results = new ArrayList<T>();
        try {

            Connection c = DBManager.getConnection();

            List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);
            String tableName = EntityUtils.getTableName(entityClass);
            List<Field> colFields = EntityUtils.getFieldsByAnnotations(entityClass, Column.class);
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setTableName(tableName).setQueryType(QueryType.SELECT);

            queryBuilder.addQueryColumns(columns);
            String s = queryBuilder.createQuery();
            Statement state = c.createStatement();
            ResultSet resultSet = state.executeQuery(s);

            while(resultSet.next() == true) {


                T instance = entityClass.newInstance();
                for (ColumnInfo cc : columns) {
                    Field f = instance.getClass().getDeclaredField(cc.getColumnName());
                    f.setAccessible(true);
                    f.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(cc.getDbName()), f.getType()));

                }
                results.add(instance);
            }
        /*List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);
        List<Field> idFields = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);
        */
        }
        catch (Exception ex){
            ex.getMessage();
        }
        return results;
    }

    @Override
    public <T> Object insert(T entity) throws SQLException, ClassNotFoundException {
        Connection conn= DBManager.getConnection();
        String tableName=EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> columns=EntityUtils.getColumns(entity.getClass());
        long id=0;
        for (ColumnInfo columnInfo: columns) {
                if(columnInfo.isId()){
                    id=getNextIdVal(tableName,columnInfo.getDbName());
                    columnInfo.setValue(id);
                }else{
                    Field field=null;
                    try {
                        field= entity.getClass().getDeclaredField(columnInfo.getColumnName());
                        field.setAccessible(true);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    try {
                        columnInfo.setValue(field.get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            QueryBuilder queryBuilder=new QueryBuilder();
            queryBuilder.setTableName(tableName);
            queryBuilder.setQueryType(QueryType.INSERT);
            queryBuilder.addQueryColumns(columns);
            String query=queryBuilder.createQuery();
            try {
                Statement stm=conn.createStatement();
                stm.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //nu returneaza ce trebuie
            Object result=findById(entity.getClass(),id);
            return result;
        }

    }


