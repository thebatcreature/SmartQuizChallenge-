// ============================================================
// QuizWindow.java
// Handles full quiz flow - Setup, Questions, Result
// ============================================================

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizWindow extends JFrame {

	private CardLayout cardLayout;
	private JPanel mainPanel;

	private static final String SCREEN_SETUP  = "SETUP";
	private static final String SCREEN_QUIZ   = "QUIZ";
	private static final String SCREEN_RESULT = "RESULT";

	private QuizManager quizManager;
	private ScoreManager scoreManager;

	private JComboBox<String> categoryCombo;
	private JComboBox<String> difficultyCombo;

	private JLabel questionNumberLabel;
	private JLabel questionLabel;
	private ButtonGroup optionGroup;
	private JRadioButton[] optionButtons;
	private JButton nextButton;
	private JButton submitButton;
	private JLabel feedbackLabel;

	private JLabel resultScoreLabel;
	private JLabel resultMessageLabel;
	private JTextField playerNameField;

	private final Color primaryBlue  = new Color(37, 99, 235);
	private final Color lightBlue    = new Color(239, 246, 255);
	private final Color successGreen = new Color(22, 163, 74);
	private final Color errorRed     = new Color(220, 38, 38);
	private final Color white        = Color.WHITE;
	private final Color textDark     = new Color(15, 23, 42);
	private final Color textGray     = new Color(100, 116, 139);

	public QuizWindow() {
		quizManager  = new QuizManager();
		scoreManager = new ScoreManager();

		setTitle("Smart Quiz Challenge");
		setSize(680, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		cardLayout = new CardLayout();
		mainPanel  = new JPanel(cardLayout);

		mainPanel.add(buildSetupScreen(),  SCREEN_SETUP);
		mainPanel.add(buildQuizScreen(),   SCREEN_QUIZ);
		mainPanel.add(buildResultScreen(), SCREEN_RESULT);

		add(mainPanel);
		showScreen(SCREEN_SETUP);
	}

	private void showScreen(String name) {
		cardLayout.show(mainPanel, name);
	}

	// ============================================================
	// SETUP SCREEN
	// ============================================================
	private JPanel buildSetupScreen() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(white);

		// Blue header
		JPanel header = new JPanel(new GridLayout(2, 1));
		header.setBackground(primaryBlue);
		header.setPreferredSize(new Dimension(680, 110));
		header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

		JLabel title = new JLabel("Quiz Setup", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 26));
		title.setForeground(white);
		header.add(title);

		JLabel subtitle = new JLabel("Choose your category and difficulty", SwingConstants.CENTER);
		subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
		subtitle.setForeground(new Color(191, 219, 254));
		header.add(subtitle);

		panel.add(header, BorderLayout.NORTH);

		// Center form
		JPanel form = new JPanel(new GridBagLayout());
		form.setBackground(white);
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(12, 20, 12, 20);
		g.fill = GridBagConstraints.HORIZONTAL;

		// Category label
		JLabel catLabel = new JLabel("Select Category:");
		catLabel.setFont(new Font("Arial", Font.BOLD, 14));
		catLabel.setForeground(textDark);
		g.gridx = 0; g.gridy = 0;
		form.add(catLabel, g);

		// Category dropdown - EVENT HANDLING
		categoryCombo = new JComboBox<>(new String[]{"Programming", "Sports", "General Knowledge"});
		categoryCombo.setFont(new Font("Arial", Font.PLAIN, 14));
		categoryCombo.setBackground(white);
		categoryCombo.setPreferredSize(new Dimension(250, 36));
		g.gridx = 1; g.gridy = 0;
		form.add(categoryCombo, g);

		// Difficulty label
		JLabel diffLabel = new JLabel("Select Difficulty:");
		diffLabel.setFont(new Font("Arial", Font.BOLD, 14));
		diffLabel.setForeground(textDark);
		g.gridx = 0; g.gridy = 1;
		form.add(diffLabel, g);

		// Difficulty dropdown - EVENT HANDLING
		difficultyCombo = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
		difficultyCombo.setFont(new Font("Arial", Font.PLAIN, 14));
		difficultyCombo.setBackground(white);
		difficultyCombo.setPreferredSize(new Dimension(250, 36));
		g.gridx = 1; g.gridy = 1;
		form.add(difficultyCombo, g);

		panel.add(form, BorderLayout.CENTER);

		// Bottom buttons
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		bottom.setBackground(white);

		JButton backButton = new JButton("← Home");
		backButton.setFont(new Font("Arial", Font.PLAIN, 13));
		backButton.setForeground(textGray);
		backButton.setBackground(new Color(241, 245, 249));
		backButton.setFocusPainted(false);
		backButton.setPreferredSize(new Dimension(120, 44));
		backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backButton.addActionListener(e -> {
			new MainWindow().setVisible(true);
			dispose();
		});
		bottom.add(backButton);

		JButton beginButton = new JButton("Begin Quiz →");
		beginButton.setFont(new Font("Arial", Font.BOLD, 14));
		beginButton.setForeground(white);
		beginButton.setBackground(primaryBlue);
		beginButton.setFocusPainted(false);
		beginButton.setPreferredSize(new Dimension(160, 44));
		beginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		beginButton.addActionListener(e -> startQuizSession());
		bottom.add(beginButton);

		panel.add(bottom, BorderLayout.SOUTH);

		return panel;
	}

	// ============================================================
	// Start quiz with selected settings
	// EXCEPTION HANDLING
	// ============================================================
	private void startQuizSession() {
		try {
			String category   = (String) categoryCombo.getSelectedItem();
			String difficulty = (String) difficultyCombo.getSelectedItem();

			if (category == null || difficulty == null) {
				throw new IllegalArgumentException("Please select category and difficulty.");
			}

			quizManager.startQuiz(category, difficulty);

			if (quizManager.getTotalQuestions() == 0) {
				JOptionPane.showMessageDialog(this,
					"No questions found.", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}

			loadCurrentQuestion();
			showScreen(SCREEN_QUIZ);

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this,
				ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// ============================================================
	// QUIZ SCREEN
	// ============================================================
	private JPanel buildQuizScreen() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(white);

		// Blue header
		JPanel header = new JPanel(new GridLayout(2, 1));
		header.setBackground(primaryBlue);
		header.setPreferredSize(new Dimension(680, 90));
		header.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

		questionNumberLabel = new JLabel("Question 1 of 3", SwingConstants.CENTER);
		questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 13));
		questionNumberLabel.setForeground(new Color(191, 219, 254));
		header.add(questionNumberLabel);

		questionLabel = new JLabel("", SwingConstants.CENTER);
		questionLabel.setFont(new Font("Arial", Font.BOLD, 15));
		questionLabel.setForeground(white);
		header.add(questionLabel);

		panel.add(header, BorderLayout.NORTH);

		// Options panel
		JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 8, 8));
		optionsPanel.setBackground(white);
		optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 10, 60));

		optionGroup   = new ButtonGroup();
		optionButtons = new JRadioButton[4];

		for (int i = 0; i < 4; i++) {
			final int index = i;
			optionButtons[i] = new JRadioButton();
			optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
			optionButtons[i].setForeground(textDark);
			optionButtons[i].setBackground(white);
			optionButtons[i].setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(219, 234, 254), 1),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)
			));
			optionButtons[i].setOpaque(true);
			optionGroup.add(optionButtons[i]);
			optionsPanel.add(optionButtons[i]);

			// Highlight selected option - EVENT HANDLING
			optionButtons[i].addActionListener(e -> {
				for (JRadioButton btn : optionButtons) {
					btn.setBackground(white);
					btn.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(new Color(219, 234, 254), 1),
						BorderFactory.createEmptyBorder(8, 15, 8, 15)
					));
				}
				optionButtons[index].setBackground(lightBlue);
				optionButtons[index].setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(primaryBlue, 2),
					BorderFactory.createEmptyBorder(8, 15, 8, 15)
				));
			});
		}

		panel.add(optionsPanel, BorderLayout.CENTER);

		// Bottom area
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setBackground(white);

		feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
		feedbackLabel.setFont(new Font("Arial", Font.BOLD, 13));
		feedbackLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		bottom.add(feedbackLabel, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		btnPanel.setBackground(white);

		nextButton = new JButton("Next Question →");
		nextButton.setFont(new Font("Arial", Font.BOLD, 13));
		nextButton.setForeground(white);
		nextButton.setBackground(primaryBlue);
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(180, 42));
		nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nextButton.addActionListener(e -> handleNextQuestion());
		btnPanel.add(nextButton);

		submitButton = new JButton("Submit Quiz ✓");
		submitButton.setFont(new Font("Arial", Font.BOLD, 13));
		submitButton.setForeground(white);
		submitButton.setBackground(successGreen);
		submitButton.setFocusPainted(false);
		submitButton.setPreferredSize(new Dimension(180, 42));
		submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		submitButton.addActionListener(e -> handleSubmitQuiz());
		btnPanel.add(submitButton);

		bottom.add(btnPanel, BorderLayout.CENTER);
		panel.add(bottom, BorderLayout.SOUTH);

		return panel;
	}

	private void loadCurrentQuestion() {
		QuizQuestion q = quizManager.getCurrentQuestion();
		if (q == null) return;

		int current = quizManager.getCurrentQuestionNumber();
		int total   = quizManager.getTotalQuestions();

		questionNumberLabel.setText("Question " + current + " of " + total);
		questionLabel.setText("<html><div style='text-align:center;'>" + q.getQuestionText() + "</div></html>");

		String[] options = q.getOptions();
		for (int i = 0; i < 4; i++) {
			optionButtons[i].setText("  " + (char)('A' + i) + ".   " + options[i]);
			optionButtons[i].setEnabled(true);
			optionButtons[i].setBackground(white);
			optionButtons[i].setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(219, 234, 254), 1),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)
			));
		}

		optionGroup.clearSelection();
		feedbackLabel.setText(" ");

		boolean isLast = (current == total);
		nextButton.setVisible(!isLast);
		submitButton.setVisible(isLast);
	}

	// ============================================================
	// Next button - INPUT VALIDATION
	// ============================================================
	private void handleNextQuestion() {
		int selected = getSelectedIndex();

		if (selected == -1) {
			JOptionPane.showMessageDialog(this,
				"Please select an answer first.",
				"No Answer", JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (JRadioButton btn : optionButtons) btn.setEnabled(false);

		boolean correct = quizManager.submitAnswer(selected);
		feedbackLabel.setText(correct ? "✔  Correct!" : "✘  Wrong!");
		feedbackLabel.setForeground(correct ? successGreen : errorRed);

		Timer t = new Timer(800, e -> {
			if (quizManager.hasNextQuestion()) {
				loadCurrentQuestion();
			} else {
				showResultScreen();
			}
		});
		t.setRepeats(false);
		t.start();
	}

	// ============================================================
	// Submit button - INPUT VALIDATION
	// ============================================================
	private void handleSubmitQuiz() {
		int selected = getSelectedIndex();

		if (selected == -1) {
			JOptionPane.showMessageDialog(this,
				"Please select an answer first.",
				"No Answer", JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (JRadioButton btn : optionButtons) btn.setEnabled(false);

		boolean correct = quizManager.submitAnswer(selected);
		feedbackLabel.setText(correct ? "✔  Correct!" : "✘  Wrong!");
		feedbackLabel.setForeground(correct ? successGreen : errorRed);

		Timer t = new Timer(800, e -> showResultScreen());
		t.setRepeats(false);
		t.start();
	}

	private int getSelectedIndex() {
		for (int i = 0; i < optionButtons.length; i++) {
			if (optionButtons[i].isSelected()) return i;
		}
		return -1;
	}

	// ============================================================
	// RESULT SCREEN
	// ============================================================
	private JPanel buildResultScreen() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(white);

		// Blue header
		JPanel header = new JPanel(new GridLayout(2, 1));
		header.setBackground(primaryBlue);
		header.setPreferredSize(new Dimension(680, 110));
		header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

		JLabel done = new JLabel("Quiz Complete!", SwingConstants.CENTER);
		done.setFont(new Font("Arial", Font.BOLD, 26));
		done.setForeground(white);
		header.add(done);

		JLabel sub = new JLabel("Here is how you did", SwingConstants.CENTER);
		sub.setFont(new Font("Arial", Font.PLAIN, 13));
		sub.setForeground(new Color(191, 219, 254));
		header.add(sub);

		panel.add(header, BorderLayout.NORTH);

		// Center score
		JPanel center = new JPanel(new GridBagLayout());
		center.setBackground(white);
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(8, 20, 8, 20);
		g.fill = GridBagConstraints.HORIZONTAL;

		resultScoreLabel = new JLabel("0 / 0", SwingConstants.CENTER);
		resultScoreLabel.setFont(new Font("Arial", Font.BOLD, 52));
		resultScoreLabel.setForeground(primaryBlue);
		g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
		center.add(resultScoreLabel, g);

		resultMessageLabel = new JLabel("", SwingConstants.CENTER);
		resultMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		resultMessageLabel.setForeground(textGray);
		g.gridy = 1;
		center.add(resultMessageLabel, g);

		JSeparator sep = new JSeparator();
		sep.setForeground(new Color(219, 234, 254));
		g.gridy = 2;
		center.add(sep, g);

		JLabel nameLabel = new JLabel("Your name:");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
		g.gridx = 0; g.gridy = 3; g.gridwidth = 1;
		center.add(nameLabel, g);

		playerNameField = new JTextField();
		playerNameField.setFont(new Font("Arial", Font.PLAIN, 13));
		playerNameField.setPreferredSize(new Dimension(180, 34));
		g.gridx = 1; g.gridy = 3;
		center.add(playerNameField, g);

		JButton saveButton = new JButton("Save Score");
		saveButton.setFont(new Font("Arial", Font.BOLD, 13));
		saveButton.setForeground(white);
		saveButton.setBackground(primaryBlue);
		saveButton.setFocusPainted(false);
		saveButton.setPreferredSize(new Dimension(180, 38));
		saveButton.addActionListener(e -> saveScore());
		g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
		center.add(saveButton, g);

		panel.add(center, BorderLayout.CENTER);

		// Bottom buttons
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		bottom.setBackground(white);

		JButton playAgain = new JButton("▶  Play Again");
		playAgain.setFont(new Font("Arial", Font.BOLD, 13));
		playAgain.setForeground(white);
		playAgain.setBackground(primaryBlue);
		playAgain.setFocusPainted(false);
		playAgain.setPreferredSize(new Dimension(150, 40));
		playAgain.addActionListener(e -> showScreen(SCREEN_SETUP));
		bottom.add(playAgain);

		JButton homeBtn = new JButton("🏠  Home");
		homeBtn.setFont(new Font("Arial", Font.BOLD, 13));
		homeBtn.setForeground(primaryBlue);
		homeBtn.setBackground(lightBlue);
		homeBtn.setFocusPainted(false);
		homeBtn.setPreferredSize(new Dimension(150, 40));
		homeBtn.addActionListener(e -> {
			new MainWindow().setVisible(true);
			dispose();
		});
		bottom.add(homeBtn);

		panel.add(bottom, BorderLayout.SOUTH);

		return panel;
	}

	private void showResultScreen() {
		int score   = quizManager.getScore();
		int total   = quizManager.getTotalQuestions();
		int percent = quizManager.getScorePercentage();

		resultScoreLabel.setText(score + " / " + total);

		if (percent >= 80) {
			resultScoreLabel.setForeground(successGreen);
			resultMessageLabel.setText("Excellent! Outstanding performance!");
		} else if (percent >= 50) {
			resultScoreLabel.setForeground(new Color(234, 179, 8));
			resultMessageLabel.setText("Good job! Keep practicing!");
		} else {
			resultScoreLabel.setForeground(errorRed);
			resultMessageLabel.setText("Keep studying! You can do better!");
		}

		playerNameField.setText("");
		showScreen(SCREEN_RESULT);
	}

	// ============================================================
	// Save score to file - EXCEPTION HANDLING
	// ============================================================
	private void saveScore() {
		try {
			String name = playerNameField.getText().trim();

			// Input validation
			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(this,
					"Please enter your name.", "Required", JOptionPane.WARNING_MESSAGE);
				return;
			}

			scoreManager.saveScore(name, quizManager.getScore(),
				quizManager.getTotalQuestions(),
				(String) categoryCombo.getSelectedItem(),
				(String) difficultyCombo.getSelectedItem());

			JOptionPane.showMessageDialog(this,
				"Score saved! Well done, " + name + "!",
				"Saved", JOptionPane.INFORMATION_MESSAGE);

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this,
				ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
		} catch (RuntimeException ex) {
			// This block handles file write errors
			JOptionPane.showMessageDialog(this,
				"Could not save: " + ex.getMessage(),
				"File Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}