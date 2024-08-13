package ui.panel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    public MainPanel() {
        // Set layout manager (Make them align vertically)
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Border panelBorder = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(panelBorder);

        // Create a label for the title
        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Create the buttons
        JButton playButton = new JButton("Play");
        JButton configButton = new JButton("Configuation");
        JButton highScoreButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");

        // Set buttons to have the same size and align center
        Dimension buttonSize = new Dimension(200, 40);
        playButton.setMaximumSize(buttonSize);
        configButton.setMaximumSize(buttonSize);
        highScoreButton.setMaximumSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);

        // Align buttons to the center of the panel
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        configButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners to buttons
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Start the game or transition to the game screen
                System.out.println("Play button pressed");
            }
        });

        configButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Transition to the configuration screen
                System.out.println("Config button pressed");
            }
        });

        highScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Transition to the high scores screen
                System.out.println("High Scores button pressed");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the program
                System.exit(0);
            }
        });

        // Create a label for the author
        JLabel authorLabel = new JLabel("Author: Naoya and Ryota");
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add spacing between elements
        add(Box.createVerticalStrut(20));  // Add some space at the top
        add(titleLabel);
        add(Box.createVerticalStrut(40));  // Add space between title and buttons
        add(playButton);
        add(Box.createVerticalStrut(20));
        add(configButton);
        add(Box.createVerticalStrut(20));
        add(highScoreButton);
        add(Box.createVerticalStrut(20));
        add(exitButton);
        add(Box.createVerticalStrut(40));  // Add space between buttons and author label
        add(authorLabel);
    }
}
