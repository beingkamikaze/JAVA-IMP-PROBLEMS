public class Main {
    public static void main(String[] args) {
        // Create two players
        Player player1 = new Player("Alice", 'X');
        Player player2 = new Player("Bob", 'O');

        // Initialize the game with board size (3x3)
        Game game = new Game(3, player1, player2);

        System.out.println("=== TIC-TAC-TOE GAME STARTED ===");
        System.out.println(player1.getName() + " (" + player1.getSymbol() + ") vs " + 
                          player2.getName() + " (" + player2.getSymbol() + ")\n");

        // Play moves - Alice (X) vs Bob (O)
        game.play(0, 0); // Alice plays at (0,0)
        game.play(1, 1); // Bob plays at (1,1)
        game.play(0, 1); // Alice plays at (0,1)
        game.play(0, 2); // Bob plays at (0,2)
        game.play(1, 0); // Alice plays at (1,0)
        game.play(2, 2); // Bob plays at (2,2)
        game.play(2, 0); // Alice plays at (2,0) - ALICE WINS!

        System.out.println("\n=== GAME FINISHED ===");
    }
}