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

public interface UserRecord {
    void saveRecord(User user);
    User loadRecord(String username);

    void saveGame(QuizSession session);
    QuizSession loadGame(String username);
}
