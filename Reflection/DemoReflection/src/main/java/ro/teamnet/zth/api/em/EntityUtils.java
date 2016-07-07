package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotation.Column;
import ro.teamnet.zth.api.annotation.Id;
import ro.teamnet.zth.api.annotation.Table;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/7/2016.
 */
public class EntityUtils{

    private static Field[] fields;

    private EntityUtils() throws UnsupportedOperationException{

        }
       public static String getTableName(Class entity){

           Table t = (Table) entity.getAnnotation(Table.class);
           if(t != null)
               return t.name();
           return entity.getSimpleName();
       }

        public static List<ColumnInfo> getColumns(Class enitity){

            List<ColumnInfo> columnInfo = new ArrayList<ColumnInfo>();
            fields = enitity.getDeclaredFields();
            for(Field f:fields)
            {
                ColumnInfo info = new ColumnInfo();
                info.setColumnInfo(f.getName());
                info.setColumnType(f.getType());

                Id id = f.getAnnotation(Id.class);
                if(id != null) {
                    info.setId(true);
                    info.setDbName(id.name());
                }
                else {
                    info.setId(false);
                    Column c = f.getAnnotation(Column.class);
                    info.setDbName(c.name());
                }
                //info.setValue();
                    columnInfo.add(info);
            }
            return columnInfo;
        }

        public static Object castFromSqlType(Object value, Class wantedType){

            if(value instanceof BigDecimal)
            {
                BigDecimal bdValue = (BigDecimal) value;
                if(wantedType.equals(Integer.class))
                {
                    return bdValue.intValue();
                }
                if(wantedType.equals(Long.class))
                {
                    return bdValue.longValue();
                }
                if(wantedType.equals(Float.class))
                {
                    return bdValue.floatValue();
                }
                if(wantedType.equals(Double.class))
                {
                    return bdValue.doubleValue();
                }
            }
            return value;
        }

        public static List<Field> getFieldsByAnnotations(Class clazz, Class annotation){
            List<Field> fld = new ArrayList<Field>();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f:fields)
            {
                Object o = f.getAnnotation(annotation);
                if(o!=null)
                    fld.add(f);
            }
            return fld;
        }

        public static Object getSqlValue(Object object) throws IllegalAccessException {
            Table t = object.getClass().getAnnotation(Table.class);
            if(t != null)
            {
                List<Field> fields = getFieldsByAnnotations(object.getClass(), Id.class);
                fields.get(0).setAccessible(true);
                try {
                    return fields.get(0).get(object);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return object;
        }
}
