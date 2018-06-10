package tictactoe;

import java.util.Random;

public class NaiveAIPlayer extends Player {

    private final Random rnd = new Random();

    public NaiveAIPlayer(String name, char mark) {
        super(name, mark);
    }

    @Override
    public Move nextMove(Board board) {
        while (true){
            int row = rnd.nextInt(board.getSize());
            int col = rnd.nextInt(board.getSize());
            if(!board.isMarked(row, col))
                return new Move(row, col);
        }
    }
}
