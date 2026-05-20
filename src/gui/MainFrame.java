package gui;

import logic.SessionManager;
import logic.StatsCalculator;

import javax.swing.*;
import java.awt.*;

/**
 * owns the JFrame, switches between panels using CardLayout(home, active session, etc.)
 * part of the presentation layer(refer to structure diagram)
 * TODO: mainframe will eventually need a reference to StudyTrackerCore (the logic layer coordinator)
 *  and pass it down to panels that need it. Currently panels only know abt MainFrame
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
        frame.setTitle("Study Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout.show(cardContainer, "home"); //start on homescreen
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
    //TODO: also call sessionManager here once SessionManager exists
    // MainFrame coordinates between GUI and logic

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

        sessionPanel.refresh();
        homePanel.refreshStats();

        showCard("home");
    }

    public void endActiveSession(){
        activeSessionPanel.stopTimer();
        sessionManager.endSessionEarly();

        int expEarned = statsCalculator.calculatEarlyEndPenalty(sessionManager.getStudiedTime());
        statsCalculator.addExp(expEarned); //this will be negative

        sessionManager.saveCurrentSession(expEarned);

        sessionPanel.refresh();
        homePanel.refreshStats();

        showCard("home");
    }

    public void showSessions() {
        sessionPanel.refresh();
        showCard("sessions");
    }
}
