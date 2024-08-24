package ui.panel;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    private MainFrame mainFrame;

    public MainPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Use GridBagLayout for flexible positioning and vertical centering
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components

        // Create a label for the title
        JLabel titleLabel = createLabel("Main Menu", new Font("Arial", Font.BOLD, 24));
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Create the buttons
        JButton playButton = createButton("Play");
        JButton configButton = createButton("Configuration");
        JButton highScoreButton = createButton("High Scores");
        JButton exitButton = createButton("Exit");

        // Add action listeners to buttons
        playButton.addActionListener(createPlayButtonListener());
        configButton.addActionListener(createConfigButtonListener());
        highScoreButton.addActionListener(createHighScoreButtonListener());
        exitButton.addActionListener(createExitButtonListener());

        // Add buttons to the panel
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 1;
        add(playButton, gbc);

        gbc.gridy = 2;
        add(configButton, gbc);

        gbc.gridy = 3;
        add(highScoreButton, gbc);

        gbc.gridy = 4;
        add(exitButton, gbc);

        // Author Label
        JLabel authorLabel = createLabel("Author: Naoya/Ryota", new Font("Arial", Font.PLAIN, 10));
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(authorLabel, gbc);
    }

    // Helper method to create a JLabel with default settings
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    // Helper method to create a JButton with default settings
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    // ActionListener for the Play button
    private ActionListener createPlayButtonListener() {
        return e -> {
            mainFrame.showScreen("Game");
            System.out.println("Play button pressed");
        };
    }

    // ActionListener for the Config button
    private ActionListener createConfigButtonListener() {
        return e -> {
            mainFrame.showScreen("Config");
            System.out.println("Config button pressed");
        };
    }

    // ActionListener for the High Score button
    private ActionListener createHighScoreButtonListener() {
        return e -> {
            mainFrame.showScreen("HighScores");
            System.out.println("High Scores button pressed");
        };
    }

    // ActionListener for the Exit button
    private ActionListener createExitButtonListener() {
        return e -> {
            // Show confirmation dialog
            int choice = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Are you sure you want to exit the program?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );

            // Handle user's choice
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0); // Exit the program
            } else {
                mainFrame.showScreen("Main"); // Return to main screen (though we're already here)
            }
        };
    }
}
