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
 private EndPanel endPanel;
 public GUI(AppStateModel appState, MainController controller) {
 this.appState = appState;
 this.controller = controller;
 this.cardLayout = new CardLayout();
 this.container = new JPanel(cardLayout);
 setTitle("Feed Me Java");
 // 1. CRITICAL: Strictly forbid the operating system from closing the window automatically
 setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 setSize(800, 600);
 setLocationRelativeTo(null);
 // 2. Add an explicit WindowListener to catch the exact moment the top-right "X" is clicked
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
 endPanel = new EndPanel(controller, appState);
 container.add(menuPanel, GameState.MENU.name());
 container.add(newGamePanel, GameState.NAME_INPUT.name());
 container.add(introPanel, GameState.INTRO.name());
 container.add(quizPanel, GameState.QUIZ.name());
 container.add(resultPanel, GameState.RESULT.name());
 container.add(loadPanel, GameState.LOAD_SCREEN.name());
 container.add(endPanel, GameState.END.name());
 add(container);
 setVisible(true);
 }
 // 3. Intercepts the window close action and forces the interactive choice prompt if in-game
 private void handleWindowCloseRequest() {
 GameState currentState = appState.getState();
 // Check if the player is currently sitting in either the live quiz card or the results card view
 if (currentState == GameState.QUIZ || currentState == GameState.RESULT) {
 String[] options = {"Save & Exit", "Exit Without Saving", "Cancel"};
 int choice = JOptionPane.showOptionDialog(
 this,
 "Would you like to save your current quiz progression before exiting?",
 "Exit Game",
 JOptionPane.YES_NO_CANCEL_OPTION,
 JOptionPane.QUESTION_MESSAGE,
 null,
 options,
 options
 );
 if (choice == 0) {
 // Player selected "Save & Exit" -> Persist data and shut down
 controller.saveAndExit();
 } else if (choice == 1) {
 // Player selected "Exit Without Saving" -> Shut down immediately
 controller.exit();
 }
 // If choice is 2 (Cancel) or the popup dialog itself is closed out, simply return to the game loop
 } else {
 // If the player is on any secondary screen (Menu, Input forms, End game board), close gracefully directly
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
 } else if (state == GameState.END) {
 endPanel.refreshResults();
 }
 cardLayout.show(container, state.name());
 }
}
