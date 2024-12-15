package com.tadalist.dao.fopqrs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//PreparedStatement -> for inserting values in database
public class TaskDAO {
    public static void addTask(Tasks Tasks) throws SQLException{
        String sql = "insert into tasks(Title,Description,DueDate,Priority,Status,CreatedAt," +
                "UpdatedAt,IsRecurring,ParentTaskID,StreakCount,CreatedByUser,AssignedToUser) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1,Tasks.getTitle());
                stmt.setString(2,Tasks.getDescription());
                stmt.setDate(3,Tasks.getDueDate());
                stmt.setString(4,Tasks.getPriority().name());
                stmt.setString(5,Tasks.getStatus().name());
                stmt.setTimestamp(6,Tasks.getCreatedAt());
                stmt.setTimestamp(7,Tasks.getUpdatedAt());
                stmt.setShort(8,Tasks.getIsRecurring());
                stmt.setInt(9,Tasks.getParentTaskID());
                stmt.setInt(10,Tasks.getStreakCount());
                stmt.setInt(11,Tasks.getCreatedByUser());
                stmt.setInt(12,Tasks.getAssignedToUser());


                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Tasks.setTaskId(generatedKeys.getInt(1));
                    }
                }

        }
    }

    public static void editTask(Tasks Tasks) throws SQLException{
        String sql = "UPDATE tasks SET Title=?, Description=?, DueDate=?, Priority=?, Status=?," +
                "UpdatedAt=?, IsRecurring=?, ParentTaskID=?, StreakCount=?, CreatedByUser=?" +
                "AssignedToUser=? WHERE TaskId=?";
        try(Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1,Tasks.getTitle());
            stmt.setString(2,Tasks.getDescription());
            stmt.setDate(3,Tasks.getDueDate());
            stmt.setString(4,Tasks.getPriority().name());
            stmt.setString(5,Tasks.getStatus().name());
            stmt.setTimestamp(6,Tasks.getCreatedAt());
            stmt.setTimestamp(7,Tasks.getUpdatedAt());
            stmt.setShort(8,Tasks.getIsRecurring());
            stmt.setInt(9,Tasks.getParentTaskID());
            stmt.setInt(10,Tasks.getStreakCount());
            stmt.setInt(11,Tasks.getCreatedByUser());
            stmt.setInt(12,Tasks.getAssignedToUser());


            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Tasks.setTaskId(generatedKeys.getInt(1));
                }
            }

        }
    }

    public static void deleteTasks(int TaskId) throws SQLException{
        String sql = "delete from tasks where TaskID = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,TaskId);
            stmt.executeUpdate();
        }
    }

    private static Tasks mapResultSetToTask(ResultSet rs) throws SQLException {
        return new Tasks(
                rs.getInt("TaskId"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getDate("DueDate"),
                Tasks.Priority.valueOf(rs.getString("Priority")),
                Tasks.Status.valueOf(rs.getString("Status")),
                rs.getTimestamp("CreatedAt"),
                rs.getTimestamp("UpdatedAt"),
                rs.getShort("IsRecurring"),
                rs.getInt("ParentTaskID"),
                rs.getInt("StreakCount"),
                rs.getInt("CreatedByUser"),
                rs.getInt("AssignedToUser")
        );
    }

    // Get Tasks by Status
    public static List<Tasks> getTasksByStatus(Tasks.Status status) throws SQLException {
        List<Tasks> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE Status = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    taskList.add(mapResultSetToTask(rs));
                }
            }
        }
        return taskList;
    }

    public static List<Tasks> getTasksByUser(int userId) throws SQLException {
        List<Tasks> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE CreatedByUser = ? OR AssignedToUser = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    taskList.add(mapResultSetToTask(rs));
                }
            }
        }
        return taskList;
        }

    // Get Task by ID
    public static Tasks getTaskById(int taskId) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE TaskId = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taskId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTask(rs);
                }
            }
        }
        return null;
    }

    // Get All Tasks
    public static List<Tasks> getAllTasks() throws SQLException {
        List<Tasks> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                taskList.add(mapResultSetToTask(rs));
            }
        }
        return taskList;
    }

}


