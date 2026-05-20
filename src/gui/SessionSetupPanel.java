package gui;

import javax.swing.*;
import java.awt.*;

/**
 * screen where user chooses how long to study
 * shows preset duration buttons (15/30/45/60 min) and a custom input field
 */

public class SessionSetupPanel extends JPanel {
    private MainFrame mainFrame;
    private JLabel titleLabel;
    private JLabel promptLabel;
    private JTextField minutesField;
    private JButton preset15;
    private JButton preset30;
    private JButton preset45;
    private JButton preset60;
    private JButton beginButton;
    private JButton cancelButton;
    private JLabel subjectLabel;
    private JTextField subjectField;

    public SessionSetupPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        titleLabel = new JLabel("New Session", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        promptLabel = new JLabel("How long do you want to study? (minutes)", SwingConstants.CENTER);
        promptLabel.setFont(Theme.FONT_BODY);
        promptLabel.setForeground(Theme.TEXT_PRIMARY);

        minutesField = new JTextField(5);
        minutesField.setHorizontalAlignment(SwingConstants.CENTER);
        minutesField.setFont(new Font("SansSerif", Font.PLAIN, 24));
        minutesField.setBackground(Theme.SURFACE);
        minutesField.setForeground(Theme.TEXT_PRIMARY);
        minutesField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        //TODO: preset are currently hardcoded and they could come from the logic layer.
        preset15 = createPresetButton("15");
        preset30 = createPresetButton("30");
        preset45 = createPresetButton("45");
        preset60 = createPresetButton("60");

        beginButton = createPrimaryButton("Begin");
        cancelButton = createSecondaryButton("Cancel");

        //preset buttons fill the text field
        preset15.addActionListener(e -> minutesField.setText("15"));
        preset30.addActionListener(e -> minutesField.setText("30"));
        preset45.addActionListener(e -> minutesField.setText("45"));
        preset60.addActionListener(e -> minutesField.setText("60"));

        beginButton.addActionListener(e -> handleBeginClicked());
        cancelButton.addActionListener(e -> mainFrame.showCard("home"));


        //enter subject name
        subjectLabel = new JLabel("Subject name (optional)", SwingConstants.CENTER);
        subjectLabel.setFont(Theme.FONT_BODY);
        subjectLabel.setForeground(Theme.TEXT_PRIMARY);

        subjectField = new JTextField(15);
        subjectField.setHorizontalAlignment(SwingConstants.CENTER);
        subjectField.setFont(Theme.FONT_BODY);
        subjectField.setBackground(Theme.SURFACE);
        subjectField.setForeground(Theme.TEXT_PRIMARY);
        subjectField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        //preset row
        JPanel presetPanel = new JPanel();
        presetPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        presetPanel.setBackground(Theme.BACKGROUND);
        presetPanel.add(preset15);
        presetPanel.add(preset30);
        presetPanel.add(preset45);
        presetPanel.add(preset60);

        //action row
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionPanel.setBackground(Theme.BACKGROUND);
        actionPanel.add(cancelButton);
        actionPanel.add(beginButton);

        //all content stacked in the center of the screen
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Theme.BACKGROUND);

        //centering
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        minutesField.setAlignmentX(Component.CENTER_ALIGNMENT);
        minutesField.setMaximumSize(new Dimension(150, 40));

        subjectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subjectField.setAlignmentX(Component.CENTER_ALIGNMENT);
        subjectField.setMaximumSize(new Dimension(220, 40));

        presetPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        presetPanel.setMaximumSize(presetPanel.getPreferredSize());
        actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.setMaximumSize(actionPanel.getPreferredSize());


        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(promptLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(minutesField);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(presetPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(subjectLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(subjectField);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(actionPanel);
        contentPanel.add(Box.createVerticalGlue());

        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    //resets panel to its default state, call before showing it
    //once timer is real, this function will also need to be called for ActiveSessionPanel
    public void reset(){
        minutesField.setText("");
        subjectField.setText("");
    }

    //validates the duration input and starts a session if valid
    //shows an error dialog if input is missing or not a number
    private void handleBeginClicked(){
        String input = minutesField.getText().trim();
        int minutes;
        try{
            minutes = Integer.parseInt(input);
        }catch (NumberFormatException ex){
            showError("Please enter a valid number.");
            return;
        }

        // TODO: move this check into SessionManager once it exists
        if(minutes <= 0){
            showError("Please enter a positive number of minutes.");
            return;
        }
        String subjectName = subjectField.getText().trim();
        mainFrame.startActiveSession(minutes, subjectName);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid input", JOptionPane.WARNING_MESSAGE);
    }

    //TODO: button functions repeat across classes. should create a ButtonCreator class
    private JButton createPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BUTTON);
        b.setBackground(Theme.ACCENT);
        b.setForeground(Theme.TEXT_ACCENT);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        return b;
    }

    private JButton createSecondaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BUTTON);
        b.setBackground(Theme.ACCENT_LIGHT);
        b.setForeground(Theme.TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        return b;
    }

    private JButton createPresetButton(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_LARGE);
        b.setBackground(Theme.SURFACE);
        b.setForeground(Theme.TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(14, 22, 14, 22)
        ));
        return b;
    }
}
