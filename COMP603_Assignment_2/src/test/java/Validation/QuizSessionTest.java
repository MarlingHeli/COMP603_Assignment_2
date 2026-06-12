/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

/**
 *
 * @author 2
 */

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import Model.QuizSession;
import java.util.List;
import Question.Question;
import Question.MultiChoiceQuestion;

public class QuizSessionTest {

    @Test
    public void correctAnswerShouldIncreaseScore() {

        QuizSession quiz =
            new QuizSession(
                0,
                new ArrayList<>(),
                0,
                null
            );

        quiz.answerCorrect();

        assertEquals(
            1,
            quiz.getNumCorrectAnswers()
        );
    }
    
    
    //this was an AI suggested test case
    @Test
    public void wrongAnswerShouldNotIncreaseScore() {

        QuizSession quiz =
            new QuizSession(
                0,
                new ArrayList<>(),
                0,
                null
            );

        quiz.answerWrong();

        assertEquals(
            0,
            quiz.getNumCorrectAnswers()
        );
    }
    
    //this was an AI suggested test case
    @Test
    public void correctAnswerShouldMoveQuestionForward() {

        QuizSession quiz =
            new QuizSession(
                0,
                new ArrayList<>(),
                0,
                null
            );

        quiz.answerCorrect();

        assertEquals(
            1,
            quiz.getCurrentQuestionIndex()
        );
    }
    
    //this was an AI suggested test case
    @Test
    public void wrongAnswerShouldMoveQuestionForward() {

        QuizSession quiz =
            new QuizSession(
                0,
                new ArrayList<>(),
                0,
                null
            );

        quiz.answerWrong();

        assertEquals(
            1,
            quiz.getCurrentQuestionIndex()
        );
    }
    
    //this was an AI suggested test case
    @Test
    public void multipleCorrectAnswersShouldAccumulateScore() {

        QuizSession quiz =
            new QuizSession(
                0,
                new ArrayList<>(),
                0,
                null
            );

        quiz.answerCorrect();
        quiz.answerCorrect();
        quiz.answerCorrect();

        assertEquals(
            3,
            quiz.getNumCorrectAnswers()
        );
    }
    
    //this was an AI suggested test case
    @Test
    public void mixedAnswersShouldTrackScoreCorrectly() {

        QuizSession quiz =
            new QuizSession(
                0,
                new ArrayList<>(),
                0,
                null
            );

            quiz.answerCorrect();   // +1
            quiz.answerWrong();     // +0
            quiz.answerCorrect();   // +1

            assertEquals(
                2,
                quiz.getNumCorrectAnswers()
            );
    }
    
    private List<Question> makeQuestions(int amount) {

        List<Question> questions =
            new ArrayList<>();

        for(int i = 0; i < amount; i++) {

            MultiChoiceQuestion q =
                new MultiChoiceQuestion(
                    "Test Question",
                    "Test explanation",
                    1,      // correct answer index
                    i       // id
                );

            questions.add(q);
        }

        return questions;
    }
    
    @Test
    public void goldTrophyShouldBeAwarded() {

        QuizSession quiz =
            new QuizSession(
                0,
                makeQuestions(10),
                0,
                null
            );

        for(int i = 0; i < 9; i++) {
            quiz.answerCorrect();
        }

        assertEquals(
            "Gold",
            quiz.getTrophy()
        );
    }


    @Test
    public void silverTrophyShouldBeAwarded() {

        QuizSession quiz =
            new QuizSession(
                0,
                makeQuestions(10),
                0,
                null
            );

        for(int i = 0; i < 7; i++) {
            quiz.answerCorrect();
        }

        assertEquals(
            "Silver",
            quiz.getTrophy()
        );
    }


    @Test
    public void bronzeTrophyShouldBeAwarded() {

        QuizSession quiz =
            new QuizSession(
                0,
                makeQuestions(10),
                0,
                null
            );

        for(int i = 0; i < 6; i++) {
            quiz.answerCorrect();
        }

        assertEquals(
            "Bronze",
            quiz.getTrophy()
        );
    }


    @Test
    public void noTrophyShouldBeAwarded() {

        QuizSession quiz =
            new QuizSession(
                0,
                makeQuestions(10),
                0,
                null
            );

        for(int i = 0; i < 2; i++) {
            quiz.answerCorrect();
        }

        assertEquals(
            "No Trophy",
            quiz.getTrophy()
        );
    }
    
    
}
