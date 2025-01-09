package classes;

public class AIGamePlay extends GamePlay {

    String print;
    Boolean checkWin;

    // AI Turn
    boolean isAITurn = false;

    public AIGamePlay(Player p1, Player p2) {
        super(p1, p2);
    }

    String playXO(int position) {
        if (!turn) {
            print = "x";
            makeMove(position, 0);
        } else {
            if (isAITurn) {
                // AI's move
                int aiPosition = findBestMove();
                makeMove(aiPosition, 1);
                print = "o";
            } else {
                // Player 2's move (if not using AI)
                print = "o";
                makeMove(position, 1);
            }
        }

        checkWin = checkWinner();
        if (checkWin) {
            print = "w";
        }
        return print;
    }

    private void makeMove(int position, int player) {
        if (position == 0 || position == 1 || position == 2) {
            b1.boardxy[0][position] = player;
        } else if (position == 3 || position == 4 || position == 5) {
            b1.boardxy[1][position] = player;
        } else if (position == 6 || position == 7 || position == 8) {
            b1.boardxy[2][position] = player;
        }
    }

    private int findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b1.boardxy[i][j] == -1) { // Assuming -1 represents an empty cell
                    b1.boardxy[i][j] = 1; // AI makes a tentative move
                    int score = minimax(false);
                    b1.boardxy[i][j] = -1; // Undo the move
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i * 3 + j; // Convert 2D index to 1D position
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMaximizing) {
        if (checkWinner()) {
            
            return isMaximizing ? -1 : 1; // AI wins = 1, Player wins = -1
        }

        if (isBoardFull()) {
            return 0; // Tie
        }
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b1.boardxy[i][j] == -1) { // Empty cell
                    b1.boardxy[i][j] = isMaximizing ? 1 : 0; // Tentative move
                    int score = minimax(!isMaximizing);
                    b1.boardxy[i][j] = -1; // Undo the move
                    if (isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b1.boardxy[i][j] == -1) {
                    return false;
                }
            }
        }
        return true;
    }
}
