package com.tadalist.gui.fopqrs;

import javax.swing.*;
import java.awt.*;

public class SortTask extends JPanel {
    public SortTask() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Sort Tasks", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnDueDateAsc = new JButton("Due Date (Ascending to Descending)");
        JButton btnDueDateDesc = new JButton("Due Date (Descending to Ascending)");
        JButton btnPriorityHighLow = new JButton("Priority (High to Low)");
        JButton btnPriorityLowHigh = new JButton("Priority (Low to High)");

        buttonPanel.add(btnDueDateAsc);
        buttonPanel.add(btnDueDateDesc);
        buttonPanel.add(btnPriorityHighLow);
        buttonPanel.add(btnPriorityLowHigh);

        this.add(buttonPanel, BorderLayout.CENTER);
    }
}
