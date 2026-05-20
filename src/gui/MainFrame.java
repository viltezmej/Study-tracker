package gui;

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
    private int width;
    private int height;

    public MainFrame(int w, int h){
        width = w;
        height = h;
        frame = new JFrame();

        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);

        homePanel = new HomePanel(this);
        sessionSetupPanel = new SessionSetupPanel(this);
        activeSessionPanel = new ActiveSessionPanel(this);

        cardContainer.add(homePanel, "home");
        cardContainer.add(sessionSetupPanel, "setup");
        cardContainer.add(activeSessionPanel, "active");
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
        cardLayout.show(cardContainer, name);
    }

    //sets up active session panel for new session and shows it
    //TODO: also call sessionManager here once SessionManager exists
    // MainFrame coordinates between GUI and logic

    public void startActiveSession(int minutes) {
        activeSessionPanel.setDuration(minutes);
        showCard("active");
    }
}
