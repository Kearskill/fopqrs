package com.tadalist.dao.fopqrs;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.io.Console;

public class MainLogin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            System.out.println("Welcome to TaDaList!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Quit");
            System.out.print(">>> ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    loginReq(sc);
                    break;
                case 2:
                    registerReq(sc);
                    break;
                case 3:
                    System.out.println("Thank you for using TaDaList! See you next time!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unless you have a time machine, that content is not available.");
                    break;
            }
        }
        sc.close();
    }
    private static void loginReq(Scanner sc){
        System.out.println("---Login---");
        try{
            System.out.println("Enter email: ");
            String email = sc.nextLine();

            System.out.println("Enter password: ");
            String password = sc.nextLine();

            Users user = new Users(email, password);
            UsersDAO.loginUser(user);
        } catch(SQLException | IllegalArgumentException e){
            System.out.println("Error adding user: " + e.getMessage());
        }

    }

    private static void registerReq (Scanner sc){
        System.out.println("---Registering---");
//        Console console = System.console(); //Setup for password masking
        try {
            System.out.println("Enter user details:");

            System.out.print("Email:");
            String email = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            String password;
            while(true){
                String savedPassword = sc.nextLine();
                String confirmPassword = sc.nextLine();
                if(savedPassword.equals(confirmPassword)){
                    System.out.println("Password match. Success!");
                    password = new String(savedPassword);
                    break;
                }
                System.out.println("Password doesn't match. Please try again!");
//                this is for password masking but i failed
//                char[] savedPassword = console.readPassword("Password: "); // console.readPassword only gets char array
//                char[] confirmPassword = console.readPassword("Confirm password: ");
//                if (new String(savedPassword).equals(new String(confirmPassword))){
            }

            System.out.print("Notification Preference (EMAIL, SMS, APPNOTIFICATION): ");
            Users.NotificationPreference preference = Users.NotificationPreference.valueOf(sc.nextLine().toUpperCase());

            System.out.print("Last Login (yyyy-mm-dd hh:mm:ss): ");
            Timestamp lastLogin = Timestamp.valueOf(sc.nextLine());

            Timestamp now = new Timestamp(System.currentTimeMillis());

            Users user = new Users(email, name, password, preference, lastLogin);
            UsersDAO.addUser(user);
            System.out.println("User added successfully with ID: " + user.getUserID());
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
}
