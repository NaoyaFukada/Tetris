package ui.panel;

import ui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurePanel extends JPanel {
    private MainFrame parentFrame;

    public ConfigurePanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Use GridBagLayout for flexible positioning
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = createGridBagConstraints();

        // Title label
        JLabel titleLabel = createLabel("Configuration", new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;  // Reset gridwidth for other components
        gbc.anchor = GridBagConstraints.WEST;  // Left align the labels

        // Field Width
        JLabel widthLabel = createLabel("Field Width (No of cells):", null);
        JSlider widthSlider = createSlider(5, 15, 10);
        JLabel widthValueLabel = createValueLabel(widthSlider.getValue());
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(widthLabel, gbc);
        gbc.gridx = 1;
        add(widthSlider, gbc);
        gbc.gridx = 2;
        add(widthValueLabel, gbc);

        widthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                widthValueLabel.setText(String.valueOf(widthSlider.getValue()));
            }
        });

        // Field Height
        JLabel heightLabel = createLabel("Field Height (No of cells):", null);
        JSlider heightSlider = createSlider(15, 30, 20);
        JLabel heightValueLabel = createValueLabel(heightSlider.getValue());
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(heightLabel, gbc);
        gbc.gridx = 1;
        add(heightSlider, gbc);
        gbc.gridx = 2;
        add(heightValueLabel, gbc);

        heightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                heightValueLabel.setText(String.valueOf(heightSlider.getValue()));
            }
        });

        // Game Level
        JLabel levelLabel = createLabel("Game Level:", null);
        JSlider levelSlider = createSlider(1, 10, 4);
        JLabel levelValueLabel = createValueLabel(levelSlider.getValue());
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(levelLabel, gbc);
        gbc.gridx = 1;
        add(levelSlider, gbc);
        gbc.gridx = 2;
        add(levelValueLabel, gbc);

        levelSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                levelValueLabel.setText(String.valueOf(levelSlider.getValue()));
            }
        });

        // Music On/Off
        JLabel musicLabel = createLabel("Music (On|Off):", null);
        JCheckBox musicCheckBox = createCheckBox(true);
        JLabel musicValueLabel = createValueLabel(musicCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(musicLabel, gbc);
        gbc.gridx = 1;
        add(musicCheckBox, gbc);
        gbc.gridx = 2;
        add(musicValueLabel, gbc);

        musicCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicValueLabel.setText(musicCheckBox.isSelected() ? "On" : "Off");
            }
        });

        // Sound Effect On/Off
        JLabel soundLabel = createLabel("Sound Effect (On|Off):", null);
        JCheckBox soundCheckBox = createCheckBox(true);
        JLabel soundValueLabel = createValueLabel(soundCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(soundLabel, gbc);
        gbc.gridx = 1;
        add(soundCheckBox, gbc);
        gbc.gridx = 2;
        add(soundValueLabel, gbc);

        soundCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundValueLabel.setText(soundCheckBox.isSelected() ? "On" : "Off");
            }
        });

        // AI Play On/Off
        JLabel aiLabel = createLabel("AI Play (On|Off):", null);
        JCheckBox aiCheckBox = createCheckBox(false);
        JLabel aiValueLabel = createValueLabel(aiCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(aiLabel, gbc);
        gbc.gridx = 1;
        add(aiCheckBox, gbc);
        gbc.gridx = 2;
        add(aiValueLabel, gbc);

        aiCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aiValueLabel.setText(aiCheckBox.isSelected() ? "On" : "Off");
            }
        });

        // Extend Mode On/Off
        JLabel extendLabel = createLabel("Extend Mode (On|Off):", null);
        JCheckBox extendCheckBox = createCheckBox(false);
        JLabel extendValueLabel = createValueLabel(extendCheckBox.isSelected() ? "On" : "Off");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(extendLabel, gbc);
        gbc.gridx = 1;
        add(extendCheckBox, gbc);
        gbc.gridx = 2;
        add(extendValueLabel, gbc);

        extendCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extendValueLabel.setText(extendCheckBox.isSelected() ? "On" : "Off");
            }
        });

        gbc.insets = new Insets(0, 0, 0, 0);

        // Back Button
        JButton backButton = createButton("Back", createBackButtonListener());
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        // Author Label
        JLabel authorLabel = createLabel("Author: Naoya/Ryota", new Font("Arial", Font.PLAIN, 10));
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 9;
        add(authorLabel, gbc);
    }
    private GridBagConstraints createGridBagConstraints() {
        // Create a new GridBagConstraints object to define how components should be laid out
        GridBagConstraints gbc = new GridBagConstraints();
        // Component will stretch to fill the available space horizontally but not vertically
        // when the display area is larger than the component’s requested size.
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Add 10 pixels of padding around the component (top, left, bottom, right)
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        if (font != null) {
            label.setFont(font);
        }
        return label;
    }
    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(min, max, value);
        slider.setPreferredSize(new Dimension(300, 40));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }
    // Overloaded method to create a JLabel for an integer value
    private JLabel createValueLabel(int value) {
        // Create a label to display the slider's current value
        return new JLabel(String.valueOf(value));
    }
    // Overloaded method to create a JLabel for a string value
    private JLabel createValueLabel(String value) {
        // Create a label to display the checkbox's current state
        return new JLabel(value);
    }
    private JCheckBox createCheckBox(boolean isSelected) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(isSelected);
        return checkBox;
    }
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
    private ActionListener createBackButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showScreen("Main");
                // Handle back button press
                System.out.println("Back button pressed");
            }
        };
    }
}


