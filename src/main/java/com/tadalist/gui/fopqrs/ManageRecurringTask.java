package com.tadalist.gui.fopqrs;
import com.tadalist.dao.fopqrs.recurringTask;
import com.tadalist.dao.fopqrs.recurringTaskDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageRecurringTask extends JPanel {
    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JTextArea taskArea;

    public ManageRecurringTask() {
        // Set layout for the panel
        this.setLayout(new BorderLayout());

        // Create a title label
        JLabel label = new JLabel("Manage Recurring Tasks", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(label, BorderLayout.NORTH);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Add buttons for Add, Delete, and Edit
        addButton = new JButton("Add Recurring Task");
        deleteButton = new JButton("Delete Recurring Task");
        editButton = new JButton("Edit Recurring Task");

        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Create a text area to display tasks (just for illustration)
        taskArea = new JTextArea(10, 40);
        taskArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskArea);
        this.add(scrollPane, BorderLayout.CENTER);

        // Action listeners for the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddTaskDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteTaskDialog();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditTaskDialog();
            }
        });
    }

    // Method to show the Add Task Dialog
    private void showAddTaskDialog() {
        JTextField titleField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JComboBox<String> recurrenceTypeCombo = new JComboBox<>(new String[]{"DAILY", "WEEKLY", "MONTHLY"});
        Object[] message = {
                "Task Title:", titleField,
                "Task Description:", descriptionField,
                "Recurrence Type:", recurrenceTypeCombo
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Recurring Task", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String recurrenceType = (String) recurrenceTypeCombo.getSelectedItem();

                recurringTask task = new recurringTask(0, title, description, recurringTask.recurrenceType.valueOf(recurrenceType.toUpperCase()));
                recurringTaskDAO.addRecurringTask(task);

                JOptionPane.showMessageDialog(null, "Recurring task successfully added with Task ID of " + task.getRecurringID());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error adding recurring task: " + e.getMessage());
            }
        }
    }

    // Method to show the Delete Task Dialog
    private void showDeleteTaskDialog() {
        String recurringIDString = JOptionPane.showInputDialog("Enter Task ID to delete:");

        if (recurringIDString != null && !recurringIDString.isEmpty()) {
            try {
                int recurringID = Integer.parseInt(recurringIDString);
                recurringTaskDAO.deleteRecurringTask(recurringID);
                JOptionPane.showMessageDialog(null, "Recurring Task deleted successfully!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting task: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Task ID.");
            }
        }
    }

    // Method to show the Edit Task Dialog
    private void showEditTaskDialog() {
        String recurringIDString = JOptionPane.showInputDialog("Enter Task ID to edit:");

        if (recurringIDString != null && !recurringIDString.isEmpty()) {
            try {
                int recurringID = Integer.parseInt(recurringIDString);
                recurringTask task = recurringTaskDAO.getRecurringTaskById(recurringID);

                if (task != null) {
                    JTextField titleField = new JTextField(task.getTitle(), 20);
                    JTextField descriptionField = new JTextField(task.getDescription(), 20);
                    JComboBox<String> recurrenceTypeCombo = new JComboBox<>(new String[]{"DAILY", "WEEKLY", "MONTHLY"});
                    recurrenceTypeCombo.setSelectedItem(task.getRecurrenceType().toString());

                    Object[] message = {
                            "Task Title:", titleField,
                            "Task Description:", descriptionField,
                            "Recurrence Type:", recurrenceTypeCombo
                    };

                    int option = JOptionPane.showConfirmDialog(null, message, "Edit Recurring Task", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String title = titleField.getText();
                        String description = descriptionField.getText();
                        String recurrenceType = (String) recurrenceTypeCombo.getSelectedItem();

                        task.setTitle(title);
                        task.setDescription(description);
                        task.setRecurrenceType(recurringTask.recurrenceType.valueOf(recurrenceType.toUpperCase()));

                        recurringTaskDAO.updateRecurringTask(task);
                        JOptionPane.showMessageDialog(null, "Recurring Task updated successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Task not found.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error editing task: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Task ID.");
            }
        }
    }

    // Method to update the task display area (just for testing purposes)
    public void updateTaskArea(String tasks) {
        taskArea.setText(tasks);
    }
}
