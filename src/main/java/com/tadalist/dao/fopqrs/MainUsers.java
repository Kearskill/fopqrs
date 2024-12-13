package com.tadalist.dao.fopqrs;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class MainUsers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while(!exit){
            System.out.println("\n===== Task Management System =====");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. View User by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); //Consume new line? wdym ameer -keni

            switch(choice){
                case 1:
                    addUser(sc);
                    break;
                case 2:
                    viewAllUser(sc);
                    break;
                case 3:
                    //                updateUser(sc);
                    break;
                case 4:
                    //                deleteUser(sc);
                    break;
                case 5:
                    //                viewUserByID(sc);
                    break;
                case 6:
                    System.out.println("Exiting User Management System. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }

    }
    private static void addUser(Scanner sc){
        try {
            System.out.println("Enter user details:");

            System.out.print("Email:");
            String email = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

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
    private static void viewAllUser(Scanner sc){
        try {
            List<Users> usersList = UsersDAO.getAllUsers();
            System.out.println("\n===== All Users =====");
            for (Users user : usersList) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
    }
}
