package ro.teamnet.zth.api.em;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.database.DBManager;
import ro.teamnet.zth.appl.domain.Department;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImplTest {
    EntityManagerImpl ent = new EntityManagerImpl();

    @Test
    public void testFindNext() throws SQLException, ClassNotFoundException {
        long aux  = ent.getNextIdVal("departments", "DEPARTMENT_ID");
        assertEquals("VAL", 271, aux);
    }

    @Test
    public void testFindAll() throws NoSuchFieldException, SQLException, ClassNotFoundException {

        assertEquals("FIND ALL TEST", 25,  ent.findAll(Department.class).size());
    }

    @Test
    public void testInsert() throws SQLException, ClassNotFoundException {
        Department department=new Department();
        department.setDepartmentName("HR");
        department.setId(101);
        department.setLocations(1000);

        EntityManager entityManager=new EntityManagerImpl();
        Object resultObj = entityManager.insert(department);
        Assert.assertNotNull(resultObj);
        Assert.assertTrue(resultObj instanceof Department);
        Assert.assertEquals("VAL",101,(long) department.getId());
    }


    @Test
    public void testFindById(){
        Department obj = ent.findById(Department.class, (long)270);
        System.out.println(obj.getId());
        assert( obj.getId().equals(270));
    }

    @Test
    public void testUpdate() throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Department department=new Department();
        department.setDepartmentName("BBBR");
        department.setId(270);
        department.setLocations(1000);

        EntityManager entityManager=new EntityManagerImpl();
        Object resultObj = entityManager.update(department);
        Assert.assertNotNull(resultObj);
        Assert.assertTrue(resultObj instanceof Department);
    }

    @Test
    public void testFindByParams() {

        Department dp = new Department();
        EntityManagerImpl em = new EntityManagerImpl();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("DEPARTMENT_ID",270);
        assert(em.findByParams(Department.class, map).size() != 0);

    }


    @Test
    public void testDelete(){
        Department department=new Department();
        department.setDepartmentName("HR");
        department.setId(200);
        department.setLocations(1000);
        EntityManager entityManager=new EntityManagerImpl();
        entityManager.delete(department);


    }
}
