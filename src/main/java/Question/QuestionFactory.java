/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Question;

/**
 *
 * @author hmarl
 */
public class QuestionFactory {
//only has MCQ at the moment. could add true/false later, etc.
    public Question createMultiChoiceQuestion(int id, String questionText, 
            String answer1, String answer2, String answer3, int correctAnswer,
            String explanation) {
        
        return new MultiChoiceQuestion(id, questionText, answer1, answer2, answer3,
            correctAnswer, explanation  );
    }
}