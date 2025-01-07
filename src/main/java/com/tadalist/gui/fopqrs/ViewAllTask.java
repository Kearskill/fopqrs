package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAllTask extends JPanel {
    private JPanel taskPanel;

    public ViewAllTask() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("View All Tasks", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Panel to hold task icons
        taskPanel = new JPanel();
        taskPanel.setLayout(new GridLayout(3, 4, 10, 10)); // 3 rows, 4 columns with spacing

        loadTasks();

        // Make scrollable
        JScrollPane scrollPane = new JScrollPane(taskPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        taskPanel.removeAll();

        // Database connection and fetching tasks
        try {
            Connection conn = dbConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");

            while (rs.next()) {
                String taskName = rs.getString("Title");

                // Assign icon based on task type
                String iconPath = "default_icon.svg";

                JButton taskButton = new JButton(taskName, new ImageIcon(iconPath));
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

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        taskPanel.revalidate();
        taskPanel.repaint();
    }

    private void reloadTasks() {
        loadTasks();
    }
}
