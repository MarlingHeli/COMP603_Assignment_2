package User_Interface.Graphical;
import Controller.MainController;
import Model.GameState;
import Model.StateListener;
import Model.AppStateModel;
import javax.swing.*;
import java.awt.*;
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
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setSize(800, 600);
 setLocationRelativeTo(null);
 menuPanel = new MenuPanel(controller);
 newGamePanel = new NewGamePanel(controller);
 introPanel = new IntroPanel(controller, appState);
 quizPanel = new QuizPanel(controller, appState);
 resultPanel = new ResultPanel(controller, appState);
 loadPanel = new LoadPanel(controller); // Mount load game screen panels
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
 @Override
 public void onStateChanged(GameState state) {
 if (state == GameState.QUIZ) {
 quizPanel.refreshQuiz();
 } else if (state == GameState.RESULT) {
 resultPanel.refreshFeedback();
 } else if (state == GameState.LOAD_SCREEN) {
 loadPanel.resetToUserLookup(); // Automatically resets view states on entry
 } else if (state == GameState.END) {
 endPanel.refreshResults();
 }
 cardLayout.show(container, state.name());
 }
}
