package ui.panel;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScorePanel extends JPanel {

    private MainFrame mainFrame;

    public HighScorePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = createGbc();

        // Title label
        JLabel titleLabel = createLabel("High Scores", SwingConstants.CENTER, new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // High scores list
        String[] highScores = generateDummyScores();
        JList<String> scoreList = createScoreList(highScores);
        gbc.gridy = 1;
        add(scoreList, gbc);

        // Back button
        JButton backButton = createButton("Back", e -> mainFrame.showScreen("Main"));
        backButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);
    }

    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private JLabel createLabel(String text, Integer position, Font font) {
        JLabel label = new JLabel(text, position);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }

    private JList<String> createScoreList(String[] highScores) {
        JList<String> scoreList = new JList<>(highScores);
        scoreList.setFont(new Font("Arial", Font.PLAIN, 18));
        return scoreList;
    }

    private String[] generateDummyScores() {
        // Dummy player names
        String[] playerNames = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy"};

        // Generate random scores and pair them with player names
        List<String> highScoresList = new ArrayList<>();
        for (int i = 0; i < playerNames.length; i++) {
            int score = (int) (Math.random() * 1000) + 1000; // Random score between 1000 and 2000
            highScoresList.add((i + 1) + ". " + playerNames[i] + " - " + score);
        }

        // Sort scores in descending order
        Collections.sort(highScoresList, Collections.reverseOrder((a, b) -> {
            int scoreA = Integer.parseInt(a.split(" - ")[1]);
            int scoreB = Integer.parseInt(b.split(" - ")[1]);
            return Integer.compare(scoreA, scoreB);
        }));

        // Update the list with correct ranks after sorting
        for (int i = 0; i < highScoresList.size(); i++) {
            String entry = highScoresList.get(i);
;           String nameAndScore = entry.substring(entry.indexOf(". ") + 2);
            highScoresList.set(i, (i + 1) + ". " + nameAndScore);
        }

        return highScoresList.toArray(new String[0]);
    }
}
