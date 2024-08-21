package ui.panel;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class HighScorePanel extends JPanel {

    private MainFrame mainFrame;

    public HighScorePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("High Scores", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // High scores (dummy data)
        String[] highScores = {"1. Alice - 1500", "2. Bob - 1200", "3. Charlie - 900"};
        JList<String> scoreList = new JList<>(highScores);
        scoreList.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        add(scoreList, gbc);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        backButton.addActionListener(e -> mainFrame.showScreen("Main"));
    }
}
