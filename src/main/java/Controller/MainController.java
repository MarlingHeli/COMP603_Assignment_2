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
//import User_Interface.Console.Menu;
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
    //added variable to easily adjust question size
    //note: only applies to new games. old games based off size of question id list
    int numQuestions = 10;
            
    private UI ui;
    //private Menu menu;
    private UserRecord userRecord;
    private QuestionPool questionPool;
    private QuizSession quiz;
    private User user;
    private InputHelper inHelp;

    //define objects in constructor
    public MainController() {
        GUI gui = new GUI();
        gui.setVisible(true);

        ui = gui;
        
        //ui = new CUI();
        //pass on ui object to be used in menu
        //menu = new Menu(ui);
        userRecord = new UserRecordFileIO();
        questionPool = new QuestionPool();
        
        // I will eventually get rid of this:
        inHelp = new InputHelper();
    }

    public static void main(String[] args) {
        new MainController().start();
    }

    //display menu, will loop until user exits
    public void start() {
        while (true) {
            int choice = ui.showMenu();

            switch (choice) {
                case 1 -> { startNewGame();
                            return;
                        }
                case 2 -> loadGame();
                case 3 -> exit();
                //menu returns -1 if invalid input
                default -> ui.displayError("Invalid option.");
            }
        }
    }

    public void startNewGame() {
        String username = ui.getUserInput("Enter username: ");
        String petName = ui.getUserInput("Enter pet name: ");
        
        //keep existing highScore if user file already exists
        user = userRecord.loadRecord(username);
        //create new user if user file does not exist
        if (user == null) {
            user = new User(username, petName, 0);
        }
        
        //update petName in case users have existing file but want to start
        //new game and change pet name
        user.setPetName(petName);
        System.out.println("1. Before printStory");

        //ui.printStory(username, petName);
        ui.printStory(username, petName, () -> {
            beginQuiz(username, petName);} 
        );

        System.out.println("2. After printStory");
             
        
        /*
        String username =
            ui.getUserInput("Enter username: ");
        String petName =
                ui.getUserInput("Enter pet name: ");
        
        //keep existing highScore if user file already exists
        user = userRecord.loadRecord(username);
        //create new user if user file does not exist
        if (user == null) {
            user = new User(username, petName, 0);
        }
        
        //update petName in case users have existing file but want to start
        //new game and change pet name
        user.setPetName(petName);
        
//        System.out.println("Before printStory");
        ui.printStory(username, petName);
//        System.out.println("After printStory");
        
        //fetch generated questions with size as arg
        List<Question> questions = questionPool.getRandomQuestions(numQuestions);
        ui.displayText("\nQuest: You will now attempt the Java competition!");
        ui.getUserInput("Enter anything to continue...");
        ui.displayText("");
        
        //create QuizSession object
        quiz = new QuizSession(0, questions, 0, user);
        //TESTING HIGHSCORE
//        System.out.println("HIGHSCORE "+ quiz.getUser().getHighScore());
        runQuiz();
        */
        System.out.println("startNewGame finished");
    }
    
    public void beginQuiz(String username, String petName){
        List<Question> questions = questionPool.getRandomQuestions(numQuestions);

        System.out.println("3. Questions loaded");

        ui.getUserInput("Enter anything to continue...");

        System.out.println("4. Continue pressed");

        quiz = new QuizSession(
            0, questions, 0, user
        );

        System.out.println("5. Starting quiz");

        runQuiz();
    }
    

    public void loadGame() {
        String username = ui.getUserInput("Enter username: ");
        //attempt to load save file
        user = userRecord.loadRecord(username);
        quiz = userRecord.loadGame(username);
        //return if no save file found corresponding to user
        if (quiz == null || user == null) {
            ui.displayError("No saved game found.\n");
            return;
        }
        
        ui.displayText("Saved File Found!!");
        ui.slowPrint("Returning where you left off...\n");
        
        runQuiz();
    }

    private void runQuiz() {
        System.out.println("Inside runQuiz()");
        //fetch generated questions
        List<Question> questions = quiz.getQuestions();

        //iterate through questions list, starting at CurrentQuestionIndex
        //suitable for continuing off a save file and starting new game
        for (int i = quiz.getCurrentQuestionIndex(); i < questions.size(); i++) {         
            //get Question object (element) at index i in questions list
            Question q = questions.get(i);
            int questionNumber = i + 1;
            //display current question number starting at 1
            ui.displayText("\n=== QUESTION: "+ questionNumber + " ===");
            //get question text
            ui.displayText(q.getQuestionText());

            String input = inHelp.getQuizAnswer(ui);
            
            //check user input
            if (input.equalsIgnoreCase("quit"))
            {
                handleExitDuringGame();
                return;
            }
            //check if user answer matches answer
            else if (q.checkAnswer(input)) {
                quiz.answerCorrect();
                ui.displayText("Correct!");
            } 
            else 
            {
                quiz.answerWrong();
                ui.displayText("Incorrect.");
            }
            
            //display question explanation
            ui.displayText(questions.get(i).getExplanation());
            //display current score
            ui.displayText("Score: " + quiz.getNumCorrectAnswers()+ "/"
                    + questions.size());
            ui.getUserInput("Enter anything to continue:");
        }

        //goes to finishQuiz if users try to load a game that has already finished
        finishQuiz();   // occurs after all questions
    }

    private void finishQuiz() {
//        int score = quiz.getNumCorrectAnswers();
//        int totalQuestions = quiz.getQuestions().size();
        //get trophy type
        String result = quiz.calculateResult();
        
        ui.displayText("\n=== RESULTS ===");
        // 1. Show score + result
        ui.displayText(quiz.getScoreText());
        ui.displayText("Result: " + result + "\n");
        
        //play a concluding scene from bro
                
        //only keep highest score in user file
        user.saveHighestScore(quiz.getNumCorrectAnswers());
        //save game
        userRecord.saveRecord(user);
        userRecord.saveGame(quiz);
        
        // 3. Ask to continue
        //added error checking
        String continueChoice = inHelp.getYesNo(ui, "Would you like to like to return to menu? (y/n): ");

        if (continueChoice.equalsIgnoreCase("n")) {
            exit();
        }
        // if yes -> returns to menu automatically because of while true loop
    }

    private void handleExitDuringGame() {
 
        String save = inHelp.getYesNo(ui, "Save progress? (y/n): ");
        if (save.equalsIgnoreCase("y")) {
            userRecord.saveRecord(user);
            userRecord.saveGame(quiz);
        }
    }

    public void exit() {
        ui.displayText("Thanks for playing!");
        System.exit(0);
    }
}
