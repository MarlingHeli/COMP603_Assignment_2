/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface.Console;

import User_Interface.UI;

/**
 *
 * @author hmarl
 */
// package ui;

public class Menu {
    private UI ui;

    public Menu(UI ui) {
        this.ui = ui;
    }

    /**
     * Displays menu and returns user choice
     */
    public int displayMenu() {
        ui.displayText("\n=== Feed Me Java! ===");
        ui.displayText("1. Start New Game");
        ui.displayText("2. Load Game");
        ui.displayText("3. Exit");

        try {
            return Integer.parseInt(ui.getUserInput("Choose option (1/2/3): "));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}