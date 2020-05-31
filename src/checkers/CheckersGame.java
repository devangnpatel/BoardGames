package checkers;

import checkers.gui.CheckersGraphicsBoard;
import checkers.players.CheckersPlayerHuman;
import checkers.pieces.PieceRegular;
import checkers.moves.CheckerMove;
import checkers.players.CheckersPlayerCPU;
import checkers.players.CheckersPlayerNetwork;
import game.boardgame.BoardGame;
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
import java.util.List;
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
public class CheckersGame extends BoardGame {
    protected CheckersGraphicsBoard gui = null;
    protected BoardState            currentBoardState;
    protected PlayerColor           currentPlayerColor;
    
    /**
     * A Checkers Game for 2 local human players on same screen
     */
    public CheckersGame()
    {
        super();
    }
    
    /**
     * A Checkers Game for 1 player against a Phantom CPU AI-controlled player<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */
    public static void newCheckersAIGame()
    {
        CheckersGame game = new CheckersGame();
        
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
    public static void newCheckersLocalGame()
    {
        CheckersGame game = new CheckersGame();
        
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
    public static void newCheckersNetworkGame(PlayerColor color, Client networkClient)
    {
        CheckersGame game = new CheckersGame();
        
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
        gui = new CheckersGraphicsBoard("Checkers");
    }
   
    /**
     * initializes the Properties(color,directions,dimensions) for a chess game
     */
    @Override
    public void initializeProperties()
    {
        properties = CheckersProperties.init();
    }

    /**
     * initializes 2 players
     * - this method is a shell:
     *   the three methods, LocalGame, AIGame, NetworkGame need to be called instead
     */
    @Override
    public void initializePlayers()
    {
        String msg = "CheckersGame::initializePlayers() called\n";
               msg+= "instead, call appropriate method instead: local,AI,network...";
        
        String loggerMsg = msg;
        Logger.getLogger(CheckersGame.class.getName()).log(Level.SEVERE,loggerMsg);
        /*
        initializePlayersLocalGame(gui);
        initializePlayersAIGame(gui);
        initializePlayersNetworkGame(gui,color,client);
        */
    }
    
    protected void initializePlayersLocalGame(CheckersGraphicsBoard gui)
    {
        players            = new Player[2];
        players[0]         = CheckersPlayerHuman.create(this,gui,RED);
        players[1]         = CheckersPlayerHuman.create(this,gui,BLACK);
    }
    
    protected void initializePlayersAIGame(CheckersGraphicsBoard gui)
    {
        PlayerColor humanPlayerColor;
        PlayerColor CPUPlayerColor;
        if (Math.random() > 0.5)
            humanPlayerColor = PlayerColor.RED;
        else
            humanPlayerColor = PlayerColor.BLACK;
        
        CPUPlayerColor     = CheckersProperties.oppositeColor(humanPlayerColor);
        
        players            = new Player[2];
        players[0]         = CheckersPlayerHuman.create(this,gui,humanPlayerColor);
        players[1]         = CheckersPlayerCPU.create(this,CPUPlayerColor);
    }
    
    protected void initializePlayersNetworkGame(CheckersGraphicsBoard gui, PlayerColor color, Client networkClient)
    {
        
        PlayerColor localPlayerColor  = color;
        PlayerColor remotePlayerColor = CheckersProperties.oppositeColor(color);
        
        players = new Player[2];
        players[0] = CheckersPlayerHuman.create(this,gui,localPlayerColor);
        players[1] = CheckersPlayerNetwork.create(this,networkClient,remotePlayerColor);
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
        currentPlayerColor = CheckersProperties.INITIAL_PLAYER_COLOR;
        if (currentPlayerColor == players[0].getColor())
            currentPlayer = players[0];
        else
            currentPlayer = players[1];
        
        gui.init(currentBoardState,properties);
        
        if ((currentPlayer == players[1]) && (players[1] instanceof CheckersPlayerCPU))
        {
            ((CheckersPlayerCPU)currentPlayer).evaluateNextMove();
        }

    }
    
    /**
     * initializes the pieces on the game board
     */
    @Override
    public void initializePieces()
    {
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(0, 7));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(2, 7));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(4, 7));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(6, 7));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(1, 6));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(3, 6));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(5, 6));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(7, 6));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(0, 5));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(2, 5));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(4, 5));
        currentBoardState.setPiece(PieceRegular.create(RED),Location.at(6, 5));
        
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(1, 0));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(3, 0));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(5, 0));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(7, 0));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(0, 1));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(2, 1));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(4, 1));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(6, 1));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(1, 2));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(3, 2));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(5, 2));
        currentBoardState.setPiece(PieceRegular.create(BLACK),Location.at(7, 2));

    }

    /**
     * called after a move is made: changes the player turn to who has the current move
     */
    @Override
    public void togglePlayer()
    {
        currentPlayerColor = CheckersProperties.oppositeColor(currentPlayerColor);
        if (currentPlayer == players[0])
            currentPlayer = players[1];
        else if (currentPlayer == players[1])
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
        if (!(move instanceof CheckerMove)) return;
        
        ((CheckerMove)move).commitMove(currentBoardState);
        
        togglePlayer();
        
        persistMove(player,move);
        
        if (checkGameOver())
        {
            if (gameOverWindow(player)) terminate();
        }
    }

    private boolean gameOverWindow(Player player)
    {
        PlayerColor winnerColor = CheckersProperties.oppositeColor(currentPlayerColor);
        
        String gameOverText;
        gameOverText = "game over: ";
        gameOverText+= winnerColor.toString();
        gameOverText+= " wins!";
        
        boolean result = false; // = gameOverWindow(gameOverText);
        if (player instanceof CheckersPlayerHuman)
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
     * - Checkers ends when there are no possible moves or remaining pieces for a player<br>
     * @return True if the game is over, False otherwise
     */
    @Override
    public boolean checkGameOver()
    {
        int numPiecesRemaining = 0;
        int numPossibleMoves   = 0;
        
        for (Location location : Location.allLocations())
        {
            if (!currentBoardState.isEmpty(location))
            {
                Piece piece = currentBoardState.getPiece(location);
                if (piece.getColor() == currentPlayerColor)
                {
                    numPiecesRemaining++;
                    List<Move> validMoves = piece.getValidMoves(location,currentBoardState);
                    if (validMoves.size() > 0)
                        numPossibleMoves++;
                }
            }
        }
        
        if ((numPiecesRemaining > 0) && (numPossibleMoves > 0))
            return false;
        
        return true;
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
