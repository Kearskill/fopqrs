package com.tadalist;

import com.tadalist.dao.fopqrs.TaskDAO;
import com.tadalist.dao.fopqrs.Tasks;
import com.tadalist.dao.fopqrs.recurringTaskDAO;
import com.tadalist.dao.fopqrs.recurringTask;

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
            System.out.println("7. Search Task");
            System.out.println("8. Manage Recurring Task");
            System.out.println("9. Add Task Dependency");
            System.out.println("10. Mark Task As Complete");
            System.out.println("11. Exit");
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
                    //search
                    searchTasksByKeyword(scanner);
                    break;
                case 8:
                    recurringTaskMenu(scanner);
                    break;
                case 9:
                    //taskdependency
                    recurringTaskMenu(scanner);
                    break;
                case 10:
                    //Mark Task
                    System.out.println("Exiting TaDaList! Goodbye :((((((((((");
                    exit = true;
                    break;
                case 11:
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

    // Option 7: Search Tasks by Keyword
    private static void searchTasksByKeyword(Scanner scanner) {
        try {
            System.out.println("Enter a keyword to search by title or description:");
            String keyword = scanner.nextLine();

            List<Tasks> tasks = TaskDAO.searchTasksByKeyword(keyword);

            if (tasks.isEmpty()) {
                System.out.println("\nNo tasks found for the keyword: " + keyword);
            } else {
                System.out.println("\n===== Search Results =====");
                for (Tasks task : tasks) {
                    String status = task.getStatus() == Tasks.Status.PENDING ? "[INCOMPLETE]" : "[COMPLETED]";
                    System.out.printf(
                            "%d. %s %s - Due: %s - Category: %s - Priority: %s\n",
                            task.getTaskId(),
                            status,
                            task.getTitle(),
                            task.getDueDate(),
                            task.getCategory().toString().toLowerCase(),
                            task.getPriority().toString().toLowerCase()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }
    }

    //Option 8: Manage Recurring Task
    private static void recurringTaskMenu(Scanner scanner){
        boolean exit = false;
        while(!exit){
            System.out.println("\n===== Recurring Task Management =====");
            System.out.println("1. Add Recurring Task");
            System.out.println("2. Delete Recurring Tasks");
            System.out.println("3. Edit Recurring Task");
            System.out.println("4. View recurring Task");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch(choice){
                case "1":
                    addRecurringTask(scanner);
                    break;
                case "2":
                    deleteRecurringTask(scanner);
                    break;
                case "3":
                    editRecurringTask(scanner);
                    break;
                case "4":
                    System.out.println("Going back");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }

    }
    private static void addRecurringTask(Scanner scanner) {
        try {
            System.out.println("=== Add a Recurring Task ===");
            System.out.println("Enter Task Title:");
            String title = scanner.nextLine();
            System.out.print("Enter Task Description: ");
            String description =  scanner.nextLine();

            System.out.print("Recurrence Type (DAILY, WEEKLY, MONTHLY): ");
            recurringTask.recurrenceType recurrenceType =
                    recurringTask.recurrenceType.valueOf(scanner.nextLine().toUpperCase());

            recurringTask task = new recurringTask(0,title, description, recurrenceType);
            recurringTaskDAO.addRecurringTask(task);

            System.out.println("Recurring task successfully added with Task ID of " + task.getRecurringID());
        } catch (Exception e) {
            System.out.println("Error adding recurring task: " + e.getMessage());
        }
    }
    //delete recurring task
    private static void deleteRecurringTask(Scanner scanner) {
        try {
            System.out.print("Enter Task ID to delete: ");
            int recurringID = scanner.nextInt();
            recurringTaskDAO.deleteRecurringTask(recurringID);
            System.out.println("Recurring Task deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }

    private static void editRecurringTask(Scanner scanner){
        try{
            System.out.println("Enter Task ID to edit: ");
            int recurringID = scanner.nextInt();
            scanner.nextLine();

            recurringTask edit = recurringTaskDAO.getRecurringTaskById(recurringID);
            System.out.println("Enter new title: ");
            String title = scanner.nextLine();
            System.out.println("Enter Description: ");
            String description = scanner.nextLine();
            System.out.print("Recurrence Type (DAILY, WEEKLY, MONTHLY): ");
            recurringTask.recurrenceType recurrenceType =
                    recurringTask.recurrenceType.valueOf(scanner.nextLine().toUpperCase());



            edit.setTitle(title);
            edit.setDescription(description);
            edit.setRecurrenceType(recurrenceType);

            recurringTaskDAO.updateRecurringTask(edit);
        }catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }


}
