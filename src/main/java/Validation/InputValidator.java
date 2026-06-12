/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

/**
 *
 * @author 2
 */

public class InputValidator {

//    public static boolean validUsername(
//        String username
//    ) {
//        return username != null
//            && !username.trim().isEmpty();
//    }
    
     public static boolean validStr(
        String str
    ) {
        return str != null
            && !str.trim().isEmpty()
            && str.matches("[a-zA-Z ]+");
    }

}
