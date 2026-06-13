/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface.Console;

/**
 *
 * @author hmarl
 */

import User_Interface.UI;
import java.util.Scanner;
import Model.QuizSession;
import Question.Question;
import Model.User;
import java.util.function.Consumer;

public class CUI implements UI {
    private Scanner scanner = new Scanner(System.in);
    private StoryPrinter storyPrint;
    
    public CUI() {
        storyPrint = new StoryPrinter();
    }

    //oops, let me know if I should make this into a class again, but under CUI.java to stick to proper OOP...
    @Override
    public int showMenu (){
        displayText("\n=== Feed Me Java! ===");
        displayText("1. Start New Game");
        displayText("2. Load Game");
        displayText("3. Exit");

        try {
            return Integer.parseInt(getUserInput("Choose option (1/2/3): "));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    @Override
        public void printStory(
               String user,
               String pet,
               Runnable onFinish) {

           String introChoice = getUserInput(
               "Enter anything to view introduction, or type \"s\" to skip: "
           );

           if (!introChoice.equalsIgnoreCase("s")) {
               storyPrint.showIntro(this, user, pet);
           }

           // Tell the controller the story is finished
           if (onFinish != null) {
               onFinish.run();
           }
    }
    
    
     @Override
    public void showQuiz(
        QuizSession quiz,
        Runnable onFinish
    ) {
        while (
            quiz.getCurrentQuestionIndex()
            < quiz.getQuestions().size()
        ) {

            Question q =
                quiz.getCurrentQuestion();

            displayText(
                q.getQuestionText()
            );

            String input =
                getUserInput("Answer: ");

            if (q.checkAnswer(input)) {
                quiz.answerCorrect();
                displayText("Correct");
            }
            else {
                quiz.answerWrong();
                displayText("Wrong");
            }
        }

        // quiz finished → notify controller
        if (onFinish != null) {
            onFinish.run();
        }
    }
    
    @Override
    public void showEnd(
        QuizSession quiz,
        Runnable onFinish
    ) {

        // heading
        displayText("\n=== RESULTS ===");

        // final score
        displayText(
            quiz.getScoreText()
        );

        // trophy
        displayText(
            "Trophy: "
            + quiz.getTrophy()
        );

        // ending dialogue
        displayText(
            quiz.getEndingDialogue()
        );

        getUserInput(
            "Press Enter to continue..."
        );

        String choice =
            getUserInput(
                "Return to menu? (y/n): "
            );

        if(choice.equalsIgnoreCase("n")) {
            displayText(
                "Thanks for playing!"
            );
            System.exit(0);
        }

        // return control to controller
        if(onFinish != null) {
            onFinish.run();
        }
    }
    
    
    @Override
    public void displayText(String text) {
        System.out.println(text);
    }

    @Override
    public void displayError(String text) {
        System.err.println(text);
    }

    @Override
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    @Override
    public void slowPrint(String text) {
        displayText(text);

            try {
                Thread.sleep(3000); // 3000 ms = 3 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }
    
    @Override
    public void inputNames(Consumer<User> onSubmit) {
        String username =
            getUserInput("Enter username: ");

        String petName =
            getUserInput("Enter pet name: ");

        User user =
            new User(username, petName, 0);

        onSubmit.accept(user);
    }
    
    @Override
    public String inputLoadName() {
        return getUserInput("Enter username: ");
    }
}