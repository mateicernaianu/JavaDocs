package ro.teamnet.zth.api.em;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 7/8/2016.
 */
public interface EntityManager {

    <T> T findById(Class<T> entityClass, Long id);
    <T> Long getNextIdVal(String tableName, String columnIdName) throws SQLException, ClassNotFoundException;
    <T> Object insert(T entity) throws SQLException, ClassNotFoundException;
    <T> List<T> findAll(Class<T> entityClass) throws NoSuchFieldException, SQLException, ClassNotFoundException;

}
