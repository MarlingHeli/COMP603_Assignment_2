package User_Interface.Graphical;

import Controller.MainController;
import Model.AppStateModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResultPanel extends JPanel {

    private MainController controller;
    private AppStateModel appState;
    private JLabel statusLabel, explanationLabel;
    private JLabel nextBtnLabel;
    private ImageIcon defaultIcon, hoverIcon;

    public ResultPanel(MainController controller, AppStateModel appState) {
        this.controller = controller;
        this.appState = appState;
        setLayout(new BorderLayout());
        // 1. Initialise tracking panel matching identical backgrounds to Quiz Panel
        BackgroundPanel bgPanel;
        try {
            bgPanel = new BackgroundPanel("/Backgrounds/QuizBg.png");
        } catch (Exception e) {
            bgPanel = new BackgroundPanel("");
            bgPanel.setBackground(Color.DARK_GRAY);
        }
        bgPanel.setLayout(new GridBagLayout());
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.fill = GridBagConstraints.BOTH;
        mainGbc.weightx = 1.0;
        mainGbc.weighty = 1.0;
        // Mimics identical skinny structural rectangle margins width compression
        mainGbc.insets = new Insets(3, 130, 3, 130);
        JPanel contentCard = new JPanel(new BorderLayout());
        contentCard.setOpaque(false);
        contentCard.setBorder(BorderFactory.createEmptyBorder());
        JPanel innerGrid = new JPanel(new GridBagLayout());
        innerGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        // 2. Header label field signaling pass or fail correctness metrics
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(40, 30, 10, 30);
        innerGrid.add(statusLabel, gbc);
        // 3. Sub-text summary block rendering multiline explanations inside rectangle bounds
        explanationLabel = new JLabel("", SwingConstants.CENTER);
        explanationLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        explanationLabel.setForeground(Color.BLACK);
        gbc.gridy = 1;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(10, 30, 40, 30);
        innerGrid.add(explanationLabel, gbc);
        contentCard.add(innerGrid, BorderLayout.CENTER);
        // 4. Remount custom page-turning button vector graphic asset links
        try {
            java.net.URL imgURL = getClass().getResource("/Components/PageTurn.png");
            if (imgURL != null) {
                defaultIcon = new ImageIcon(imgURL);
                Image img = defaultIcon.getImage();
                java.awt.image.ImageFilter filter = new javax.swing.GrayFilter(true, 50);
                java.awt.image.ImageProducer producer = new java.awt.image.FilteredImageSource(img.getSource(), filter);
                hoverIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(producer));
            }
        } catch (Exception e) {
            defaultIcon = null;
            hoverIcon = null;
        }
        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        actionRow.setOpaque(false);
        nextBtnLabel = new JLabel();
        if (defaultIcon != null) {
            nextBtnLabel.setIcon(defaultIcon);
            nextBtnLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            nextBtnLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (hoverIcon != null) {
                        nextBtnLabel.setIcon(hoverIcon);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (defaultIcon != null) {
                        nextBtnLabel.setIcon(defaultIcon);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    controller.advanceFromFeedback();
                }
            });
            actionRow.add(nextBtnLabel);
        } else {
            JButton fallbackBtn = new JButton("Next Question");
            fallbackBtn.addActionListener(e -> controller.advanceFromFeedback());
            actionRow.add(fallbackBtn);
        }
        contentCard.add(actionRow, BorderLayout.SOUTH);
        bgPanel.add(contentCard, mainGbc);
        add(bgPanel, BorderLayout.CENTER);
    }
    // Refreshes textual validation configurations using HTML macros upon state navigation switches

    public void refreshFeedback() {
        if (controller.isLastAnswerCorrect()) {
            statusLabel.setText("CORRECT!");
            statusLabel.setForeground(new Color(34, 139, 34)); // Crisp dark green tone
        } else {
            statusLabel.setText("INCORRECT");
            statusLabel.setForeground(new Color(178, 34, 34)); // Crisp dark red tone
        }
        String rawExplanation = controller.getLastExplanationText();
        if (rawExplanation == null || rawExplanation.trim().isEmpty()) {
            rawExplanation = "No extra details supplied for this question task entry.";
        }
        // Constraints multiline width cleanly to wrap inside the skinny rectangle boundary card
        explanationLabel.setText("<html><center><div style='width: 320px; text-align: center;'>"
                + rawExplanation.replace("\n", "<br>") + "</div></center></html>");
    }
}
