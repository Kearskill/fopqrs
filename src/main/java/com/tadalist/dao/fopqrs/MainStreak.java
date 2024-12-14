package com.tadalist.dao.fopqrs;

import java.util.Scanner;

public class MainStreak {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while(!exit){
            System.out.println("\n===== Streak Management System =====");
            System.out.println("1. Add Streak");
            System.out.println("2. View All Streak");
            System.out.println("3. Update Streak");
            System.out.println("4. Delete Streak");
            System.out.println("5. View Streak by ID");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); //Consume new line? wdym ameer -keni

            switch(choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
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
}
