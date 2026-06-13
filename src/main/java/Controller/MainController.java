package Controller;

import Model.AppStateModel;
import Model.GameState;
import Persistence.DatabaseManager;
import Persistence.DatabaseQuestions;
import Persistence.DatabaseQuizSession;
import Persistence.DatabaseUser;
import Question.QuestionPool;
import User_Interface.Graphical.GUI;
import User_Interface.UI;

import java.sql.Connection;

public class MainController {

    private UI ui;

    private DatabaseManager databaseManager;
    private DatabaseUser databaseUser;
    private DatabaseQuizSession databaseQuizSession;
    private DatabaseQuestions databaseQuestions;

    private QuestionPool questionPool;

    private AppStateModel appState;
    private GUI gui;

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

    public void startNewGame() {

        appState.setState(
            GameState.NAME_INPUT
        );
    }

    public void loadGame() {

        appState.setState(
            GameState.LOAD_GAME
        );
    }

    public void exit() {

        System.exit(0);
    }
}