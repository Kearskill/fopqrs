package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.TaskDependency;
import com.tadalist.dao.fopqrs.TaskDependencyDAO;
import com.tadalist.dao.fopqrs.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AddTaskDependencies extends JPanel {
    private JTextField taskIdField, dependentTaskIdField;
    private JButton addButton;
    private JLabel statusLabel;

    public AddTaskDependencies() {
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel taskIdLabel = new JLabel("Task ID:");
        taskIdField = new JTextField();

        JLabel dependentTaskIdLabel = new JLabel("Dependent Task ID:");
        dependentTaskIdField = new JTextField();

        addButton = new JButton("Add Dependency");
        statusLabel = new JLabel("");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTaskDependency();
            }
        });

        add(taskIdLabel);
        add(taskIdField);
        add(dependentTaskIdLabel);
        add(dependentTaskIdField);
        add(addButton);
        add(statusLabel);
    }

    private void addTaskDependency() {
        try {
            int taskId = Integer.parseInt(taskIdField.getText());
            int dependentTaskId = Integer.parseInt(dependentTaskIdField.getText());

            Connection connection = dbConnection.getConnection();
            TaskDependencyDAO dao = new TaskDependencyDAO(connection);
            TaskDependency dependency = new TaskDependency(0, taskId, dependentTaskId);
            dao.addTaskDependency(dependency);

            statusLabel.setText("Dependency added successfully!");
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input! Enter numeric values.");
        } catch (SQLException ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }
}
