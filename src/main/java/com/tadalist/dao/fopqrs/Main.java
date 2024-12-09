package com.tadalist.dao.fopqrs;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Users Leith = new Users(10000, "leith@gmail.com", "leith", "password",EMAIL,"2024-11-18 2:20:20");
        System.out.print(Leith);

        Connection con = Database.getConnection();

        if (con!= null){
            //berjaya
            System.out.println("Database connection successful!");
        }

//        CONTOH INSERT
//        INSERT INTO `tadalist_db`.`users` (`UserID`, `Email`, `UserName`, `UserPassword`, `NotificationPreference`, `LastLogin`) VALUES ('10001', 'leith@gmail.com', 'Leith', 'password', 'EMAIL', '2024-11-18 2:14:20');

//        CONTOH UPDATE
//        UPDATE `tadalist_db`.`users` SET `UserName` = 'Leithi' WHERE (`UserID` = '10001');

//        CONTOH DELETE
//        DELETE FROM `tadalist_db`.`users` WHERE (`UserID` = '10002');
    }
}