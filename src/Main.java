import javax.swing.*;
import ui.panel.MainPanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            JFrame frame = new JFrame("Tetris");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Create the main panel and add it to the frame
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);
            // Make the frame visible
            frame.setVisible(true);
        });
    }
}