package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.TaskDAO;
import com.tadalist.dao.fopqrs.Tasks;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DisplayTaskCompletionRate extends JPanel {
    private JLabel completionLabel;
    private JProgressBar completionProgress;
    private JButton refreshButton;

    public DisplayTaskCompletionRate() {
        // Set layout
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel("Task Completion Rate", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Completion Label
        completionLabel = new JLabel("Loading...", SwingConstants.CENTER);
        completionLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

        // Progress Bar
        completionProgress = new JProgressBar(0, 100);
        completionProgress.setStringPainted(true);
        completionProgress.setValue(0);

        // Panel for Label + Progress Bar
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.add(completionLabel);
        centerPanel.add(completionProgress);
        add(centerPanel, BorderLayout.CENTER);

        // Refresh Button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateCompletionRate());

        // Add button to panel
        add(refreshButton, BorderLayout.SOUTH);

        // Initial Load
        updateCompletionRate();
    }

    private void updateCompletionRate() {
        try {
            List<Tasks> taskList = TaskDAO.getAllTasks();  // Fetch all tasks

            if (taskList == null || taskList.isEmpty()) {
                completionLabel.setText("No tasks found.");
                completionProgress.setValue(0);
                return;
            }

            int totalTasks = taskList.size();
            int completedTasks = 0;

            for (Tasks task : taskList) {
                if (task.getStatus() == Tasks.Status.COMPLETED) {
                    completedTasks++;
                }
            }

            double completionRate = ((double) completedTasks / totalTasks) * 100;

            // Update UI Components
            completionLabel.setText(String.format("Completion Rate: %.2f%%", completionRate));
            completionProgress.setValue((int) completionRate);
            completionProgress.setBackground(Color.WHITE);

        } catch (SQLException e) {
            completionLabel.setText("Error fetching data.");
            e.printStackTrace();
        }
    }
}
