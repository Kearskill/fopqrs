package com.tadalist.gui.fopqrs;
import javax.naming.directory.SearchControls;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    private static CardLayout cardLayout;
    private static JPanel mainPanel;  // This panel will hold other panels (pages)

    public static void main(String[] args) {
        JFrame frame = new JFrame("TaDaList"); //title
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Initialize the application
                initialize();
            }
        });
    }

    public static void initialize() {
        JFrame frame = new JFrame("TaDaList");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel and set its layout to CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create instances of page classes
        JPanel addTask = new AddTask();  // Page1 instance
        JPanel viewAllTasks = new ViewAllTask();  // Page2 instance
        JPanel editTask = new EditTask();
        JPanel deleteTask = new DeleteTask();
        JPanel viewTaskByID = new ViewTaskByID();
        JPanel sortTask = new SortTask();
        JPanel searchTask = new SearchTask();
        JPanel manageRecurringTask = new ManageRecurringTask();
        JPanel addTaskDependencies = new AddTaskDependencies();
        JPanel markTaskAsComplete = new MarkTaskAsComplete();
        JPanel displayTaskCompletionRate = new DisplayTaskCompletionRate();
        JPanel vectorSearch = new VectorSearch();

        // Add pages to the main panel
        mainPanel.add(addTask, "Page 1");
        mainPanel.add(viewAllTasks, "Page 2"); // 2 3 4 5
        mainPanel.add(sortTask, "Page 6");
        mainPanel.add(manageRecurringTask, "Page 8");
        mainPanel.add(addTaskDependencies,"Page 9"); // 10
        mainPanel.add(displayTaskCompletionRate, "Page 11");
        mainPanel.add(vectorSearch, "Page 12");

        // Create the menu panel with buttons
        JPanel menuPanel = createMenuPanel();

        // ADd the scroll panel to
        JScrollPane scrollPanel = new JScrollPane(menuPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPanel.setPreferredSize(new Dimension(1200, 100));

        // Set up the layout of the frame
        frame.setLayout(new BorderLayout());
        frame.add(menuPanel, BorderLayout.NORTH);  // Menu on top
        frame.add(mainPanel, BorderLayout.CENTER);  // Main content in center

        // Make the frame visible
        frame.setVisible(true);
    }

    // Create a menu panel with buttons to navigate between pages
    public static JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Buttons for navigation
        JButton addTask = new JButton("Add New Tasks");
        addTask.setToolTipText("Add new task");
        JButton viewAllTask = new JButton("View All Tasks");
        viewAllTask.setToolTipText("View All Tasks");
        JButton sortTask = new JButton("Sort Task");
        sortTask.setToolTipText("Sort Task");
        JButton manageRecurringTask = new JButton("Manage recurring task");
        manageRecurringTask.setToolTipText("Manage recurring text");
        JButton addTaskDependencies = new JButton("Add Task Dependencies");
        addTaskDependencies.setToolTipText("Add Task Dependencies");
        JButton displayTaskCompletionRate = new JButton("Display Task Completion Rate");
        displayTaskCompletionRate.setToolTipText("Display Task completion rate");
        JButton vectorSearch = new JButton("Vector Search for tasks");
        vectorSearch.setToolTipText("Vector search for tasks");

        addTask.setPreferredSize(new Dimension(80, 20));
        viewAllTask.setPreferredSize(new Dimension(80, 20));
        sortTask.setPreferredSize(new Dimension(80, 20));
        manageRecurringTask.setPreferredSize(new Dimension(80, 20));
        addTaskDependencies.setPreferredSize(new Dimension(80, 20));
        displayTaskCompletionRate.setPreferredSize(new Dimension(80, 20));
        vectorSearch.setPreferredSize(new Dimension(80, 20));

        // Add action listeners to switch between pages
        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 1");
            }
        });

        viewAllTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 2");
            }
        });

        sortTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 6");
            }
        });

        manageRecurringTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 8");
            }
        });

        addTaskDependencies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 9");
            }
        });


        displayTaskCompletionRate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 11");
            }
        });

        vectorSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 12");
            }
        });

        // Add buttons to the menu panel
        menuPanel.add(addTask);
        menuPanel.add(viewAllTask);
        menuPanel.add(sortTask);
        menuPanel.add(manageRecurringTask);
        menuPanel.add(addTaskDependencies);
        menuPanel.add(displayTaskCompletionRate);
        menuPanel.add(vectorSearch);

        return menuPanel;
    }
}
