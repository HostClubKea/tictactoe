# Tic Tac Toe 2.0


Solution is splited in 3 main classes:

1. Board
2. Game
3. Player

Board class responsible for storing Player moves and checking for win/draw conditions. Game handles main game loop, maintains
order of Player turns, ask them for their next move, marks board. In general we can fully trust Player, so for 
his next move decision he receives copy of Board so he can't change status of original board. In general game could be played 
with any board size >= 3 and at least one player, so real restrictions like board size between 3x3 and 10x10 and 3 players 
are handled by game starter class which reads configuration (TicTacToe). 


## Build

```$xslt
gradle build
```

## Start

With default config
```$xslt
java -jar tic-tac-toe-1.0-SNAPSHOT.jar
```

With custom config (in same folder with jar)
```$xslt
java -jar tic-tac-toe-1.0-SNAPSHOT.jar ./customgame.properties
```

##Example propertie file
```$xslt
board.size=7
player1.name=Player 1
player1.mark=x
player2.name=Player 2
player2.mark=o
player3.name=AI
player3.mark=a
```