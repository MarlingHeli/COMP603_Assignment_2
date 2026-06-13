package Controller;
import Model.*;
import Persistence.*;
import Question.Question;
import Question.QuestionPool;
import User_Interface.Graphical.GUI;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.List;
public class MainController {
 private DatabaseManager databaseManager;
 private DatabaseUser databaseUser;
 private DatabaseQuizSession databaseQuizSession;
 private DatabaseQuestions databaseQuestions;
 private QuestionPool questionPool;
 private AppStateModel appState;
 private GUI gui;
 private User user;
 public MainController() {
 appState = new AppStateModel();
 gui = new GUI(appState, this);
 databaseManager = new DatabaseManager();
 Connection connection = databaseManager.getConnection();
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
 public void completeQuiz() {
 QuizSession quiz = appState.getCurrentQuiz();
 if (quiz != null) {
 quiz.updateHighScore();
 }
 appState.setState(GameState.END);
 }
 public void loadGame() {
 String inputName = JOptionPane.showInputDialog(gui, "Enter your Username to load game:", "Load Saved Game", JOptionPane.QUESTION_MESSAGE);
 if (inputName == null || inputName.trim().isEmpty()) {
 return;
 }
 inputName = inputName.trim();
 User loadedUser = databaseUser.loadRecord(inputName);
 if (loadedUser == null) {
 JOptionPane.showMessageDialog(gui, "Username not found in database!", "Error", JOptionPane.ERROR_MESSAGE);
 return;
 }
 this.user = loadedUser;
 appState.setCurrentUser(this.user);
 QuizSession savedQuiz = databaseQuizSession.loadGame(this.user);
 if (savedQuiz != null) {
 appState.setCurrentQuiz(savedQuiz);
 appState.setState(GameState.QUIZ);
 JOptionPane.showMessageDialog(gui, "Welcome back " + this.user.getUsername() + "! Resuming quiz at question " + (savedQuiz.getCurrentQuestionIndex() + 1) + ".");
 } else {
 JOptionPane.showMessageDialog(gui, "No active saved session found for this user.", "No Save Found", JOptionPane.INFORMATION_MESSAGE);
 }
 }
 public void exit() {
 databaseManager.close();
 System.exit(0);
 }
}
