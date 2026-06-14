package gui;

import logic.StatsCalculator;

import javax.swing.*;
import java.awt.*;

/**
 * the default home/mainpage shown when program runs
 * lets user start a session or view past sessions
 */

public class HomePanel extends JPanel{
    private MainFrame mainFrame; //reference back to the frame for switchin
    private JLabel titleLabel;
    private JLabel statsLabel;
    private JButton viewSessionsButton;
    private JButton startButton;
    private StatsCalculator statsCalculator;
    private LevelBadge levelBadge;
    private JProgressBar expProgressBar;

    public HomePanel(MainFrame mainFrame, StatsCalculator statsCalculator){
        this.mainFrame = mainFrame;
        this.statsCalculator = statsCalculator;

        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        titleLabel = new JLabel("Study Tracker", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        viewSessionsButton = ButtonCreator.secondary("View Sessions");
        startButton = ButtonCreator.primary("Start");

        //button actions
        startButton.addActionListener(e -> mainFrame.showCard("setup"));
        viewSessionsButton.addActionListener(e -> mainFrame.showSessions());

        statsLabel = new JLabel("", SwingConstants.CENTER);
        statsLabel.setFont(Theme.FONT_BUTTON);
        statsLabel.setForeground(Theme.TEXT_PRIMARY);

        levelBadge = new LevelBadge(1);
        expProgressBar = new JProgressBar(0, 100);
        expProgressBar.setValue(0);
        expProgressBar.setForeground(Theme.ACCENT);
        expProgressBar.setBackground(Theme.ACCENT_BG);
        expProgressBar.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1));
        expProgressBar.setStringPainted(false);
        expProgressBar.setPreferredSize(new Dimension(280, 16));
        expProgressBar.setMaximumSize(new Dimension(280, 16));

        setLayout(new BorderLayout());

        JPanel statsCard = new JPanel();
        statsCard.setLayout(new BoxLayout(statsCard, BoxLayout.Y_AXIS));
        statsCard.setBackground(Theme.SURFACE);
        statsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER, 1),
                BorderFactory.createEmptyBorder(24, 32, 24, 32)
        ));

        levelBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
        expProgressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsCard.add(levelBadge);
        statsCard.add(Box.createVerticalStrut(16));
        statsCard.add(expProgressBar);
        statsCard.add(Box.createVerticalStrut(10));
        statsCard.add(statsLabel);

        JPanel cardWrapper = new JPanel(new GridBagLayout());
        cardWrapper.setBackground(Theme.BACKGROUND);
        cardWrapper.add(statsCard);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Theme.BACKGROUND);
        buttonPanel.add(viewSessionsButton);
        buttonPanel.add(startButton);

        add(titleLabel, BorderLayout.NORTH);
        add(cardWrapper, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshStats();
    }

    public void refreshStats(){
        int currentLevel = statsCalculator.getCurrentLevel();
        String currentTitle = statsCalculator.getCurrentTitle();
        int totalExp = statsCalculator.getTotalExp();
        int expNeeded = statsCalculator.getExpNeededForNextLevel();
        String text;

        levelBadge.setLevel(currentLevel);

        if (currentLevel == 20) {
            expProgressBar.setValue(100);
        } else {
            expProgressBar.setValue(100 - expNeeded);
        }

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
}
