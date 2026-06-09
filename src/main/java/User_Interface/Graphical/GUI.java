package User_Interface.Graphical;

import User_Interface.UI;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.*;

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
        
        //backgroundImage = new ImageIcon(getClass().getResource("/FeedMeJavaBg.png")).getImage();
        JPanel panel = new BackgroundPanel();
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
    public void printStory(String user, String pet) {

        getContentPane().removeAll();
        
        setVisible(true);
        add(new IntroPanel(user, pet));

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

        displayText(prompt);

        inputField.setText("");
        inputField.requestFocusInWindow();

        final String[] result = new String[1];

        submitButton.addActionListener(e -> {
            result[0] = inputField.getText();

            synchronized (result) {
                result.notify();
            }
        });

        synchronized (result) {

            while (result[0] == null) {

                try {
                    result.wait();

                } catch (InterruptedException ex) {

                    Thread.currentThread().interrupt();
                }
            }
        }
        //submitButton.removeActionListener(listener);
        
        return result[0];
    }

    @Override
    public void slowPrint(String text) {
        return;
    }
}