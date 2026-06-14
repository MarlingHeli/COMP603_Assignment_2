/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface;

/**
 *
 * @author hmarl
 */
import Model.QuizSession;
import Model.User;
import java.util.function.Consumer;

public interface UI {

    void displayText(String text);

    void displayError(String text);

    String getUserInput(String prompt);

    void slowPrint(String text);

    int showMenu();

    String inputLoadName();

    void inputNames(Consumer<User> onSubmit);

    void printStory(String user, String pet, Runnable onFinish);

    void showQuiz(QuizSession quiz, Runnable onFinish);

    void showEnd(QuizSession quiz, Runnable onFinish);
}
