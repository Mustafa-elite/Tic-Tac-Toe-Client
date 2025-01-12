package classes;

public class LocalGamePlay extends GamePlay {

    String print ;
    int winCase  ; 
   
    public LocalGamePlay(String player1Name,String player2Name)
    {
        super(new Player(player1Name),new Player(player2Name));
    }
    @Override
    public GameStatus playXO(int position) {
        //player 1 turn 
        GameStatus gs=new GameStatus(null,null,0);
        
        if (!turn) {
            gs.setPlayedChar("X");
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
            gs.setPlayedChar("O");
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
        winCase = checkWinner(); 
        if(winCase!=0){
            if(!turn)
            {
                gs.setWinnerName(player1.name); 
            }
            else
            {
                gs.setWinnerName(player2.name);
            }
            gs.setWinCase(winCase);  
        }
        turn=!turn;
        return gs;
    }

}
