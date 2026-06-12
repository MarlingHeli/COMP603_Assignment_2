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

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    /*
    public BackgroundPanel() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/FeedMeJavaBg.png"));
        System.out.println(icon.getIconWidth());
        System.out.println(icon.getIconHeight());

        backgroundImage = icon.getImage();
        
    }*/
    
    public BackgroundPanel(String backImg) {

    java.net.URL url =
        getClass().getResource(backImg);

//    System.out.println(url);

    ImageIcon icon = new ImageIcon(url);

//    System.out.println(
//        "Width = " + icon.getIconWidth()
//    );
//
//    System.out.println(
//        "Height = " + icon.getIconHeight()
//    );

    backgroundImage = icon.getImage();
}

    @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);

//       System.out.println("Painting background");

       g.drawImage(backgroundImage, 0, 0,
                   getWidth(), getHeight(), this);
   }
}
