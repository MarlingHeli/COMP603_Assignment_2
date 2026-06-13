package User_Interface.Graphical;
import Controller.MainController;
import Model.GameState;
import Model.StateListener;
import Model.AppStateModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class GUI extends JFrame implements StateListener {
 private AppStateModel appState;
 private MainController controller;
 private CardLayout cardLayout;
 private JPanel container;
 private MenuPanel menuPanel;
 private NewGamePanel newGamePanel;
 private IntroPanel introPanel;
 private QuizPanel quizPanel;
 private ResultPanel resultPanel;
 private LoadPanel loadPanel;
 private LeaderboardPanel leaderboardPanel;
 private EndPanel endPanel;
 public GUI(AppStateModel appState, MainController controller) {
 this.appState = appState;
 this.controller = controller;
 this.cardLayout = new CardLayout();
 this.container = new JPanel(cardLayout);
 setTitle("Feed Me Java");
 setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 setSize(800, 600);
 setLocationRelativeTo(null);
 // FIXED: Lock the program window dimensions so users cannot stretch or resize any screen
 setResizable(false);
 addWindowListener(new WindowAdapter() {
 @Override
 public void windowClosing(WindowEvent e) {
 handleWindowCloseRequest();
 }
 });
 menuPanel = new MenuPanel(controller);
 newGamePanel = new NewGamePanel(controller);
 introPanel = new IntroPanel(controller, appState);
 quizPanel = new QuizPanel(controller, appState);
 resultPanel = new ResultPanel(controller, appState);
 loadPanel = new LoadPanel(controller);
 leaderboardPanel = new LeaderboardPanel(controller);
 endPanel = new EndPanel(controller, appState);
 container.add(menuPanel, GameState.MENU.name());
 container.add(newGamePanel, GameState.NAME_INPUT.name());
 container.add(introPanel, GameState.INTRO.name());
 container.add(quizPanel, GameState.QUIZ.name());
 container.add(resultPanel, GameState.RESULT.name());
 container.add(loadPanel, GameState.LOAD_SCREEN.name());
 container.add(leaderboardPanel, GameState.LEADERBOARD.name());
 container.add(endPanel, GameState.END.name());
 add(container);
 setVisible(true);
 }
 private void handleWindowCloseRequest() {
 GameState currentState = appState.getState();
 if (currentState == GameState.QUIZ || currentState == GameState.RESULT) {
 String[] options = {"Save & Exit", "Exit Without Saving", "Cancel"};
 int choice = JOptionPane.showOptionDialog(
 this, "Would you like to save your current quiz progression before exiting?", "Exit Game",
 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options
 );
 if (choice == 0) { controller.saveAndExit(); } 
 else if (choice == 1) { controller.exit(); }
 } else {
 controller.exit();
 }
 }
 @Override
 public void onStateChanged(GameState state) {
 if (state == GameState.QUIZ) {
 quizPanel.refreshQuiz();
 } else if (state == GameState.RESULT) {
 resultPanel.refreshFeedback();
 } else if (state == GameState.LOAD_SCREEN) {
 loadPanel.resetToUserLookup();
 } else if (state == GameState.LEADERBOARD) {
 leaderboardPanel.refreshLeaderboard();
 } else if (state == GameState.END) {
 endPanel.refreshResults();
 }
 cardLayout.show(container, state.name());
 }
}
