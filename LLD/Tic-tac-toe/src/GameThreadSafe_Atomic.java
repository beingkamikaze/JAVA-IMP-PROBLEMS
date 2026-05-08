import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Thread-safe Game implementation using Atomic classes
 * Good for simple flag/counter types with lock-free algorithm
 */
public class GameThreadSafe_Atomic {
    private BoardThreadSafe_Lock board;
    private Player[] players;
    
    // Atomic fields for thread-safe access without locks
    private AtomicInteger currentPlayerIndex = new AtomicInteger(0);
    private AtomicInteger moves = new AtomicInteger(0);
    private AtomicReference<GameStatus> gameStatus = new AtomicReference<>(GameStatus.ONGOING);
    
    private int totalMoves;

    public GameThreadSafe_Atomic(int n, Player p1, Player p2) {
        this.board = new BoardThreadSafe_Lock(n);
        this.players = new Player[]{p1, p2};
        this.totalMoves = n * n;
    }

    /**
     * Thread-safe play using Atomic operations
     * Note: Board operations still use internal locks
     */
    public void play(int row, int col) {
        if (gameStatus.get() != GameStatus.ONGOING) {
            System.out.println("Game Already finished");
            return;
        }

        Player player = players[currentPlayerIndex.get()];

        if (!board.isValidMove(row, col)) {
            System.out.println("Invalid Move");
            return;
        }

        board.makemove(row, col, player);
        moves.incrementAndGet();

        if (board.checkWinner(row, col, player)) {
            gameStatus.set(GameStatus.WIN);
            board.printBoard();
            System.out.println(player.name + " WINS THE MATCH with sym : " + player.Symbol);
            return;
        }

        if (moves.get() == totalMoves) {
            gameStatus.set(GameStatus.DRAW);
            board.printBoard();
            System.out.println("GAME DRAWS");
            return;
        }

        // Switch player atomically
        currentPlayerIndex.updateAndGet(i -> 1 - i);
    }

    public GameStatus getGameStatus() {
        return gameStatus.get();
    }

    public int getCurrentPlayer() {
        return currentPlayerIndex.get();
    }

    public void printBoard() {
        board.printBoard();
    }

    public int getMoveCount() {
        return moves.get();
    }
}

/**
 * Advantages of Atomic Approach:
 * 
 * 1. LOCK-FREE:
 *    - Uses Compare-And-Swap (CAS) internally
 *    - No blocking, threads don't wait
 *    - Better for low-contention scenarios
 * 
 * 2. PERFORMANCE:
 *    - Faster than ReentrantLock in many cases
 *    - Better CPU cache locality
 *    - Less context switching
 * 
 * 3. SIMPLICITY:
 *    - Clean API - no try/finally needed
 *    - No deadlock risk for these operations
 *    - Self-documenting (atomic operations evident)
 * 
 * Disadvantages:
 * 
 * 1. LIMITED FUNCTIONALITY:
 *    - Only works with primitive/reference types
 *    - Can't protect arrays or complex objects
 *    - Each operation is atomic, but compound operations aren't
 * 
 * 2. COMPLEX OPERATIONS:
 *    - Game.play() is a compound operation
 *    - Checking and updating gameStatus separately
 *    - Still need external synchronization for atomicity
 * 
 * CONCLUSION FOR TIC-TAC-TOE:
 * - ReentrantLock is better choice
 * - Need to ensure entire play() is atomic
 * - Atomic is good for simple flags, not for complex logic
 */

