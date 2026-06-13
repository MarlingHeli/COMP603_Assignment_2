package User_Interface.Graphical;

import Controller.MainController;
import Model.AppStateModel;
import Model.QuizSession;
import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    private AppStateModel appState;
    private MainController controller;
    private JLabel scoreLabel, dialogueLabel, trophyLabel;
    private JPanel contentWrapper;

    public EndPanel(MainController controller, AppStateModel appState) {
        this.controller = controller;
        this.appState = appState;
        
        // Use a BorderLayout as the root layout to allow the background panel to scale fully
        setLayout(new BorderLayout());
        
        // Create an inner transparent box layout to wrap and group our active text components
        contentWrapper = new JPanel(new GridBagLayout());
        contentWrapper.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Display final score details
        scoreLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        scoreLabel.setForeground(Color.WHITE);
        gbc.gridy = 0; 
        contentWrapper.add(scoreLabel, gbc);

        // Display current trophy evaluation badge tier text
        trophyLabel = new JLabel("", SwingConstants.CENTER);
        trophyLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        trophyLabel.setForeground(Color.YELLOW);
        gbc.gridy = 1; 
        contentWrapper.add(trophyLabel, gbc);

        // Display dynamic companion narrative speech text blocks
        dialogueLabel = new JLabel("", SwingConstants.CENTER);
        dialogueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        dialogueLabel.setForeground(Color.WHITE);
        gbc.gridy = 2; 
        contentWrapper.add(dialogueLabel, gbc);

        // Return button back out safely into the Main Menu layout sequence loop
        JButton restartBtn = new JButton("Back to Main Menu");
        restartBtn.setPreferredSize(new Dimension(200, 40));
        restartBtn.addActionListener(e -> controller.start());
        gbc.gridy = 3; 
        contentWrapper.add(restartBtn, gbc);

        // Standard exit execution button link
        JButton quitBtn = new JButton("Quit Game");
        quitBtn.setPreferredSize(new Dimension(200, 40));
        quitBtn.addActionListener(e -> controller.exit());
        gbc.gridy = 4; 
        contentWrapper.add(quitBtn, gbc);
    }

    // Automatically recalculates results data and builds background images on screen navigation changes
    public void refreshResults() {
        // Strip out any previous components from the panel container root layer
        removeAll();
        
        QuizSession session = appState.getCurrentQuiz();
        String backgroundPath = "/Backgrounds/MenuBgWithLogo.png"; // Default fallback menu artwork
        
        if (session != null) {
            // Update the textual tracking values straight from model metrics
            scoreLabel.setText(session.getScoreText());
            String trophy = session.getTrophy();
            trophyLabel.setText("Trophy Earned: " + trophy);
            dialogueLabel.setText("<html><center>" + session.getEndingDialogue() + "</center></html>");
            
            // SPECIFIC REQUIREMENT: If the user achieves a GOLD tier, update background image to Gold.png
            if ("GOLD".equalsIgnoreCase(trophy)) {
                backgroundPath = "/Backgrounds/Gold.png";
            }
        }
        
        // Initialise the dynamic structural background panel component wrapping our updated layout path
        BackgroundPanel bgPanel;
        try {
            bgPanel = new BackgroundPanel(backgroundPath);
        } catch (Exception e) {
            bgPanel = new BackgroundPanel("");
            bgPanel.setBackground(Color.DARK_GRAY);
        }
        
        // Anchor our centered labels and menu control options inside the background panel space canvas
        bgPanel.setLayout(new GridBagLayout());
        bgPanel.add(contentWrapper);
        
        // Remount the refreshed background component tree back to screen view coordinates
        add(bgPanel, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
}
