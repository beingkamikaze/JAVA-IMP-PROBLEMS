/**
 * Thread-safe Board implementation using synchronized methods
 */
public class BoardThreadSafe_Synchronized {
    private int n;
    private char grid[][];
    private final Object lock = new Object();

    public BoardThreadSafe_Synchronized(int n) {
        this.n = n;
        grid = new char[n][n];
    }

    public synchronized boolean isValidMove(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && grid[row][col] == '\0';
    }

    public synchronized void makemove(int row, int col, Player player) {
        grid[row][col] = player.Symbol;
    }

    public synchronized boolean checkWinner(int row, int col, Player player) {
        char sym = player.Symbol;

        // check row
        boolean win = true;
        for (int j = 0; j < n; j++) {
            if (grid[row][j] != sym) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // check col
        win = true;
        for (int i = 0; i < n; i++) {
            if (grid[i][col] != sym) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // check diagonal
        if (row == col) {
            win = true;
            for (int i = 0; i < n; i++) {
                if (grid[i][i] != sym) {
                    win = false;
                    break;
                }
            }
        }
        if (win) return true;

        // check anti diagonal
        if (row + col == n - 1) {
            win = true;
            for (int i = 0; i < n; i++) {
                if (grid[i][n - 1 - i] != sym) {
                    win = false;
                    break;
                }
            }
        }
        if (win) return true;
        return false;
    }

    public synchronized void printBoard() {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print((c == '\0' ? "-" : c) + " ");
            }
            System.out.println();
        }
    }
}

