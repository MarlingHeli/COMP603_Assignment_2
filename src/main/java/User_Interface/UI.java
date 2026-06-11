/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface;

/**
 *
 * @author hmarl
 */

import Question.Question;
import Model.User;
import java.util.List;

public interface UI {
    void displayText(String text);
    void displayError(String text);
    String getUserInput(String prompt);
    void slowPrint(String text);
    int showMenu ();
    void printStory(String user, String pet, Runnable onFinish);
    void showQuiz(List<Question> questions, User user);
    //void showResults();
    
    
    
}


