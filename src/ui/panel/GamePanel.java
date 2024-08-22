package ui.panel;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MainFrame parentFrame;
    private BoardPanel board;

    public GamePanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());

        board = new BoardPanel();
        resetGame();
        add(board, BorderLayout.CENTER);

        // Create the title label
        JLabel titleLabel = createLabel("Play", new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Create a panel for the "Back" button and author label
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> parentFrame.showScreen("Main"));
        bottomPanel.add(backButton, BorderLayout.CENTER);

        JLabel authorLabel = createLabel("Author: Naoya/Ryota", new Font("Arial", Font.PLAIN, 10));
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(authorLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void resetGame() {
        // Call the reset logic from BoardPanel or handle it directly here
        board.resetBoard();
        board.setCurrentShape();
        board.setGamePlayState();
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }

    public BoardPanel getBoard() {
        return board;
    }
}
