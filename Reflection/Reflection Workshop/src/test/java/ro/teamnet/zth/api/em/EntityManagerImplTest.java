package ro.teamnet.zth.api.em;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.database.DBManager;
import ro.teamnet.zth.appl.domain.Department;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/8/2016.
 */
public class EntityManagerImplTest {
    EntityManagerImpl ent = new EntityManagerImpl();

    @Test
    public void testFindNext() throws SQLException, ClassNotFoundException {
        assertEquals(271, ent.getNextIdVal("departments", "DEPARTMENT_ID"));
    }

    @Test
    public void testFindAll() throws NoSuchFieldException, SQLException, ClassNotFoundException {

        assertEquals("FIND ALL TEST", 27,  ent.findAll(Department.class).size());
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

    }
}
