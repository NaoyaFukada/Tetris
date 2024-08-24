package ui.panel;

import javax.swing.*;
import java.awt.*;

public class SplashPanel extends JPanel {

    public SplashPanel() {
        setLayout(new BorderLayout());

        // Load the image
        ImageIcon splashImage = new ImageIcon("../image/splash_image.jpg");
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create and set up the splash screen text
        JLabel titleLabel = new JLabel("Tetris Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JLabel groupLabel = new JLabel("Group35: Ryota Ando & Naoya Fukada", SwingConstants.CENTER);
        groupLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel courseLabel = new JLabel("Course Code: 2805ICT Software Design", SwingConstants.CENTER);
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create a panel to hold the text labels
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(3, 1));
        textPanel.add(titleLabel);
        textPanel.add(groupLabel);
        textPanel.add(courseLabel);

        // Add the components to the main panel
        add(imageLabel, BorderLayout.CENTER);
        add(textPanel, BorderLayout.SOUTH);

        // Set background color if desired
        setBackground(Color.white);
    }
}
