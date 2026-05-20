package gui;

import logic.StatsCalculator;

import javax.swing.*;
import java.awt.*;

/**
 * the default home/mainpage shown when program runs
 * lets user start a session or view past sessions
 */

public class HomePanel extends JPanel{
    private MainFrame mainFrame;    //reference back to the frame for switchin
    private JLabel label;
    private JLabel statsLabel;
    private JButton viewSessionsButton;
    private JButton startButton;
    private StatsCalculator statsCalculator;

    public HomePanel(MainFrame mainFrame, StatsCalculator statsCalculator){
        this.mainFrame = mainFrame;
        this.statsCalculator = statsCalculator;

        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        label = new JLabel("Study Tracker", SwingConstants.CENTER);
        label.setFont(Theme.FONT_TITLE);
        label.setForeground(Theme.TEXT_PRIMARY);

        viewSessionsButton = createSecondaryButton("View Sessions");
        startButton = createPrimaryButton("Start");

        //button actions
        startButton.addActionListener(e -> mainFrame.showCard("setup"));
        viewSessionsButton.addActionListener(e -> mainFrame.showSessions());

        //statsLabel = new JLabel("<html><div style='text-align: center;'>Welcome back!<br><br>Your stats will appear here.</div></html>", SwingConstants.CENTER);
        statsLabel = new JLabel("", SwingConstants.CENTER);
        statsLabel.setFont(Theme.FONT_BUTTON);
        statsLabel.setForeground(Theme.TEXT_PRIMARY);

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Theme.BACKGROUND);
        buttonPanel.add(viewSessionsButton);
        buttonPanel.add(startButton);

        add(label, BorderLayout.NORTH);
        add(statsLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void refreshStats(){
        int currentLevel = statsCalculator.getCurrentLevel();
        String currentTitle = statsCalculator.getCurrentTitle();
        int totalExp = statsCalculator.getTotalExp();
        int expNeeded = statsCalculator.getExpNeededForNextLevel();

        String text;

        if (currentLevel == 20){
            text = "<html><div style='text-align: center;'>"
                    + "Lvl 20 " + currentTitle
                    + "<br><br>Total EXP: " + totalExp
                    + "<br>Max level reached!"
                    + "</div></html>";
        } else {
            int nextLevel = statsCalculator.getNextLevel();
            String nextTitle = statsCalculator.getNextTitle();

            text = "<html><div style='text-align: center;'>"
                    + "Lvl " + currentLevel + " " + currentTitle
                    + "<br><br>Total EXP: " + totalExp
                    + "<br>" + expNeeded + " EXP needed for Lvl " + nextLevel + " " + nextTitle
                    + "</div></html>";
        }

        statsLabel.setText(text);
    }

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
}
