/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface.Console;

/**
 *
 * @author hmarl
 */

import User_Interface.UI;
import java.util.Scanner;

public class CUI implements UI {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void displayText(String text) {
        System.out.println(text);
    }

    @Override
    public void displayError(String text) {
        System.err.println(text);
    }

    @Override
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    @Override
    public void slowPrint(String text) {
        displayText(text);

            try {
                Thread.sleep(3000); // 3000 ms = 3 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }
}