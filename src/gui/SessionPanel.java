package gui;

import logic.SessionLog;
import logic.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * shows past study sessions
 * part of the presentation layer
 */

public class SessionPanel extends JPanel {
    private MainFrame mainFrame;
    private SessionManager sessionManager;
    private JLabel titleLabel;
    private JTextArea sessionsArea;
    private JButton backButton;

    public SessionPanel(MainFrame mainFrame, SessionManager sessionManager) {
        this.mainFrame = mainFrame;
        this.sessionManager = sessionManager;

        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        titleLabel = new JLabel("Past Sessions", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        sessionsArea = new JTextArea();
        sessionsArea.setEditable(false);
        sessionsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        sessionsArea.setBackground(Theme.SURFACE);
        sessionsArea.setForeground(Theme.TEXT_PRIMARY);
        sessionsArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(sessionsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1));
        scrollPane.getViewport().setBackground(Theme.SURFACE);

        backButton = createSecondaryButton("Back");
        backButton.addActionListener(e -> mainFrame.showCard("home"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Theme.BACKGROUND);
        buttonPanel.add(backButton);

        setLayout(new BorderLayout(0, 20));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        ArrayList<SessionLog> logs = sessionManager.getSessionLogs();

        if (logs.isEmpty()) {
            sessionsArea.setText("No sessions yet.");
            return;
        }

        StringBuilder text = new StringBuilder();

        text.append(String.format("%-17s %-18s %-12s %-10s\n",
                "Date", "SubjectName", "TimeStudied", "EXPearned"));
        text.append("------------------------------------------------------------\n");

        for (SessionLog log : logs) {
            text.append(String.format("%-17s %-18s %-12s %-10d\n",
                    log.getFormattedDate(),
                    shortenSubject(log.getSubjectName()),
                    log.getFormattedStudyTime(),
                    log.getExpEarned()));
        }

        sessionsArea.setText(text.toString());
        sessionsArea.setCaretPosition(0);
    }

    private String shortenSubject(String subject) {
        if (subject.length() > 16) {
            return subject.substring(0, 13) + "...";
        }

        return subject;
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