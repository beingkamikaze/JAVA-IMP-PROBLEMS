import java.util.List;

public class Board {
    private int n;
    private char grid[][];

    public Board(int n) {
        this.n = n;
        grid = new char[n][n];
    }

    public boolean isValidMove(int row,int col)
    {
        return row>=0 && row<n && col>=0 && col<n && grid[row][col]=='\0';
    }

    public void makemove(int row,int col,Player player)
    {
        grid[row][col] = player.Symbol;
    }

    public boolean checkWinner(int row,int col, Player player)
    {
        char sym = player.Symbol;

        //check row
        boolean win = true;
        for(int j=0;j<n;j++){
            if(grid[row][j]!=sym)
            {
                win = false;
                break;
            }
        }
        if(win) return true;

        //check col
        win =true;
        for(int i=0;i<n;i++)
        {
            if(grid[i][col]!=sym)
            {
                win = false;
                break;
            }
        }
        if(win) return true;

        //check diagonal
        if(row==col)
        {
            win=true;
            for(int i=0;i<n;i++){
                if(grid[i][i]!=sym)
                {
                    win=false;
                    break;
                }
            }
        }
        if(win) return true;

        //check anti diagonal [logic to remeber]
        if(row+col==n-1)
        {
            win=true;
            for(int i=0;i<n;i++)
            {
                if(grid[i][n-1-i]!=sym)
                {
                    win=false;
                    break;
                }
            }
        }
        if(win) return true;
        return false;
    }
    public void printBoard() {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print((c == '\0' ? "-" : c) + " ");
            }
            System.out.println();
        }
    }

}
