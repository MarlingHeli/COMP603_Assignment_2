package Controller;

import Model.AppStateModel;
import Model.GameState;
import Model.QuizSession;
import Model.User;
import Persistence.DatabaseManager;
import Persistence.DatabaseQuestions;
import Persistence.DatabaseQuizSession;
import Persistence.DatabaseUser;
import Question.Question;
import Question.QuestionPool;
import User_Interface.Graphical.GUI;
import User_Interface.UI;

import java.sql.Connection;
import java.util.List;

public class MainController {

    private UI ui;

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

        gui = new GUI(appState);

        // database initialization here

        databaseManager =
            new DatabaseManager();

        Connection connection =
            databaseManager.getConnection();

        databaseUser =
            new DatabaseUser(
                connection
            );

        databaseQuestions =
            new DatabaseQuestions(
                connection
            );

        databaseQuizSession =
            new DatabaseQuizSession(
                connection
            );

        questionPool =
            new QuestionPool(
                databaseQuestions
            );
    }

    public static void main(String[] args) {
        new MainController().start();
    }

    public void start() {

        appState.setState(
            GameState.MENU
        );
    }

    public void startNewGame(String username, String petName) {
        List<Question> questions =
                questionPool.getRandomQuestions(10);

        QuizSession quiz =
                new QuizSession(
                        0,
                        questions,
                        0,
                        user
                );

        appState.setCurrentQuiz(quiz);

        appState.setState(
                GameState.QUIZ
        );
    }

    public void loadGame() {
        QuizSession quiz =
                databaseQuizSession.loadGame(user);

        appState.setCurrentQuiz(quiz);

        appState.setState(
                GameState.QUIZ
        );
    }

    public void exit() {

        System.exit(0);
    }
}