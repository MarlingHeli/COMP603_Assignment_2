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

public interface UI {
    void displayText(String text);
    void displayError(String text);
    String getUserInput(String prompt);
    void slowPrint(String text);
    int showMenu ();
    void printStory(String user, String pet, Runnable onFinish);
    void showQuiz(QuizSession quiz);
    //void showResults();
    
    
    
}


