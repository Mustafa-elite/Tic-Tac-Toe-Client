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
public abstract class GamePlay {
    Board b1;
    Player player1;
    Player player2;
    boolean turn;
    class Board
    {
        int[][] boardxy=new int[3][3];
    }
    
    boolean checkWinner()
    {
        int playerToCheck=turn ? 1:0;
        for(int i=0;i<3;i++)
        {
            
            if(b1.boardxy[i][0]==playerToCheck && b1.boardxy[i][1]==playerToCheck && b1.boardxy[i][2]==playerToCheck)
            {
                return true;
            }
            if(b1.boardxy[0][i]==playerToCheck && b1.boardxy[1][i]==playerToCheck && b1.boardxy[2][i]==playerToCheck)
            {
                return true;
            }
        }
        if(b1.boardxy[0][2]==playerToCheck && b1.boardxy[1][1]==playerToCheck && b1.boardxy[2][0]==playerToCheck)
        {
            return true;
        }
        if(b1.boardxy[0][0]==playerToCheck && b1.boardxy[1][1]==playerToCheck && b1.boardxy[2][2]==playerToCheck)
        {
            return true;
        }
        
        //did not find a win situation;
        return false;
    }
    
}
