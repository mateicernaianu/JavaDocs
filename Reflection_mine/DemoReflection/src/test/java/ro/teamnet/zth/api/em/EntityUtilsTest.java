package ro.teamnet.zth.api.em;

import org.junit.Test;
import ro.teamnet.zth.api.annotation.Id;
import ro.teamnet.zth.appl.domain.Department;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/7/2016.
 */
public class EntityUtilsTest {

    @Test
    public void testGetTableNameMethod()
    {
        String tableName = EntityUtils.getTableName(Department.class);
        assertEquals("Table name should be departments!", "departments", tableName);
    }

    @Test
    public void testGetColumns()
    {
        List<ColumnInfo> c = EntityUtils.getColumns(Department.class);
        System.out.println(c);
        assertEquals("Number of elements should be 3", 3, c.size());
    }

    @Test
    public void testCastFromSqlType()
    {
        Integer i = 100;
        Double d = 3.6;
        Float f = 7.8f;
        Long ll = Long.valueOf(4002000);
        String s = "ana";
        assertEquals("Integer", i, EntityUtils.castFromSqlType(i,Integer.class));
        assertEquals("Double", d, EntityUtils.castFromSqlType(d,Double.class));
        assertEquals("Float", f, EntityUtils.castFromSqlType(f,Integer.class));
        assertEquals("Long", ll, EntityUtils.castFromSqlType(ll,Integer.class));
        assertEquals("NO big decimal", s, EntityUtils.castFromSqlType(s,Integer.class));
    }

    @Test
    public void testGetFieldsByAnnotation(){
        List<Field> test = EntityUtils.getFieldsByAnnotations(Department.class, Id.class);
        assertEquals("ID field",1 , test.size());
    }

    @Test
    public void testGetSqlValue() throws IllegalAccessException {
        Department d = new Department();
        d.setId((long)5);
        assert (long)EntityUtils.getSqlValue(d) == (long)5;
    }
}
