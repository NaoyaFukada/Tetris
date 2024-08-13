import javax.swing.*;
import javax.swing.border.Border;

import ui.panel.MainPanel;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            JFrame frame = new JFrame("Tetris");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Create the main panel and add it to the frame
            MainPanel mainPanel = new MainPanel();

            // Create a wrapper panel with GridBagLayout to center the main panel
            JPanel wrapperPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            wrapperPanel.add(mainPanel, gbc);

            frame.add(wrapperPanel);
            // Make the frame visible
            frame.setVisible(true);
        });
    }
}