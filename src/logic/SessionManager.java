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
        running = true;
        onBreak = false;
        finished = false;
    }

    //called once every second by the GUI timer
    public void tick(){
        if (!running || onBreak || finished){
            return;
        }

        if (remainingTime > 0){
            remainingTime--;
            studiedTime++;
        }

        if(remainingTime <= 0){
            remainingTime = 0;
            running = false;
            finished = true;
        }
    }

    public void toggleBreak(){
        if (!finished) {
            onBreak = !onBreak;
        }
    }

    public void endSessionEarly(){
        running = false;
        finished = true;

        //TODO: trigger EXP penalty in StatsCalculator
    }

    public int getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getStudiedTime() {
        return studiedTime;
    }

    public void setStudiedTime(int studiedTime) {
        this.studiedTime = studiedTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isOnBreak() {
        return onBreak;
    }

    public void setOnBreak(boolean onBreak) {
        this.onBreak = onBreak;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
