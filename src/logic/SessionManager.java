package logic;

import java.util.ArrayList;

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
    private boolean currentSessionSaved;
    private String currentSubject;

    private ArrayList<SessionLog> sessionLogs;


    public SessionManager(){
        targetTime = 0;
        remainingTime = 0;
        studiedTime = 0;
        running = false;
        onBreak = false;
        finished = false;
        currentSessionSaved = false;
        currentSubject = "";

        sessionLogs = new ArrayList<>();
    }

    //start a new study session
    public void startSession(int mins, String subjectName){
        targetTime = mins;
        remainingTime = mins*60; //in seconds
        studiedTime = 0; //in seconds
        running = true;
        onBreak = false;
        finished = false;
        currentSessionSaved = false;
        currentSubject = subjectName;
    }
    //tmp
    public void startSession(int minutes) {
        startSession(minutes, "");
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

    public void saveCurrentSession(int expEarned){
        if (currentSessionSaved){
            return;
        }

        SessionLog log = new SessionLog(currentSubject, studiedTime, expEarned);
        sessionLogs.add(log);

        currentSessionSaved = true;
        //TODO: later save this log using FileHandler
    }

//    private int calculateExp(boolean completed) {
//        int studiedMinutes = studiedTime / 60;
//
//        if (completed) {
//            return studiedMinutes;
//        }
//
//        //temp penalty until the final lvl system is implemented
//        //TODO: decide exact penalty
//        return -5;
//    }

    public ArrayList<SessionLog> getSessionLogs(){
        return sessionLogs;
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
