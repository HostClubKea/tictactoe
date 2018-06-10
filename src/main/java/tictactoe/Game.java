package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private final int boardSize;
    private final List<Player> players;
    private int turn;

    //Check board size,
    public Game(int boardSize, List<Player> players){
        if(boardSize < 3 || boardSize > 10)
            throw new IllegalArgumentException("Board size expected to be between 3x3 and 10x10");

        if(players == null || players.size() != 3)
            throw new IllegalArgumentException("Game expected to be played by 3 players");

        this.boardSize = boardSize;
        this.players = new ArrayList<>(players);
    }

    private Player getNextPlayer(){
        if(turn < 0) {
            Random rnd = new Random();
            turn = rnd.nextInt(players.size());
        }
        return players.get(turn++ % players.size());
    }

    private boolean isGameEnd(Board board){
        return board.hasWinner() || board.isDraw();
    }

    public void play(){
        Board board = new Board(boardSize);
        int turn = -1;

        do {
            Player player = getNextPlayer();

            while(true) {
                System.out.println(String.format("%s turn:", player.getName()));
                //Player receives clone of board so he can't cheat and make several moves
                Move mp = player.nextMove(board.clone());

                try {
                    board.setMark(mp.getRow(), mp.getCol(), player);
                    break;
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("You have entered wrong data, please repeat your move.");
                }
            };

            printBoard(board);
        } while (!isGameEnd(board));

        if(board.hasWinner()){
            Player winner = board.findWinner();
            System.out.println("The End. Winner: " + winner.getName());
        } else if(board.isDraw()){
            System.out.println("The End. Draw");
        }

    }

    private void printBoard(Board board){
        System.out.println("Board:");
        System.out.println(board);
    }
}
