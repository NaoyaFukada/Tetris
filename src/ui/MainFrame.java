package ui;

import ui.panel.*;

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

        // Add SplashPanel to the mainPanel
        SplashPanel splashPanel = new SplashPanel();
        mainPanel.add(splashPanel, "Splash");

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

        // Add HighScorePanel to the mainPanel
        HighScorePanel highScorePanel = new HighScorePanel(this);
        mainPanel.add(highScorePanel, "HighScores");

        // Add the mainPanel to the JFrame
        add(mainPanel);

        // Show the splash screen first
        cardLayout.show(mainPanel, "Splash");

        // Transition to the main screen after a delay
        Timer timer = new Timer(5000, e -> {
            cardLayout.show(mainPanel, "Main");
            // Ensure main frame is visible after splash
            setVisible(true);
        });
        timer.setRepeats(false); // Ensure the timer runs only once
        timer.start();

        // Optionally, you can set the splash screen to be invisible or remove it after the transition
        timer.addActionListener(e -> splashPanel.setVisible(false));
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
            // Create and configure the main frame
            MainFrame frame = new MainFrame("Tetris");
            frame.setLocationRelativeTo(null);  // Center on screen
            // Make the frame visible after splash screen is handled
            frame.setVisible(true);
        });
    }
}
