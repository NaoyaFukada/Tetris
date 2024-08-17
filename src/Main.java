import ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        // Create and set up the main application window
        JFrame frame = new MainFrame("Tetris");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}