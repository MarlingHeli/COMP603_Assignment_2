/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Question;

/**
 *
 * @author hmarl
 */

public interface Question {

    String getQuestionText();

    String getExplanation();

    String[] getOptions();

    boolean checkAnswer(String userAnswer);

    int getQuestionID();
}
