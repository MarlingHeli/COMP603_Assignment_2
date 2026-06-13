package Controller;

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

    private static final int NUM_QUESTIONS = 10;

    private UI ui;

    private DatabaseUser databaseUser;
    private DatabaseQuizSession databaseQuizSession;
    private DatabaseManager databaseManager;
    private DatabaseQuestions databaseQuestions;
    private QuestionPool questionPool;

    private QuizSession quiz;
    private User user;

    public MainController() {

        GUI gui = new GUI();
        gui.setVisible(true);
        ui = gui;
        
        

        Connection connection = databaseManager.getConnection();

        databaseUser = new DatabaseUser(connection);
        databaseQuestions = new DatabaseQuestions(connection);
        databaseQuizSession = new DatabaseQuizSession(connection);

        questionPool = new QuestionPool(databaseQuestions);
    }

    public static void main(String[] args) {
        new MainController().start();
    }

    public void start() {
        showMenu();
    }

    private void showMenu() {

        while (true) {

            int choice = ui.showMenu();

            switch (choice) {

                case 1 -> {
                    startNewGame();
                    return;
                }

                case 2 -> {
                    loadGame();
                    return;
                }

                case 3 -> exit();

                default ->
                    ui.displayError(
                        "Invalid option"
                    );
            }
        }
    }

    private void startNewGame() {

        User inputUser =
            ui.inputNames();

        User existing =
            databaseUser.loadRecord(
                inputUser.getUsername()
            );

        if (existing != null) {

            existing.setPetName(
                inputUser.getPetName()
            );

            user = existing;

        } else {

            user = inputUser;
        }

        List<Question> questions =
            questionPool.getRandomQuestions(
                NUM_QUESTIONS
            );

        quiz = new QuizSession(
            0,
            questions,
            0,
            user
        );

        ui.printStory(
            user.getUsername(),
            user.getPetName(),
            () -> ui.showQuiz(
                quiz,
                this::finishQuiz
            )
        );
    }

    private void loadGame() {

        String username =
            ui.inputLoadName();

        user =
            databaseUser.loadRecord(
                username
            );

        if (user == null) {

            ui.displayError(
                "No saved user found."
            );

            return;
        }

        quiz =
            databaseQuizSession.loadGame(
                user
            );

        if (quiz == null) {

            ui.displayError(
                "No saved quiz found."
            );

            return;
        }

        ui.showQuiz(
            quiz,
            this::finishQuiz
        );
    }

    private void finishQuiz() {

        user = quiz.getUser();

        user.saveHighestScore(
            quiz.getNumCorrectAnswers()
        );

        databaseUser.saveRecord(user);

        databaseQuizSession.saveGame(
            quiz
        );

        ui.showEnd(
            quiz,
            this::start
        );
    }

    public void exit() {

        ui.displayText(
            "Thanks for playing!"
        );

        System.exit(0);
    }
}