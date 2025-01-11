package classes;

public class LocalGamePlay extends GamePlay {

    String print ;
    Boolean checkWin  ; 
   
    public LocalGamePlay(String player1Name,String player2Name)
    {
        super(new Player(player1Name),new Player(player2Name));
    }
    public String playXO(int position) {
        //player 1 turn 
        if (!turn) {
            print = "X";
            if (position == 0 || position == 1 || position == 2) {
                b1.boardxy[0][position] = 0;
            }
            if (position == 3 || position == 4 || position == 5) {
                b1.boardxy[1][position-3] = 0;
            }
            if (position == 6 || position == 7 || position == 8) {
                b1.boardxy[2][position-6] = 0;
            }
        } //player 2 turn 
        else {
                print = "O";
                if (position == 0 || position == 1 || position == 2) {
                    b1.boardxy[0][position] = 1;
                }
                if (position == 3 || position == 4 || position == 5) {
                    b1.boardxy[1][position-3] = 1;
                }
                if (position == 6 || position == 7 || position == 8) {
                    b1.boardxy[2][position-6] = 1;
                }
        }
        checkWin = checkWinner() ; 
        if(checkWin) print = "w" ; 
        turn=!turn;
        return print;
    }

}
