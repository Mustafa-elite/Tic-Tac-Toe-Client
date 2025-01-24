package classes;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import clientapp.controllers.BoardController;

public class OnlineGamePlay extends GamePlay {

    int winCase;
 
    public OnlineGamePlay(String player1Name, String player2Name) {
        super(new Player(player1Name), new Player(player2Name));
    }

    @Override
    public GameStatus playXO(int position) {
        GameStatus gameStatus = new GameStatus(-1, null, null, 0);
        gameStatus.setPosition(position);
        if (isTurn()) {

            gameStatus.setPlayedChar("X");
            if (position == 0 || position == 1 || position == 2) {
                b1.boardxy[0][position] = 1;
            }
            if (position == 3 || position == 4 || position == 5) {
                b1.boardxy[1][position - 3] = 1;
            }
            if (position == 6 || position == 7 || position == 8) {
                b1.boardxy[2][position - 6] = 1;
            }

        } else {
           

            gameStatus.setPlayedChar("O");
            if (position == 0 || position == 1 || position == 2) {
                b1.boardxy[0][position] = 0;
            }
            if (position == 3 || position == 4 || position == 5) {
                b1.boardxy[1][position - 3] = 0;
            }
            if (position == 6 || position == 7 || position == 8) {
                b1.boardxy[2][position - 6] = 0;
            }
        }

        winCase = checkWinner();
        if (winCase > 0) {
            if (turn) {
                gameStatus.setWinnerName(player1.name);
            } else {
                gameStatus.setWinnerName(player2.name);
            }
            gameStatus.setWinCase(winCase);
        } else if (winCase == -1) {
            //draw case
            gameStatus.setDraw(true);

        }
      

        return gameStatus;
    }

}
