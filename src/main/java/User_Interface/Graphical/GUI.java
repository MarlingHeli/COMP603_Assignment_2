package User_Interface.Graphical;

import User_Interface.UI;
import java.awt.BorderLayout;
import javax.swing.*;

public class GUI extends JPanel implements UI {

    protected JTextArea mainTextArea;
    protected JTextField errorField;
    protected JTextField inputField;

    public GUI() {

        mainTextArea = new JTextArea();
        errorField = new JTextField();
        inputField = new JTextField();

        errorField.setEditable(false);

        add(mainTextArea);
        add(errorField);
        add(inputField);
    }
    
    public static void main(String[] args) {

        JFrame frame = new JFrame("Feed Me Java!");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GUI gui = new GUI();

        frame.add(gui);

        frame.setVisible(true);
    }

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

        JTextField inputField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(submitButton);

        add(inputPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();

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

        remove(inputPanel);

        revalidate();
        repaint();

        return result[0];
    }

    @Override
    public void slowPrint(String text) {

        displayText(text);

        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}