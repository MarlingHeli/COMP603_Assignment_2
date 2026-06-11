package User_Interface.Graphical;

import Model.User;
import Question.Question;
import User_Interface.UI;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI extends JFrame implements UI {

    protected JTextArea mainTextArea;
    protected JTextField errorField;
    protected JTextField inputField;
    protected JButton submitButton;

    public GUI() {

        setTitle("Feed Me Java!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        mainTextArea = new JTextArea();
        errorField = new JTextField();
        inputField = new JTextField(20);
        submitButton = new JButton("Submit");

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(inputField);
        bottomPanel.add(submitButton);

        add(bottomPanel, BorderLayout.SOUTH);

        errorField.setEditable(false);

        setLayout(new BorderLayout());

        add(mainTextArea, BorderLayout.CENTER);
        //add(inputField, BorderLayout.SOUTH);
        add(errorField, BorderLayout.EAST);
    }
    
    //potentially create a different class to handle this.
    @Override
    public int showMenu() {

        final int[] result = {-1};

        JDialog dialog = new JDialog((JFrame) null, "Feed Me Java!", true);
        dialog.setSize(900, 700);
        dialog.setLocationRelativeTo(null);
        
        JPanel panel = new BackgroundPanel("/FeedMeJavaBg.png");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start New Game");
        JButton loadButton = new JButton("Load Game");
        JButton exitButton = new JButton("Exit");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            result[0] = 1;
            dialog.dispose();
        });

        loadButton.addActionListener(e -> {
            result[0] = 2;
            dialog.dispose();
        });

        exitButton.addActionListener(e -> {
            result[0] = 3;
            dialog.dispose();
        });

        panel.add(Box.createVerticalGlue());
        panel.add(startButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loadButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(exitButton);
        panel.add(Box.createVerticalGlue());

        dialog.add(panel);

        dialog.setVisible(true);

        return result[0];
    }
    
    @Override
    public void printStory(String user, String pet, Runnable onFinish) {

        getContentPane().removeAll();

        add(new IntroPanel(user, pet, onFinish));

        revalidate();
        repaint();
    }
    
    /*
    @Override
    int showQuiz(){
        return 0;
    }
    
    @Override
    int showResults(){
        return 0;
    }
    */

    @Override
    public void displayText(String text) {
        mainTextArea.setText(text);
    }

    @Override
    public void displayError(String text) {
        errorField.setText(text);
    }
    @Override
    public String getUserInput(String prompt) {

        // Clear current screen
        getContentPane().removeAll();

        // Create a centered panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel promptLabel = new JLabel(prompt);
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputField.setText("");
        inputField.setMaximumSize(new Dimension(300, 30));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(promptLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(inputField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(submitButton);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        revalidate();
        repaint();

        final String[] result = new String[1];
        
        ActionListener submitAction = e -> {
            String input = inputField.getText().trim();

            // Reject empty input
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a value."
                );
                return;
            }

            result[0] = input;

            synchronized (result) {
                result.notify();
            }
        };

        // Click Submit button
        submitButton.addActionListener(submitAction);

        // Press Enter in text field
        inputField.addActionListener(submitAction);
        

        inputField.requestFocusInWindow();

        synchronized (result) {
            while (result[0] == null) {
                try {
                    result.wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        submitButton.removeActionListener(submitAction);
        inputField.removeActionListener(submitAction);
        return result[0];
    }

    @Override
    public void slowPrint(String text) {
        return;
    }

    @Override
    public void showQuiz(List<Question> questions, User user) {

        getContentPane().removeAll();

        add(new QuizPanel());

        revalidate();
        repaint();
    }
    
    public void showResult(
        boolean correct,
        int score,
        String explanation
    ) {

        getContentPane().removeAll();

        add(
            new ResultPanel(
                correct,
                score,
                explanation
            )
        );

        revalidate();
        repaint();
    }
    
}