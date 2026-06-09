/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface.Graphical;

/**
 *
 * @author 2
 */

import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {

    private JTextArea storyText;
    private JLabel imageLabel;
    private JButton nextButton;

    private int currentPage = 0;

    private String user;
    private String pet;

    private String[] storyPages;
    
    public IntroPanel(String user, String pet) {
        this.user = user;
        this.pet = pet;
        
        setLayout(new BorderLayout());
        
        storyText = new JTextArea();
        imageLabel = new JLabel();
        nextButton = new JButton("Next");
        
        storyPages = new String[] {

            "=== INTRODUCTION ===",

            "I was about to relax at home when "
            + pet
            + " squeezed through the window.",

            "\"Where did you go, buddy?\" I pulled "
            + pet
            + " into my arms.",

            "\"You're always so adventurous.\"",

            "\"I passed by the Animal University of Technology today.\"",

            "\"There's a Java competition going on,\" "
            + pet
            + " announced.",

            "\"I have enrolled you, "
            + user
            + ".\"",

            "\"Why!? I don't know much Java,\" I protested.",

            "\"Because first place wins a lifetime supply of pet food.",

            "And I plan to FEAST!\" "
            + pet
            + " cackled.",

            "I paused.",

            "\"Well, it would spare my wallet...\""
        };

        showIntroduction();
    }
    
    
    
    //-------------------------------------Methods------------------

    private void typeWriter(JTextArea textArea, String text) {

        textArea.setText("");

        Timer timer = new Timer(30, null);

        final int[] index = {0};

        timer.addActionListener(e -> {

            if (index[0] < text.length()) {

                textArea.append(String.valueOf(text.charAt(index[0])));
                index[0]++;

            } else {

                timer.stop();
            }
        });

        timer.start();
    }
    
    private void showIntroduction() {

        removeAll();

        JLabel introLabel =
            new JLabel("=== INTRODUCTION ===");

        introLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        add(introLabel, BorderLayout.CENTER);

        revalidate();
        repaint();

        Timer timer = new Timer(3000, e -> {

            currentPage = 1;

            showStoryPage();

        });

        timer.setRepeats(false);

        timer.start();
    }
    
    private void showStoryPage() {

        removeAll();

        JPanel centerPanel = new JPanel(
            new BorderLayout()
        );

        storyText = new JTextArea();

        storyText.setEditable(false);

        imageLabel = new JLabel();

        imageLabel.setPreferredSize(
            new Dimension(300,300)
        );

        centerPanel.add(storyText,
                        BorderLayout.NORTH);

        centerPanel.add(imageLabel,
                        BorderLayout.CENTER);

        add(centerPanel,
            BorderLayout.CENTER);

        nextButton = new JButton("Next");

        JPanel bottomPanel = new JPanel(
            new FlowLayout(
                FlowLayout.RIGHT
            )
        );

        bottomPanel.add(nextButton);

        add(bottomPanel,
            BorderLayout.SOUTH);

        typeWriter(
            storyText,
            storyPages[currentPage]
        );

        nextButton.addActionListener(e -> {

            currentPage++;

            if (currentPage < storyPages.length) {

                showStoryPage();

            } else {

                finishStory();

            }

        });

        revalidate();
        repaint();
    }
    
    private void finishStory() {

        JOptionPane.showMessageDialog(
            this,
            "Story Complete!"
        );
    }
    
    
}