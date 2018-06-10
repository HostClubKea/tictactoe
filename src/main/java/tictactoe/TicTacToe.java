package tictactoe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TicTacToe {

    private static Game buildGame(){
        String propFileName = "game.properties";

        try (InputStream inputStream = TicTacToe.class.getClassLoader().getResourceAsStream(propFileName)){
            Properties prop = new Properties();
            prop.load(inputStream);

            //Read board size
            int boardSize =  Integer.parseInt(prop.getProperty("board.size"));

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

            return new Game(boardSize, players);
        } catch (FileNotFoundException e) {
            System.out.println("Property file '" + propFileName + "' not found");
        }  catch (Exception e) {
            System.out.println("Propertie file have wrong format");
        }

        return null;
    }


    public static void main(String[] args){

        Game game = TicTacToe.buildGame();
        game.play();

    }
}
