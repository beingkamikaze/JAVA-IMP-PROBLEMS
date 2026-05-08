import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe Game implementation using ReentrantLock
 * Ensures atomic operations and proper state management
 */
public class GameThreadSafe {
    private BoardThreadSafe_Lock board;
    private Player[] players;
    private int currentPlayerIndex;
    private int moves;
    private int totalMoves;
    private GameStatus gameStatus;
    
    // Lock to ensure atomic operations during play()
    private final ReentrantLock gameLock = new ReentrantLock();

    public GameThreadSafe(int n, Player p1, Player p2) {
        this.board = new BoardThreadSafe_Lock(n);
        this.players = new Player[]{p1, p2};
        this.currentPlayerIndex = 0;
        this.moves = 0;
        this.totalMoves = n * n;
        this.gameStatus = GameStatus.ONGOING;
    }

    /**
     * Thread-safe play method
     * Entire move sequence is atomic to prevent race conditions
     */
    public void play(int row, int col) {
        gameLock.lock();
        try {
            if (gameStatus != GameStatus.ONGOING) {
                System.out.println("Game Already finished");
                return;
            }

            Player player = players[currentPlayerIndex];

            if (!board.isValidMove(row, col)) {
                System.out.println("Invalid Move");
                return;
            }

            board.makemove(row, col, player);
            moves++;

            if (board.checkWinner(row, col, player)) {
                gameStatus = GameStatus.WIN;
                board.printBoard();
                System.out.println(Thread.currentThread().getName() + " | " +
                        player.name + " WINS THE MATCH with sym : " + player.Symbol);
                return;
            }

            if (moves == totalMoves) {
                gameStatus = GameStatus.DRAW;
                board.printBoard();
                System.out.println("GAME DRAWS");
                return;
            }

            // Switch player
            currentPlayerIndex = 1 - currentPlayerIndex;

        } finally {
            gameLock.unlock();
        }
    }

    public synchronized GameStatus getGameStatus() {
        return gameStatus;
    }

    public synchronized int getCurrentPlayer() {
        return currentPlayerIndex;
    }

    public synchronized void printBoard() {
        board.printBoard();
    }

    public synchronized int getMoveCount() {
        return moves;
    }
}

