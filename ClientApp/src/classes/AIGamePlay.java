package classes;

public class AIGamePlay extends GamePlay {

    String print;
    public static int winCase;

    public AIGamePlay(String playerName, String ai) {
        super(new Player(playerName), new Player(ai));
        // mode ="AI";
    }

@Override
    public GameStatus playXO(int position) {
        GameStatus gs = new GameStatus(-1, null, null, 0);

        if (turn) {
            gs.setPlayedChar("O");
            updateBoard(position, 1);
            printBoard();
            if (GamePlay.record == true) {
                recorder.recordPlay(position);
            }
        } else {
            System.out.println("Hi");
            gs.setPlayedChar("X");
            Move bestMove = findBestMove();
            gs.setPosition(bestMove.row * 3 + bestMove.col);
            System.out.println("my" + gs.getPosition());
            updateBoard(bestMove.row * 3 + bestMove.col, 0);
            printBoard();
            if (GamePlay.record == true) {
                recorder.recordPlay(gs.getPosition());
            }
        }
        winCase = checkWinner();
        if (winCase > 0) {
            gs.setWinnerName(turn ? player1.name : player2.name);
            System.out.println(gs.getWinnerName());
            gs.setWinCase(winCase);
            return gs; // Stop further actions
        } else if (winCase == -1) {
            // Draw case
            gs.setDraw(true);
            return gs; // Stop further actions
        }
        return gs;
    }



    private void updateBoard(int position, int playerMark) {
        if (position >= 0 && position < 3) {
            b1.boardxy[0][position] = playerMark;
        } else if (position >= 3 && position < 6) {
            b1.boardxy[1][position - 3] = playerMark;
        } else if (position >= 6 && position < 9) {
            b1.boardxy[2][position - 6] = playerMark;
        }
    }

    private Move findBestMove() {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b1.boardxy[i][j] == -1) {
                    b1.boardxy[i][j] = 0;
                    int moveVal = minimax(b1.boardxy, 0, false); // Start minimax with depth 0 and minimizing opponent
                    b1.boardxy[i][j] = -1;

                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        if (bestMove.row == -1 && bestMove.col == -1) {
            System.out.println("No valid moves left!");
        }

        return bestMove;
    }

    // Minimax implementation
    private int minimax(int[][] board, int depth, boolean isMax) {
        int score = evaluateBoard(board);

        if (score == 10 || score == -10) {
            return score - depth; // Reward shorter wins and penalize delayed wins
        }
        if (!isMovesLeft(board)) {
            return 0; // Draw
        }

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == -1) {
                        board[i][j] = 0;
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[i][j] = -1;
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == -1) {
                        board[i][j] = 1;
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[i][j] = -1;
                    }
                }
            }
            return best;
        }
    }

    private int evaluateBoard(int[][] board) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] != -1 && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 0) {
                    return 10;
                }
                if (board[row][0] == 1) {
                    return -10;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] != -1 && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 0) {
                    return 10;
                }
                if (board[0][col] == 1) {
                    return -10;
                }
            }
        }

        if (board[0][0] != -1 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 0) {
                return 10;
            }
            if (board[0][0] == 1) {
                return -10;
            }
        }

        if (board[0][2] != -1 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 0) {
                return 10;
            }
            if (board[0][2] == 1) {
                return -10;
            }
        }

        return 0;
    }

    private boolean isMovesLeft(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == -1) {
                    return true;
                }
            }
        }
        return false;
    }

    private void printBoard() {
        System.out.println("Current Board State:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char cell;
                if (b1.boardxy[i][j] == -1) {
                    cell = '.';
                } else if (b1.boardxy[i][j] == 0) {
                    cell = 'X';
                } else {
                    cell = 'O';
                }
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static class Move {
        int row, col;
    }
}
