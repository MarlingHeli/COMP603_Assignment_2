package User_Interface.Console;

import User_Interface.UI;

public class StoryPrinter {

    public static void showIntro(UI ui, String user, String pet) {
        ui.displayText("\n=== INTRODUCTION ===");
        
        ui.slowPrint("I was about to relax at home when " + pet + " squeezed through the window.");
        ui.slowPrint("\"Where did you go, buddy?\" I pulled " + pet + " into my arms.");
        ui.slowPrint("\"You're always so adventurous.\"");
        ui.slowPrint("\"I passed by the Animal University of Technology today.");
        ui.slowPrint("There's a Java competition going on,\" " + pet + " announced.");
        ui.slowPrint("\"I have enrolled you, " + user + ".\"");
        ui.slowPrint("\"Why!? I don't know much Java,\" " + "I protested.");
        ui.slowPrint("\"Because first place wins a lifetime supply of pet food.");
        ui.slowPrint("And I plan to FEAST!\" " + pet + " cackled.");
        ui.slowPrint("I paused.");
        ui.slowPrint("\"Well, it would spare my wallet...\"");
        ui.slowPrint("""
                         
                            ^---^
                          =( owo )=
                           /     \\  ^
                          (  U U  )//
                      """);
        ui.displayText("=================");
    }
}
