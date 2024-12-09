package com.tadalist.dao.fopqrs;
import java.sql.*;
import java.sql.DriverManager;


public class dbConnection {
    public static String url = "jdbc:mysql://localhost:3306/tadalist_db"; //change abd to whichever schema youre using
    public static String user = "root";
    public static String password = "localroot";

    public static dbConnection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//loading driver
            System.out.println("Connecting to: " + url + " with user " + user);
        } catch (ClassNotFoundException e) {
            System.out.println("Error connection!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }
}