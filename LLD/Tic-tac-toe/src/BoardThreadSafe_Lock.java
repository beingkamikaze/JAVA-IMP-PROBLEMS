import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe Board implementation using ReentrantLock
 * Provides more control than synchronized methods
 */
public class BoardThreadSafe_Lock {
    private int n;
    private char grid[][];
    private final ReentrantLock lock = new ReentrantLock();

    public BoardThreadSafe_Lock(int n) {
        this.n = n;
        grid = new char[n][n];
    }

    public boolean isValidMove(int row, int col) {
        lock.lock();
        try {
            return row >= 0 && row < n && col >= 0 && col < n && grid[row][col] == '\0';
        } finally {
            lock.unlock();
        }
    }

    public void makemove(int row, int col, Player player) {
        lock.lock();
        try {
            grid[row][col] = player.Symbol;
        } finally {
            lock.unlock();
        }
    }

    public boolean checkWinner(int row, int col, Player player) {
        lock.lock();
        try {
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
        } finally {
            lock.unlock();
        }
    }

    public void printBoard() {
        lock.lock();
        try {
            for (char[] row : grid) {
                for (char c : row) {
                    System.out.print((c == '\0' ? "-" : c) + " ");
                }
                System.out.println();
            }
        } finally {
            lock.unlock();
        }
    }
}

