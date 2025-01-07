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
        mainPanel.add(viewAllTasks, "Page 2");
        mainPanel.add(editTask, "Page 3");
        mainPanel.add(deleteTask, "Page 4");
        mainPanel.add(viewTaskByID, "Page 5");
        mainPanel.add(sortTask, "Page 6");
        mainPanel.add(searchTask, "Page 7");
        mainPanel.add(manageRecurringTask, "Page 8");
        mainPanel.add(addTaskDependencies,"Page 9");
        mainPanel.add(markTaskAsComplete,"Page 10");
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
        JButton viewAllTask = new JButton("View All Tasks");
        JButton editTask = new JButton("Edit Task");
        JButton deleteTask = new JButton("Delete Task");
        JButton viewTaskByID = new JButton("View Task by ID");
        JButton sortTask = new JButton("Sort Task");
        JButton searchTask = new JButton("Search Task");
        JButton manageRecurringTask = new JButton("Manage recurring task");
        JButton addTaskDependencies = new JButton("Add Task Dependencies");
        JButton markTaskAsComplete = new JButton("Mark Task as complete");
        JButton displayTaskCompletionRate = new JButton("Display Task Completion Rate");
        JButton vectorSearch = new JButton("Vector Search for tasks");

        addTask.setPreferredSize(new Dimension(80, 20));
        viewAllTask.setPreferredSize(new Dimension(80, 20));
        editTask.setPreferredSize(new Dimension(80, 20));
        deleteTask.setPreferredSize(new Dimension(80, 20));
        viewTaskByID.setPreferredSize(new Dimension(80, 20));
        sortTask.setPreferredSize(new Dimension(80, 20));
        searchTask.setPreferredSize(new Dimension(80, 20));
        manageRecurringTask.setPreferredSize(new Dimension(80, 20));
        addTaskDependencies.setPreferredSize(new Dimension(80, 20));
        markTaskAsComplete.setPreferredSize(new Dimension(80, 20));
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

        editTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 3");
            }
        });

        deleteTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 4");
            }
        });

        viewTaskByID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 5");
            }
        });

        sortTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 6");
            }
        });

        searchTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 7");
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

        markTaskAsComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Page 10");
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
        menuPanel.add(editTask);
        menuPanel.add(deleteTask);
        menuPanel.add(viewTaskByID);
        menuPanel.add(sortTask);
        menuPanel.add(searchTask);
        menuPanel.add(manageRecurringTask);
        menuPanel.add(addTaskDependencies);
        menuPanel.add(markTaskAsComplete);
        menuPanel.add(displayTaskCompletionRate);
        menuPanel.add(vectorSearch);

        return menuPanel;
    }
}
