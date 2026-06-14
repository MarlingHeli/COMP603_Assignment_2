package Controller;

import Model.*;
import Persistence.*;
import Question.Question;
import Question.QuestionPool;
import User_Interface.Graphical.GUI;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MainController {

    private DatabaseManager databaseManager;
    private DatabaseUser databaseUser;
    private DatabaseQuizSession databaseQuizSession;
    private DatabaseQuestions databaseQuestions;
    private QuestionPool questionPool;
    private AppStateModel appState;
    private GUI gui;
    private User user;
    private boolean lastAnswerWasCorrect;
    private String lastExplanationText;

    public MainController() {
        appState = new AppStateModel();
        gui = new GUI(appState, this);
        databaseManager = new DatabaseManager();
        Connection connection = databaseManager.getConnection();
        if (connection == null) {
            JOptionPane.showMessageDialog(gui,
                    "Database Connection Error!\nThe database file might be locked by another running instance of the game or IDE.\nPlease close background Java tasks and restart the application.",
                    "Connection Failure", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        databaseUser = new DatabaseUser(connection);
        databaseQuestions = new DatabaseQuestions(connection);
        databaseQuizSession = new DatabaseQuizSession(connection);
        questionPool = new QuestionPool(databaseQuestions);
        appState.addListener(gui);
    }

    public static void main(String[] args) {
        new MainController().start();
    }

    public void start() {
        appState.setState(GameState.MENU);
    }

    public void handleNewGameSetup() {
        appState.setState(GameState.NAME_INPUT);
    }

    public void startNewGame(String username, String petName, boolean skipIntro) {
        this.user = new User(username, petName, 0);
        appState.setCurrentUser(user);
        appState.setSkipIntro(skipIntro);
        List<Question> questions = questionPool.getRandomQuestions(10);
        QuizSession quiz = new QuizSession(0, questions, 0, user);
        appState.setCurrentQuiz(quiz);
        if (skipIntro) {
            appState.setState(GameState.QUIZ);
        } else {
            appState.setState(GameState.INTRO);
        }
    }

    public void proceedToQuiz() {
        appState.setState(GameState.QUIZ);
    }

    public void processAnswerSubmission(boolean isCorrect, String explanation) {
        this.lastAnswerWasCorrect = isCorrect;
        this.lastExplanationText = explanation;
        QuizSession session = appState.getCurrentQuiz();
        if (session != null) {
            if (isCorrect) {
                session.answerCorrect();
            } else {
                session.answerWrong();
            }
        }
        appState.setState(GameState.RESULT);
    }

    public void advanceFromFeedback() {
        QuizSession session = appState.getCurrentQuiz();
        if (session == null || session.isFinished()) {
            completeQuiz();
        } else {
            appState.setState(GameState.QUIZ);
        }
    }

    public void completeQuiz() {
        QuizSession quiz = appState.getCurrentQuiz();
        if (quiz != null) {
            // Ask the player explicitly if they want to update and save their score values to high score tracking
            int saveScoreChoice = JOptionPane.showConfirmDialog(gui,
                    "Congratulations on finishing! Would you like to save your final quiz score to the leaderboard database?",
                    "Save Score", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (saveScoreChoice == JOptionPane.YES_OPTION) {
                quiz.updateHighScore();
                databaseUser.saveRecord(user);
                // Cleanly delete active saved state history upon completion to avoid loaded resume loop exceptions
                databaseQuizSession.deleteSession(user.getUsername());
            }
        }
        appState.setState(GameState.END);
    }

    public void showLeaderboard() {
        appState.setState(GameState.LEADERBOARD);
    }
    // Intercepts database mappings and forwards parsed map values to UI panels

    public HashMap<String, Integer> fetchLeaderboardScores() {
        return databaseManager.getHighScores();
    }

    public void loadGame() {
        appState.setState(GameState.LOAD_SCREEN);
    }

    public User verifyAndFetchUser(String username) {
        return databaseUser.loadRecord(username);
    }

    public QuizSession fetchSavedSession(User targetedUser) {
        return databaseQuizSession.loadGame(targetedUser);
    }

    public void resumeLoadedQuiz(User gameUser, QuizSession restoredQuiz) {
        this.user = gameUser;
        appState.setCurrentUser(this.user);
        appState.setCurrentQuiz(restoredQuiz);
        appState.setState(GameState.QUIZ);
    }

    public void saveAndExit() {
        QuizSession currentSession = appState.getCurrentQuiz();
        if (currentSession != null && user != null) {
            try {
                databaseUser.saveRecord(user);
                databaseQuizSession.saveGame(currentSession);
                JOptionPane.showMessageDialog(gui, "Game progression saved successfully for " + user.getUsername() + "!", "Save Complete", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(gui, "Failed to save game data progression records properly.", "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        exit();
    }

    public boolean isLastAnswerCorrect() {
        return lastAnswerWasCorrect;
    }

    public String getLastExplanationText() {
        return lastExplanationText;
    }

    public void exit() {
        databaseManager.close();
        System.exit(0);
    }
}
