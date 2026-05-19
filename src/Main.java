import java.awt.*;
import javax.swing.*;

public class Main {
    private JFrame frame;
    private JLabel label;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private int width;
    private int height;
//test
    public Main(int w, int h){
        frame = new JFrame();
        label = new JLabel("Study Tracker app", SwingConstants.CENTER);
        button1 = new JButton("View sessions");
        button2 = new JButton("Start");
        button3 = new JButton("End");
        button4 = new JButton("Break");

        width = w;
        height = h;
    }

    public void setUpGUI() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button4);
        buttonPanel.add(button3);

        Container cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(label, BorderLayout.NORTH);
        cp.add(buttonPanel,BorderLayout.SOUTH);

        frame.setSize(width,height);
        frame.setTitle("GUI Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
