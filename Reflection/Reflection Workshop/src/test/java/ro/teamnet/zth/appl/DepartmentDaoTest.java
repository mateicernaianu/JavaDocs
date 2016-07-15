package ro.teamnet.zth.appl;

import org.junit.Assert;
import org.junit.Test;
import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.dao.DepartmentDao;
import ro.teamnet.zth.appl.domain.Department;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/11/2016.
 */
public class DepartmentDaoTest {

        DepartmentDao dao;

        public DepartmentDaoTest() {
            Department department=new Department();
            department.setDepartmentName("HR");
            department.setId(200);
            department.setLocations(1000);
            this.dao = new DepartmentDao(department);
        }


        @Test
        public void testFindAll() throws NoSuchFieldException, SQLException, ClassNotFoundException {

            assertEquals("FIND ALL TEST", 25,  dao.findAll().size());
        }

        @Test
        public void testInsert() throws SQLException, ClassNotFoundException {

            Object resultObj = dao.insert();
            Assert.assertNotNull(resultObj);
            Assert.assertTrue(resultObj instanceof Department);
        }


        @Test
        public void testFindById(){

            Department obj = dao.findById();
            System.out.println(obj.getId());
            assert( obj.getId() > 0);
        }

        @Test
        public void testUpdate() throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

            Object resultObj = dao.update();
            Assert.assertNotNull(resultObj);
            Assert.assertTrue(resultObj instanceof Department);
        }

        @Test
        public void testFindByParams() {

            assert(dao.findByParams().size() != 0);

        }


        @Test
        public void testDelete(){

            dao.delete();
        }
    }


