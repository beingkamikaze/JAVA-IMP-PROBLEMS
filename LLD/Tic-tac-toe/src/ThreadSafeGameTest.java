/**
 * Test class demonstrating thread-safe tic-tac-toe game
 * Multiple threads can safely play the game concurrently
 */
public class ThreadSafeGameTest {
    public static void main(String[] args) throws InterruptedException {
        // Create players
        Player player1 = new Player("Alice", 'X');
        Player player2 = new Player("Bob", 'O');

        // Initialize thread-safe game
        GameThreadSafe game = new GameThreadSafe(3, player1, player2);

        System.out.println("=== THREAD-SAFE TIC-TAC-TOE GAME ===\n");

        // Example 1: Sequential plays (safe)
        System.out.println("--- Sequential Plays ---");
        game.play(0, 0);
        game.play(1, 1);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(2, 2);
        game.play(2, 0);

        System.out.println("\n=== GAME 2: Concurrent Thread Test ===\n");

        // Example 2: Create a new game for concurrent testing
        Player p1 = new Player("Player1", 'X');
        Player p2 = new Player("Player2", 'O');
        GameThreadSafe concurrentGame = new GameThreadSafe(5, p1, p2);

        // Create threads simulating concurrent players
        Thread thread1 = new Thread(() -> {
            concurrentGame.play(0, 0);
            concurrentGame.play(1, 1);
            concurrentGame.play(2, 2);
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            concurrentGame.play(0, 1);
            concurrentGame.play(1, 2);
            concurrentGame.play(2, 3);
        }, "Thread-2");

        // Start threads - they will safely coordinate moves
        thread1.start();
        thread2.start();

        // Wait for both threads to complete
        thread1.join();
        thread2.join();

        System.out.println("\nFinal Board State:");
        concurrentGame.printBoard();
        System.out.println("Game Status: " + concurrentGame.getGameStatus());
    }
}

