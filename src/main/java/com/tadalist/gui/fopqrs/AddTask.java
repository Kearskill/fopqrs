package com.tadalist.gui.fopqrs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTask extends JPanel implements ActionListener {
    private Container c;
    private JLabel title, header, description, dueDate, priority, category, status, add, res;
    private JTextField textFieldTitle, textFieldDescription;
    private JRadioButton low, medium, high, homework, personal, work, pending, completed;
    private ButtonGroup groupButtonPriority, groupButtonCategory, groupButtonStatus;
    private JComboBox date, month, year;
    private JTextArea tadd, tout, resadd;
    private JCheckBox term;
    private JButton sub, reset;

    private String dates[]
            = {"1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31"};
    private String months[]
            = {"Jan", "feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sup", "Oct", "Nov", "Dec"};
    private String years[]
            = {"2020", "2021", "2022", "2023",
            "2024", "2025", "2026", "2027",
            "2028", "2029", "2030", "2031",
            "2032", "2033", "2034", "2035"};

    public AddTask() {
        setLayout(null);

        // Title
        header = new JLabel("Enter Task Details");
        header.setFont(new Font("Arial", Font.PLAIN, 30));
        header.setSize(300, 30);
        header.setLocation(300, 30);
        header.setForeground(Color.blue);
        add(header);

        // task title
        title = new JLabel("Title");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(100, 20);
        title.setLocation(100, 100);
        title.setForeground(Color.red);
        add(title);

        textFieldTitle = new JTextField();
        textFieldTitle.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldTitle.setSize(190, 20);
        textFieldTitle.setLocation(200, 100);
        add(textFieldTitle);

        // description
        description = new JLabel("Description");
        description.setFont(new Font("Arial", Font.PLAIN, 20));
        description.setSize(100, 20);
        description.setLocation(100, 150);
        description.setForeground(Color.YELLOW);
        add(description);

        textFieldDescription = new JTextField();
        textFieldDescription.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldDescription.setSize(150, 20);
        textFieldDescription.setLocation(200, 150);
        add(textFieldDescription);

        // Due Date
        dueDate = new JLabel("Due Date");
        dueDate.setFont(new Font("Arial", Font.PLAIN, 20));
        dueDate.setSize(100, 20);
        dueDate.setLocation(100, 300);
        dueDate.setForeground(Color.MAGENTA);
        add(dueDate);

        date = new JComboBox(dates);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setSize(50, 20);
        date.setLocation(200, 300);
        add(date);

        month = new JComboBox(months);
        month.setFont(new Font("Arial", Font.PLAIN, 15));
        month.setSize(60, 20);
        month.setLocation(250, 300);
        add(month);

        year = new JComboBox(years);
        year.setFont(new Font("Arial", Font.PLAIN, 15));
        year.setSize(60, 20);
        year.setLocation(320, 300);
        add(year);


        // Priority
        priority = new JLabel("Priority");
        priority.setFont(new Font("Arial", Font.PLAIN, 20));
        priority.setSize(100, 20);
        priority.setLocation(100, 200);
        priority.setForeground(Color.orange);
        add(priority);

        low = new JRadioButton("Low");
        low.setFont(new Font("Arial", Font.PLAIN, 15));
        low.setSelected(true); // default selected
        low.setSize(75, 20);
        low.setLocation(200, 200);
        add(low);

        medium = new JRadioButton("Medium");
        medium.setFont(new Font("Arial", Font.PLAIN, 15));
        medium.setSelected(false);
        medium.setSize(80, 20);
        medium.setLocation(275, 200);
        add(medium);

        high = new JRadioButton("High");
        high.setFont(new Font("Arial", Font.PLAIN, 15));
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
        category.setFont(new Font("Arial", Font.PLAIN, 20));
        category.setSize(100, 20);
        category.setLocation(100, 250);
        category.setForeground(Color.red);
        add(category);

        homework = new JRadioButton("Homework");
        homework.setFont(new Font("Arial", Font.PLAIN, 15));
        homework.setSelected(true);
        homework.setSize(75, 20);
        homework.setLocation(200, 250);
        add(homework);

        personal = new JRadioButton("Personal");
        personal.setFont(new Font("Arial", Font.PLAIN, 15));
        personal.setSelected(false);
        personal.setSize(80, 20);
        personal.setLocation(275, 250);
        add(personal);

        work = new JRadioButton("Work");
        work.setFont(new Font("Arial", Font.PLAIN, 15));
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
        status.setFont(new Font("Arial", Font.PLAIN, 20));
        status.setSize(100, 20);
        status.setLocation(100, 350);
        status.setForeground(Color.GREEN);
        add(status);

        pending = new JRadioButton("Pending");
        pending.setFont(new Font("Arial", Font.PLAIN, 15));
        pending.setSelected(true);
        pending.setSize(75, 20);
        pending.setLocation(200, 350);
        add(pending);

        completed = new JRadioButton("Completed");
        completed.setFont(new Font("Arial", Font.PLAIN, 15));
        completed.setSelected(false);
        completed.setSize(80, 20);
        completed.setLocation(275, 350);
        add(completed);

        groupButtonCategory = new ButtonGroup();
        groupButtonCategory.add(pending);
        groupButtonCategory.add(completed);

        // Buttons
        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(175, 400);
        sub.addActionListener(this);
        sub.setForeground(Color.blue);
        add(sub);


        // Output Text Area
        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        add(res);

        resadd = new JTextArea();
        resadd.setFont(new Font("Arial", Font.PLAIN, 15));
        resadd.setSize(200, 75);
        resadd.setLocation(580, 175);
        resadd.setLineWrap(true);
        add(resadd);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {

            String data1, data2, data3;
            String data0
                    = "Title : "
                    + textFieldTitle.getText() + "\n"
                    + "Description : "
                    + textFieldDescription.getText() + "\n";

            if (low.isSelected())
                data1 = "Priority : LOW"
                        + "\n";
            else if (medium.isSelected())
                data1 = "Priority : MEDIUM"
                        + "\n";
            else if (high.isSelected())
                data1 = "Priority : HIGH"
                        + "\n";
            else
                data1 = "If you see this error message, How?."
                        + "\n";

            if (homework.isSelected())
                data2 = "Category : HOMEWORK"
                        + "\n";
            else if (personal.isSelected())
                data2 = "Category : PERSONAL"
                        + "\n";
            else if (work.isSelected())
                data2 = "Category : WORK"
                        + "\n";
            else
                data2 = "If you see this error message, How?.";

            if (pending.isSelected())
                data3 = "Status : PENDING"
                        + "\n";
            else if (completed.isSelected())
                data3 = "Status : COMPLETED"
                        + "\n";
            else
                data3 = "If you see this error message, how?";


            String data4
                    = "DueDate : "
                    + (String) date.getSelectedItem()
                    + "/" + (String) month.getSelectedItem()
                    + "/" + (String) year.getSelectedItem()
                    + "\n";

            tout.setText(data0 + data1 + data2 + data3 + data4);
            tout.setEditable(false);
            res.setText("Tasks Added Successfully");
        }
    }
}