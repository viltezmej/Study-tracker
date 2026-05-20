package gui;

import javax.swing.*;
import java.awt.*;

public class LevelBadge extends JComponent {
    private int level;
    private static final int SIZE = 72;

    public LevelBadge(int level){
        this.level = level;
        setPreferredSize(new Dimension(SIZE, SIZE));
        setMaximumSize(new Dimension(SIZE, SIZE));
        setMinimumSize(new Dimension(SIZE, SIZE));
    }

    public void setLevel(int level) {
        this.level = level;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //draw filled circle
        g2.setColor(Theme.ACCENT);
        g2.fillOval(0, 0, SIZE, SIZE);

        //draw the number centered
        g2.setColor(Theme.TEXT_ACCENT);
        g2.setFont(new Font("SansSerif", Font.BOLD, 32));
        String text = String.valueOf(level);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (SIZE - textWidth) / 2;
        int y = (SIZE + textHeight) / 2 - 4;
        g2.drawString(text, x, y);

        g2.dispose();
    }
}
