// ============================================================
// QuizManager.java
// This class manages the quiz logic.
// It loads questions, filters them by category and difficulty,
// shuffles them randomly, tracks score, and checks answers.
// ============================================================

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizManager {

    // List that holds all available questions in the system
    private List<QuizQuestion> allQuestions;

    // List that holds the questions for the current quiz session
    private List<QuizQuestion> currentQuestions;

    // Tracks which question the user is currently on
    private int currentQuestionIndex;

    // Tracks how many answers the user got correct
    private int score;

    // Constructor - loads all questions when QuizManager is created
    public QuizManager() {
        allQuestions = new ArrayList<>();
        currentQuestions = new ArrayList<>();
        loadAllQuestions(); // Fill up the question bank
    }

    // ============================================================
    // This method loads all quiz questions into the question bank.
    // In a real project this could read from a file or database.
    // For simplicity we are hardcoding questions here.
    // ============================================================
    private void loadAllQuestions() {

        // --- PROGRAMMING - EASY ---
        allQuestions.add(new QuizQuestion(
            "What does JVM stand for?",
            new String[]{"Java Virtual Machine", "Java Variable Method", "Java Verified Module", "Java Version Manager"},
            0, "Programming", "Easy"
        ));

        allQuestions.add(new QuizQuestion(
            "Which keyword is used to create an object in Java?",
            new String[]{"create", "object", "new", "make"},
            2, "Programming", "Easy"
        ));

        allQuestions.add(new QuizQuestion(
            "What is the default value of an int in Java?",
            new String[]{"1", "null", "0", "-1"},
            2, "Programming", "Easy"
        ));

        // --- PROGRAMMING - MEDIUM ---
        allQuestions.add(new QuizQuestion(
            "Which of these is NOT a Java access modifier?",
            new String[]{"public", "private", "secured", "protected"},
            2, "Programming", "Medium"
        ));

        allQuestions.add(new QuizQuestion(
            "What does OOP stand for?",
            new String[]{"Object Oriented Programming", "Open Online Process", "Optional Output Program", "Object Output Printing"},
            0, "Programming", "Medium"
        ));

        allQuestions.add(new QuizQuestion(
            "Which collection does NOT allow duplicate values?",
            new String[]{"ArrayList", "LinkedList", "HashSet", "Vector"},
            2, "Programming", "Medium"
        ));

        // --- PROGRAMMING - HARD ---
        allQuestions.add(new QuizQuestion(
            "What is the time complexity of binary search?",
            new String[]{"O(n)", "O(n^2)", "O(log n)", "O(1)"},
            2, "Programming", "Hard"
        ));

        allQuestions.add(new QuizQuestion(
            "Which design pattern ensures only one instance of a class exists?",
            new String[]{"Factory", "Observer", "Singleton", "Decorator"},
            2, "Programming", "Hard"
        ));

        allQuestions.add(new QuizQuestion(
            "What is method overriding in Java?",
            new String[]{"Defining two methods with same name in same class",
                         "Redefining a parent class method in a child class",
                         "Creating a method with no return type",
                         "Calling a method from another class"},
            1, "Programming", "Hard"
        ));

        // --- SPORTS - EASY ---
        allQuestions.add(new QuizQuestion(
            "How many players are in a football team on the field?",
            new String[]{"9", "10", "11", "12"},
            2, "Sports", "Easy"
        ));

        allQuestions.add(new QuizQuestion(
            "Which country won the 2022 FIFA World Cup?",
            new String[]{"Brazil", "France", "Germany", "Argentina"},
            3, "Sports", "Easy"
        ));

        allQuestions.add(new QuizQuestion(
            "In cricket, how many balls are in one over?",
            new String[]{"4", "5", "6", "8"},
            2, "Sports", "Easy"
        ));

        // --- SPORTS - MEDIUM ---
        allQuestions.add(new QuizQuestion(
            "How many Grand Slam tournaments are there in tennis?",
            new String[]{"2", "3", "4", "5"},
            2, "Sports", "Medium"
        ));

        allQuestions.add(new QuizQuestion(
            "Which country has won the most Cricket World Cups?",
            new String[]{"India", "Australia", "West Indies", "Pakistan"},
            1, "Sports", "Medium"
        ));

        allQuestions.add(new QuizQuestion(
            "What is the diameter of a basketball hoop in inches?",
            new String[]{"16", "18", "20", "22"},
            1, "Sports", "Medium"
        ));

        // --- SPORTS - HARD ---
        allQuestions.add(new QuizQuestion(
            "Who holds the record for most Olympic gold medals?",
            new String[]{"Usain Bolt", "Michael Phelps", "Carl Lewis", "Mark Spitz"},
            1, "Sports", "Hard"
        ));

        allQuestions.add(new QuizQuestion(
            "In which year were the first modern Olympic Games held?",
            new String[]{"1896", "1900", "1904", "1892"},
            0, "Sports", "Hard"
        ));

        allQuestions.add(new QuizQuestion(
            "Which cricketer has the most Test centuries?",
            new String[]{"Ricky Ponting", "Sachin Tendulkar", "Jacques Kallis", "Brian Lara"},
            1, "Sports", "Hard"
        ));

        // --- GENERAL KNOWLEDGE - EASY ---
        allQuestions.add(new QuizQuestion(
            "What is the capital of France?",
            new String[]{"London", "Berlin", "Paris", "Madrid"},
            2, "General Knowledge", "Easy"
        ));

        allQuestions.add(new QuizQuestion(
            "How many continents are there on Earth?",
            new String[]{"5", "6", "7", "8"},
            2, "General Knowledge", "Easy"
        ));

        allQuestions.add(new QuizQuestion(
            "What is the largest planet in our solar system?",
            new String[]{"Saturn", "Neptune", "Jupiter", "Uranus"},
            2, "General Knowledge", "Easy"
        ));

        // --- GENERAL KNOWLEDGE - MEDIUM ---
        allQuestions.add(new QuizQuestion(
            "Which element has the chemical symbol 'Au'?",
            new String[]{"Silver", "Aluminum", "Gold", "Copper"},
            2, "General Knowledge", "Medium"
        ));

        allQuestions.add(new QuizQuestion(
            "Who painted the Mona Lisa?",
            new String[]{"Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"},
            2, "General Knowledge", "Medium"
        ));

        allQuestions.add(new QuizQuestion(
            "What is the speed of light in km/s (approximately)?",
            new String[]{"100,000", "200,000", "300,000", "400,000"},
            2, "General Knowledge", "Medium"
        ));

        // --- GENERAL KNOWLEDGE - HARD ---
        allQuestions.add(new QuizQuestion(
            "Which country has the longest coastline in the world?",
            new String[]{"USA", "Russia", "Canada", "Australia"},
            2, "General Knowledge", "Hard"
        ));

        allQuestions.add(new QuizQuestion(
            "What is the smallest country in the world by area?",
            new String[]{"Monaco", "San Marino", "Vatican City", "Liechtenstein"},
            2, "General Knowledge", "Hard"
        ));

        allQuestions.add(new QuizQuestion(
            "In what year did the Berlin Wall fall?",
            new String[]{"1987", "1989", "1991", "1993"},
            1, "General Knowledge", "Hard"
        ));
    }

    // ============================================================
    // This method sets up the quiz for a specific category and difficulty.
    // It filters questions, shuffles them, and resets the score.
    // ============================================================
    public void startQuiz(String category, String difficulty) {
        currentQuestions = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;

        // Filter questions that match the selected category and difficulty
        for (QuizQuestion question : allQuestions) {
            if (question.getCategory().equals(category) &&
                question.getDifficulty().equals(difficulty)) {
                currentQuestions.add(question);
            }
        }

        // Shuffle the questions so they appear in random order every time
        Collections.shuffle(currentQuestions);
    }

    // Returns the current question the user should answer
    public QuizQuestion getCurrentQuestion() {
        if (currentQuestions.isEmpty() || currentQuestionIndex >= currentQuestions.size()) {
            return null;
        }
        return currentQuestions.get(currentQuestionIndex);
    }

    // ============================================================
    // This method checks the user's answer and moves to next question.
    // Returns true if the answer was correct, false if wrong.
    // ============================================================
    public boolean submitAnswer(int selectedIndex) {
        QuizQuestion current = getCurrentQuestion();

        // Exception handling - make sure a question exists
        if (current == null) {
            throw new IllegalStateException("No current question available.");
        }

        boolean isCorrect = current.isCorrectAnswer(selectedIndex);

        // If the answer is correct, increase the score
        if (isCorrect) {
            score++;
        }

        // Move to the next question
        currentQuestionIndex++;

        return isCorrect;
    }

    // Returns true if there are more questions remaining
    public boolean hasNextQuestion() {
        return currentQuestionIndex < currentQuestions.size();
    }

    // Returns the current score
    public int getScore() {
        return score;
    }

    // Returns the total number of questions in this quiz session
    public int getTotalQuestions() {
        return currentQuestions.size();
    }

    // Returns which question number the user is currently on (1-based)
    public int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }

    // Returns the score as a percentage (for display purposes)
    public int getScorePercentage() {
        if (currentQuestions.isEmpty()) return 0;
        return (score * 100) / currentQuestions.size();
    }
}
