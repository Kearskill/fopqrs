package com.tadalist.dao.fopqrs;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class recurringTaskDAO {

    // Adding a recurring task
    public static void addRecurringTask(recurringTask recurringTask) throws SQLException {
        String sql = "INSERT INTO recurringtask (RecurrenceType, Title, Description) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, recurringTask.getRecurrenceType().name());
            stmt.setString(2, recurringTask.getTitle());
            stmt.setString(3, recurringTask.getDescription());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recurringTask.setRecurringID(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Deleting a recurring task
    public static void deleteRecurringTask(int recurringID) throws SQLException {
        String sql = "DELETE FROM recurringtask WHERE RecurringID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recurringID);
            stmt.executeUpdate();
        }
    }

    // Updating a recurring task
    public static void updateRecurringTask(recurringTask recurringTask) throws SQLException {
        String sql = "UPDATE recurringtask SET Title = ?, Description = ?, RecurrenceType = ? WHERE RecurringID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recurringTask.getTitle());
            stmt.setString(2, recurringTask.getDescription());
            stmt.setString(3, recurringTask.getRecurrenceType().name());
            stmt.setInt(4, recurringTask.getRecurringID());
            stmt.executeUpdate();
        }
    }

    // Getting all recurring tasks with details
    public static List<Map<String, Object>> getRecurringTasksWithDetails() throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String sql = "SELECT RecurringID, Title, Description, RecurrenceType FROM recurringtask";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("RecurringID", rs.getInt("RecurringID"));
                row.put("Title", rs.getString("Title"));
                row.put("Description", rs.getString("Description"));
                row.put("RecurrenceType", rs.getString("RecurrenceType"));
                resultList.add(row);
            }
        }
        return resultList;
    }

    // Getting a specific recurring task by ID
    public static recurringTask getRecurringTaskById(int recurringID) throws SQLException {
        String sql = "SELECT * FROM recurringtask WHERE RecurringID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recurringID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRecurringTask(rs);
                }
            }
        }
        return null;
    }

    // Getting a specific recurring task by ID
    public static List<recurringTask> getAllRecurringTasks() throws SQLException {
        List<recurringTask> tasks = new ArrayList<>();  // List to store all tasks
        String sql = "SELECT * FROM recurringtask";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    recurringTask task = mapResultSetToRecurringTask(rs); // Map ResultSet to task
                    tasks.add(task);  // Add task to the list
                }
            }
        }
        return tasks;  // Return the list of tasks
    }


    // Mapping a ResultSet to a recurringTask object
    private static recurringTask mapResultSetToRecurringTask(ResultSet rs) throws SQLException {
        return new recurringTask(
                rs.getInt("RecurringID"),
                rs.getString("Title"),
                rs.getString("Description"),
                recurringTask.recurrenceType.valueOf(rs.getString("RecurrenceType"))
        );
    }
}
