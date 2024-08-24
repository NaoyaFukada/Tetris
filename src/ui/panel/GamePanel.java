package ui.panel;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
        backButton.addActionListener(e -> handleBackButton());
        bottomPanel.add(backButton, BorderLayout.CENTER);

        JLabel authorLabel = createLabel("Author: Naoya/Ryota", new Font("Arial", Font.PLAIN, 10));
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(authorLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void resetGame() {
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

    private void handleBackButton() {
        if (board.isGameRunning() || board.isGamePaused()) {
            board.pauseGame();

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to stop the game?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                parentFrame.showScreen("Main");
            } else {
                if (!board.isGamePauseByP()) {
                    board.resumeGame();
                }
                board.requestFocusInWindow();
            }
        } else {
            parentFrame.showScreen("Main");
        }
    }


    // Ensure GamePanel does not process key events accidentally
    @Override
    protected void processKeyEvent(KeyEvent e) {
        // Do nothing to prevent accidental key processing
    }
}
