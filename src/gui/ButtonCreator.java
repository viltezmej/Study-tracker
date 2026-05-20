package gui;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Class for styled buttons used across the GUI.
 */
public class ButtonCreator {

    private static final Border STANDARD_PADDING =
            BorderFactory.createEmptyBorder(10, 24, 10, 24);

    private static final Border PRESET_PADDING =
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Theme.BORDER, 1),
                    BorderFactory.createEmptyBorder(14, 22, 14, 22)
            );

    private ButtonCreator() {
        // utility class — not meant to be instantiated
    }

    //Filled accent colored button for primary actions (start, begin)
    public static JButton primary(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BUTTON);
        b.setBackground(Theme.ACCENT);
        b.setForeground(Theme.TEXT_ACCENT);
        b.setFocusPainted(false);
        b.setBorder(STANDARD_PADDING);
        return b;
    }

    //muted button for secondary actions (cancel, view session, back, break)
    public static JButton secondary(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BUTTON);
        b.setBackground(Theme.ACCENT_LIGHT);
        b.setForeground(Theme.TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(STANDARD_PADDING);
        return b;
    }

    //dark accent button (End Session)
    public static JButton destructive(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_BUTTON);
        b.setBackground(Theme.ACCENT_DARK);
        b.setForeground(Theme.TEXT_ACCENT);
        b.setFocusPainted(false);
        b.setBorder(STANDARD_PADDING);
        return b;
    }

    //wite outlined button for preset choices (15/30/45/60)
    public static JButton preset(String text) {
        JButton b = new JButton(text);
        b.setFont(Theme.FONT_LARGE);
        b.setBackground(Theme.SURFACE);
        b.setForeground(Theme.TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(PRESET_PADDING);
        return b;
    }
}
