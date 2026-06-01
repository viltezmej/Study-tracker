package logic;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CSVHandler {
    private static final String STATS_FILE = "user_stats.csv";
    private static final String HISTORY_FILE = "study_history.csv";
    private static final String DELIMITER = ";";

    //---STATS MANAGEMENT---

    public static void saveStats(int totalExp) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STATS_FILE))) {
            writer.println(totalExp);
        } catch (IOException e) {
            System.err.println("Could not save stats to file: " + e.getMessage());
        }
    }

    public static int loadStats() {
        File file = new File(STATS_FILE);
        if (!file.exists()) {
            return 0; //Default
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Could not load stats, resetting to 0: " + e.getMessage());
        }
        return 0;
    }

    //---HISTORY MANAGEMENT---

    public static void saveHistory(ArrayList<SessionLog> logs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            for (SessionLog log : logs) {
                writer.println(log.getDate().toString() + DELIMITER + log.getSubjectName() + DELIMITER + log.getStudiedSeconds() + DELIMITER + log.getExpEarned());
            }
        } catch (IOException e) {
            System.err.println("Could not save history to file: " + e.getMessage());
        }
    }

    public static ArrayList<SessionLog> loadHistory() {
        ArrayList<SessionLog> logs = new ArrayList<>();
        File file = new File(HISTORY_FILE);

        if (!file.exists()) {
            return logs; //Return empty history list if no file exists
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(DELIMITER);
                if (tokens.length == 4) {
                    LocalDateTime date = LocalDateTime.parse(tokens[0]);
                    String subject = tokens[1];
                    int seconds = Integer.parseInt(tokens[2]);
                    int exp = Integer.parseInt(tokens[3]);

                    logs.add(new SessionLog(date, subject, seconds, exp));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading history file: " + e.getMessage());
        }
        return logs;
    }
}
