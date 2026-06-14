// ============================================================
// QuizQuestion.java
// This class represents a single quiz question.
// It holds the question text, four options, correct answer,
// category, and difficulty level.
// This is a simple data model class (like a blueprint for a question).
// ============================================================

public class QuizQuestion {

    // These variables store all the details of one question
    private String questionText;      // The actual question
    private String[] options;         // Four answer choices
    private int correctAnswerIndex;   // Index of the correct option (0, 1, 2, or 3)
    private String category;          // e.g., "Programming", "Sports", "General Knowledge"
    private String difficulty;        // e.g., "Easy", "Medium", "Hard"

    // Constructor - this runs when we create a new QuizQuestion object
    public QuizQuestion(String questionText, String[] options, int correctAnswerIndex,
                        String category, String difficulty) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    // --- Getter methods --- //
    // These methods return the values stored in this question

    // Returns the question text
    public String getQuestionText() {
        return questionText;
    }

    // Returns the array of four answer options
    public String[] getOptions() {
        return options;
    }

    // Returns the index of the correct answer
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    // Returns the category of this question
    public String getCategory() {
        return category;
    }

    // Returns the difficulty level of this question
    public String getDifficulty() {
        return difficulty;
    }

    // This method checks if the selected answer is correct
    // It returns true if correct, false if wrong
    public boolean isCorrectAnswer(int selectedIndex) {
        return selectedIndex == correctAnswerIndex;
    }
}
