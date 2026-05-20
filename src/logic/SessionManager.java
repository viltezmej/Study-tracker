package logic;
/**
 * Handles the timer state for one study session.
 */
public class SessionManager {
    private int targetTime;
    private int remainingTime; //in seconds
    private int studiedTime; //in seconds
    private boolean running;
    private boolean onBreak;
    private boolean finished;


    public SessionManager(){
        targetTime = 0;
        remainingTime = 0;
        studiedTime = 0;
        running = false;
        onBreak = false;
        finished = false;
    }

    //start a new study session

    public void startSession(int mins){
        targetTime = mins;
        remainingTime = mins*60; //in seconds
        studiedTime = 0; //in seconds
        running = false;
        onBreak = false;
        finished = false;
    }
}
