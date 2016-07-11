package ro.teamnet.zth.api.database;

import static ro.teamnet.zth.api.database.DBProperties.*;
import java.sql.*;

/**
 * Created by user on 7/8/2016.
 */
public class DBManager implements DBProperties{


    private static boolean registerDriverStatus = false;
    private static final String CONNECTION_STRING = "jdbc:oracle:thin:@" + DBProperties.IP + ":" + DBProperties.PORT;


    public DBManager()throws UnsupportedOperationException{
    }

    private static boolean registerDriver() throws ClassNotFoundException {

        if(registerDriverStatus == false) {
           Class.forName("oracle.jdbc.driver.OracleDriver");
           registerDriverStatus = true;
           return registerDriverStatus;
       }
        return registerDriverStatus;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        System.out.println(CONNECTION_STRING);
        Connection conn = null;
        registerDriver();
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static boolean checkConnection(Connection connection) throws SQLException, ClassNotFoundException {

        Connection conn = getConnection();
        try (Statement  stmt = conn.createStatement( )){
            stmt.execute("SELECT 1 FROM DUAL");
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }
}
