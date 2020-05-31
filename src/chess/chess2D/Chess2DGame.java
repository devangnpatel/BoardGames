package chess.chess2D;

import chess.ChessGame;
import game.network.Client;
import game.utility.Properties.Direction;
import game.utility.Properties.PlayerColor;
import static game.utility.Properties.PlayerColor.RED;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Overarching Class to establish a Chess Game<br>
 * - establishes players, colors for players, board directions for a player<br>
 * - initializes GUI<br>
 * - initializes Network connection for client-server games<br>
 * - maintains the state of the game board and the current player's turn<br>
 * - handles the turn-based game loop
 * 
 * @author devang
 */
public class Chess2DGame extends ChessGame {
    
    /**
     * A Chess Game for 2 local human players on same screen
     */
    private Chess2DGame()
    {
        super();
    }

    /**
     * A Chess Game for 1 player against a Phantom CPU AI-controlled player<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */    
    public static void new2DChessAIGame()
    {
        Chess2DGame game = new Chess2DGame();
        
        // order of initialization is important . . .
        game.initializeHistory();
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersAIGame(game.gui);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    /**
     * A Chess Game for 2 players over network: initializes the players<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     * @param color indexed color of the local player
     * @param networkClient client object for socket-communication
     */
    public static void new2DChessNetworkGame(PlayerColor color, Client networkClient)
    {
        Chess2DGame game = new Chess2DGame();
        
        // order of initialization is important . . .
        game.initializeHistory();
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersNetworkGame(game.gui,color,networkClient);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    public static void new2DChessLocalGame()
    {
        Chess2DGame game = new Chess2DGame();
        
        // order of initialization is important . . .
        game.initializeHistory();
        game.initializeBoard();
        game.initializeGUI();                      // needs board, properties
        game.initializePlayersLocalGame(game.gui); // needs gui
        game.initializeProperties();               // needs players
        game.initializePieces();                   // needs properties,players
        game.initializeGame();                     // needs players
    }
    
    @Override
    public void initializeGUI()
    {
        gui = new Chess2DBoard("Basic 2D Chess");
    }
    
    @Override
    public void initializeProperties()
    {
        if (players[0].getColor().compareTo(RED) == 0)
            properties = Chess2DProperties.init(Direction.UP,Direction.DOWN);
        else
            properties = Chess2DProperties.init(Direction.DOWN,Direction.UP);
    }
    
    /**
     * initializes 2 players
     * - this method is a shell:
     *   the three methods, LocalGame, AIGame, NetworkGame need to be called instead
     */
    @Override
    public void initializePlayers()
    {
        String msg = "Chess2DGame::initializePlayers() called\n";
               msg+= "instead, call appropriate method instead: local,AI,network...";
        String loggerMsg = msg;
        Logger.getLogger(Chess2DGame.class.getName()).log(Level.SEVERE,loggerMsg);
        /*
        initializePlayersLocalGame(gui);
        initializePlayersAIGame(gui);
        initializePlayersNetworkGame(gui,color,client);
        */
    }
}
