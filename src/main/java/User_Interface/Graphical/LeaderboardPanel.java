package User_Interface.Graphical;

import Controller.MainController;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LeaderboardPanel extends JPanel {

    private MainController controller;
    private DefaultListModel<String> listModel;
    private JList<String> scoreList;

    public LeaderboardPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    public void refreshLeaderboard() {
        removeAll();
        BackgroundPanel bgPanel;
        try {
            bgPanel = new BackgroundPanel("/Backgrounds/ScoreboardBg.png");
        } catch (Exception e) {
            bgPanel = new BackgroundPanel("");
            bgPanel.setBackground(Color.DARK_GRAY);
        }
        bgPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // FIXED: Changed anchor to WEST and added left padding to push title towards the left side
        gbc.anchor = GridBagConstraints.WEST;
        JLabel titleLabel = new JLabel("LEADERBOARD", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        gbc.weighty = 0.15;
        gbc.insets = new Insets(35, 110, 5, 20); // 60px padding from the left edge
        bgPanel.add(titleLabel, gbc);
        listModel = new DefaultListModel<>();
        HashMap<String, Integer> rawScores = controller.fetchLeaderboardScores();
        if (rawScores != null && !rawScores.isEmpty()) {
            List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(rawScores.entrySet());
            sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            for (Map.Entry<String, Integer> entry : sortedList) {
                String formattedLine = String.format(" Score: %-6d | Player: %s", entry.getValue(), entry.getKey());
                listModel.addElement(formattedLine);
            }
        } else {
            listModel.addElement(" No high scores recorded inside database tables yet.");
        }
        scoreList = new JList<>(listModel);
        scoreList.setFont(new Font("Monospaced", Font.BOLD, 15));
        scoreList.setBackground(new Color(0, 0, 0, 160));
        scoreList.setForeground(Color.YELLOW);
        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setPreferredSize(new Dimension(440, 260));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1));
        // FIXED: Changed anchor to WEST and applied matching left inset to shift the table box leftward
        gbc.gridy = 1;
        gbc.weighty = 0.70;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 15, 250); // Aligned 60px padding from the left edge
        bgPanel.add(scrollPane, gbc);
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.setPreferredSize(new Dimension(220, 40));
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.addActionListener(e -> controller.start());
        // FIXED: Kept button aligned leftwards with the data table elements
        gbc.gridy = 2;
        gbc.weighty = 0.15;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 110, 30, 20);
        bgPanel.add(backBtn, gbc);
        add(bgPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
