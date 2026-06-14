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

    public BackgroundPanel(String backImg) {
        java.net.URL url = getClass().getResource(backImg);

        System.out.println("Searching for: " + backImg);
        System.out.println("URL = " + url);

        if (url == null) {
            throw new IllegalArgumentException("Image not found: " + backImg);
        }

        ImageIcon icon = new ImageIcon(url);
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
