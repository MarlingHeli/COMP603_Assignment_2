package User_Interface.Console;

import User_Interface.UI;

public class InputHelper {

    //error check for prompts that ask for "y" or "n"
    public static String getYesNo(UI ui, String msg) {
        while(true) {
            String input = ui.getUserInput(msg);

            if(input.equalsIgnoreCase("y") ||
               input.equalsIgnoreCase("n")) {
                return input;
            }

            ui.displayError("Please enter y or n.");
        }
    }

    //check user input for answering a quiz question
    public static String getQuizAnswer(UI ui) {
        while(true) {
            String input = ui.getUserInput("Answer (1-3 or quit): ");

            if(input.equalsIgnoreCase("quit"))
                return input;

            try {
                int num = Integer.parseInt(input);

                if(num >=1 && num <=3)
                    return input;

            } catch(Exception e){}

            ui.displayError("Please enter 1,2,3 or quit.");
        }
    }
}