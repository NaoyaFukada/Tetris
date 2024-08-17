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

        // Set layout manager (Make them align vertically)
        // Y_AXIS - Components are laid out vertically from top to bottom.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create a label for the title
        JLabel titleLabel = createLabel("Main Menu", new Font("Arial", Font.BOLD, 24));

        // Create the buttons
        JButton playButton = createButton("Play");
        JButton configButton = createButton("Configuation");
        JButton highScoreButton = createButton("High Scores");
        JButton exitButton = createButton("Exit");

        // Add action listeners to buttons
        playButton.addActionListener(createPlayButtonListener());
        configButton.addActionListener(createConfigButtonListener());
        highScoreButton.addActionListener(createHighScoreButtonListener());
        exitButton.addActionListener(createExitButtonListener());

        // Create a label for the author
        JLabel authorLabel = createLabel("Author: Naoya/Ryota", null);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add spacing between elements
        // createVerticalStrut: Creates an invisible, fixed-height component. In a vertical box,
        // you typically use this method to force a certain amount of space between two components.
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

    // Helper method to create a JLabel with default settings
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    // Helper method to create a JButton with default settings
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Dimension buttonSize = new Dimension(200, 40);
        button.setMaximumSize(buttonSize);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // ActionListener for the Play button
    private ActionListener createPlayButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play button pressed");
            }
        };
    }

    // ActionListener for the Config button
    private ActionListener createConfigButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showScreen("Config");
                System.out.println("Config button pressed");
            }
        };
    }

    // ActionListener for the High Score button
    private ActionListener createHighScoreButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("High Scores button pressed");
            }
        };
    }

    // ActionListener for the Exit button
    private ActionListener createExitButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }
}
