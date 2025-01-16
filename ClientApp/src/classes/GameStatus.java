/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author user
 */
public class GameStatus {
    private String playedChar;
    private String WinnerName;
    private int WinCase;

    public GameStatus(String playedChar, String WinnerName, int WinCase) {
        this.playedChar = playedChar;
        this.WinnerName = WinnerName;
        this.WinCase = WinCase;
    }

    public String getPlayedChar() {
        return playedChar;
    }

    public void setPlayedChar(String playedChar) {
        this.playedChar = playedChar;
    }

    public String getWinnerName() {
        return WinnerName;
    }

    public void setWinnerName(String WinnerName) {
        this.WinnerName = WinnerName;
    }

    public int getWinCase() {
        return WinCase;
    }

    public void setWinCase(int WinCase) {
        this.WinCase = WinCase;
    }
    
}
