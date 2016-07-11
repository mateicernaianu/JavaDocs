package ro.teamnet.zth.api.database;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.*;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by user on 7/8/2016.
 */
public class DBManagerTest {

    @Test
    public void testGetConnection() throws SQLException, ClassNotFoundException {
        assertFalse("NOT NULL", DBManager.getConnection().equals(null));
    }

    @Test
    public void testCheckConnection() throws SQLException, ClassNotFoundException {
        assertEquals("CONNECTION ESTABLISH",true, DBManager.checkConnection(DBManager.getConnection()));
    }

}
