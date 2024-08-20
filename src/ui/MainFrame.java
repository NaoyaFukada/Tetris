package ui;

import ui.panel.ConfigurePanel;
import ui.panel.GamePanel;
import ui.panel.MainPanel;
import ui.panel.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private BoardPanel board;

    public MainFrame(String title) {
        super(title);
        setSize(600, 679);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different panels to the mainPanel with CardLayout
        mainPanel.add(new MainPanel(this), "Main");
        mainPanel.add(new ConfigurePanel(this), "Config");

        // Create the BoardPanel and GamePanel
        board = new BoardPanel();
        GamePanel gamePanel = new GamePanel(this);

        // Add the BoardPanel to the GamePanel (assuming GamePanel holds the game board)
        gamePanel.add(board, BorderLayout.CENTER);

        // Add GamePanel to mainPanel
        mainPanel.add(gamePanel, "Game");

        // Add the mainPanel to the JFrame
        add(mainPanel);

        // Start with the main screen
        cardLayout.show(mainPanel, "Main");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);

        // Request focus for the BoardPanel if the screen is the game screen
        if (screenName.equals("Game")) {
            SwingUtilities.invokeLater(() -> {
                board.requestFocusInWindow();
                System.out.println("BoardPanel focus requested. Focused: " + board.isFocusOwner());
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame("Tetris");
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
