package User_Interface.Graphical;
import Controller.MainController;
import Model.User;
import Model.QuizSession;
import javax.swing.*;
import java.awt.*;
public class LoadPanel extends JPanel {
 private MainController controller;
 private CardLayout nestedLayout;
 private JPanel deckPanel;
 // Components for User Lookup Phase
 private JTextField searchUserField;
 // Components for Save Selection Phase
 private DefaultListModel<String> saveListModel;
 private JList<String> saveList;
 private User activeUser;
 private QuizSession activeSession;
 public LoadPanel(MainController controller) {
 this.controller = controller;
 setLayout(new BorderLayout());
 nestedLayout = new CardLayout();
 deckPanel = new JPanel(nestedLayout);
 deckPanel.setOpaque(false);
 buildLookupView();
 buildSelectionView();
 add(deckPanel, BorderLayout.CENTER);
 }
 // Phase 1 View: Searches usernames centered on the right side over WhichUser.png
 private void buildLookupView() {
 BackgroundPanel bgPanel;
 try {
 bgPanel = new BackgroundPanel("/Backgrounds/WhichUser.png");
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
 gbc.insets = new Insets(10, 10, 10, 10);
 gbc.fill = GridBagConstraints.HORIZONTAL;
 gbc.gridx = 0; gbc.gridy = 0;
 JLabel promptLabel = new JLabel("Enter Username to Load:");
 promptLabel.setFont(new Font("Arial", Font.BOLD, 14));
 promptLabel.setForeground(Color.WHITE);
 rightSidePanel.add(promptLabel, gbc);
 searchUserField = new JTextField(15);
 gbc.gridy = 1;
 rightSidePanel.add(searchUserField, gbc);
 JButton verifyBtn = new JButton("Search Saves");
 verifyBtn.addActionListener(e -> processUserVerification());
 gbc.gridy = 2; gbc.insets = new Insets(20, 10, 5, 10);
 rightSidePanel.add(verifyBtn, gbc);
 // FIXED: Added a Back option to return out of the entry lookup card back into the Main Menu
 JButton backBtn = new JButton("Back to Menu");
 backBtn.addActionListener(e -> controller.start());
 gbc.gridy = 3; gbc.insets = new Insets(5, 10, 10, 10);
 rightSidePanel.add(backBtn, gbc);
 bgPanel.add(rightSidePanel);
 deckPanel.add(bgPanel, "LOOKUP");
 }
 // Phase 2 View: Displays scrollable file entries on the left side over LoadGameBg.png
 private void buildSelectionView() {
 BackgroundPanel bgPanel;
 try {
 bgPanel = new BackgroundPanel("/Backgrounds/LoadGameBg.png");
 } catch (Exception e) {
 bgPanel = new BackgroundPanel("");
 bgPanel.setBackground(Color.LIGHT_GRAY);
 }
 bgPanel.setLayout(new GridLayout(1, 2));
 JPanel leftSidePanel = new JPanel(new BorderLayout(10, 10));
 leftSidePanel.setOpaque(false);
 leftSidePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 20));
 JLabel listHeader = new JLabel("Available Saved Sessions:");
 listHeader.setFont(new Font("Arial", Font.BOLD, 14));
 listHeader.setForeground(Color.WHITE);
 leftSidePanel.add(listHeader, BorderLayout.NORTH);
 saveListModel = new DefaultListModel<>();
 saveList = new JList<>(saveListModel);
 saveList.setFont(new Font("Monospaced", Font.PLAIN, 13));
 saveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 JScrollPane scrollPane = new JScrollPane(saveList);
 leftSidePanel.add(scrollPane, BorderLayout.CENTER);
 // Control column to trigger launching save states or shifting back to lookup choices
 JPanel controlsColumn = new JPanel(new GridLayout(2, 1, 0, 10));
 controlsColumn.setOpaque(false);
 JButton confirmLoadBtn = new JButton("Launch Selected Save");
 confirmLoadBtn.setPreferredSize(new Dimension(0, 40));
 confirmLoadBtn.addActionListener(e -> processSaveExecution());
 controlsColumn.add(confirmLoadBtn);
 JButton innerBackBtn = new JButton("Access a different user's saves");
 innerBackBtn.setPreferredSize(new Dimension(0, 40));
 innerBackBtn.addActionListener(e -> resetToUserLookup());
 controlsColumn.add(innerBackBtn);
 leftSidePanel.add(controlsColumn, BorderLayout.SOUTH);
 bgPanel.add(leftSidePanel);
 JPanel rightSpacer = new JPanel();
 rightSpacer.setOpaque(false);
 bgPanel.add(rightSpacer);
 deckPanel.add(bgPanel, "SELECTION");
 }
 public void resetToUserLookup() {
 searchUserField.setText("");
 activeUser = null;
 activeSession = null;
 nestedLayout.show(deckPanel, "LOOKUP");
 }
 private void processUserVerification() {
 String input = searchUserField.getText().trim();
 if (input.isEmpty()) {
 JOptionPane.showMessageDialog(this, "Please input a valid username value.");
 return;
 }
 User foundUser = controller.verifyAndFetchUser(input);
 if (foundUser == null) {
 JOptionPane.showMessageDialog(this, "Invalid Username! Profile not found inside database.", "Error", JOptionPane.ERROR_MESSAGE);
 controller.start();
 return;
 }
 this.activeUser = foundUser;
 this.activeSession = controller.fetchSavedSession(this.activeUser);
 saveListModel.clear();
 if (this.activeSession != null) {
 String fileEntrySummaryText = String.format("Player: %-12s | Companion: %-12s | Index: %d", 
 activeUser.getUsername(), activeUser.getPetName(), (activeSession.getCurrentQuestionIndex() + 1));
 saveListModel.addElement(fileEntrySummaryText);
 } else {
 saveListModel.addElement("No active game progression records found for this user.");
 }
 nestedLayout.show(deckPanel, "SELECTION");
 }
 private void processSaveExecution() {
 if (activeUser == null || activeSession == null || saveList.getSelectedIndex() == -1) {
 JOptionPane.showMessageDialog(this, "Please highlight a valid saved quiz session line item.");
 return;
 }
 controller.resumeLoadedQuiz(activeUser, activeSession);
 }
}
