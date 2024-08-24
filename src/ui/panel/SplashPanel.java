package ui.panel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class SplashPanel extends JPanel {

    public SplashPanel() {
        setLayout(new BorderLayout());

        // Load the image securely
        JLabel imageLabel = createImageLabel("../image/splash_image.jpg", 600);

        // Create and set up the splash screen text with enhanced styles
        JLabel titleLabel = createLabel("Tetris Game", new Font("Arial", Font.BOLD, 40), new Color(0x3B3B98));
        JLabel groupLabel = createLabel("Group35: Ryota Ando & Naoya Fukada", new Font("Arial", Font.ITALIC, 14), Color.GRAY);
        JLabel courseLabel = createLabel("Course Code: 3815ICT Software Design", new Font("Arial", Font.PLAIN, 14), Color.GRAY);

        // Create a panel to hold the text labels with centered alignment
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        // Center align labels
        groupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(Box.createVerticalGlue()); // Push content to the center
        textPanel.add(groupLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add space between labels
        textPanel.add(courseLabel);
        textPanel.add(Box.createVerticalGlue()); // Push content to the center

        // Create a title panel with centered alignment and custom background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0xF3F3F3));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding around title

        // Create a main content panel to center everything vertically
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.add(titlePanel, BorderLayout.NORTH);
        mainContentPanel.add(imageLabel, BorderLayout.CENTER);
        mainContentPanel.add(textPanel, BorderLayout.SOUTH);

        // Add the main content panel to the main panel
        add(mainContentPanel, BorderLayout.CENTER);

        // Set background color for the entire panel
        setBackground(Color.WHITE);
    }

    // Method to create labels securely and consistently
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    // Method to create an image label with secure loading and scaling
    private JLabel createImageLabel(String imagePath, int width) {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            URL imageUrl = getClass().getResource(imagePath); // Using a resource from the classpath
            if (imageUrl != null) {
                ImageIcon splashImage = new ImageIcon(imageUrl);

                // Scale the image
                Image image = splashImage.getImage();
                Image scaledImage = image.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
                ImageIcon scaledSplashImage = new ImageIcon(scaledImage);

                imageLabel.setIcon(scaledSplashImage);
            } else {
                imageLabel.setText("Image not found");
            }
        } catch (Exception e) {
            imageLabel.setText("Failed to load image");
            e.printStackTrace();
        }

        return imageLabel;
    }
}
