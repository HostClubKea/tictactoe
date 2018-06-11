package tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    private final int boardSize;
    private final List<Player> players;
    private int turn;

    //Check board size,
    public Game(int boardSize, List<Player> players) {
        if (players == null || players.size() <= 0)
            throw new IllegalArgumentException("Game have to be played by someone");

        this.boardSize = boardSize;
        // We don't want sudden change in players list
        this.players = new ArrayList<>(players);
    }

    private Player getNextPlayer() {
        // on first turn we select random player
        if (turn < 0) {
            Random rnd = new Random();
            turn = rnd.nextInt(players.size());
        }
        // switching between players in the list
        return players.get(turn++ % players.size());
    }

    private boolean isGameEnd(Board board) {
        return board.hasWinner() || board.isDraw();
    }

    public void play() {
        System.out.println(String.format("Tic Tac Toe, board: %sx%s, Players: %s", boardSize, boardSize,
                players.stream().map(p -> p.getName()).collect(Collectors.joining(", "))));

        Board board = new Board(boardSize);
        turn = -1;

        do {
            Player player = getNextPlayer();

            while (true) {
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
            }

            printBoard(board);
        } while (!isGameEnd(board));

        if (board.hasWinner()) {
            Player winner = board.findWinner();
            System.out.println("The End. Winner: " + winner.getName());
        } else if (board.isDraw()) {
            System.out.println("The End. Draw");
        }

    }

    private void printBoard(Board board) {
        System.out.println("Board:");
        System.out.println(board);
    }
}
