package User_Interface.Graphical;
import Controller.MainController;
import Model.AppStateModel;
import Model.QuizSession;
import javax.swing.*;
import java.awt.*;
public class EndPanel extends JPanel {
 private AppStateModel appState;
 private JLabel scoreLabel, dialogueLabel, trophyLabel;
 public EndPanel(MainController controller, AppStateModel appState) {
 this.appState = appState;
 setLayout(new GridBagLayout());
 GridBagConstraints gbc = new GridBagConstraints();
 gbc.insets = new Insets(10, 10, 10, 10);
 gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
 scoreLabel = new JLabel("", SwingConstants.CENTER);
 scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
 gbc.gridy = 0; add(scoreLabel, gbc);
 trophyLabel = new JLabel("", SwingConstants.CENTER);
 trophyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
 gbc.gridy = 1; add(trophyLabel, gbc);
 dialogueLabel = new JLabel("", SwingConstants.CENTER);
 gbc.gridy = 2; add(dialogueLabel, gbc);
 JButton restartBtn = new JButton("Back to Main Menu");
 restartBtn.addActionListener(e -> controller.start());
 gbc.gridy = 3; add(restartBtn, gbc);
 JButton quitBtn = new JButton("Quit Game");
 quitBtn.addActionListener(e -> controller.exit());
 gbc.gridy = 4; add(quitBtn, gbc);
 }
 public void refreshResults() {
 QuizSession session = appState.getCurrentQuiz();
 if (session != null) {
 scoreLabel.setText(session.getScoreText());
 trophyLabel.setText("Trophy Earned: " + session.getTrophy());
 dialogueLabel.setText("<html>" + session.getEndingDialogue() + "</html>");
 }
 }
}
