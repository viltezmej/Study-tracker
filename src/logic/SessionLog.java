package logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores information about one finished study session.
 * Part of the data/logic layer.
 */

public class SessionLog {
    private LocalDateTime date;
    private String subjectName;
    private int studiedSeconds;
    private int expEarned;

    public SessionLog(String subjectName, int studiedSeconds, int expEarned){
        this.date = LocalDateTime.now();
        this.subjectName = subjectName;
        this.studiedSeconds = studiedSeconds;
        this.expEarned = expEarned;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getStudiedSeconds() {
        return studiedSeconds;
    }

    public void setStudiedSeconds(int studiedSeconds) {
        this.studiedSeconds = studiedSeconds;
    }

    public int getExpEarned() {
        return expEarned;
    }

    public void setExpEarned(int expEarned) {
        this.expEarned = expEarned;
    }

    public String getFormattedStudyTime(){
        int hours = studiedSeconds / 3600;
        int minutes = (studiedSeconds %3600) / 60;
        int seconds = studiedSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    // Overloaded constructor
    public SessionLog(LocalDateTime date, String subjectName, int studiedSeconds, int expEarned) {
        this.date = date;
        this.subjectName = subjectName;
        this.studiedSeconds = studiedSeconds;
        this.expEarned = expEarned;
    }

    public LocalDateTime getDate() {
        return this.date;
    }
}
