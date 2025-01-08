package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAllTask extends JPanel {
    private JPanel taskPanel;
    private JScrollPane scrollPane;

    public ViewAllTask() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("View All Tasks", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Panel to hold task icons
        taskPanel = new JPanel(new GridLayout(0, 4, 10, 10)); // Dynamic rows, 4 columns

        loadTasks(); // Initial Load

        // Make scrollable
        scrollPane = new JScrollPane(taskPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadTasks();
            }
        });
        add(refreshButton, BorderLayout.SOUTH);
    }

    private void loadTasks() {
        taskPanel.removeAll(); // Clear panel before loading new tasks

        // Database connection and fetching tasks
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tasks")) {

            while (rs.next()) {
                String taskName = rs.getString("Title");

                // Assign icon based on task type
                String iconPath = "default_icon.svg";
                ImageIcon icon = new ImageIcon(iconPath);

                if (icon.getIconWidth() <= 0) { // If the image file is not found
                    icon = new ImageIcon(new byte[0]); // Use an empty icon
                }

                JButton taskButton = new JButton(taskName, icon);
                taskButton.setVerticalTextPosition(SwingConstants.BOTTOM);
                taskButton.setHorizontalTextPosition(SwingConstants.CENTER);
                taskButton.setFocusPainted(false);
                taskButton.setBorder(BorderFactory.createEmptyBorder());

                // Add click event
                taskButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "You clicked on " + taskName);
                    }
                });

                taskPanel.add(taskButton);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ensure UI updates correctly
        taskPanel.revalidate();
        taskPanel.repaint();
    }

    private void reloadTasks() {
        loadTasks(); // Re-fetch and update tasks
    }
}
