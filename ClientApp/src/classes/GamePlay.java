/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.util.Random;
/**
 *
 * @author user
 */
public abstract class GamePlay {
    Board b1=new Board();
    Player player1;
    Player player2;
    boolean turn;
    public static final int WIN_ROW_1=1;
    public static final int WIN_ROW_2=2;
    public static final int WIN_ROW_3=3;
    public static final int WIN_COL_1=4;
    public static final int WIN_COL_2=5;
    public static final int WIN_COL_3=6;
    public static final int WIN_DIAGONAL_1=7;
    public static final int WIN_DIAGONAL_2=8;
    
    class Board
    {
        int[][] boardxy = {{-1, -1, -1},
                           {-1, -1, -1},
                           {-1, -1, -1}
                          };
    }
    
    GamePlay(Player p1,Player p2)
    {
        player1=p1;
        player2=p2;
        Random random = new Random();
        turn = random.nextBoolean();
    }
    
    public abstract GameStatus playXO(int position);

    int checkWinner()
    {
        int playerToCheck=turn ? 1:0;
        int winCase = 0;
        for(int i=0;i<3;i++){
            
            if(b1.boardxy[i][0]==playerToCheck && b1.boardxy[i][1]==playerToCheck && b1.boardxy[i][2]==playerToCheck){
              
                //row num
                winCase = i+1;
                break;
                
            }
            if(b1.boardxy[0][i]==playerToCheck && b1.boardxy[1][i]==playerToCheck && b1.boardxy[2][i]==playerToCheck){
               
                //col num
                winCase = i+4;
                break;
            }
            
        }
        if(b1.boardxy[0][2]==playerToCheck && b1.boardxy[1][1]==playerToCheck && b1.boardxy[2][0]==playerToCheck){
            
            //diagonal 1
            winCase = 7;
        }
        if(b1.boardxy[0][0]==playerToCheck && b1.boardxy[1][1]==playerToCheck && b1.boardxy[2][2]==playerToCheck){
            //diagonal 2
            winCase = 8;
        }
        //did not find a win situation;
        return winCase;
    }
    
}
