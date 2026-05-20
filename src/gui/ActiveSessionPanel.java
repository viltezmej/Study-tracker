package gui;

import javax.swing.*;
import java.awt.*;

/**
 * shows while a study session is running
 * displays current time and lets user end session
 */

public class ActiveSessionPanel extends JPanel{
    private MainFrame mainFrame;
    private JLabel timerLabel;
    private JButton endButton;
    private JButton breakButton;
    private int targetMinutes;

    public ActiveSessionPanel(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(Theme.FONT_TIMER);
        timerLabel.setForeground(Theme.TEXT_PRIMARY);

        endButton = createEndButton("End Session");
        breakButton = createSecondaryButton("Break");

        //TODO: ending should also call SessionManager. It currently just navigates bacK
        // so no session data or XP penalty is recorded
        endButton.addActionListener(e -> mainFrame.showCard("home"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Theme.BACKGROUND);
        buttonPanel.add(breakButton);
        buttonPanel.add(endButton);

        setLayout(new BorderLayout());
        add(timerLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //sets how long this session should run. call before showing panel.
     //@param minutes target duration in minutes

    //TODO: once SessionManager exists, the panel wont need to store targetMinutes
    // in theory it should just call sessionManager.getRemainingSeconds()
    public void setDuration(int minutes){
        this.targetMinutes = minutes;
        timerLabel.setText(formatTime(minutes * 60));
    }

    //formats a number of seconds as HH:MM:SS
    private String formatTime(int totalSeconds){
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds %3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private JButton createEndButton(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BUTTON);
        b.setBackground(Theme.ACCENT_DARK);
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

}
