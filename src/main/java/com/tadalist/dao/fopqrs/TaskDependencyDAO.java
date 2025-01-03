//taskdependencydao//
package com.tadalist.dao.fopqrs;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDependencyDAO {

    private final Connection connection;

    public TaskDependencyDAO(Connection connection) {
        this.connection = connection;
    }

    public void addTaskDependency(TaskDependency dependency) throws SQLException {
        String query = "INSERT INTO taskdependencies (TaskID, DependentTaskID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, dependency.getTaskId());
            stmt.setInt(2, dependency.getDependentTaskId());
            stmt.executeUpdate();
        }
    }

    public List<TaskDependency> getTaskDependenciesByTaskId(int taskId) throws SQLException {
        List<TaskDependency> dependencies = new ArrayList<>();
        String query = "SELECT * FROM taskdependencies WHERE TaskID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dependencies.add(mapRowToTaskDependency(rs));
            }
        }
        return dependencies;
    }

    public void deleteTaskDependency(int dependencyId) throws SQLException {
        String query = "DELETE FROM taskdependencies WHERE DependenciesID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, dependencyId);
            stmt.executeUpdate();
        }
    }

    private TaskDependency mapRowToTaskDependency(ResultSet rs) throws SQLException {
        return new TaskDependency(
                rs.getInt("DependenciesID"),
                rs.getInt("TaskID"),
                rs.getInt("DependentTaskID")
        );
    }
}