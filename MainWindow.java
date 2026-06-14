// ============================================================
// MainWindow.java
// Smart Quiz Challenge System - Welcome Screen
// Theme: Blue and White
// ============================================================

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public MainWindow() {
		setTitle("Smart Quiz Challenge System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(680, 520);
		setLocationRelativeTo(null);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		// -------------------------------------------------------
		// TOP BLUE HEADER
		// -------------------------------------------------------
		JPanel header = new JPanel(new GridLayout(2, 1));
		header.setBackground(new Color(37, 99, 235));
		header.setPreferredSize(new Dimension(680, 160));
		header.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

		JLabel titleLabel = new JLabel("Smart Quiz Challenge", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
		titleLabel.setForeground(Color.WHITE);
		header.add(titleLabel);

		JLabel subtitleLabel = new JLabel("Challenge Yourself. Learn Something New.", SwingConstants.CENTER);
		subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		subtitleLabel.setForeground(new Color(191, 219, 254));
		header.add(subtitleLabel);

		contentPane.add(header, BorderLayout.NORTH);

		// -------------------------------------------------------
		// CENTER - category cards
		// -------------------------------------------------------
		JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 0));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

		centerPanel.add(makeCard("Programming", new Color(37, 99, 235)));
		centerPanel.add(makeCard("Sports", new Color(14, 165, 233)));
		centerPanel.add(makeCard("General Knowledge", new Color(79, 70, 229)));

		contentPane.add(centerPanel, BorderLayout.CENTER);

		// -------------------------------------------------------
		// BOTTOM BUTTONS
		// -------------------------------------------------------
		JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 0, 10));
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 150, 25, 150));

		// Start Quiz button - EVENT HANDLING
		JButton startButton = new JButton("Start Quiz");
		startButton.setFont(new Font("Arial", Font.BOLD, 16));
		startButton.setForeground(Color.WHITE);
		startButton.setBackground(new Color(37, 99, 235));
		startButton.setFocusPainted(false);
		startButton.setBorderPainted(false);
		startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		startButton.setPreferredSize(new Dimension(200, 50));

		// This event opens the quiz window when Start Quiz is clicked
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuizWindow quiz = new QuizWindow();
				quiz.setVisible(true);
				dispose();
			}
		});

		bottomPanel.add(startButton);

		// Bottom row - High Scores and Exit
		JPanel rowPanel = new JPanel(new GridLayout(1, 2, 15, 0));
		rowPanel.setBackground(Color.WHITE);

		JButton scoresButton = new JButton("High Scores");
		scoresButton.setFont(new Font("Arial", Font.BOLD, 13));
		scoresButton.setForeground(new Color(37, 99, 235));
		scoresButton.setBackground(new Color(239, 246, 255));
		scoresButton.setFocusPainted(false);
		scoresButton.setBorder(BorderFactory.createLineBorder(new Color(191, 219, 254)));
		scoresButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// This event shows high scores when clicked
		scoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHighScores();
			}
		});

		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Arial", Font.BOLD, 13));
		exitButton.setForeground(new Color(220, 38, 38));
		exitButton.setBackground(new Color(254, 242, 242));
		exitButton.setFocusPainted(false);
		exitButton.setBorder(BorderFactory.createLineBorder(new Color(254, 202, 202)));
		exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// This event exits the application
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to exit?",
					"Exit", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		rowPanel.add(scoresButton);
		rowPanel.add(exitButton);
		bottomPanel.add(rowPanel);

		contentPane.add(bottomPanel, BorderLayout.SOUTH);
	}

	// ============================================================
	// Creates a category card - Code Refactoring
	// ============================================================
	private JPanel makeCard(String text, Color accent) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(new Color(239, 246, 255));
		card.setBorder(BorderFactory.createLineBorder(accent, 1));

		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 13));
		label.setForeground(accent);
		label.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		card.add(label, BorderLayout.CENTER);

		return card;
	}

	// ============================================================
	// Shows high scores - Exception Handling for file errors
	// ============================================================
	private void showHighScores() {
		try {
			ScoreManager scoreManager = new ScoreManager();
			java.util.List<String> scores = scoreManager.loadScores();

			if (scores.isEmpty()) {
				JOptionPane.showMessageDialog(this,
					"No scores saved yet!\nComplete a quiz to save your score.",
					"High Scores", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			StringBuilder sb = new StringBuilder("HIGH SCORES\n");
			sb.append("─────────────────────────────\n");
			for (String entry : scores) {
				sb.append(entry).append("\n");
			}

			JTextArea area = new JTextArea(sb.toString());
			area.setFont(new Font("Monospaced", Font.PLAIN, 13));
			area.setEditable(false);
			JScrollPane scroll = new JScrollPane(area);
			scroll.setPreferredSize(new Dimension(400, 250));

			JOptionPane.showMessageDialog(this, scroll,
				"High Scores", JOptionPane.PLAIN_MESSAGE);

		} catch (Exception ex) {
			// Exception Handling - file read error
			JOptionPane.showMessageDialog(this,
				"Could not load scores: " + ex.getMessage(),
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}