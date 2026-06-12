/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hmarl
 */

public class User {
    private String username;
    private String petName;
    private int highScore;

    public User(String username, String petName, int highScore) {
        this.username = username;
        this.petName = petName;
        //TO DO: remove or use
        this.highScore = highScore;
    }

    public String getUsername() 
    { 
        return username;
    }
    public String getPetName()
    { 
        return petName;
    }
    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
    
    //keep highest ever score only
    public void saveHighestScore(int numCorrectAnswers)
    {
        if (numCorrectAnswers > highScore)
        {
            highScore = numCorrectAnswers;
        }
    }
    
    //added for when users have existing file but start new game with different pet name
    public void setPetName(String name)
    {
        this.petName = name;
    }
    
}