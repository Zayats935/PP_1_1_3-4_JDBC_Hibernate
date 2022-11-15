package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static Connection mySQLConnection;
    private static final String hostName = "localhost";
    private static final String dbName = "test";
    private static final String userName = "root";
    private static final String password = "root";

    private Util(){}

    public static Connection getMySQLConnection() {
        try {
            if (mySQLConnection == null || mySQLConnection.isClosed()) {

                String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
                mySQLConnection = DriverManager.getConnection(connectionURL, userName, password);
            }

            return mySQLConnection;
        } catch (SQLException e) {
            throw new RuntimeException("Проблема с подключение к БД");
        }
    }
}
