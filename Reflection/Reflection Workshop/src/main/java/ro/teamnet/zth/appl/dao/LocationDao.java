package ro.teamnet.zth.appl.dao;

import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Nicolescu on 11.07.2016.
 */
public class LocationDao {
    private Location location;
    private EntityManager entityManager;

    public LocationDao(Location location) {
        this.location = location;
        entityManager = new EntityManagerImpl();
    }

    public Location findLocationById(long id){
        Location locationResult = null;
        locationResult=entityManager.findById(Location.class,id);
        return  locationResult;
    }

    public List<Location> findAllLocation() throws ClassNotFoundException, NoSuchFieldException, SQLException {
        return entityManager.findAll(Location.class);
    }

    public void insertLocation() throws SQLException, ClassNotFoundException {

            entityManager.insert(location);
            }

    public List<Location> findLocationByParams(Map<String,Object> params){
        List<Location> resultLocation=null;
        resultLocation=entityManager.findByParams(Location.class,params);
        return  resultLocation;
    }

    public  void updateLocation(Location location) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException {

            entityManager.update(location);

    }

}