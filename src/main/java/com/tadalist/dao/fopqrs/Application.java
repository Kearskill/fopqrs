package com.tadalist.dao.fopqrs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TaDaList"); //title
        JLabel label = new JLabel("Welcome to TaDaList");

        //main frame
        frame.add(label);
        frame.setSize(800,600);

        //tinkering with menu
        JMenuBar menu = new JMenuBar();
        JMenu home = new JMenu("Home");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem closeItem = new JMenuItem("Close");
        home.add(openItem);
        home.addSeparator();
        home.add(closeItem);
        menu.add(home);

        //adding another menubar
        JMenu aboutMenu = new JMenu("View");
        JMenuItem aboutMe = new JMenuItem("About...");
        JMenuItem aboutSettings = new JMenuItem("Settings");
        aboutMenu.add(aboutMe);
        aboutMenu.addSeparator();
        aboutMenu.add(aboutSettings);
        menu.add(aboutMenu);

        //panel with button
        JPanel panel = new JPanel();


        JButton button = new JButton("Press me");
        JButton addTask = new JButton("Add New Tasks");
        JButton viewAllTasks = new JButton("View All Tasks");
        JButton editTask = new JButton("Edit Task");
        JButton deleteTask = new JButton ("Delete Task by ID");
        JButton viewTaskByID = new JButton ("View Task by ID");
        JButton sortTask = new JButton ("Sort Tasks");
        JButton searchTask = new JButton("Search Task");
        JButton manageRecurringTask = new JButton("Manage recurring Task");
        JButton addTaskDependencies = new JButton("Adding Task dependencies");
        JButton markTaskAsComplete = new JButton("Mark Task as complete");
        JButton displayTaskCompletionRate = new JButton("Display Task Completion Rate");
        JButton vectorSearch = new JButton("Vector search for tasks");

        panel.add(button);
        panel.add(addTask);
        panel.add(viewAllTasks);
        panel.add(editTask);
        panel.add(deleteTask);
        panel.add(viewTaskByID);
        panel.add(sortTask);
        panel.add(searchTask);
        panel.add(manageRecurringTask);
        panel.add(addTaskDependencies);
        panel.add(markTaskAsComplete);
        panel.add(displayTaskCompletionRate);
        panel.add(vectorSearch);

        //add some action to the button
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(frame, "Button Clicked!");
            }
        });

        // Set button font and colors
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(76, 175, 80)); // Green color
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createRaisedBevelBorder()); // Add button border

        //Create another panel with text
        JPanel textPanel = new JPanel();
        JLabel textLabel = new JLabel("App made from Team fopqrs!");
        textPanel.add(textLabel);

        //put the layout
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(menu);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.SOUTH);

        //exit program peacefully
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}