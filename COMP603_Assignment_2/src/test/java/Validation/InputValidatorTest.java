/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

/**
 *
 * @author 2
 */

import Validation.QuizValidator;
import org.junit.Test;
import static org.junit.Assert.*;

public class InputValidatorTest {

    @Test
    public void emptyUsernameShouldFail() {
        assertFalse(
            InputValidator.validStr("")
        );
    }

    @Test
    public void nullUsernameShouldFail() {
        assertFalse(
            InputValidator.validStr(null)
        );
    }

    @Test
    public void normalUsernameShouldPass() {
        assertTrue(
            InputValidator.validStr("Chris")
        );
    }

    @Test
    public void nullAnswerShouldFail() {
        assertFalse(
            QuizValidator.canSubmitAnswer(null)
        );
    }
    
    @Test
    public void randomCharactersUsernameShouldFail() {
        assertFalse(
            InputValidator.validStr("@#$%^&*")
        );
    }

    @Test
    public void randomCharactersPetNameShouldFail() {
        assertFalse(
            InputValidator.validStr("!@Fluf$$")
        );
    }

    @Test
    public void numbersInUsernameShouldFail() {
        assertFalse(
            InputValidator.validStr("Chris123")
        );
    }

    @Test
    public void normalPetNameShouldPass() {
        assertTrue(
            InputValidator.validStr("Fluffy")
        );
    }

    @Test
    public void spacesInNameShouldPass() {
        assertTrue(
            InputValidator.validStr("John Smith")
        );
    }
    
    
}