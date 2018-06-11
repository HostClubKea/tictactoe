package tictactoe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TicTacToe {

    private final static int MIN_BOARD_SIZE = 3;
    private final static int MAX_BOARD_SIZE = 10;

    private static int readBoardSide(Properties prop){
        int boardSize = Integer.parseInt(prop.getProperty("board.size"));
        if(boardSize < MIN_BOARD_SIZE && boardSize > MAX_BOARD_SIZE){
            throw new IllegalArgumentException("Board size should be between 3x3 and 10x10");
        }
        return boardSize;
    }

    private static List<Player> readPlayers(Properties prop){
        List<Player> players = new ArrayList<>();

        //Read player configuration
        String playerName = prop.getProperty("player1.name");
        Character playerMark = prop.getProperty("player1.mark").charAt(0);
        players.add(new HumanPlayer(playerName, playerMark));

        playerName = prop.getProperty("player2.name");
        playerMark = prop.getProperty("player2.mark").charAt(0);
        players.add(new HumanPlayer(playerName, playerMark));

        playerName = prop.getProperty("player3.name");
        playerMark = prop.getProperty("player3.mark").charAt(0);
        players.add(new NaiveAIPlayer(playerName, playerMark));

        return players;
    }

    private static Game buildGame(String config) {
        String propFileName = "game.properties";

        try (InputStream inputStream =  isCustomConfig(config)? new FileInputStream(config) :
                TicTacToe.class.getClassLoader().getResourceAsStream(propFileName)) {
            Properties prop = new Properties();
            prop.load(inputStream);

            //Read board size
            int boardSize = readBoardSide(prop);
            List<Player> players = readPlayers(prop);

            return new Game(boardSize, players);
        } catch (FileNotFoundException e) {
            System.out.println("Property file '" + (isCustomConfig(config)? config: propFileName) + "' not found");
        } catch (IllegalArgumentException e) {
            System.out.println("Property file have wrong settings");
        } catch (Exception e) {
            System.out.println("Property file have wrong format");
        }

        return null;
    }

    private static boolean isCustomConfig(String config){
        return config != null && !config.isEmpty();
    }

    public static void main(String[] args) {
        Game game = TicTacToe.buildGame(args != null && args.length == 1? args[0]: null);
        if(game != null)
            game.play();
    }
}
