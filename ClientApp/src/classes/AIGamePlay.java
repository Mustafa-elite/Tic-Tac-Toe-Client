package classes;

public class AIGamePlay extends GamePlay {

    String print;
    Boolean checkWin;

    public AIGamePlay(Player player, Player aiPlayer) {
        super(player, aiPlayer);
    }

    String playXO(int position) {
        if (turn) {
            print = "x";
            makeMove(position, 0);
        } else {
            int aiPosition = findBestMove();
            makeMove(aiPosition, 1);
            print = "o";
        }

        checkWin = checkWinner();
        if (checkWin) {
            print = "w";
        }

        return print;
    }

    private void makeMove(int position, int player) {
        if (position >= 0 && position <= 2) {
            b1.boardxy[0][position] = player;
        } else if (position >= 3 && position <= 5) {
            b1.boardxy[1][position] = player;
        } else if (position >= 6 && position <= 8) {
            b1.boardxy[2][position] = player;
        }
    }

    private int findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b1.boardxy[i][j] == -1) {
                    b1.boardxy[i][j] = 1;
                    int score = minimax(false);
                    b1.boardxy[i][j] = -1;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = i * 3 + j;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMaximizing) {
        if (checkWinner()) {
            return isMaximizing ? -1 : 1;
        }

        if (isBoardFull()) {
            return 0;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b1.boardxy[i][j] == -1) {
                    b1.boardxy[i][j] = isMaximizing ? 1 : 0;
                    int score = minimax(!isMaximizing);
                    b1.boardxy[i][j] = -1; 

                    if (isMaximizing) {
                        bestScore = Math.max(score, bestScore);
                    } else {
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
