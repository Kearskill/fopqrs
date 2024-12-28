package com.tadalist.dao.fopqrs;

import java.util.Map;
import java.util.HashMap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class recurringTaskDAO {
    //adding task
    public static void addRecurringTask(recurringTask recurringTask) throws SQLException{
        String validateTask = "select TaskID from Tasks where TaskID = ?";
        String sql = "insert into recurringtask (TaskID,RecurrenceType,NextDueDate," +
                "RecurrenceeEnd,RecurrenceFrequency,Reminder) values (?,?,?,?,?,?)";

        try (Connection conn = dbConnection.getConnection()) {
            // Validate TaskID
            try (PreparedStatement validateStmt = conn.prepareStatement(validateTask)) {
                validateStmt.setInt(1, recurringTask.getTaskID());
                try (ResultSet rs = validateStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Task with ID " + recurringTask.getTaskID() + " does not exist.");
                    }
                }
            }
        }

        try(Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, recurringTask.getTaskID());
            stmt.setString(2, recurringTask.getRecurrenceType().name());
            stmt.setDate(3, recurringTask.getNextDueDate());
            stmt.setDate(4, recurringTask.getRecurrenceEnd());
            stmt.setInt(5, recurringTask.getRecurrenceFrequency());
            stmt.setDate(6,recurringTask.getReminder());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    recurringTask.setRecurringID(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static void deleteRecurringTask(int recurringID) throws SQLException {
        String sql = "DELETE FROM recurringtask WHERE RecurringID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recurringID);
            stmt.executeUpdate();
        }
    }
    private static recurringTask mapResultSetToRecurringTask(ResultSet rs) throws SQLException {
        return new recurringTask(
                rs.getInt("RecurringID"),
                rs.getInt("TaskID"), // Foreign key reference to tasks
                recurringTask.recurrenceType.valueOf(rs.getString("RecurrenceType")),
                rs.getDate("NextDueDate"),
                rs.getDate("RecurrenceEnd"),
                rs.getInt("RecurrenceFrequency"),
                rs.getDate("Reminder")
        );
    }


    public static void updateRecurringTask(recurringTask recurringTask) throws SQLException {
        String sql = "UPDATE recurringtask SET TaskID = ?, RecurrenceType = ?, NextDueDate = ?, " +
                "RecurrenceEnd = ?, RecurrenceFrequency = ?, Reminder = ? WHERE RecurringID = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recurringTask.getTaskID()); // TaskID references 'tasks'
            stmt.setString(2, recurringTask.getRecurrenceType().name());
            stmt.setDate(3, recurringTask.getNextDueDate());
            stmt.setDate(4, recurringTask.getRecurrenceEnd());
            stmt.setInt(5, recurringTask.getRecurrenceFrequency());
            stmt.setDate(6,recurringTask.getReminder());
            stmt.setInt(7, recurringTask.getRecurringID());

            stmt.executeUpdate();
        }
    }

    public static List<Map<String, Object>> getRecurringTasksWithDetails() throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String sql = "SELECT rt.RecurringID, t.TaskID, t.Title, t.Description, rt.RecurrenceType, " +
                "rt.NextDueDate, rt.RecurrenceEnd, rt.RecurrenceFrequency, rt.reminder " +
                "FROM recurringtask rt " +
                "JOIN tasks t ON rt.TaskID = t.TaskID";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("RecurringID", rs.getInt("RecurringID"));
                row.put("TaskID", rs.getInt("TaskID"));
                row.put("Title", rs.getString("Title"));
                row.put("Description", rs.getString("Description"));
                row.put("RecurrenceType", rs.getString("RecurrenceType"));
                row.put("NextDueDate", rs.getDate("NextDueDate"));
                row.put("RecurrenceEnd", rs.getDate("RecurrenceEnd"));
                row.put("RecurrenceFrequency", rs.getInt("RecurrenceFrequency"));
                row.put("Reminder", rs.getDate("Reminder"));

                resultList.add(row);
            }
        }
        return resultList;
    }

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

}
