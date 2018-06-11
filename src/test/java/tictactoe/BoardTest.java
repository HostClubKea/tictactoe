package tictactoe;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testEmptyBoardHasNoWinner() {
        assertEquals(null, new Board(3).findWinner());
    }

    @Test
    public void testRowWin() {
        Board b = new Board(3);
        Player p = new HumanPlayer("dummy", 'x');
        b.setMark(0, 0, p);
        b.setMark(0, 1, p);
        b.setMark(0, 2, p);
        assertEquals(p, b.findWinner());
    }

    @Test
    public void testColumnWin() {
        Board b = new Board(3);
        Player p = new HumanPlayer("dummy", 'x');
        b.setMark(0, 0, p);
        b.setMark(1, 0, p);
        b.setMark(2, 0, p);
        assertEquals(p, b.findWinner());
    }

    @Test
    public void testMainDiagonalWin() {
        Board b = new Board(3);
        Player p = new HumanPlayer("dummy", 'x');
        b.setMark(0, 0, p);
        b.setMark(1, 1, p);
        b.setMark(2, 2, p);
        assertEquals(p, b.findWinner());
    }

    @Test
    public void testSecondDiagonalWin() {
        Board b = new Board(3);
        Player p = new HumanPlayer("dummy", 'x');
        b.setMark(0, 2, p);
        b.setMark(1, 1, p);
        b.setMark(2, 0, p);
        assertEquals(p, b.findWinner());
    }

    @Test
    public void testNotDraw() {
        Board b = new Board(3);
        Player p1 = new HumanPlayer("dummy1", 'x');
        Player p2 = new HumanPlayer("dummy2", 'o');
        b.setMark(0, 0, p1);
        b.setMark(0, 1, p2);
        b.setMark(1, 1, p1);
        b.setMark(1, 0, p2);
        assertEquals(false, b.isDraw());
    }

    @Test
    public void testDraw() {
        Board b = new Board(3);
        Player p1 = new HumanPlayer("dummy1", 'x');
        Player p2 = new HumanPlayer("dummy2", 'o');

        /*
            x 0 0
            0 x x
            x   0
         */

        b.setMark(0, 0, p1);
        b.setMark(0, 1, p2);
        b.setMark(0, 2, p2);
        b.setMark(1, 0, p2);
        b.setMark(1, 1, p1);
        b.setMark(1, 2, p1);
        b.setMark(2, 0, p1);
        b.setMark(2, 2, p2);

        assertEquals(true, b.isDraw());
    }

}
