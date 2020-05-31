package checkers.warped;

import checkers.CheckersGame;
import checkers.players.CheckersPlayerHuman;
import checkers.players.CheckersPlayerCPU;
import game.players.Player;
import game.utility.Properties.PlayerColor;
import static game.utility.Properties.PlayerColor.BLACK;
import static game.utility.Properties.PlayerColor.RED;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Overarching Class to establish and maintain a Checkers Game<br>
 * - establishes players, colors for players, board directions for a player<br>
 * - initializes GUI<br>
 * - initializes Network connection for client-server games<br>
 * - maintains the state of the game board and the current player's turn<br>
 * - handles the turn-based game loop
 * 
 * @author devang
 */
public class CheckersWarpedGame extends CheckersGame {
    
    /**
     * A Checkers Game for 2 local human players on same screen
     */
    public CheckersWarpedGame()
    {
        super();
    }
    
    /**
     * A Checkers Game for 1 player against a Phantom CPU AI-controlled player<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */
    public static void newCheckersWarpedAIGame()
    {
        CheckersWarpedGame game = new CheckersWarpedGame();
        
        // order of initialization is important . . .
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersAIGame(game.gui);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    /**
     * A Checkers Game for 2 players on the same local screen: initializes the players<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */
    public static void newCheckersWarpedLocalGame()
    {
        CheckersWarpedGame game = new CheckersWarpedGame();
        
        // order of initialization is important . . .
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersLocalGame(game.gui);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    /**
     * A Checkers Game for 2 players over network: initializes the players<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     * @param color indexed color of the local player
     * @param networkClient client object for socket-communication
     */
    /*public static void newCheckersWarpedNetworkGame(PlayerColor color, Client networkClient)
    {
        CheckersWarpedGame game = new CheckersWarpedGame();
        
        // order of initialization is important . . .
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersNetworkGame(game.gui,color,networkClient);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }*/

    /**
     * initializes the GUI for a board game
     */
    @Override
    public void initializeGUI()
    {
        gui = new CheckersWarpedBoard("Warped Checkers");
    }
   
    /**
     * initializes the Properties(color,directions,dimensions) for a chess game
     */
    @Override
    public void initializeProperties()
    {
        properties = CheckersWarpedProperties.init();
    }

    /**
     * initializes 2 players
     * - this method is a shell:
     *   the three methods, LocalGame, AIGame, NetworkGame need to be called instead
     */
    @Override
    public void initializePlayers()
    {
        String msg = "CheckersWarpedGame::initializePlayers() called\n";
               msg+= "instead, call appropriate method instead: local,AI,network...";
        
        String loggerMsg = msg;
        Logger.getLogger(CheckersGame.class.getName()).log(Level.SEVERE,loggerMsg);
        /*
        initializePlayersLocalGame(gui);
        initializePlayersAIGame(gui);
        initializePlayersNetworkGame(gui,color,client);
        */
    }
    
    protected void initializePlayersLocalGame(CheckersWarpedBoard gui)
    {
        players            = new Player[2];
        players[0]         = CheckersPlayerHuman.create(this,gui,RED);
        players[1]         = CheckersPlayerHuman.create(this,gui,BLACK);
    }
    
    protected void initializePlayersAIGame(CheckersWarpedBoard gui)
    {
        PlayerColor humanPlayerColor;
        PlayerColor CPUPlayerColor;
        if (Math.random() > 0.5)
            humanPlayerColor = PlayerColor.RED;
        else
            humanPlayerColor = PlayerColor.BLACK;
        
        CPUPlayerColor     = CheckersWarpedProperties.oppositeColor(humanPlayerColor);
        
        players            = new Player[2];
        players[0]         = CheckersPlayerHuman.create(this,gui,humanPlayerColor);
        players[1]         = CheckersPlayerCPU.create(this,CPUPlayerColor);
    }
}
