package ro.teamnet.zth.appl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.dao.LocationDao;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Location;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nicolescu on 11.07.2016.
 */
public class LocationDaoTest {

    Location location=null;
    EntityManager entityManager=null;
    LocationDao locationDao=null;

    public LocationDaoTest(){
        location=new Location();
        location.setId(1000);
        location.setCity("Roma");
        location.setPostalCode("00989");
        location.setStateProvince("Romania");
        location.setStreetAddress("Bd. Vladimirescu");

    }

    @Test
    public void testFindLocationById(){
        locationDao=new LocationDao(location);
        Assert.assertNotNull(locationDao.findLocationById(100));
    }
    @Test
    public void testfindAllLocation() throws SQLException, NoSuchFieldException, ClassNotFoundException {
        List<Location> resultList = locationDao.findAllLocation();
        assertEquals("FIND ALL TEST", 23,  resultList.size());
    }
    @Test
    public void testInsertLocation() throws SQLException, ClassNotFoundException {

        locationDao.insertLocation();
    }

    @Test
    public  void testfindLocationByParams() throws ClassNotFoundException, SQLException, NoSuchFieldException, IllegalAccessException {
        locationDao.updateLocation(location);

    }
}