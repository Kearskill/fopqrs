package com.tadalist;

import com.tadalist.dao.fopqrs.TaskDAO;
import com.tadalist.dao.fopqrs.Tasks;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Display menu options
            System.out.println("\n===== TaDaList!! - Your Number One To-Do List App =====");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Edit Task");
            System.out.println("4. Delete Task");
            System.out.println("5. View Task by ID");
            System.out.println("6. Sort Task");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    viewAllTasks();
                    break;
                case 3:
                    updateTask(scanner);
                    break;
                case 4:
                    deleteTask(scanner);
                    break;
                case 5:
                    viewTaskById(scanner);
                    break;
                case 6:
                    sortBy(scanner);
                    break;
                case 7:
                    System.out.println("Exiting TaDaList! Goodbye :((((((((((");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice idiot!. Try again.");
            }
        }
        scanner.close();
    }

    // Option 1: Add a Task
    private static void addTask(Scanner scanner) {
        try {
            System.out.println("Enter task details:");
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Due Date (yyyy-mm-dd): ");
            Date dueDate = Date.valueOf(scanner.nextLine());

            System.out.print("Priority (LOW, MEDIUM, HIGH): ");
            Tasks.Priority priority = Tasks.Priority.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Category (HOMEWORK, PERSONAL, WORK): ");
            Tasks.Category Category = Tasks.Category.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Status (PENDING, COMPLETED): ");
            Tasks.Status status = Tasks.Status.valueOf(scanner.nextLine().toUpperCase());

            Timestamp now = new Timestamp(System.currentTimeMillis());

            Tasks task = new Tasks(0, title, description, dueDate, priority, status, now, now, (short) 0,
                    0, 0,Category);
            TaskDAO.addTask(task);
            System.out.println("Task added successfully with ID: " + task.getTaskId());
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }

    // Option 2: View All Tasks
    private static void viewAllTasks() {
        try {
            List<Tasks> taskList = TaskDAO.getAllTasks();
            System.out.println("\n===== All Tasks =====");
            for (Tasks task : taskList) {
                System.out.println(task);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
    }

    // Option 3: Update Task
    private static void updateTask(Scanner scanner) {
        try {
            System.out.print("Enter Task ID to update: ");
            int taskId = scanner.nextInt();
            scanner.nextLine();

            Tasks existingTask = TaskDAO.getTaskById(taskId);
            if (existingTask == null) {
                System.out.println("Task with ID " + taskId + " not found.");
                return;
            }

            System.out.print("New Title (" + existingTask.getTitle() + "): ");
            String title = scanner.nextLine();
            title = title.isEmpty() ? existingTask.getTitle() : title;

            System.out.print("New Description (" + existingTask.getDescription() + "): ");
            String description = scanner.nextLine();
            description = description.isEmpty() ? existingTask.getDescription() : description;

            System.out.print("New Due Date (yyyy-mm-dd, " + existingTask.getDueDate() + "): ");
            String dateInput = scanner.nextLine();
            Date dueDate = dateInput.isEmpty() ? existingTask.getDueDate() : Date.valueOf(dateInput);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            existingTask.setTitle(title);
            existingTask.setDescription(description);
            existingTask.setDueDate(dueDate);
            existingTask.setUpdatedAt(now);

            TaskDAO.editTask(existingTask);
            System.out.println("Task updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    // Option 4: Delete Task
    private static void deleteTask(Scanner scanner) {
        try {
            System.out.print("Enter Task ID to delete: ");
            int taskId = scanner.nextInt();
            TaskDAO.deleteTasks(taskId);
            System.out.println("Task deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }

    // Option 5: View Task by ID
    private static void viewTaskById(Scanner scanner) {
        try {
            System.out.print("Enter Task ID to view: ");
            int taskId = scanner.nextInt();
            Tasks task = TaskDAO.getTaskById(taskId);
            if (task != null) {
                System.out.println("Task Details: " + task);
            } else {
                System.out.println("Task with ID " + taskId + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching task: " + e.getMessage());
        }
    }

    //6 : Sort Task
    private static void sortBy(Scanner scanner){
        try{
            System.out.println("===== Task Sorting ====");
            System.out.println("1. Due Date (Ascending to Descending)");
            System.out.println("2. Due Date (Descending to Ascending)");
            System.out.println("3. Priority (High to Low)");
            System.out.println("4. Priority (Low to High)");
            String userOption = scanner.nextLine();

            boolean ascending  = switch(userOption) {
                case "1" -> true;
                case "2" -> false;
                case "3" -> true;
                case "4" -> false;
                default -> throw new IllegalArgumentException("Invalid option selected!");
            };
            String sortByColumn = switch (userOption) {
                case "1", "2" -> "DueDate";
                case "3", "4" -> "Priority";
                default -> throw new IllegalArgumentException("Invalid option selected!");
            };

            System.out.println("Tasks sorted by " + sortByColumn +
                    " (" + (ascending ? "Ascending" : "Descending") + "):");
            List<Tasks> sortedTasks = TaskDAO.getTasksSorted(sortByColumn, ascending);

            for (Tasks task : sortedTasks) {
                System.out.println(task.getTitle() + " - " + userOption + ": "
                + getColumnValue(task, userOption));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static Object getColumnValue(Tasks task, String sortBy) {
        return switch (sortBy) {
            case "DueDate" -> task.getDueDate();
            case "Priority" -> task.getPriority();
            case "Category" -> task.getCategory();
            default -> null;
        };
    }

}
