/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Question;

/**
 *
 * @author hmarl
 */

public class MultiChoiceQuestion implements Question {
    private String questionText;
    //added explanation text
    private String explanation;
    private int answer;
    private int id;

    public MultiChoiceQuestion(String questionText, String explanation, int answer, int id) {
        this.questionText = questionText;
        this.explanation = explanation;
        this.answer = answer;
        this.id = id;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }
    
    @Override
    public String getExplanation(){
        return explanation;
    }
    
    @Override
    public boolean checkAnswer(String userAnswer) {
        try {
            //parse string for integer
            int choice = Integer.parseInt(userAnswer.trim());
            //check if user input (int) matches question's answer
            return choice == answer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public int getQuestionID() {
        return id;
    }
    
    @Override
    public String[] getOptions() {
        return new String[] {
            "A. Dog",
            "B. Cat",
            "C. Bird"
        };
    }
}