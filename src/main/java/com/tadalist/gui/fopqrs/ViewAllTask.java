package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
                String UpdatedAt = rs.getString("UpdatedAt");
                boolean isRecurring = rs.getBoolean("IsRecurring");
                String category = rs.getString("Category");

                // Create task object
                TaskItem taskItem = new TaskItem(taskId, taskName, description, status);
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
        editorFrame.setSize(400, 300);
        editorFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JTextField titleField = new JTextField(task.title);
        JTextArea descriptionArea = new JTextArea(task.description);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        statusBox.setSelectedItem(task.status);

        inputPanel.add(titleField);
        inputPanel.add(new JScrollPane(descriptionArea));
        inputPanel.add(statusBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            task.title = titleField.getText();
            task.description = descriptionArea.getText();
            task.status = (String) statusBox.getSelectedItem();
            editorFrame.dispose();
            reloadTasks();
        });

        editorFrame.add(inputPanel, BorderLayout.CENTER);
        editorFrame.add(saveButton, BorderLayout.SOUTH);
        editorFrame.setVisible(true);
    }

    private static class TaskItem {
        int id;
        String title;
        String description;
        String status;

        public TaskItem(int id, String title, String description, String status) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.status = status;
        }
    }
}
