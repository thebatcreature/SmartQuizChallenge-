# Smart Quiz Challenge System

A Java Swing desktop application built for the Software Construction and Development (SCD) semester project.

## Project Description
Smart Quiz Challenge is an interactive quiz application that tests users on three categories: Programming, Sports, and General Knowledge — across three difficulty levels: Easy, Medium, and Hard.

## Features
- Welcome screen with navigation
- Category and difficulty selection
- Randomized multiple choice questions
- Real-time answer feedback
- Final score with performance message
- High score saving to a text file
- View previous scores

## Project Structure
```
SmartQuizChallenge/
├── src/
│   ├── Main.java          → Entry point
│   ├── MainWindow.java    → GUI (all screens)
│   ├── QuizQuestion.java  → Question data model
│   ├── QuizManager.java   → Quiz logic and scoring
│   └── ScoreManager.java  → File-based score saving
├── test/
│   └── QuizAppTest.java   → JUnit test cases
└── README.md
```

## How to Run in Eclipse
1. Open Eclipse → File → New → Java Project → Name: `SmartQuizChallenge`
2. Copy all `.java` files from `src/` into the `src` folder of the project
3. Right click project → Build Path → Add Libraries → JUnit 4
4. Right click `Main.java` → Run As → Java Application

## How to Run Tests
1. Right click `QuizAppTest.java` → Run As → JUnit Test
2. All 12 tests should pass (green bar)

## SCD Requirements Covered

| Requirement | How it's implemented |
|---|---|
| Event Handling | Button clicks, combo box selections, radio buttons |
| Exception Handling | File not found, invalid input, empty name, bad score values |
| Code Refactoring | Separate classes, no duplicate code, `createStyledButton()` helper |
| Unit Testing | 12 JUnit test cases in `QuizAppTest.java` |
| Git & GitHub | Maintained with meaningful commits |

## Suggested Git Commit History
```
Initial commit - project structure created
Add QuizQuestion model class
Add QuizManager with question bank and logic
Add ScoreManager with file read/write
Add MainWindow Swing GUI - welcome and setup screens
Add quiz screen with radio buttons and event handling
Add result screen and score saving
Add high scores screen
Add JUnit test cases - 12 tests
Fix input validation for empty answers
Final cleanup and comments added
```

## Technologies Used
- Java 8+
- Java Swing (GUI)
- JUnit 4 (Unit Testing)
- File I/O (Score saving)
- Eclipse IDE
