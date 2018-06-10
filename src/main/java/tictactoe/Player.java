package tictactoe;

public abstract class Player {

    private final String name;
    private final char mark;

    public Player(String name, char mark){
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public char getMark() {
        return mark;
    }

    abstract public Move nextMove(Board board);
}
