package gui;

import logic.SessionManager;
import logic.StatsCalculator;

import javax.swing.*;
import java.awt.*;

/**
 * owns the JFrame, switches between panels using CardLayout(home, active session, etc.)
 * part of the presentation layer(refer to structure diagram)
 */

public class MainFrame {
    private JFrame frame;
    private JPanel cardContainer;   //holds all panels, only shows one at atime
    private CardLayout cardLayout;  //controls which panel is visible
    private HomePanel homePanel;
    private ActiveSessionPanel activeSessionPanel;
    private SessionSetupPanel sessionSetupPanel;
    private SessionManager sessionManager;
    private SessionPanel sessionPanel;
    private StatsCalculator statsCalculator;
    private int width;
    private int height;

    public MainFrame(int w, int h){
        width = w;
        height = h;
        frame = new JFrame();

        sessionManager = new SessionManager();
        statsCalculator = new StatsCalculator();

        //Load files from disk
        int savedExp = logic.CSVHandler.loadStats();
        statsCalculator.setTotalExp(savedExp);

        java.util.ArrayList<logic.SessionLog> savedLogs = logic.CSVHandler.loadHistory();
        sessionManager.getSessionLogs().addAll(savedLogs);
        //end

        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);

        homePanel = new HomePanel(this, statsCalculator);
        sessionSetupPanel = new SessionSetupPanel(this);
        activeSessionPanel = new ActiveSessionPanel(this, sessionManager);
        sessionPanel = new SessionPanel(this, sessionManager);

        cardContainer.add(homePanel, "home");
        cardContainer.add(sessionSetupPanel, "setup");
        cardContainer.add(activeSessionPanel, "active");
        cardContainer.add(sessionPanel, "sessions");

    }

    //configures the frame and makes it visible, called once at startup
    public void setUpGUI() {
        frame.setContentPane(cardContainer);
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Study Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        showCard("home");
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void showCard(String name){
        if(name.equals("setup")){
            sessionSetupPanel.reset();
        }

        if(name.equals("home")){
            homePanel.refreshStats();
        }

        if(name.equals("sessions")){
            sessionPanel.refresh();
        }

        cardLayout.show(cardContainer, name);
    }

    //sets up active session panel for new session and shows it
    public void startActiveSession(int minutes, String subjectName) {
        sessionManager.startSession(minutes, subjectName);
        activeSessionPanel.setDuration(minutes);
        activeSessionPanel.startTimer();
        showCard("active");
    }

    public void completeActiveSession() {
        activeSessionPanel.stopTimer();

        int expEarned = statsCalculator.calculateCompletedSessionExp(sessionManager.getStudiedTime());
        statsCalculator.addExp(expEarned);

        sessionManager.saveCurrentSession(expEarned);

        //Save data changes to disk
        logic.CSVHandler.saveStats(statsCalculator.getTotalExp());
        logic.CSVHandler.saveHistory(sessionManager.getSessionLogs());
        //end

        sessionPanel.refresh();
        homePanel.refreshStats();

        showCard("home");
    }

    public void endActiveSession(){
        activeSessionPanel.stopTimer();
        sessionManager.endSessionEarly();

        int expEarned = statsCalculator.calculateEarlyEndPenalty(sessionManager.getTargetTime());
        statsCalculator.addExp(expEarned); //this will be negative

        sessionManager.saveCurrentSession(expEarned);

        //Save data changes to disk
        logic.CSVHandler.saveStats(statsCalculator.getTotalExp());
        logic.CSVHandler.saveHistory(sessionManager.getSessionLogs());
        //end

        sessionPanel.refresh();
        homePanel.refreshStats();

        showCard("home");
    }

    public void showSessions() {
        sessionPanel.refresh();
        showCard("sessions");
    }

    public void clearHistory(){
        sessionManager.clearSessionLogs();

        //Save empty history state to disk
        logic.CSVHandler.saveHistory(sessionManager.getSessionLogs());
        //end

        sessionPanel.refresh();
        homePanel.refreshStats();
    }
}
