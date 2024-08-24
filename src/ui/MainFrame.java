package ui;

import ui.panel.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GamePanel gamePanel; // Reference to the GamePanel

    public MainFrame(String title) {
        super(title);
        setSize(600, 679);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add SplashPanel to the mainPanel with CardLayout
        mainPanel.add(new SplashPanel(), "Splash");

        // Add different panels to the mainPanel with CardLayout
        mainPanel.add(new MainPanel(this), "Main");
        mainPanel.add(new ConfigurePanel(this), "Config");

        // Initialize and add the GamePanel
        gamePanel = new GamePanel(this); // Initialize GamePanel
        mainPanel.add(gamePanel, "Game");

        // Add HighScorePanel to the mainPanel
        HighScorePanel highScorePanel = new HighScorePanel(this);
        mainPanel.add(highScorePanel, "HighScores");

        // Add the mainPanel to the JFrame
        add(mainPanel);

        Timer splashTimer = new Timer(3000, e -> {
            cardLayout.show(mainPanel, "Main"); // Switch to the Main screen
            ((Timer) e.getSource()).stop(); // Stop the timer to prevent further actions
        });
        splashTimer.setRepeats(false); // Ensure the timer only fires once
        splashTimer.start();

        // Start with the splash screen
        cardLayout.show(mainPanel, "Splash");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);

        // Request focus for the BoardPanel if the screen is the game screen
        if (screenName.equals("Game")) {
            SwingUtilities.invokeLater(() -> {
                gamePanel.resetGame(); // Reset the game state
                gamePanel.getBoard().requestFocusInWindow(); // Request focus on the board
                System.out.println("BoardPanel focus requested. Focused: " + gamePanel.getBoard().isFocusOwner());
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
