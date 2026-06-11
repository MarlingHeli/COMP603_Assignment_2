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
    private Runnable onFinish;

    private JTextArea storyText;
    private JLabel imageLabel;
    private JButton nextButton;

    private int currentPage = 0;

    private String user;
    private String pet;

    private String[] storyPages;
    
    
    
    public IntroPanel(String user, String pet, Runnable onFinish) {
        System.out.println("IntroPanel created");
        this.user = user;
        this.pet = pet;
        this.onFinish = onFinish;
        
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
            + " into my arms."+
            "\n\"You're always so adventurous.\"",

            "\"I passed by the Animal University of Technology today.\""+
            "\n\"There's a Java competition going on,\" "
            + pet
            + " announced."+
            "\"I have enrolled you, "
            + user
            + ".\"",

            "\"Why!? I don't know much Java!\" I protested.",

            "\"Because first place wins a lifetime supply of pet food."+
            "\nAnd I plan to FEAST!\" "
            + pet
            + " cackled.",

            "I paused."+
            "\n\"Well, it would spare my wallet...\""
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
        System.out.println("Showing introduction");

        removeAll();

        JLabel introLabel = new JLabel("=== INTRODUCTION ===");
        introLabel.setFont( new Font("Serif", Font.BOLD, 42));

        
        introLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        add(introLabel, BorderLayout.CENTER);

        revalidate();
        repaint();

        Timer timer = new Timer(1500, e -> {

            currentPage = 1;

            showStoryPage();

        });

        timer.setRepeats(false);

        timer.start();
    }
    
    private void showStoryPage() {
        System.out.println("Showing page " + currentPage);

        removeAll();

        JPanel centerPanel = new JPanel(
            new BorderLayout()
        );
        
        storyText = new JTextArea();
        storyText.setPreferredSize(new Dimension(800, 220));
        storyText.setFont(new Font("Serif", Font.PLAIN, 28));
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setOpaque(false);
        storyText.setEditable(false);
        storyText.setEditable(false);

        imageLabel = new JLabel();

        imageLabel.setPreferredSize(
            new Dimension(300,300)
        );

//      centerPanel.add(storyText, BorderLayout.NORTH);
        JPanel textWrapper = new JPanel(new BorderLayout());

        // Transparent so background can show through
        textWrapper.setOpaque(false);
        // 40px padding on top
        textWrapper.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        textWrapper.add(storyText, BorderLayout.CENTER);
        centerPanel.add(textWrapper, BorderLayout.NORTH);
        
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

        if (onFinish != null) {
            onFinish.run();
        }
    }
    
    
}