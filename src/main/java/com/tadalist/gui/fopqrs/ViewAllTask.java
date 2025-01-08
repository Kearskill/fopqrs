package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewAllTask extends JPanel {
    private JPanel taskPanel;
    private JScrollPane scrollPane;
    private JTextField searchField;
    private List<TaskItem> allTasks = new ArrayList<>();

    public ViewAllTask() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("View All Tasks", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> filterTasks(searchField.getText()));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // Task panel
        taskPanel = new JPanel(new GridLayout(0, 4, 10, 10)); // Dynamic rows, 4 columns
        scrollPane = new JScrollPane(taskPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> reloadTasks());
        add(refreshButton, BorderLayout.SOUTH);

        // Load tasks initially
        loadTasks();
    }

    private void loadTasks() {
        taskPanel.removeAll();
        allTasks.clear();

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tasks")) {

            while (rs.next()) {
                int taskId = rs.getInt("TaskID");
                String taskName = rs.getString("Title");
                String description = rs.getString("Description");
                String dueDate = rs.getString("DueDate");
                String priority = rs.getString("Priority");
                String status = rs.getString("Status");
                String createdAt = rs.getString("CreatedAt");
                String updatedAt = rs.getString("UpdatedAt");
                boolean isRecurring = rs.getBoolean("IsRecurring");
                String category = rs.getString("Category");

                // Create task object
                TaskItem taskItem = new TaskItem(taskId, taskName, description, dueDate, priority, status, createdAt, updatedAt, isRecurring, category);
                allTasks.add(taskItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Display tasks
        updateTaskDisplay(allTasks);
    }

    private void updateTaskDisplay(List<TaskItem> tasks) {
        taskPanel.removeAll();

        for (TaskItem task : tasks) {
            JButton taskButton = new JButton(task.title);
            taskButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            taskButton.setHorizontalTextPosition(SwingConstants.CENTER);
            taskButton.setFocusPainted(false);
            taskButton.setBorder(BorderFactory.createEmptyBorder());

            // Clicking opens editor
            taskButton.addActionListener(e -> openTaskEditor(task));

            taskPanel.add(taskButton);
        }

        taskPanel.revalidate();
        taskPanel.repaint();
    }

    private void reloadTasks() {
        loadTasks();
        searchField.setText("");
    }

    private void filterTasks(String query) {
        List<TaskItem> filteredTasks = new ArrayList<>();
        for (TaskItem task : allTasks) {
            if (task.title.toLowerCase().contains(query.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        updateTaskDisplay(filteredTasks);
    }

    private void openTaskEditor(TaskItem task) {
        JFrame editorFrame = new JFrame("Edit Task - " + task.title);
        editorFrame.setSize(400, 400);
        editorFrame.setLayout(new GridLayout(9, 2, 5, 5));

        JTextField titleField = new JTextField(task.title);
        JTextArea descriptionArea = new JTextArea(task.description);
        JTextField dueDateField = new JTextField(task.dueDate);
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        priorityBox.setSelectedItem(task.priority);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        statusBox.setSelectedItem(task.status);
        JTextField createdAtField = new JTextField(task.createdAt);
        createdAtField.setEditable(false);
        JTextField updatedAtField = new JTextField(task.updatedAt);
        updatedAtField.setEditable(false);
        JCheckBox isRecurringBox = new JCheckBox("Recurring", task.isRecurring);
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Work", "Personal", "Others"});
        categoryBox.setSelectedItem(task.category);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            updateTaskInDatabase(task.id, titleField.getText(), descriptionArea.getText(), dueDateField.getText(),
                    (String) priorityBox.getSelectedItem(), (String) statusBox.getSelectedItem(),
                    isRecurringBox.isSelected(), (String) categoryBox.getSelectedItem());

            editorFrame.dispose();
            reloadTasks();
        });

        editorFrame.add(new JLabel("Title:"));
        editorFrame.add(titleField);
        editorFrame.add(new JLabel("Description:"));
        editorFrame.add(new JScrollPane(descriptionArea));
        editorFrame.add(new JLabel("Due Date (YYYY-MM-DD):"));
        editorFrame.add(dueDateField);
        editorFrame.add(new JLabel("Priority:"));
        editorFrame.add(priorityBox);
        editorFrame.add(new JLabel("Status:"));
        editorFrame.add(statusBox);
        editorFrame.add(new JLabel("Created At:"));
        editorFrame.add(createdAtField);
        editorFrame.add(new JLabel("Updated At:"));
        editorFrame.add(updatedAtField);
        editorFrame.add(new JLabel("Recurring:"));
        editorFrame.add(isRecurringBox);
        editorFrame.add(new JLabel("Category:"));
        editorFrame.add(categoryBox);
        editorFrame.add(saveButton);

        editorFrame.setVisible(true);
    }

    private void updateTaskInDatabase(int taskId, String title, String description, String dueDate,
                                      String priority, String status, boolean isRecurring, String category) {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE tasks SET Title=?, Description=?, DueDate=?, Priority=?, Status=?, IsRecurring=?, Category=?, UpdatedAt=NOW() WHERE TaskID=?")) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, dueDate);
            stmt.setString(4, priority);
            stmt.setString(5, status);
            stmt.setBoolean(6, isRecurring);
            stmt.setString(7, category);
            stmt.setInt(8, taskId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class TaskItem {
        int id;
        String title, description, dueDate, priority, status, createdAt, updatedAt, category;
        boolean isRecurring;

        public TaskItem(int id, String title, String description, String dueDate, String priority, String status, String createdAt, String updatedAt, boolean isRecurring, String category) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.isRecurring = isRecurring;
            this.category = category;
        }
    }
}
