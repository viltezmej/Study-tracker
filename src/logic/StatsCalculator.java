package logic;

/**
 * Calculates EXP, levels, and level titles.
 * Part of the logic layer.
 *
 * TODO: when file saving is added, totalExp can be loaded from a file
 */

public class StatsCalculator {
    private static final int MAX_LEVEL = 20;
    private static final int EXP_PER_LEVEL = 100;
    private static final int COMPLETION_BONUS = 10;

    private int totalExp;

    private final String[] levelTitles = {
            "Rookie",
            "Apprentice",
            "Amateur",
            "Capable Amateur",
            "Journeyman",
            "Accomplished Journeyman",
            "Adept",
            "Expert",
            "Scholar",
            "Exemplar",
            "Mentor",
            "Guru",
            "Master",
            "Grandmaster",
            "Preeminent",
            "Supreme",
            "Legend",
            "Sigma",
            "Mastermind",
            "Mafia boss"
    };

    public StatsCalculator(){
        totalExp = 0;
    }

    public int calculateCompletedSessionExp(int studiedSeconds){
        int studiedMinutes = studiedSeconds / 60;
        return studiedMinutes + COMPLETION_BONUS;
    }

    public int calculatEarlyEndPenalty(int targetMinutes){
        int penalty = targetMinutes / 2;

        if(penalty < 5){
            penalty = 5;
        }

        if(penalty > 30){
            penalty = 30;
        }

        return -penalty;
    }

    public void addExp(int expChange){
        totalExp += expChange;

        if (totalExp < 0){
            totalExp = 0;
        }
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;

        if (this.totalExp < 0){
            this.totalExp = 0;
        }
    }

    public int getCurrentLevel(){
        int level = (totalExp/EXP_PER_LEVEL) + 1;

        if (level > MAX_LEVEL){
            level = MAX_LEVEL;
        }
        return level;
    }

    public String getCurrentTitle() {
        return levelTitles[getCurrentLevel() - 1];
    }

    public int getExpNeededForNextLevel(){
        if (getCurrentLevel() == MAX_LEVEL){
            return 0;
        }

        int nextLevelExp = getCurrentLevel() * EXP_PER_LEVEL;
        return nextLevelExp - totalExp;
    }

    public int getNextLevel(){
        if(getCurrentLevel() == MAX_LEVEL){
            return MAX_LEVEL;
        }

        return getCurrentLevel() + 1;
    }

    public String getNextTitle(){

        if (getCurrentLevel() == MAX_LEVEL){
            return levelTitles[MAX_LEVEL - 1];
        }

        return levelTitles[getNextLevel() - 1];
    }

}
