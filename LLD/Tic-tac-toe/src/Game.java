public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    int moves;
    int totalMoves;
    GameStatus gameStatus;

    public Game(int n,Player p1,Player p2) {
        this.board = new Board(n);
        this.players = new Player[]{p1,p2};
        this.currentPlayerIndex = 0;
        this.moves = 0;
        this.totalMoves = n*n;
        this.gameStatus = GameStatus.ONGOING;
    }

    public void play(int row,int col)
    {
        if(gameStatus!=GameStatus.ONGOING)
        {
            System.out.println("Game Alraedy finished");
            return;
        }

        Player player = players[currentPlayerIndex];

        if(!board.isValidMove(row,col))
        {
            System.out.println("Invalid Move");
            return;
        }
        board.makemove(row,col,player);
        moves++;

        if(board.checkWinner(row,col,player))
        {
            gameStatus = GameStatus.WIN;
            board.printBoard();
            System.out.println(player.name + " WINS THE MATCH with sym : " + player.Symbol);
            return;
        }
        if(moves==totalMoves)
        {
            gameStatus=GameStatus.DRAW;
            board.printBoard();
            System.out.println("GAME DARWS");
            return;
        }

        currentPlayerIndex = 1-currentPlayerIndex;

    }
}
