/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

/**
 *
 * @author hmarl
 */

import Model.User;
import Model.QuizSession;

public interface QuizRecord {
    void saveGame(QuizSession session);
    QuizSession loadGame(User user);
}
