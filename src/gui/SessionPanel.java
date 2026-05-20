package gui;

import logic.SessionLog;
import logic.SessionManager;
import javax.swing.table.DefaultTableModel;
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
    private JTable sessionsTable;
    private DefaultTableModel tableModel;
    private JButton backButton;

    public SessionPanel(MainFrame mainFrame, SessionManager sessionManager) {
        this.mainFrame = mainFrame;
        this.sessionManager = sessionManager;

        setBackground(Theme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        titleLabel = new JLabel("Past Sessions", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        String[] columnNames = {"Date", "Subject", "Time", "EXP"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sessionsTable = new JTable(tableModel);
        sessionsTable.setFont(Theme.FONT_BODY);
        sessionsTable.setForeground(Theme.TEXT_PRIMARY);
        sessionsTable.setBackground(Theme.SURFACE);
        sessionsTable.setRowHeight(28);
        sessionsTable.setShowGrid(false);
        sessionsTable.setIntercellSpacing(new Dimension(0, 0));
        sessionsTable.setFillsViewportHeight(true);

        //header
        sessionsTable.getTableHeader().setFont(Theme.FONT_BUTTON);
        sessionsTable.getTableHeader().setBackground(Theme.ACCENT_LIGHT);
        sessionsTable.getTableHeader().setForeground(Theme.TEXT_PRIMARY);
        sessionsTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        sessionsTable.getTableHeader().setReorderingAllowed(false);

        // body cell renderer for left padding
        javax.swing.table.DefaultTableCellRenderer cellRenderer = new javax.swing.table.DefaultTableCellRenderer();
        cellRenderer.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        for (int i = 0; i < sessionsTable.getColumnCount(); i++) {
            sessionsTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(sessionsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.BORDER, 1));
        scrollPane.getViewport().setBackground(Theme.SURFACE);

        backButton = ButtonCreator.secondary("Back");
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
        // clear existing rows
        tableModel.setRowCount(0);

        ArrayList<SessionLog> logs = sessionManager.getSessionLogs();

        for (SessionLog log : logs) {
            Object[] row = {
                    log.getFormattedDate(),
                    log.getSubjectName(),
                    log.getFormattedStudyTime(),
                    log.getExpEarned()
            };
            tableModel.addRow(row);
        }
    }
}