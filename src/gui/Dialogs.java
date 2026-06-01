package gui;

import javax.swing.*;
import java.awt.*;

/**
 * custom themed dialogs to replace JOptionPane
 */

public class Dialogs {

    private Dialogs(){
        //utility class
    }

    //shows a warning/info dialog with OK button
    public static void showMessage(Component parent, String title, String message) {
        JDialog dialog = buildDialog(parent, title);

        JLabel messageLabel = buildMessageLabel(message);

        JButton okButton = ButtonCreator.primary("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Theme.SURFACE);
        buttonPanel.add(okButton);

        layoutDialog(dialog, messageLabel, buttonPanel);
    }

        //shows confirmation dialog with yes/no buttons
        public static boolean confirm(Component parent, String title, String message){
            JDialog dialog = buildDialog(parent, title);
            final boolean[] result = {false};

            JLabel messageLabel =buildMessageLabel(message);

            JButton noButton = ButtonCreator.secondary("Cancel");
            noButton.addActionListener(e -> dialog.dispose());

            JButton yesButton = ButtonCreator.destructive("Yes, delete");
            yesButton.addActionListener(e -> {
                result[0] = true;
                dialog.dispose();
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttonPanel.setBackground(Theme.SURFACE);
            buttonPanel.add(noButton);
            buttonPanel.add(yesButton);

            layoutDialog(dialog, messageLabel, buttonPanel);

            return result[0];
    }

    //helpers

    private static JDialog buildDialog(Component parent, String title){
        Window owner = SwingUtilities.getWindowAncestor(parent);
        JDialog dialog = new JDialog(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        return dialog;
    }

    private static JLabel buildMessageLabel(String message){
        JLabel label = new JLabel("<html><div style='width: 280px;'>" + message + "</div></html>");
        label.setFont(Theme.FONT_BODY);
        label.setForeground(Theme.TEXT_PRIMARY);
        return label;
    }

    private static void layoutDialog(JDialog dialog, JLabel messageLabel, JPanel buttonPanel){
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Theme.SURFACE);
        content.setBorder(BorderFactory.createEmptyBorder(24, 28, 20, 28));

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(messageLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(buttonPanel);

        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.setVisible(true);
    }
}
