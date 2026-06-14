import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuizAppTest {

    private QuizManager quizManager;
    private ScoreManager scoreManager;
    private QuizQuestion sampleQuestion;

    @Before
    public void setUp() {
        quizManager = new QuizManager();
        scoreManager = new ScoreManager();
        String[] options = {"Java Virtual Machine", "Java Variable Method", "Java Verified Module", "Java Version Manager"};
        sampleQuestion = new QuizQuestion("What does JVM stand for?", options, 0, "Programming", "Easy");
    }

    @Test
    public void testCorrectAnswer() {
        assertTrue(sampleQuestion.isCorrectAnswer(0));
    }

    @Test
    public void testWrongAnswer() {
        assertFalse(sampleQuestion.isCorrectAnswer(1));
    }

    @Test
    public void testScoreStartsAtZero() {
        quizManager.startQuiz("Sports", "Easy");
        assertEquals(0, quizManager.getScore());
    }

    @Test
    public void testScoreIncreasesOnCorrect() {
        quizManager.startQuiz("Programming", "Easy");
        int correct = quizManager.getCurrentQuestion().getCorrectAnswerIndex();
        quizManager.submitAnswer(correct);
        assertEquals(1, quizManager.getScore());
    }

    @Test
    public void testScoreNoIncreaseOnWrong() {
        quizManager.startQuiz("Sports", "Easy");
        int correct = quizManager.getCurrentQuestion().getCorrectAnswerIndex();
        int wrong = (correct + 1) % 4;
        quizManager.submitAnswer(wrong);
        assertEquals(0, quizManager.getScore());
    }

    @Test
    public void testQuestionsLoad() {
        quizManager.startQuiz("Programming", "Easy");
        assertTrue(quizManager.getTotalQuestions() > 0);
    }

    @Test
    public void testEmptyNameThrowsException() {
        try {
            scoreManager.saveScore("", 3, 5, "Programming", "Easy");
            fail("Should have thrown exception");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testQuestionData() {
        assertEquals("What does JVM stand for?", sampleQuestion.getQuestionText());
        assertEquals("Programming", sampleQuestion.getCategory());
        assertEquals(0, sampleQuestion.getCorrectAnswerIndex());
    }
}