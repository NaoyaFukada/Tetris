package ui.panel;

import javax.swing.*;
import java.awt.*;

public class SplashPanel extends JPanel {

    public SplashPanel() {
        setLayout(new BorderLayout());

        // Load and add the image
        ImageIcon splashImage = new ImageIcon("../image/splash_image.jpg");
        JLabel imageLabel = new JLabel(splashImage);
        add(imageLabel, BorderLayout.CENTER);

        // Create and set up the splash screen text
        JLabel titleLabel = new JLabel("Tetris Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        JLabel groupLabel = new JLabel("Group35: Ryota Ando & Naoya Fukada", SwingConstants.CENTER);
        JLabel courseLabel = new JLabel("Course Code: 2805ICT Software Design", SwingConstants.CENTER);

        JPanel textPanel = new JPanel(new GridLayout(3, 1));
        textPanel.setBackground(Color.white);
        textPanel.add(titleLabel);
        textPanel.add(groupLabel);
        textPanel.add(courseLabel);

        add(textPanel, BorderLayout.SOUTH);

        // Set background color if desired
        setBackground(Color.white);
    }
}

