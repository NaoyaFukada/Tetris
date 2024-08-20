import ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        JFrame frame = new MainFrame("Tetris");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
