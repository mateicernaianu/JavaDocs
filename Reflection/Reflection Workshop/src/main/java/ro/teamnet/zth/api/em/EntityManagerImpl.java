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
import java.util.Map;

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
            ResultSet rs = st.executeQuery("SELECT " + "max(" + columnIdName + ") from " + tableName);//select mx(id_column) + 1 from tableNme
            rs.next();
            returned = rs.getLong(1);
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
    public <T> T update(T entity) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        Connection conn= DBManager.getConnection();
        String tableName=EntityUtils.getTableName(entity.getClass());
        List<ColumnInfo> columns=EntityUtils.getColumns(entity.getClass());
        Long id=0l;
        for (ColumnInfo columnInfo: columns) {
            Field field=null;
            if(columnInfo.isId()){
                field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);
                id = Long.valueOf(field.get(entity).toString());
                columnInfo.setValue(id);
            }else{

                try {
                    field= entity.getClass().getDeclaredField(columnInfo.getColumnName());

                    field.setAccessible(true);
                    columnInfo.setValue(field.get(entity));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Condition c = new Condition(columns.get(0).getDbName(), columns.get(0).getValue());

        QueryBuilder queryBuilder=new QueryBuilder();
        queryBuilder.addCondition(c);
        queryBuilder.setTableName(tableName);
        queryBuilder.setQueryType(QueryType.UPDATE);
        queryBuilder.addQueryColumns(columns);
        String query=queryBuilder.createQuery();
        try {
            Statement stm=conn.createStatement();
            stm.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //nu returneaza ce trebuie
        Object result=findById(entity.getClass(),(long) id);

        return (T) result;
    }


    @Override
    public void delete(Object entity) {

        try( Connection conn=DBManager.getConnection();
        ){
            String tableName=EntityUtils.getTableName(entity.getClass());
            List<ColumnInfo> columns=EntityUtils.getColumns(entity.getClass());
            for (ColumnInfo currentColumnInfo:columns) {
                Field field= entity.getClass().getDeclaredField(currentColumnInfo.getColumnName());
                field.setAccessible(true);
                currentColumnInfo.setValue(field.get(entity));
            }
            List<Field> fields=EntityUtils.getFieldsByAnnotations(entity.getClass(),Id.class);
            Long id = 0l;
            for (ColumnInfo columnInfo: columns) {
                Field field=null;
                if(columnInfo.isId()){
                    field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    id = Long.valueOf(field.get(entity).toString());
                    columnInfo.setValue(id);
                }else{

                    try {
                        field= entity.getClass().getDeclaredField(columnInfo.getColumnName());

                        field.setAccessible(true);
                        columnInfo.setValue(field.get(entity));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            Id idd = fields.get(0).getAnnotation(Id.class);
            String columnName = idd.name();
            Condition cond = new Condition(columnName, id);
            QueryBuilder queryBuilder=new QueryBuilder();
            queryBuilder.setTableName(tableName);
            queryBuilder.setQueryType(QueryType.DELETE);
            queryBuilder.addCondition(cond);
            String query = queryBuilder.createQuery();
            Statement statement=conn.createStatement();
            statement.executeUpdate(query);
        }catch(Exception ex){

        }
    }


    @Override
    public <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params) {
        List<T> instances = new ArrayList<T>();

        try {
            Connection c = DBManager.getConnection();
            String tableName = EntityUtils.getTableName(entityClass);
            List<ColumnInfo> columns = EntityUtils.getColumns(entityClass);

            List<Field> idFields = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);
            List<Field> colFields = EntityUtils.getFieldsByAnnotations(entityClass, Column.class);

            Id idd = idFields.get(0).getAnnotation(Id.class);
            Condition cond = null;
            QueryBuilder qb = new QueryBuilder();
            for (Map.Entry<String, Object> e : params.entrySet()) {
                cond = new Condition(e.getKey(), e.getValue() );
                qb.addCondition(cond);
            }

            qb.setTableName(tableName);
            qb.addQueryColumns(columns);
            qb.setQueryType(QueryType.SELECT);

            String s = qb.createQuery();


            Statement state = c.createStatement();
            ResultSet resultSet = state.executeQuery(s);
            Object result = resultSet.next();
            if (result != null) {
                T instance = entityClass.newInstance();
                for (ColumnInfo ci : columns) {
                    Field f = instance.getClass().getDeclaredField(ci.getColumnName());
                    f.setAccessible(true);
                    f.set(instance, EntityUtils.castFromSqlType(resultSet.getObject(ci.getDbName()), f.getType()));
                    instances.add(instance);
                }
            }
        } catch (Exception ex) {

        }

        return instances;
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


