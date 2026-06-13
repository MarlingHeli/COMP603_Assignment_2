package User_Interface.Graphical;
import Controller.MainController;
import Model.AppStateModel;
import javax.swing.*;
import java.awt.*;
public class IntroPanel extends JPanel {
 private JLabel storyLabel;
 private JPanel bubbleBox;
 private JButton actionBtn;
 private int currentPage = 1;
 private AppStateModel appState;
 public IntroPanel(MainController controller, AppStateModel appState) {
 this.appState = appState;
 setLayout(new BorderLayout());
 BackgroundPanel bgPanel;
 try {
 bgPanel = new BackgroundPanel("/Backgrounds/IntroImg.png");
 } catch (Exception e) {
 bgPanel = new BackgroundPanel("");
 bgPanel.setBackground(Color.DARK_GRAY);
 }
 bgPanel.setLayout(new GridBagLayout());
 GridBagConstraints gbc = new GridBagConstraints();
 gbc.gridx = 0;
 bubbleBox = new JPanel(new BorderLayout());
 bubbleBox.setBackground(new Color(0, 0, 0, 190));
 bubbleBox.setBorder(BorderFactory.createCompoundBorder(
 BorderFactory.createLineBorder(new Color(255, 255, 255, 60), 1),
 BorderFactory.createEmptyBorder(20, 25, 20, 25)
 ));
 storyLabel = new JLabel();
 storyLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
 storyLabel.setForeground(Color.WHITE);
 bubbleBox.add(storyLabel, BorderLayout.CENTER);
 gbc.gridy = 0; gbc.weightx = 1.0; gbc.weighty = 0.5;
 gbc.fill = GridBagConstraints.NONE;
 gbc.anchor = GridBagConstraints.NORTH;
 gbc.insets = new Insets(10, 0, 0, 0);
 bgPanel.add(bubbleBox, gbc);
 actionBtn = new JButton("Next");
 actionBtn.setPreferredSize(new Dimension(220, 42));
 actionBtn.setFont(new Font("Arial", Font.BOLD, 14));
 actionBtn.addActionListener(e -> {
 if (currentPage < 3) {
 currentPage++;
 updateStoryPage();
 } else {
 controller.proceedToQuiz();
 }
 });
 gbc.gridy = 1; gbc.weighty = 0.5;
 gbc.anchor = GridBagConstraints.SOUTH;
 gbc.insets = new Insets(0, 0, 40, 0);
 bgPanel.add(actionBtn, gbc);
 add(bgPanel, BorderLayout.CENTER);
 addComponentListener(new java.awt.event.ComponentAdapter() {
 @Override
 public void componentShown(java.awt.event.ComponentEvent e) {
 currentPage = 1;
 updateStoryPage();
 }
 });
 }
 private void updateStoryPage() {
 if (appState.getCurrentUser() == null) return;
 String user = appState.getCurrentUser().getUsername();
 String pet = appState.getCurrentUser().getPetName();
 StringBuilder sb = new StringBuilder();
 sb.append("<html><div style='text-align: center; width: 500px;'>");
 if (currentPage == 1) {
 sb.append("I was about to relax at home when <b>").append(pet).append("</b> squeezed through the window.<br><br>");
 sb.append("\"Where did you go, buddy?\" I pulled <b>").append(pet).append("</b> into my arms.<br><br>");
 sb.append("\"You're always so adventurous.\"");
 actionBtn.setText("Next");
 } else if (currentPage == 2) {
 sb.append("\"I passed by the Animal University of Technology today.<br>");
 sb.append("There's a Java competition going on,\" <b>").append(pet).append("</b> announced.<br><br>");
 sb.append("\"I have enrolled you, <b>").append(user).append("</b>.\"<br>");
 sb.append("\"Why!? I don't know much Java,\" I protested.");
 actionBtn.setText("Next");
 } else if (currentPage == 3) {
 sb.append("\"Because first place wins a lifetime supply of pet food.<br>");
 sb.append("And I plan to FEAST!\" <b>").append(pet).append("</b> cackled.<br>");
 sb.append("I paused.<br><br>");
 sb.append("\"Well, it would spare my wallet...\"");
 actionBtn.setText("Continue to Quiz");
 }
 sb.append("</div></html>");
 storyLabel.setText(sb.toString());
 bubbleBox.revalidate();
 bubbleBox.repaint();
 }
}
