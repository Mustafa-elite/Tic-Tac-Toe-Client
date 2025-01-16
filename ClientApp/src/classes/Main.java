package classes;

public class Main {

    public static void main(String[] args) {
        // Initialize players
        Player player1 = new Player("Human");
        Player player2 = new Player("AI");

        // Create game instance
        AIGamePlay game = new AIGamePlay("Human", "AI");

        // Display initial board
        System.out.println("Initial Board:");
        printBoard(game.b1.boardxy);

        // Play the game until there's a winner or draw
        for (int turn = 1; turn <= 9; turn++) {
            if (game.turn) {
                System.out.println("Human's Turn (X): ");
                // Example move: Replace this with user input or logic
                int move = (int) (Math.random() * 9) + 1; // Random position
                GameStatus status = game.playXO(move);

                if (status != null) {
                    if (!status.getWinnerName().equals("Draw")) {
                        System.out.println("Winner: " + status.getWinnerName());
                        System.out.println("Winning Case: " + status.getWinCase());
                    } else {
                        System.out.println("It's a Draw!");
                    }
                    break;
                }
            } else {
                System.out.println("AI's Turn (O):");
                GameStatus status = game.playXO(-1); // AI determines its move

                if (status != null) {
                    if (!status.getWinnerName().equals("Draw")) {
                        System.out.println("Winner: " + status.getWinnerName());
                        System.out.println("Winning Case: " + status.getWinCase());
                    } else {
                        System.out.println("It's a Draw!");
                    }
                    break;
                }
            }

            printBoard(game.b1.boardxy);
        }
    }

    // Helper method to print the board
    private static void printBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = (board[i][j] == -1) ? '_' : (board[i][j] == 1 ? 'X' : 'O');
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
