package tictactoe;

import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Scanner s = new Scanner(System.in);

    public HumanPlayer(String name, char mark) {
        super(name, mark);
    }

    @Override
    public Move nextMove(Board board) {
        while (true) {
            String input = s.nextLine();

            //Check that input contains 2 numbers
            String[] coordinates = input.split(",");
            if (coordinates.length != 2) {
                System.out.println("Wrong input data. Please input 2 comma separated numbers");
                continue;
            }

            try {
                int row = Integer.parseInt(coordinates[0].trim());
                int col = Integer.parseInt(coordinates[1].trim());

                return new Move(row - 1, col - 1);

            } catch (NumberFormatException ex) {
                System.out.println("Wrong input data. Please input 2 comma separated numbers");
            }
        }
    }
}
