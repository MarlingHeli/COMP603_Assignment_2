package User_Interface.Graphical;
import Controller.MainController;
import Model.AppStateModel;
import Model.QuizSession;
import Question.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class QuizPanel extends JPanel {
 private MainController controller;
 private AppStateModel appState;
 private JLabel qLabel, progressLabel;
 private JRadioButton opt1, opt2, opt3;
 private ButtonGroup group;
 private JLabel submitBtnLabel;
 private ImageIcon defaultIcon, hoverIcon;
 public QuizPanel(MainController controller, AppStateModel appState) {
 this.controller = controller;
 this.appState = appState;
 setLayout(new BorderLayout());
 // 1. Initialise master background panel window layer
 BackgroundPanel bgPanel;
 try {
 bgPanel = new BackgroundPanel("/Backgrounds/QuizBg.png");
 } catch (Exception e) {
 bgPanel = new BackgroundPanel("");
 bgPanel.setBackground(Color.DARK_GRAY);
 }
 bgPanel.setLayout(new GridBagLayout());
 GridBagConstraints mainGbc = new GridBagConstraints();
 mainGbc.gridx = 0; mainGbc.gridy = 0;
 mainGbc.fill = GridBagConstraints.BOTH;
 mainGbc.weightx = 1.0; mainGbc.weighty = 1.0;
 mainGbc.insets = new Insets(3, 130, 3, 130);
 // 2. Create a fully transparent content card panel with no visible borders
 JPanel contentCard = new JPanel(new BorderLayout());
 contentCard.setOpaque(false); 
 contentCard.setBorder(BorderFactory.createEmptyBorder());
 // Inner layout structure grid to stack text and buttons vertically within bounds
 JPanel innerGrid = new JPanel(new GridBagLayout());
 innerGrid.setOpaque(false);
 GridBagConstraints gbc = new GridBagConstraints();
 gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.CENTER;
 // 3. Question tracking number at the top
 progressLabel = new JLabel("Question 0/0", SwingConstants.CENTER);
 progressLabel.setFont(new Font("Arial", Font.BOLD, 13));
 progressLabel.setForeground(Color.BLACK);
 gbc.gridy = 0; gbc.weighty = 0.05; gbc.insets = new Insets(25, 30, 5, 30);
 innerGrid.add(progressLabel, gbc);
 // 4. Question content text directly below it
 qLabel = new JLabel("", SwingConstants.CENTER);
 qLabel.setFont(new Font("Arial", Font.BOLD, 15));
 qLabel.setForeground(Color.BLACK);
 gbc.gridy = 1; gbc.weighty = 0.25; gbc.insets = new Insets(5, 30, 15, 30);
 innerGrid.add(qLabel, gbc);
 // 5. Vertical choices block stacked centered on the bottom half
 JPanel optionsPanel = new JPanel(new GridBagLayout());
 optionsPanel.setOpaque(false);
 GridBagConstraints oGbc = new GridBagConstraints();
 oGbc.gridx = 0; oGbc.fill = GridBagConstraints.HORIZONTAL; oGbc.anchor = GridBagConstraints.WEST;
 oGbc.insets = new Insets(10, 0, 10, 0);
 opt1 = new JRadioButton(); opt2 = new JRadioButton(); opt3 = new JRadioButton();
 opt1.setFont(new Font("Arial", Font.PLAIN, 14)); opt1.setForeground(Color.BLACK); opt1.setOpaque(false);
 opt2.setFont(new Font("Arial", Font.PLAIN, 14)); opt2.setForeground(Color.BLACK); opt2.setOpaque(false);
 opt3.setFont(new Font("Arial", Font.PLAIN, 14)); opt3.setForeground(Color.BLACK); opt3.setOpaque(false);
 group = new ButtonGroup(); group.add(opt1); group.add(opt2); group.add(opt3);
 oGbc.gridy = 0; optionsPanel.add(opt1, oGbc);
 oGbc.gridy = 1; optionsPanel.add(opt2, oGbc);
 oGbc.gridy = 2; optionsPanel.add(opt3, oGbc);
 gbc.gridy = 2; gbc.weighty = 0.55; gbc.fill = GridBagConstraints.NONE; gbc.insets = new Insets(5, 50, 5, 50);
 innerGrid.add(optionsPanel, gbc);
 contentCard.add(innerGrid, BorderLayout.CENTER);
 // Load resource graphics assets for page turning link triggers
 try {
 java.net.URL imgURL = getClass().getResource("/Components/PageTurn.png");
 if (imgURL != null) {
 defaultIcon = new ImageIcon(imgURL);
 Image img = defaultIcon.getImage();
 Image hoverImg = grayScaleImage(img);
 hoverIcon = new ImageIcon(hoverImg);
 }
 } catch (Exception e) {
 defaultIcon = null; hoverIcon = null;
 }
 // 6. Corner-pin the submit button label structure inside the lower-right margin bounds
 JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
 actionRow.setOpaque(false);
 submitBtnLabel = new JLabel();
 if (defaultIcon != null) {
 submitBtnLabel.setIcon(defaultIcon);
 submitBtnLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 submitBtnLabel.addMouseListener(new MouseAdapter() {
 @Override
 public void mouseEntered(MouseEvent e) { if (hoverIcon != null) submitBtnLabel.setIcon(hoverIcon); }
 @Override
 public void mouseExited(MouseEvent e) { if (defaultIcon != null) submitBtnLabel.setIcon(defaultIcon); }
 @Override
 public void mouseClicked(MouseEvent e) { processAnswer(); }
 });
 actionRow.add(submitBtnLabel);
 } else {
 JButton fallbackBtn = new JButton("Submit Answer");
 fallbackBtn.addActionListener(e -> processAnswer());
 actionRow.add(fallbackBtn);
 }
 contentCard.add(actionRow, BorderLayout.SOUTH);
 bgPanel.add(contentCard, mainGbc);
 add(bgPanel, BorderLayout.CENTER);
 }
 // Synchronises component layout state changes during card transitions
 public void refreshQuiz() {
 QuizSession session = appState.getCurrentQuiz();
 if (session == null || session.isFinished()) {
 controller.completeQuiz();
 return;
 }
 progressLabel.setText("Question " + (session.getCurrentQuestionIndex() + 1) + "/" + session.getTotalQuestions());
 Question q = session.getCurrentQuestion();
 if (q != null && q.getOptions() != null && q.getOptions().length >= 3) {
 // Forces multi-line line breaks on questions to fit cleanly within rectangle bounds
 qLabel.setText("<html><center><div style='width: 320px; text-align: center;'>" + q.getQuestionText().replace("\n", "<br>") + "</div></center></html>");
 String[] options = q.getOptions();
 // FIXED: Explicitly target specific indices, [1], and [2] inside the text arrays to print clean strings
 opt1.setText("<html><div style='width: 300px; text-align: left;'>" + options[0] + "</div></html>");
 opt2.setText("<html><div style='width: 300px; text-align: left;'>" + options[1] + "</div></html>");
 opt3.setText("<html><div style='width: 300px; text-align: left;'>" + options[2] + "</div></html>");
 group.clearSelection();
 }
 }
 // Evaluates quiz radio state matching conditions against backend question rules
 private void processAnswer() {
 QuizSession session = appState.getCurrentQuiz();
 if (session == null) return;
 Question q = session.getCurrentQuestion();
 if (q == null || q.getOptions() == null) return;
 String targetChoiceIndexString = null;
 if (opt1.isSelected()) targetChoiceIndexString = "1";
 else if (opt2.isSelected()) targetChoiceIndexString = "2";
 else if (opt3.isSelected()) targetChoiceIndexString = "3";
 if (targetChoiceIndexString == null) {
 JOptionPane.showMessageDialog(this, "Please select an answer option.");
 return;
 }
 boolean isCorrect = q.checkAnswer(targetChoiceIndexString);
 controller.processAnswerSubmission(isCorrect, q.getExplanation());
 }
 private Image grayScaleImage(Image src) {
 try {
 java.awt.image.ImageFilter filter = new javax.swing.GrayFilter(true, 50);
 java.awt.image.ImageProducer producer = new java.awt.image.FilteredImageSource(src.getSource(), filter);
 return Toolkit.getDefaultToolkit().createImage(producer);
 } catch (Exception e) {
 return src;
 }
 }
}
