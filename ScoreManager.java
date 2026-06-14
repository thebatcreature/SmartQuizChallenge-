// ============================================================
// ScoreManager.java
// This class handles saving and loading high scores.
// Scores are saved to a text file called "highscores.txt".
// This demonstrates FILE HANDLING and EXCEPTION HANDLING.
// ============================================================

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    // The name of the file where scores will be saved
    private static final String SCORE_FILE = "highscores.txt";

    // ============================================================
    // This method saves a new score entry to the text file.
    // Each line in the file contains: PlayerName | Score | Total | Category | Difficulty
    // This block demonstrates FILE WRITE EXCEPTION HANDLING.
    // ============================================================
    public void saveScore(String playerName, int score, int total, String category, String difficulty) {

        // Validate inputs before saving - INPUT VALIDATION
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty.");
        }

        if (score < 0 || total <= 0) {
            throw new IllegalArgumentException("Invalid score values.");
        }

        // Try to open the file and write the score
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {

            // Format: Name | Score | Total | Category | Difficulty
            String entry = playerName.trim() + " | " + score + "/" + total +
                           " | " + category + " | " + difficulty;
            writer.write(entry);
            writer.newLine(); // Move to next line

        } catch (IOException e) {
            // This block handles file write errors
            System.err.println("Error saving score: " + e.getMessage());
            throw new RuntimeException("Could not save score to file. Please check file permissions.");
        }
    }

    // ============================================================
    // This method reads all saved scores from the text file.
    // Returns a list of strings where each string is one score entry.
    // This block demonstrates FILE READ EXCEPTION HANDLING.
    // ============================================================
    public List<String> loadScores() {
        List<String> scores = new ArrayList<>();

        File file = new File(SCORE_FILE);

        // If the file does not exist yet, return empty list (no scores saved yet)
        if (!file.exists()) {
            return scores;
        }

        // Try to read the file line by line
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Only add non-empty lines
                if (!line.trim().isEmpty()) {
                    scores.add(line);
                }
            }

        } catch (FileNotFoundException e) {
            // This handles the case where the file is missing
            System.err.println("Score file not found: " + e.getMessage());

        } catch (IOException e) {
            // This handles general file reading errors
            System.err.println("Error reading scores: " + e.getMessage());
            throw new RuntimeException("Could not read scores from file.");
        }

        return scores;
    }

    // ============================================================
    // This method clears all saved scores by deleting the file.
    // ============================================================
    public void clearScores() {
        File file = new File(SCORE_FILE);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("Could not clear scores. File may be in use.");
            }
        }
    }

    // Returns true if the score file exists
    public boolean scoresExist() {
        return new File(SCORE_FILE).exists();
    }
}
