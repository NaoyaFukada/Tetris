package ui;

import ui.panel.ConfigurePanel;
import ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame(String title) {
        // This calls the constructor of the JFrame class that accepts a String argument.
        super(title);
        setSize(600, 410);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add different panels to the mainPanel with CardLayout
        mainPanel.add(new MainPanel(this), "Main");
        mainPanel.add(new ConfigurePanel(this), "Config");

        // Add the mainPanel to the JFrame
        add(mainPanel);

        // Start with the main screen
        cardLayout.show(mainPanel, "Main");
    }

    public void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }
}
