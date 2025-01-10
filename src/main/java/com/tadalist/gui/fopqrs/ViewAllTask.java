package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.TaskDAO;
import com.tadalist.dao.fopqrs.Tasks;
import com.tadalist.dao.fopqrs.TaskDependencyDAO;
import com.tadalist.dao.fopqrs.TaskDependency;
import com.tadalist.dao.fopqrs.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ViewAllTask extends JPanel {
    private String dates[]
            = {"01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31"};
    private String months[]
            = {"Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sept", "Oct", "Nov", "Dec"};

    private String years[]
            = {"2020", "2021", "2022", "2023",
            "2024", "2025", "2026", "2027",
            "2028", "2029", "2030", "2031",
            "2032", "2033", "2034", "2035"};


    private JPanel taskPanel, dueDate;
    private JScrollPane scrollPane;
    private JTextField searchField;
    private List<TaskItem> allTasks = new ArrayList<>();
    private JComboBox date, month, year;

    public ViewAllTask() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("View All Tasks", SwingConstants.CENTER);
        title.setFont(new Font("Roboto", Font.BOLD, 24));
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
        editorFrame.setSize(500, 400);
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components

        // ** Task ID Label (Added) **
        JLabel taskIdLabel = new JLabel("Task ID: " + task.id);
        taskIdLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        addComponentToFrame(editorFrame, taskIdLabel, gbc, 0, 0, 2, 1);

        // Title Field
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(task.title, 20);
        addComponentToFrame(editorFrame, titleLabel, gbc, 0, 1, 1, 1);
        addComponentToFrame(editorFrame, titleField, gbc, 1, 1, 2, 1);

        // Description Field
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea(task.description, 5, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        addComponentToFrame(editorFrame, descriptionLabel, gbc, 0, 2, 1, 1);
        addComponentToFrame(editorFrame, descriptionScroll, gbc, 1, 2, 2, 1);

        // Due Date Field
        // Init
        date = new JComboBox<>(dates);
        month = new JComboBox<>(months);
        year = new JComboBox<>(years);

        HashMap<String, String> monthConversionReversed = new HashMap<String, String>();
        monthConversionReversed.put("01", "Jan");
        monthConversionReversed.put("02", "Feb");
        monthConversionReversed.put("03", "Mar");
        monthConversionReversed.put("04", "Apr");
        monthConversionReversed.put("05", "May");
        monthConversionReversed.put("06", "Jun");
        monthConversionReversed.put("07", "July");
        monthConversionReversed.put("08", "Aug");
        monthConversionReversed.put("09", "Sept");
        monthConversionReversed.put("10", "Oct");
        monthConversionReversed.put("11", "Nov");
        monthConversionReversed.put("12", "Dec");

        String[] a = task.dueDate.split("-");
        System.out.println(task.dueDate);
        System.out.println("a[0] contains: '" + a[0] + "' (length: " + a[0].length() + ")");
        System.out.println(a[1]);
        System.out.println(a[2]);
        System.out.println("Parsed date: " + Arrays.toString(a));  // debug



// Ensure month is mapped correctly
        String monthStr = monthConversionReversed.get(a[1]);

        // Setting the year
        String yearFromDb = a[0].trim();  // "2035"
        System.out.println("Year from DB: " + yearFromDb);  // Log the year value




        HashMap<String, String> monthConversion = new HashMap<String, String>();
        monthConversion.put("Jan", "01");
        monthConversion.put("Feb", "02");
        monthConversion.put("Mar", "03");
        monthConversion.put("Apr", "04");
        monthConversion.put("May", "05");
        monthConversion.put("Jun", "06");
        monthConversion.put("July", "07");
        monthConversion.put("Aug", "08");
        monthConversion.put("Sept", "09");
        monthConversion.put("Oct", "10");
        monthConversion.put("Nov", "11");
        monthConversion.put("Dec", "12");




        JLabel dueDateLabel = new JLabel("Due Date:");
        addComponentToFrame(editorFrame, dueDateLabel, gbc, 0, 3, 1, 1);

        date = new JComboBox(dates);
        date.setFont(new Font("Roboto", Font.PLAIN, 15));
        date.setSize(50, 20);
        date.setLocation(200, 300);
        addComponentToFrame(editorFrame, date, gbc, 1, 3, 1, 1);

        // Ensure day is always in two-digit format
        String day = Integer.parseInt(a[2]) < 10 ? "0" + Integer.parseInt(a[2]) : a[2];
        date.setSelectedItem(day);

        month = new JComboBox(months);
        month.setFont(new Font("Roboto", Font.PLAIN, 15));
        month.setSize(60, 20);
        month.setLocation(250, 300);
        addComponentToFrame(editorFrame, month, gbc, 2, 3, 1, 1);

        if (monthStr != null) {
            month.setSelectedItem(monthStr);
        } else {
            System.out.println("Invalid month from DB: " + a[1]);
        }
        month.setSelectedItem(monthConversionReversed.get(a[1]));



        year = new JComboBox(years);
        year.setFont(new Font("Roboto", Font.PLAIN, 15));
        year.setSize(60, 20);
        year.setLocation(320, 300);
        addComponentToFrame(editorFrame, year, gbc, 3, 3, 1, 1);

        boolean yearFound = false;
        for (int i = 0; i < year.getItemCount(); i++) {
            System.out.println("Year in JComboBox: " + year.getItemAt(i));  // Log each year item
            if (year.getItemAt(i).equals(yearFromDb)) {
                year.setSelectedIndex(i);  // Select the year if found
                yearFound = true;
                break;
            }
        }

        if (!yearFound) {
            System.out.println("Year " + yearFromDb + " not found in JComboBox.");
        } else {
            System.out.println("Year " + yearFromDb + " successfully selected.");
        }



        // Priority Field
        JLabel priorityLabel = new JLabel("Priority:");
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});
        addComponentToFrame(editorFrame, priorityLabel, gbc, 0, 4, 1, 1);
        addComponentToFrame(editorFrame, priorityBox, gbc, 1, 4, 2, 1);
        priorityBox.setSelectedItem(task.priority);

        // Status Field
        JLabel statusLabel = new JLabel("Status:");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"PENDING", "IN PROGRESS", "COMPLETED"});
        addComponentToFrame(editorFrame, statusLabel, gbc, 0, 5, 1, 1);
        addComponentToFrame(editorFrame, statusBox, gbc, 1, 5, 2, 1);
        statusBox.setSelectedItem(task.status);

// Check for dependencies before allowing completion
        // Check for dependencies before allowing completion
        if (statusBox.getSelectedItem().equals("COMPLETED")) {
            try (Connection conn = dbConnection.getConnection());
                 TaskDependencyDAO dependencyDAO = new TaskDependencyDAO(conn)) {

                List<TaskDependency> dependencies = dependencyDAO.getTaskDependenciesByTaskId(task.id);
                for (TaskDependency dependency : dependencies) {
                    int dependentTaskId = dependency.getDependentTaskId();
                    Tasks dependentTask = TaskDAO.getTaskById(dependentTaskId);
                    if (dependentTask != null && dependentTask.getStatus().equals(Tasks.Status.PENDING)) {
                        JOptionPane.showMessageDialog(null, "Cannot mark task as completed because dependent tasks are still pending: " + dependentTaskId, "Dependency Error", JOptionPane.ERROR_MESSAGE);
                        return; // Prevents saving if dependencies are pending
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error checking task dependencies.", "Database Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }




        // Recurring Checkbox
        JLabel recurringLabel = new JLabel("Recurring:");
        JCheckBox isRecurringBox = new JCheckBox("", task.isRecurring);
        addComponentToFrame(editorFrame, recurringLabel, gbc, 0, 6, 1, 1);
        addComponentToFrame(editorFrame, isRecurringBox, gbc, 1, 6, 1, 1);

        // Category Field
        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"WORK", "PERSONAL", "HOMEWORK"});
        addComponentToFrame(editorFrame, categoryLabel, gbc, 0, 7, 1, 1);
        addComponentToFrame(editorFrame, categoryBox, gbc, 1, 7, 2, 1);
        categoryBox.setSelectedItem(task.category);

        // Buttons (Save and Delete)
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String daySQL = (String) date.getSelectedItem();
            if (daySQL.length() == 1) {
                daySQL = "0" + daySQL;  // Ensure it is two digits
            }
            String monthSQL= monthConversion.get(month.getSelectedItem());

            if (monthSQL == null) {
                System.out.println("Invalid month selection: " + month.getSelectedItem());
                return;  // Prevents further execution if month conversion fails
            }

            String dueDateSQL = year.getSelectedItem() + "-" + monthSQL + "-" + daySQL;

            try {
                Date dueDate = Date.valueOf(dueDateSQL);
                System.out.println(dueDate);
                System.out.println(a[0]);
                System.out.println(a[1]);
                System.out.println(a[2]);


                updateTaskInDatabase(task.id, titleField.getText(), descriptionArea.getText(), dueDate,
                        (String) priorityBox.getSelectedItem(), (String) statusBox.getSelectedItem(),
                        isRecurringBox.isSelected(), (String) categoryBox.getSelectedItem());

                System.out.println(dueDate);
                editorFrame.dispose();
                reloadTasks();
            } catch(IllegalArgumentException err){
                JOptionPane.showMessageDialog(null, "Invalid date format: " + dueDateSQL, "Error", JOptionPane.ERROR_MESSAGE);
                err.printStackTrace();
            }

        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(editorFrame, "Are you sure you want to delete this task?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    System.out.println(task.id);
                    TaskDAO.deleteTasks(task.id);
                } catch (SQLException err) {
                    if (err.getMessage().contains("Cannot delete or update a parent row: a foreign key constraint fails")) {
                        JOptionPane.showMessageDialog(null, "Task is dependent on other tasks and cannot be deleted.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Handle other SQL exceptions
                        System.out.println("Error deleting task: " + err.getMessage());
                    }
                }
                editorFrame.dispose();
                reloadTasks();
            }
        });

        // Layout for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        addComponentToFrame(editorFrame, buttonPanel, gbc, 0, 8, 3, 1);

        editorFrame.setVisible(true);
    }


    // Helper method to add components to frame
    private void addComponentToFrame(JFrame frame, JComponent component, GridBagConstraints gbc, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.anchor = GridBagConstraints.WEST;  // Choose anchor as WEST or CENTER based on requirement
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure horizontal fill for text fields and buttons
        frame.add(component, gbc);
    }


    // Helper method to add components to frame
    private void addComponentToFrame(JFrame frame, JComponent component, GridBagConstraints gbc, int x, int y, int width, int height, int anchor) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.anchor = anchor; // Correct anchor values
        gbc.fill = GridBagConstraints.HORIZONTAL; // To ensure horizontal fill for most components
        frame.add(component, gbc);
    }


    private void updateTaskInDatabase(int taskId, String title, String description, Date dueDate,
                                      String priority, String status, boolean isRecurring, String category) {


        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE tasks SET Title=?, Description=?, DueDate=?, Priority=?, Status=?, IsRecurring=?, Category=?, UpdatedAt=NOW() WHERE TaskID=?")) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDate(3, dueDate);
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