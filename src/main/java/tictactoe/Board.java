package tictactoe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Board implements Cloneable {
    private final static int MIN_BOARD_SIZE = 3;

    private final Player[][] board;
    private final int size;

    private static final char EMPTY_MARK = ' ';

    public Board(int size) {
        if (size < MIN_BOARD_SIZE)
            throw new IllegalArgumentException("Board can't be less then 3x3");

        this.size = size;
        this.board = new Player[size][size];
    }

    private Board(Board board) {
        this.size = board.size;
        this.board = new Player[size][];
        for (int i = 0; i < size; i++) {
            this.board[i] = Arrays.copyOf(board.board[i], board.board[i].length);
        }
    }

    public int getSize() {
        return size;
    }

    public void setMark(int row, int col, Player player) {
        validatePosition(row, col);
        validatePositionIsEmpty(row, col);
        validatePlayer(player);
        board[row][col] = player;
    }

    public Player markedBy(int row, int col) {
        validatePosition(row, col);
        return board[row][col];
    }

    public boolean isMarked(int row, int col) {
        validatePosition(row, col);
        return board[row][col] != null;
    }

    private void validatePosition(int row, int col) {
        if (row < 0 || row >= size)
            throw new IllegalArgumentException("Wrong row index");

        if (col < 0 || col >= size)
            throw new IllegalArgumentException("Wrong column index");
    }

    private void validatePositionIsEmpty(int row, int col) {
        if (isMarked(row, col))
            throw new IllegalArgumentException("Position is already marked");
    }

    private void validatePlayer(Player player) {
        if (player == null)
            throw new IllegalArgumentException("Player should be specified");
    }

    public boolean isDraw() {
        List<Map<Player, Integer>> boardData = collectBoardData();

        boolean isDraw = boardData.stream()
                .map(m -> m.entrySet().size()) //we need number of different marks for the line
                .filter(s -> s < 2) // if there less then 2 types of marks in the line
                .map(v -> false) // then we have possible steps
                .findFirst()
                .orElse(true);

        return isDraw;
    }

    public boolean hasWinner() {
        return findWinner() != null;
    }

    public Player findWinner() {
        List<Map<Player, Integer>> boardData = collectBoardData();

        Player winner = boardData.stream().map(Map::entrySet)
                .flatMap(Collection::stream)
                .filter(es -> es.getValue() == size) //Someone was able to fill the line with marks
                .map(res -> res.getKey())
                .findFirst().orElse(null);

        return winner;
    }

    @Override
    public Board clone() {
        return new Board(this);
    }

    //TODO: this could be updated on setMark only
    private List<Map<Player, Integer>> collectBoardData() {
        List<Map<Player, Integer>> list = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            list.add(collectLine(getRow(i)));
            list.add(collectLine(getColumn(i)));
        }

        list.add(collectLine(getMainDiagonal()));
        list.add(collectLine(getSecondDiagonal()));

        return list;
    }

    private List<Player> getRow(int row) {
        return IntStream.range(0, board.length).mapToObj(i -> board[row][i]).collect(Collectors.toList());
    }

    private List<Player> getColumn(int column) {
        return IntStream.range(0, board.length).mapToObj(i -> board[i][column]).collect(Collectors.toList());
    }

    private List<Player> getMainDiagonal() {
        return IntStream.range(0, board.length).mapToObj(i -> board[i][i]).collect(Collectors.toList());
    }

    private List<Player> getSecondDiagonal() {
        return IntStream.range(0, board.length).mapToObj(i -> board[board.length - 1 - i][i]).collect(Collectors.toList());
    }

    private Map<Player, Integer> collectLine(List<Player> line) {
        Map<Player, Integer> markCounts = new HashMap<>();
        for (Player p : line)
            addMarkCounts(markCounts, p);
        return markCounts;
    }

    private void addMarkCounts(Map<Player, Integer> markCounts, Player p) {
        if (p != null) {
            if (markCounts.containsKey(p)) {
                markCounts.put(p, markCounts.get(p) + 1);
            } else {
                markCounts.put(p, 1);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                str.append(' ').append(board[row][col] != null ? board[row][col].getMark() : EMPTY_MARK).append(' ');
                if (col < size - 1)
                    str.append("|");
            }
            str.append('\n');
            if (row < size - 1) {
                char[] line = new char[size * 4 - 1];
                Arrays.fill(line, '-');
                str.append(line).append('\n');
            }
        }
        return str.toString();
    }
}
