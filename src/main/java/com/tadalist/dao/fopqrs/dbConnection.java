package com.tadalist.dao.fopqrs;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;

//connecting to connection
//Connection conn = dbConnection.getConnection()
public class dbConnection {
    // Connect to Database
    public static String url = "jdbc:mysql://localhost:3306/realtadalist_db"; //change abd to whichever schema youre using
    public static String user = "root";
    public static String password = "yzlwzx";

    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//loading driver
//            System.out.println("Connecting to: " + url + " with user " + user);
        }catch(ClassNotFoundException e){
            System.out.println("Error connection!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(url,user,password);
    }
}