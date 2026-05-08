public class Player {
    String name;
    char Symbol;

    public Player(String name, char symbol) {
        this.name = name;
        Symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return Symbol;
    }

    public void setSymbol(char symbol) {
        Symbol = symbol;
    }
}
