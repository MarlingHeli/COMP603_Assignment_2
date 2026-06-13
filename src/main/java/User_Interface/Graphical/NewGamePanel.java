package User_Interface.Graphical;
import Controller.MainController;
import javax.swing.*;
import java.awt.*;
public class NewGamePanel extends JPanel {
 public NewGamePanel(MainController controller) {
 setLayout(new BorderLayout());
 BackgroundPanel bgPanel;
 try {
 bgPanel = new BackgroundPanel("/Backgrounds/StartNewGameBg.png");
 } catch (Exception e) {
 bgPanel = new BackgroundPanel("");
 bgPanel.setBackground(Color.DARK_GRAY);
 }
 bgPanel.setLayout(new GridLayout(1, 2));
 JPanel leftSpacer = new JPanel();
 leftSpacer.setOpaque(false);
 bgPanel.add(leftSpacer);
 JPanel rightSidePanel = new JPanel(new GridBagLayout());
 rightSidePanel.setOpaque(false);
 GridBagConstraints gbc = new GridBagConstraints();
 gbc.insets = new Insets(8, 8, 8, 8);
 gbc.fill = GridBagConstraints.HORIZONTAL;
 gbc.gridx = 0; gbc.gridy = 0;
 JLabel userLabel = new JLabel("Username:");
 userLabel.setForeground(Color.WHITE);
 rightSidePanel.add(userLabel, gbc);
 JTextField userField = new JTextField(15);
 gbc.gridx = 1;
 rightSidePanel.add(userField, gbc);
 gbc.gridx = 0; gbc.gridy = 1;
 JLabel petLabel = new JLabel("Pet Name:");
 petLabel.setForeground(Color.WHITE);
 rightSidePanel.add(petLabel, gbc);
 JTextField petField = new JTextField(15);
 gbc.gridx = 1;
 rightSidePanel.add(petField, gbc);
 JCheckBox skipBox = new JCheckBox("Skip Introduction Sequence");
 skipBox.setForeground(Color.WHITE);
 skipBox.setOpaque(false);
 gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
 rightSidePanel.add(skipBox, gbc);
 JButton startBtn = new JButton("Launch Game");
 startBtn.setPreferredSize(new Dimension(150, 35));
 startBtn.addActionListener(e -> {
 String user = userField.getText().trim();
 String pet = petField.getText().trim();
 if(!user.isEmpty() && !pet.isEmpty()) {
 controller.startNewGame(user, pet, skipBox.isSelected());
 } else {
 JOptionPane.showMessageDialog(this, "Fields cannot be blank!");
 }
 });
 gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(12, 8, 4, 8);
 rightSidePanel.add(startBtn, gbc);
 // ADDED: "Back to Menu" option centered under the main submit button inside the form flow
 JButton backBtn = new JButton("Back to Menu");
 backBtn.setPreferredSize(new Dimension(150, 35));
 backBtn.addActionListener(e -> controller.start());
 gbc.gridy = 4; gbc.insets = new Insets(4, 8, 8, 8);
 rightSidePanel.add(backBtn, gbc);
 bgPanel.add(rightSidePanel);
 add(bgPanel, BorderLayout.CENTER);
 }
}
