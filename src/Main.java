import gui.MainFrame;

public class Main {

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true"); //to fix font

        MainFrame mainFrame = new MainFrame(640, 480);
        mainFrame.setUpGUI();
    }
}
