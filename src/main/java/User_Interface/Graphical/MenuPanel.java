package User_Interface.Graphical;
import Controller.MainController;
import javax.swing.*;
import java.awt.*;
public class MenuPanel extends JPanel {
 public MenuPanel(MainController controller) {
 setLayout(new BorderLayout());
 BackgroundPanel bgPanel;
 try {
 bgPanel = new BackgroundPanel("/Backgrounds/MenuBgWithLogo.png");
 } catch (Exception e) {
 bgPanel = new BackgroundPanel("");
 bgPanel.setBackground(Color.DARK_GRAY);
 }
 bgPanel.setLayout(new GridBagLayout());
 JPanel lowerHalfPanel = new JPanel(new GridBagLayout());
 lowerHalfPanel.setOpaque(false);
 GridBagConstraints gbc = new GridBagConstraints();
 gbc.insets = new Insets(8, 10, 8, 10);
 gbc.gridx = 0;
 gbc.fill = GridBagConstraints.HORIZONTAL;
 JButton newGameBtn = new JButton("New Game");
 newGameBtn.setPreferredSize(new Dimension(200, 38));
 newGameBtn.addActionListener(e -> controller.handleNewGameSetup());
 gbc.gridy = 0; lowerHalfPanel.add(newGameBtn, gbc);
 JButton loadGameBtn = new JButton("Load Game");
 loadGameBtn.setPreferredSize(new Dimension(200, 38));
 loadGameBtn.addActionListener(e -> controller.loadGame());
 gbc.gridy = 1; lowerHalfPanel.add(loadGameBtn, gbc);
 // FIXED: Appended dedicated Leaderboard view routing choice button into structural flow
 JButton leaderboardBtn = new JButton("Leaderboard");
 leaderboardBtn.setPreferredSize(new Dimension(200, 38));
 leaderboardBtn.addActionListener(e -> controller.showLeaderboard());
 gbc.gridy = 2; lowerHalfPanel.add(leaderboardBtn, gbc);
 JButton exitBtn = new JButton("Exit");
 exitBtn.setPreferredSize(new Dimension(200, 38));
 exitBtn.addActionListener(e -> controller.exit());
 gbc.gridy = 3; lowerHalfPanel.add(exitBtn, gbc);
 GridBagConstraints mainGbc = new GridBagConstraints();
 mainGbc.gridx = 0; mainGbc.gridy = 1; mainGbc.weighty = 0.5;
 mainGbc.anchor = GridBagConstraints.CENTER;
 bgPanel.add(lowerHalfPanel, mainGbc);
 GridBagConstraints spacerGbc = new GridBagConstraints();
 spacerGbc.gridx = 0; spacerGbc.gridy = 0; spacerGbc.weighty = 0.5;
 JPanel spacer = new JPanel(); spacer.setOpaque(false);
 bgPanel.add(spacer, spacerGbc);
 add(bgPanel, BorderLayout.CENTER);
 }
}
