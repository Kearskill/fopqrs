package com.tadalist.gui.fopqrs;

import com.tadalist.dao.fopqrs.TaskDAO;
import com.tadalist.dao.fopqrs.Tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SortTask extends JPanel implements ActionListener {
    private JButton btnDueDateAsc, btnDueDateDesc, btnPriorityHighLow, btnPriorityLowHigh;

    public SortTask() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Sort Tasks", SwingConstants.CENTER);
        label.setFont(new Font("Roboto", Font.BOLD, 20));
        this.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        btnDueDateAsc = new JButton("Due Date (Ascending to Descending)");
        btnDueDateDesc = new JButton("Due Date (Descending to Ascending)");
        btnPriorityHighLow = new JButton("Priority (High to Low)");
        btnPriorityLowHigh = new JButton("Priority (Low to High)");

        btnDueDateAsc.addActionListener(this);
        btnDueDateDesc.addActionListener(this);
        btnPriorityHighLow.addActionListener(this);
        btnPriorityLowHigh.addActionListener(this);

        buttonPanel.add(btnDueDateAsc);
        buttonPanel.add(btnDueDateDesc);
        buttonPanel.add(btnPriorityHighLow);
        buttonPanel.add(btnPriorityLowHigh);

        this.add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sortByColumn = "";
        boolean ascending = true;

        if (e.getSource() == btnDueDateAsc) {
            sortByColumn = "DueDate";
            ascending = true;
        } else if (e.getSource() == btnDueDateDesc) {
            sortByColumn = "DueDate";
            ascending = false;
        } else if (e.getSource() == btnPriorityHighLow) {
            sortByColumn = "Priority";
            ascending = false;
        } else if (e.getSource() == btnPriorityLowHigh) {
            sortByColumn = "Priority";
            ascending = true;
        }

        try {
            List<Tasks> sortedTasks = TaskDAO.getTasksSorted(sortByColumn, ascending);

            StringBuilder taskList = new StringBuilder("Tasks sorted by " + sortByColumn + " (" + (ascending ? "Ascending" : "Descending") + "):\n");
            for (Tasks task : sortedTasks) {
                taskList.append(task.getTitle()).append(" - ").append(sortByColumn).append(": ").append(getColumnValue(task, sortByColumn)).append("\n");
            }

            JOptionPane.showMessageDialog(this, taskList.toString(), "Sorted Tasks", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching tasks: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private String getColumnValue(Tasks task, String column) {
        return switch (column) {
            case "DueDate" -> task.getDueDate().toString();
            case "Priority" -> task.getPriority().toString();
            default -> "Unknown";
        };
    }
}
