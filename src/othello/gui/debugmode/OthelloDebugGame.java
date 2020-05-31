package othello.gui.debugmode;

import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.boardgame.players.BoardGamePlayerHuman;
import game.moves.Move;
import game.network.Client;
import game.players.Player;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import static game.utility.Properties.PlayerColor.BLACK;
import static game.utility.Properties.PlayerColor.RED;
import java.util.logging.Level;
import java.util.logging.Logger;
import othello.OthelloGame;
import othello.gui.OthelloGraphicsBoard;
import othello.moves.OthelloMove;
import othello.pieces.OthelloPiece;
import othello.players.OthelloPlayerCPU;
import othello.players.OthelloPlayerHuman;
import othello.players.OthelloPlayerNetwork;

/**
 * Initial Class to establish an Othello Game<br>
 * - this is a "debug-mode" as it was the original implementation
 * - establishes players, colors for players, board directions for a player<br>
 * - initializes GUI<br>
 * - initializes Network connection for client-server games<br>
 * - maintains the state of the game board and the current player's turn<br>
 * - handles the turn-based game loop
 * 
 * @author devang
 */
public class OthelloDebugGame extends OthelloGame {
    private OthelloGraphicsBoard gui = null;
    private BoardState    currentBoardState;
    private PlayerColor  currentPlayerColor;
    
    /**
     * An Othello Game for 2 local human players on same screen
     */
    private OthelloDebugGame()
    {
        super();
    }
    
    /**
     * A Othello Game for 1 player against a Phantom CPU AI-controlled player<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */
    public static void newOthelloDebugAIGame()
    {
        OthelloDebugGame game = new OthelloDebugGame();
        
        // order of initialization is important . . .
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersAIGame(game.gui);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    /**
     * A Othello Game for 2 players on the same local screen: initializes the players<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */
    public static void newOthelloDebugLocalGame()
    {
        OthelloDebugGame game = new OthelloDebugGame();
        
        // order of initialization is important . . .
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersLocalGame(game.gui);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    /**
     * A Othello Game for 2 players over network: initializes the players<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     * @param color indexed color of the local player
     * @param networkClient client object for socket-communication
     */
    public static void newOthelloDebugNetworkGame(PlayerColor color, Client networkClient)
    {
        OthelloDebugGame game = new OthelloDebugGame();
        
        // order of initialization is important . . .
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersNetworkGame(game.gui,color,networkClient);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }

    /**
     * initializes the GUI for a board game
     */
    @Override
    public void initializeGUI()
    {
        initializeGUI(currentBoardState);
    }
    
    @Override
    protected void initializeGUI(BoardState boardState)
    {
        gui = new OthelloDebugGraphicsBoard("Othello - Debug Mode");
    }
   
    /**
     * initializes the Properties(color,directions,dimensions) for a chess game
     */
    @Override
    public void initializeProperties()
    {
        properties = OthelloDebugProperties.init();
    }

    /**
     * initializes 2 players
     * - this method is a shell:
     *   the three methods, LocalGame, AIGame, NetworkGame need to be called instead
     */
    @Override
    public void initializePlayers()
    {
        String msg = "OthelloDebugGame::initializePlayers() called\n";
               msg+= "instead, call appropriate method instead: local,AI,network...";
        
        String loggerMsg = msg;
        Logger.getLogger(OthelloDebugGame.class.getName()).log(Level.SEVERE,loggerMsg);
        /*
        initializePlayersLocalGame(gui);
        initializePlayersAIGame(gui);
        initializePlayersNetworkGame(gui,color,client);
        */
    }
    
    @Override
    protected void initializePlayersLocalGame(OthelloGraphicsBoard gui)
    {
        players            = new Player[2];
        players[0]         = OthelloPlayerHuman.create(this,gui,RED);
        players[1]         = OthelloPlayerHuman.create(this,gui,BLACK);
    }
    
    @Override
    protected void initializePlayersAIGame(OthelloGraphicsBoard gui)
    {
        PlayerColor humanPlayerColor;
        PlayerColor CPUPlayerColor;
        if (Math.random() > 0.5)
            humanPlayerColor = PlayerColor.RED;
        else
            humanPlayerColor = PlayerColor.BLACK;
        
        CPUPlayerColor     = OthelloDebugProperties.oppositeColor(humanPlayerColor);
        
        players            = new Player[2];
        players[0]         = OthelloPlayerHuman.create(this,gui,humanPlayerColor);
        players[1]         = OthelloPlayerCPU.create(this,CPUPlayerColor);
    }
    
    @Override
    protected void initializePlayersNetworkGame(OthelloGraphicsBoard gui, PlayerColor color, Client networkClient)
    {
        
        PlayerColor localPlayerColor  = color;
        PlayerColor remotePlayerColor = OthelloDebugProperties.oppositeColor(color);
        
        players = new Player[2];
        players[0] = OthelloPlayerHuman.create(this,gui,localPlayerColor);
        players[1] = OthelloPlayerNetwork.create(this,networkClient,remotePlayerColor);
    }
    
    /**
     * sets up the pieces on the game board
     */
    @Override
    public void initializeBoard()
    {
        currentBoardState = new BoardState();
    }
    
    /**
     * sets up the current player's turn and
     * initializes the graphics gui board and
     * tells the CPU to make the next move, if this game is against AI CPU
     * and the AI CPU has the first move to make
     */
    @Override
    public void initializeGame()
    {    
        currentPlayerColor = OthelloDebugProperties.INITIAL_PLAYER_COLOR;
        if (currentPlayerColor == players[0].getColor())
            currentPlayer = players[0];
        else
            currentPlayer = players[1];
        
        gui.init(currentBoardState,properties);
        
        if ((currentPlayer == players[1]) && (players[1] instanceof OthelloPlayerCPU))
        {
            ((OthelloPlayerCPU)currentPlayer).evaluateNextMove();
        }

    }
    
    /**
     * initializes the pieces on the game board
     */
    @Override
    public void initializePieces()
    {
        currentBoardState.setPiece(OthelloPiece.create(RED),Location.at(3, 3));
        currentBoardState.setPiece(OthelloPiece.create(RED),Location.at(4, 4));
        
        currentBoardState.setPiece(OthelloPiece.create(BLACK),Location.at(3, 4));
        currentBoardState.setPiece(OthelloPiece.create(BLACK),Location.at(4, 3));
    }
    
    /**
     * called after a move is made: changes the player turn to who has the current move
     */
    @Override
    public void togglePlayer()
    {
        currentPlayerColor = OthelloDebugProperties.oppositeColor(currentPlayerColor);
        if (currentPlayer == players[0])
            currentPlayer = players[1];
        else if(currentPlayer == players[1])
            currentPlayer = players[0];
    }
    
    /**
     * sent from a Player (indicated in argument) to apply the move to the board state<br>
     * - performs the move<br>
     * - persists the move to the opponent's game state representation<br>
     * - updates the change to whoever has the current move in this board state<br>
     * - checks for end of game
     * 
     * @param player the Player making the move
     * @param move the Move to make
     */
    @Override
    public void commitMove(Player player, Move move)
    {
        if (player != currentPlayer)        return;
        if (move == null)                   return;
        if (!(move instanceof OthelloMove)) return;
        
        ((OthelloMove)move).commitMove(currentBoardState);

        togglePlayer();
        
        persistMove(player,move);
        
        if (checkGameOver())
        {
            if (gameOverWindow(player)) terminate();
        }
    }
    
    private boolean gameOverWindow(Player player)
    {
        int numRedPieces   = 0;
        int numBlackPieces = 0;
        
        for (Location location : Location.allLocations())
        {
            if (!currentBoardState.isEmpty(location))
            {
                Piece piece = currentBoardState.getPiece(location);
                if (piece.getColor() == RED)
                    numRedPieces++;
                if (piece.getColor() == BLACK)
                    numBlackPieces++;
            }
        }

        String gameOverText;
        if (numRedPieces > numBlackPieces)
            gameOverText = "game over: red wins!";
        else if (numBlackPieces > numRedPieces)
            gameOverText = "game over: black wins!";
        else
            gameOverText = "game over: tie game";
        
        boolean result = false; // = gameOverWindow(gameOverText);
        if (player instanceof OthelloPlayerHuman)
            result = gameOverWindow(gameOverText);
        return result;
    }
    
    /**
     * After a move is made, this method sends that Move to the opponent's game state 
     * to apply the move and make the disconnected game state consistent
     * @param player player making the move
     * @param move the Move that has been made
     */
    @Override
    public void persistMove(Player player, Move move)
    {
        for (Player p : players)
        {
            if (p != player)
                p.persistMove(move);
            else if (p instanceof BoardGamePlayerHuman)
                ((BoardGamePlayerHuman) p).repaint();
        }
    }
    
    /**
     * checks if the game is over, an then displays a message if it is<br>
     * end of game established per rules of specific game and piece locations:<br>
     * - Othello ends when a Player has no remaining pieces, or all spaces are filled
     * @return True if the game is over, False otherwise
     */
    @Override
    public boolean checkGameOver()
    {
        boolean validMovePossible = false;
        
        for (Location location : Location.allLocations())
        {
            OthelloMove move = new OthelloMove(currentPlayerColor,location);
            if (move.validateMove(currentBoardState))
                validMovePossible = true;
        }
        
        return !validMovePossible;
    }
        
    /**
     * public access to the official state of the game<br>
     * - if a copy of the board state is made in any class, a Move can 
     *   be evaluated on that board state copy without modifying the official
     *   current board state, that is accessed here
     * 
     * @return the current formal state of the board for the current game
     */
    @Override
    public BoardState getBoardState()
    {
        return currentBoardState;
    }

}
