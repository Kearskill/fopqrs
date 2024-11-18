package com.tadalist.dao.fopqrs;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Database {
    private static String url = "";
    private static String user = "root";
    private static String password = "password";

    private Database(){
    // placeholder
    }

    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
