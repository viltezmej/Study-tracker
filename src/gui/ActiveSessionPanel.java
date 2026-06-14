package gui;

import logic.SessionManager;

import javax.swing.*;
import java.awt.*;

/**
 * shows while a study session is running
 * displays current time and lets user end session
 */

public class ActiveSessionPanel extends JPanel{
    private MainFrame mainFrame;
    private JLabel timerLabel;
    private JLabel statusLabel;
    private JButton endButton;
    private JButton breakButton;
    private int targetMinutes;
    private Timer swingTimer;
    private SessionManager sessionManager;



    public ActiveSessionPanel(MainFrame mainFrame, SessionManager sessionManager) {
        this.mainFrame = mainFrame;
        this.sessionManager = sessionManager;

        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(Theme.FONT_TIMER);
        timerLabel.setForeground(Theme.TEXT_PRIMARY);

        statusLabel = new JLabel("Studying in progress", SwingConstants.CENTER);
        statusLabel.setFont(Theme.FONT_BODY);
        statusLabel.setForeground(Theme.TEXT_PRIMARY);

        endButton = ButtonCreator.destructive("End Session");
        breakButton = ButtonCreator.secondary("Break");

        swingTimer = new Timer(1000, e -> updateTimer());

        endButton.addActionListener(e -> mainFrame.endActiveSession());

        breakButton.addActionListener(e -> {
            sessionManager.toggleBreak();
            updateStatus();
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Theme.BACKGROUND);

        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(timerLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(statusLabel);
        centerPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Theme.BACKGROUND);
        buttonPanel.add(breakButton);
        buttonPanel.add(endButton);

        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //sets how long this session should run. call before showing panel.
     //@param minutes target duration in minutes

    public void setDuration(int minutes){
        this.targetMinutes = minutes;
        timerLabel.setText(formatTime(minutes * 60));
    }

    //start the visual timer
    public void startTimer(){
        timerLabel.setText(formatTime(sessionManager.getRemainingTime()));
        statusLabel.setText("Studying in progress");
        breakButton.setText("Break");

        if(swingTimer.isRunning()){
            swingTimer.stop();
        }

        swingTimer.start();
    }

    //stop the visual timer

    public void stopTimer(){
        if(swingTimer.isRunning()){
            swingTimer.stop();
        }
    }

    //update timer once per second
    private void updateTimer(){
        sessionManager.tick();
        timerLabel.setText(formatTime(sessionManager.getRemainingTime()));

        if(sessionManager.isFinished()){
            stopTimer();
            mainFrame.completeActiveSession();
        }
    }

    private void updateStatus(){
        if(sessionManager.isOnBreak()){
            statusLabel.setText("Break time");
            breakButton.setText("Resume");
        }else{
            statusLabel.setText("Studying in progress");
            breakButton.setText("Break");
        }
    }

    //formats a number of seconds as HH:MM:SS
    private String formatTime(int totalSeconds){
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds %3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
