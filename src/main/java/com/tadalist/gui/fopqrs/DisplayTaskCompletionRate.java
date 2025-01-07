package com.tadalist.gui.fopqrs;

import javax.swing.*;
import java.awt.*;

public class DisplayTaskCompletionRate extends JPanel {
    public DisplayTaskCompletionRate() {
        // Set layout and content for Page1
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel("How long will this task be completed?", SwingConstants.CENTER);
        this.add(label, BorderLayout.CENTER);

    }
}