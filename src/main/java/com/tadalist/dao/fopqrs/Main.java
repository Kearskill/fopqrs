package com.tadalist.dao.fopqrs;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try {
        Users Leith = new Users(10000, "leith@gmail.com", "leith", "password");
        System.out.print(Leith);

//        CONTOH INSERT
//        INSERT INTO `tadalist_db`.`users` (`UserID`, `Email`, `UserName`, `UserPassword`, `NotificationPreference`, `LastLogin`) VALUES ('10001', 'leith@gmail.com', 'Leith', 'password', 'EMAIL', '2024-11-18 2:14:20');

//        CONTOH UPDATE
//        UPDATE `tadalist_db`.`users` SET `UserName` = 'Leithi' WHERE (`UserID` = '10001');

//        CONTOH DELETE
//        DELETE FROM `tadalist_db`.`users` WHERE (`UserID` = '10002');
        // Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to Database
        String url = "jdbc:mysql://localhost:3306/abd"; //change abd to whichever schema youre using
        String user = "root";
        String password = "localroot";
        System.out.println("Connecting to: " + url + " with user " + user);
        Connection con = DriverManager.getConnection(url, user, password);

        }catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred:");
            e.printStackTrace();
        }
    }
}