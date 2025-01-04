package com.tadalist.dao.fopqrs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//PreparedStatement -> for inserting values in database
public class TaskDAO {
    public static void addTask(Tasks Tasks) throws SQLException {
        String sql = "INSERT INTO tasks(Title, Description, DueDate, Priority, Status, CreatedAt," +
                "UpdatedAt, IsRecurring, ParentTaskID, StreakCount, Category) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = dbConnection.getConnection()) {
            // ParentTaskID validation
            if (Tasks.getParentTaskID() != 0) { // Assume 0 means no ParentTaskID
                String checkParentSql = "SELECT TaskID FROM tasks WHERE TaskID = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkParentSql)) {
                    checkStmt.setInt(1, Tasks.getParentTaskID());
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Parent Task with ID " + Tasks.getParentTaskID() + " does not exist.");
                        }
                    }
                }
            }

            // INSERT the task
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, Tasks.getTitle());
                stmt.setString(2, Tasks.getDescription());
                stmt.setDate(3, Tasks.getDueDate());
                stmt.setString(4, Tasks.getPriority().name());
                stmt.setString(5, Tasks.getStatus().name());
                stmt.setTimestamp(6, Tasks.getCreatedAt());
                stmt.setTimestamp(7, Tasks.getUpdatedAt());
                stmt.setShort(8, Tasks.getIsRecurring());

                // Handle NULL ParentTaskID
                if (Tasks.getParentTaskID() != 0) {
                    stmt.setInt(9, Tasks.getParentTaskID());
                } else {
                    stmt.setNull(9, java.sql.Types.INTEGER);
                }

                stmt.setInt(10, Tasks.getStreakCount());
                stmt.setString(11, Tasks.getCategory().name());

                stmt.executeUpdate();

                // Retrieve Generated Keys
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Tasks.setTaskId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }


    public static void editTask(Tasks Tasks) throws SQLException{
        String sql = "UPDATE tasks SET Title=?, Description=?, DueDate=?, Priority=?, Status=?," +
                "CreatedAt=?,UpdatedAt=?, IsRecurring=?, ParentTaskID=?, StreakCount=?, Category=? WHERE TaskId=?";
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
            stmt.setString(11,Tasks.getCategory().name());
            stmt.setInt(12, Tasks.getTaskId());
            stmt.executeUpdate();

        }
    }

    public static void deleteTasks(int TaskId) throws SQLException{
        String sql = "delete from tasks where TaskID = ?";

        try(Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,TaskId);
            stmt.executeUpdate();
        }
    }
    public static List<Tasks> searchTasksByKeyword(String keyword) throws SQLException {
        List<Tasks> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE title LIKE ? OR description LIKE ?";
        try (Connection conn = dbConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)) {
            String keywordPattern = "%" + keyword + "%";
            stmt.setString(1, keywordPattern);
            stmt.setString(2, keywordPattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapRowToTask(rs));
            }
        }
        return tasks;
    }
    private static Tasks mapRowToTask(ResultSet rs) throws SQLException {
        return new Tasks(
                rs.getInt("TaskID"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getDate("DueDate"),
                Tasks.Priority.valueOf(rs.getString("Priority").toUpperCase()),
                Tasks.Status.valueOf(rs.getString("Status").toUpperCase()),
                rs.getTimestamp("CreatedAt"),
                rs.getTimestamp("UpdatedAt"),
                rs.getShort("IsRecurring"),
                rs.getInt("ParentTaskID"),
                rs.getInt("StreakCount"),
                Tasks.Category.valueOf(rs.getString("Category").toUpperCase())
        );
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
                Tasks.Category.valueOf(rs.getString("Category"))
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
    //Sort Task
    public static List<Tasks> getTasksSorted(String sortBy, boolean ascending) throws SQLException{
        List<String> allowedSortColumns = List.of("DueDate", "Priority", "Category");
        if (!allowedSortColumns.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sorting column: " + sortBy);
        }
        List<Tasks> taskList = new ArrayList<>();
        String order = ascending ? "asc":"desc";
        String query = "select * from Tasks order by DueDate " + order;

        try(Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                taskList.add(mapResultSetToTask(rs));
            }
        }
        return taskList;
    }

}


