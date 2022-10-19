package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    public static Connection getMySQLConnection() throws SQLException {
        String hostName = "localhost";
        String dbName = "test";
        String userName = "root";
        String password = "root";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
            throws SQLException {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        Connection connection = DriverManager.getConnection(connectionURL, userName, password);
        return connection;
    }
}
