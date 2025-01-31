package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.EmailUtil;
import com.tadalist.dao.fopqrs.TaskDAO;
import com.tadalist.dao.fopqrs.Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import java.awt.FocusTraversalPolicy;
import java.awt.Component;



public class AddTask extends JPanel implements ActionListener {
    private Container c;
    private JLabel title, header, description, dueDate, priority, category, status, add, res, email;
    private JTextField textFieldTitle, textFieldDescription, textFieldEmail;
    private JRadioButton low, medium, high, homework, personal, work, pending, completed;
    private ButtonGroup groupButtonPriority, groupButtonCategory, groupButtonStatus;
    private JComboBox date, month, year;
    private JTextArea textOut, resadd;
    private JButton submit, reset;

    private String dates[]
            = {"1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
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
            = {"2025", "2026", "2027",
            "2028", "2029", "2030", "2031",
            "2032", "2033", "2034", "2035"};

    public AddTask() {
        startEmailReminderScheduler();
        setLayout(null);

        setBackground(new Color(237, 231, 229));

        // Title
        header = new JLabel("Enter Task Details");
        header.setFont(new Font("Roboto", Font.PLAIN, 30));
        header.setSize(300, 30);
        header.setLocation(300, 30);
        header.setForeground(new Color(0x060E3C));
        add(header);

        // task title
        title = new JLabel("Title");
        title.setFont(new Font("Roboto", Font.PLAIN, 20));
        title.setSize(100, 20);
        title.setLocation(100, 100);
        title.setForeground(new Color(0x0C1D70));
        add(title);

        textFieldTitle = new JTextField();
        textFieldTitle.setFont(new Font("Roboto", Font.PLAIN, 15));
        textFieldTitle.setSize(190, 20);
        textFieldTitle.setLocation(200, 100);
        add(textFieldTitle);

        // description
        description = new JLabel("Description");
        description.setFont(new Font("Roboto", Font.PLAIN, 20));
        description.setSize(100, 20);
        description.setLocation(100, 150);
        description.setForeground(new Color(0x1637CA));
        add(description);

        textFieldDescription = new JTextField();
        textFieldDescription.setFont(new Font("Roboto", Font.PLAIN, 15));
        textFieldDescription.setSize(150, 20);
        textFieldDescription.setLocation(200, 150);
        add(textFieldDescription);

        // Due Date
        dueDate = new JLabel("Due Date");
        dueDate.setFont(new Font("Roboto", Font.PLAIN, 20));
        dueDate.setSize(100, 20);
        dueDate.setLocation(100, 300);
        dueDate.setForeground(new Color(0x2349FF));
        add(dueDate);

        date = new JComboBox(dates);
        date.setFont(new Font("Roboto", Font.PLAIN, 15));
        date.setSize(50, 20);
        date.setLocation(200, 300);
        add(date);

        month = new JComboBox(months);
        month.setFont(new Font("Roboto", Font.PLAIN, 15));
        month.setSize(60, 20);
        month.setLocation(250, 300);
        add(month);

        year = new JComboBox(years);
        year.setFont(new Font("Roboto", Font.PLAIN, 15));
        year.setSize(60, 20);
        year.setLocation(320, 300);
        add(year);


        // Priority
        priority = new JLabel("Priority");
        priority.setFont(new Font("Roboto", Font.PLAIN, 20));
        priority.setSize(100, 20);
        priority.setLocation(100, 200);
        priority.setForeground(new Color(0x4532FF));
        add(priority);

        low = new JRadioButton("Low");
        low.setFont(new Font("Roboto", Font.PLAIN, 15));
        low.setSelected(true); // default selected
        low.setSize(75, 20);
        low.setLocation(200, 200);
        add(low);

        medium = new JRadioButton("Medium");
        medium.setFont(new Font("Roboto", Font.PLAIN, 15));
        medium.setSelected(false);
        medium.setSize(80, 20);
        medium.setLocation(275, 200);
        add(medium);

        high = new JRadioButton("High");
        high.setFont(new Font("Roboto", Font.PLAIN, 15));
        high.setSelected(false);
        high.setSize(80, 20);
        high.setLocation(355, 200);
        add(high);

        groupButtonPriority = new ButtonGroup();
        groupButtonPriority.add(low);
        groupButtonPriority.add(medium);
        groupButtonPriority.add(high);

        // Category
        category = new JLabel("Category");
        category.setFont(new Font("Roboto", Font.PLAIN, 20));
        category.setSize(100, 20);
        category.setLocation(100, 250);
        category.setForeground(new Color(0x7B90FF));
        add(category);

        homework = new JRadioButton("Homework");
        homework.setFont(new Font("Roboto", Font.PLAIN, 15));
        homework.setSelected(true);
        homework.setSize(75, 20);
        homework.setLocation(200, 250);
        add(homework);

        personal = new JRadioButton("Personal");
        personal.setFont(new Font("Roboto", Font.PLAIN, 15));
        personal.setSelected(false);
        personal.setSize(80, 20);
        personal.setLocation(275, 250);
        add(personal);

        work = new JRadioButton("Work");
        work.setFont(new Font("Roboto", Font.PLAIN, 15));
        work.setSelected(false);
        work.setSize(80, 20);
        work.setLocation(355, 250);
        add(work);

        groupButtonCategory = new ButtonGroup();
        groupButtonCategory.add(homework);
        groupButtonCategory.add(personal);
        groupButtonCategory.add(work);

        // Status
        status = new JLabel("Status");
        status.setFont(new Font("Roboto", Font.PLAIN, 20));
        status.setSize(100, 20);
        status.setLocation(100, 350);
        status.setForeground(new Color(0x4C6AFF));
        add(status);

        pending = new JRadioButton("Pending");
        pending.setFont(new Font("Roboto", Font.PLAIN, 15));
        pending.setSelected(false);
        pending.setSize(75, 20);
        pending.setLocation(200, 350);
        add(pending);

        completed = new JRadioButton("Completed");
        completed.setFont(new Font("Roboto", Font.PLAIN, 15));
        completed.setSelected(true);
        completed.setSize(80, 20);
        completed.setLocation(275, 350);
        add(completed);

        groupButtonCategory = new ButtonGroup();
        groupButtonCategory.add(pending);
        groupButtonCategory.add(completed);

        // Email where it is initially hidden
        email = new JLabel("Email:");
        email.setFont(new Font("Roboto", Font.PLAIN, 20));
        email.setSize(100, 20);
        email.setLocation(100, 400);
        email.setForeground(Color.BLUE);
        email.setVisible(false); // Hide initially
        add(email);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Roboto", Font.PLAIN, 15));
        textFieldEmail.setSize(200, 20);
        textFieldEmail.setLocation(200, 400);
        textFieldEmail.setVisible(false); // Hide initially
        add(textFieldEmail);

        // Item listener hide or show email
        pending.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (pending.isSelected()){
                    email.setVisible(true);
                    textFieldEmail.setVisible(true);
                } else{
                    email.setVisible(false);
                    textFieldEmail.setVisible(false);
                }
            }
        });

        // Buttons
        submit = new JButton("Submit");
        submit.setFont(new Font("Roboto", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(175, 450);
        submit.addActionListener(this);
        submit.setForeground(Color.blue);
        add(submit);


        // Output Text Area
        textOut = new JTextArea();
        textOut.setFont(new Font("Roboto", Font.PLAIN, 15));
        textOut.setSize(300, 400);
        textOut.setLocation(500, 100);
        textOut.setLineWrap(true);
        textOut.setEditable(false);
        add(textOut);

        res = new JLabel("");
        res.setFont(new Font("Roboto", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        add(res);

        resadd = new JTextArea();
        resadd.setFont(new Font("Roboto", Font.PLAIN, 15));
        resadd.setSize(200, 75);
        resadd.setLocation(580, 175);
        resadd.setLineWrap(true);
        add(resadd);

        List<Component> tabOrder = new ArrayList<>();
        tabOrder.add(textFieldTitle);
        tabOrder.add(textFieldDescription);
        tabOrder.add(low);
        tabOrder.add(medium);
        tabOrder.add(high);
        tabOrder.add(homework);
        tabOrder.add(personal);
        tabOrder.add(work);
        tabOrder.add(pending);
        tabOrder.add(completed);
        tabOrder.add(date);
        tabOrder.add(month);
        tabOrder.add(year);
        tabOrder.add(submit);

        setFocusTraversalPolicy(new CustomFocusTraversalPolicy(tabOrder));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {

            String data1, data2, data3;
            String prioritySQL = "";
            String statusSQL = "";
            String categorySQL = "";
            String emailSQL = null;
            String data0
                    = "Title : "
                    + textFieldTitle.getText() + "\n"
                    + "Description : "
                    + textFieldDescription.getText() + "\n";

            if (low.isSelected()) {
                data1 = "Priority : LOW" + "\n";
                prioritySQL = "LOW";
            } else if (medium.isSelected()) {
                data1 = "Priority : MEDIUM" + "\n";
                prioritySQL = "MEDIUM";
            } else if (high.isSelected()) {
                data1 = "Priority : HIGH" + "\n";
                prioritySQL = "HIGH";
            } else
                data1 = "If you see this error message, How?."
                        + "\n";

            if (homework.isSelected()) {
                data2 = "Category : HOMEWORK" + "\n";
                categorySQL = "HOMEWORK";
            } else if (personal.isSelected()) {
                data2 = "Category : PERSONAL" + "\n";
                categorySQL = "PERSONAL";
            } else if (work.isSelected()) {
                data2 = "Category : WORK" + "\n";
                categorySQL = "WORK";
            } else {
                data2 = "If you see this error message, How?.";
            }

            if (pending.isSelected()){
                if (!textFieldEmail.getText().equals("")){
                    emailSQL = textFieldEmail.getText();
                }
                data3 = "Status : PENDING" + "\nEmail:" + emailSQL +"\n" ;
                statusSQL = "PENDING";
            }
            else if (completed.isSelected()) {
                data3 = "Status : COMPLETED" + "\n";
                statusSQL = "COMPLETED";
            }
            else
                data3 = "If you see this error message, how?";

            String data4
                    = "DueDate : "
                    + (String) date.getSelectedItem()
                    + "/" + (String) month.getSelectedItem()
                    + "/" + (String) year.getSelectedItem()
                    + "\n";


            textOut.setText(data0 + data1 + data2 + data3 + data4);
            textOut.setEditable(false);

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

            String dateSQL = "";
            if(date.getSelectedItem().toString().length()==1){
                dateSQL = "0" + date.getSelectedItem().toString();
            } else {
                dateSQL = date.getSelectedItem().toString();
            }


            String titleSQL = textFieldTitle.getText();
            String descriptionSQL = textFieldDescription.getText();
            String dueDateSQL = year.getSelectedItem() + "-" + monthConversion.get(month.getSelectedItem()) + "-" + dateSQL;
            Date dueDate = Date.valueOf(dueDateSQL);
            System.out.println(dueDate);
            Tasks.Priority priority = Tasks.Priority.valueOf(prioritySQL.toUpperCase());
            Tasks.Category category = Tasks.Category.valueOf(categorySQL.toUpperCase());
            Tasks.Status status = Tasks.Status.valueOf(statusSQL.toUpperCase());
            boolean emailReminder = true;
            if (emailSQL == null){
                emailReminder  = false;
            }
            try {
                Timestamp now = new Timestamp(System.currentTimeMillis());

                Tasks task = new Tasks(0, titleSQL, descriptionSQL, dueDate, priority, status, now, now, (short) 0,
                        0, 0, category,emailSQL,emailReminder,false);
                TaskDAO.addTask(task);
                res.setText("Tasks Added Successfully with ID: " + task.getTaskId());
            } catch (SQLException | IllegalArgumentException err) {
                System.out.println("Error adding task: " + err.getMessage());
            }
        }
    }

    private static void startEmailReminderScheduler() {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<Tasks> taskList = TaskDAO.getAllTasks();
                    long currentTimeMillis = System.currentTimeMillis();
                    long twentyFourHoursInMillis = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

                    for (Tasks task : taskList) {
                        if (task.isEmailReminders() && task.getDueDate() != null && !task.isReminderSent()) {
                            long dueDateMillis = task.getDueDate().getTime();

                            // Check if the task is within 24 hours of being due
                            if (dueDateMillis - currentTimeMillis <= twentyFourHoursInMillis &&
                                    dueDateMillis - currentTimeMillis > 0) {
                                sendEmailReminder(task);
                                task.setReminderSent(true); // Update the flag in the object
                                TaskDAO.updateReminderSent(task.getTaskId(), true); // Update the flag in the database
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error checking tasks for email reminders: " + e.getMessage());
                }
            }
        }, 0, 60 * 60 * 1000); // Run every hour
    }
    private static void sendEmailReminder(Tasks task) {
        String recipient = task.getEmail();
        String subject = "Task Reminder: " + task.getTitle();
        String body = "Hi,\n\nThis is a reminder that your task \"" + task.getTitle() +
                "\" is due tomorrow (" + task.getDueDate() + "). Please complete it on time.\n\nTadaa,\nTaDaList Team";

        EmailUtil.sendEmail(recipient, subject, body);
        System.out.println("Email reminder sent for task: " + task.getTitle());
    }
}

class CustomFocusTraversalPolicy extends FocusTraversalPolicy {
    private final List<Component> order;

    public CustomFocusTraversalPolicy(List<Component> order) {
        this.order = order;
    }

    @Override
    public Component getComponentAfter(Container focusCycleRoot, Component component) {
        int idx = order.indexOf(component);
        return (idx == -1 || idx == order.size() - 1) ? order.get(0) : order.get(idx + 1);
    }

    @Override
    public Component getComponentBefore(Container focusCycleRoot, Component component) {
        int idx = order.indexOf(component);
        return (idx <= 0) ? order.get(order.size() - 1) : order.get(idx - 1);
    }

    @Override
    public Component getFirstComponent(Container focusCycleRoot) {
        return order.get(0);
    }

    @Override
    public Component getLastComponent(Container focusCycleRoot) {
        return order.get(order.size() - 1);
    }

    @Override
    public Component getDefaultComponent(Container focusCycleRoot) {
        return order.get(0);
    }
}