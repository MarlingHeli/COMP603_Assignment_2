package Validation;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import Model.QuizSession;
import Question.Question;
import Question.MultiChoiceQuestion;
public class QuizSessionTest {
 @Test
 public void correctAnswerShouldIncreaseScore() {
 QuizSession quiz = new QuizSession(0, new ArrayList<>(), 0, null);
 quiz.answerCorrect();
 assertEquals(1, quiz.getNumCorrectAnswers());
 }
 @Test
 public void wrongAnswerShouldNotIncreaseScore() {
 QuizSession quiz = new QuizSession(0, new ArrayList<>(), 0, null);
 quiz.answerWrong();
 assertEquals(0, quiz.getNumCorrectAnswers());
 }
 @Test
 public void correctAnswerShouldMoveQuestionForward() {
 QuizSession quiz = new QuizSession(0, new ArrayList<>(), 0, null);
 quiz.answerCorrect();
 assertEquals(1, quiz.getCurrentQuestionIndex());
 }
 @Test
 public void wrongAnswerShouldMoveQuestionForward() {
 QuizSession quiz = new QuizSession(0, new ArrayList<>(), 0, null);
 quiz.answerWrong();
 assertEquals(1, quiz.getCurrentQuestionIndex());
 }
 @Test
 public void multipleCorrectAnswersShouldAccumulateScore() {
 QuizSession quiz = new QuizSession(0, new ArrayList<>(), 0, null);
 quiz.answerCorrect(); quiz.answerCorrect(); quiz.answerCorrect();
 assertEquals(3, quiz.getNumCorrectAnswers());
 }
 @Test
 public void mixedAnswersShouldTrackScoreCorrectly() {
 QuizSession quiz = new QuizSession(0, new ArrayList<>(), 0, null);
 quiz.answerCorrect(); quiz.answerWrong(); quiz.answerCorrect();
 assertEquals(2, quiz.getNumCorrectAnswers());
 }
 // Helper to generate valid questions matching the 7-parameter constructor signature
 private List<Question> makeQuestions(int amount) {
 List<Question> questions = new ArrayList<>();
 for(int i = 0; i < amount; i++) {
 MultiChoiceQuestion q = new MultiChoiceQuestion(
 i, "Question Text " + i, "Option A", "Option B", "Option C", 1, "Explanation Text"
 );
 questions.add(q);
 }
 return questions;
 }
 @Test
 public void goldTrophyShouldBeAwarded() {
 QuizSession quiz = new QuizSession(0, makeQuestions(10), 0, null);
 for(int i = 0; i < 9; i++) { quiz.answerCorrect(); }
 assertEquals("GOLD", quiz.getTrophy()); // FIXED: Matches exact uppercase string model return
 }
 @Test
 public void silverTrophyShouldBeAwarded() {
 QuizSession quiz = new QuizSession(0, makeQuestions(10), 0, null);
 for(int i = 0; i < 7; i++) { quiz.answerCorrect(); }
 assertEquals("SILVER", quiz.getTrophy()); // FIXED: Matches exact uppercase string model return
 }
 @Test
 public void bronzeTrophyShouldBeAwarded() {
 QuizSession quiz = new QuizSession(0, makeQuestions(10), 0, null);
 for(int i = 0; i < 6; i++) { quiz.answerCorrect(); }
 assertEquals("BRONZE", quiz.getTrophy()); // FIXED: Matches exact uppercase string model return
 }
 @Test
 public void noTrophyShouldBeAwarded() {
 QuizSession quiz = new QuizSession(0, makeQuestions(10), 0, null);
 for(int i = 0; i < 2; i++) { quiz.answerCorrect(); }
 assertEquals("NO TROPHY", quiz.getTrophy()); // FIXED: Matches exact uppercase string model return
 }
}
