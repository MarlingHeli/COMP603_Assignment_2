/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Controller;

/**
 *
 * @author hmarl
 */

import User_Interface.Graphical.GUI;
//import User_Interface.Console.CUI;
import User_Interface.UI;
import User_Interface.Console.InputHelper;
import User_Interface.Console.StoryPrinter;
import Question.Question;
import Question.QuestionPool;
import Persistence.UserRecordFileIO;
import Persistence.UserRecord;
import Model.User;
import Model.QuizSession;

import java.util.List;

public class MainController {

    int numQuestions = 10;

    private UI ui;
    private UserRecord userRecord;
    private QuestionPool questionPool;
    private QuizSession quiz;
    private User user;
    private InputHelper inHelp;

    public MainController() {

        GUI gui = new GUI();
        gui.setVisible(true);
        ui = gui;

        // ui = new CUI();

        userRecord = new UserRecordFileIO();
        questionPool = new QuestionPool();

        inHelp = new InputHelper();
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

    public void startNewGame() {

        User inputUser =
            ui.inputNames();

        User existing =
            userRecord.loadRecord(
                inputUser.getUsername()
            );

        if (existing != null) {

            existing.setPetName(
                inputUser.getPetName()
            );

            user = existing;
        }

        else {
            user = inputUser;
        }

        List<Question> questions =
            questionPool.getRandomQuestions(
                numQuestions
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
                () -> finishQuiz()
            )
        );
    }

    public void loadGame() {
        String username =
            ui.inputLoadName();

        user =
            userRecord.loadRecord(
                username
            );

        if (user == null) {
            ui.displayError(
                "No saved game found."
            );
            return;
        }

        quiz =
            userRecord.loadGame(
                user
            );

        if (quiz == null) {
            ui.displayError(
                "No saved game found."
            );
            return;
        }

        ui.showQuiz(
            quiz,
            () -> finishQuiz()
        );
    }

    private void finishQuiz() {
        if (user == null) {
            user = quiz.getUser();
        }

        user.saveHighestScore(
            quiz.getNumCorrectAnswers()
        );

        userRecord.saveRecord(user);
        userRecord.saveGame(quiz);

        ui.showEnd(
            quiz,
            () -> start()
        );
    }


    private void handleExitDuringGame() {

        String save =
            inHelp.getYesNo(
                ui,
                "Save progress? (y/n): "
            );

        if (save.equalsIgnoreCase("y")) {

            userRecord.saveRecord(user);
            userRecord.saveGame(quiz);
        }
    }

    public void exit() {

        ui.displayText(
            "Thanks for playing!"
        );

        System.exit(0);
    }
}
