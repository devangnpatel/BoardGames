package chess;

import chess.gui.ChessGraphicsBoard;
import chess.moves.ChessMove;
import chess.moves.MovePromotion;
import chess.moves.MovePromotion.PieceType;
import chess.pieces.ChessPiece;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKing;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import chess.players.ChessPlayerCPU;
import chess.players.ChessPlayerHuman;
import chess.players.ChessPlayerNetwork;
import java.util.logging.Logger;
import java.util.logging.Level;
import game.boardgame.BoardGame;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.boardgame.players.BoardGamePlayerHuman;
import game.moves.Move;
import game.network.Client;
import game.players.Player;
import game.utility.Location;
import game.utility.Properties.Direction;
import game.utility.Properties.PlayerColor;
import static game.utility.Properties.PlayerColor.RED;
import static game.utility.Properties.PlayerColor.BLACK;
import java.util.List;
import javax.swing.JOptionPane;

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
public class ChessGame extends BoardGame {
    protected ChessGraphicsBoard gui;
    protected ChessBoardState    currentBoardState;
    protected PlayerColor        currentPlayerColor;
    protected ChessGameHistory   gameHistory;

    /**
     * not yet implemented
     * @return ChessGameHistory the game history of all moves (algebraic chess notation)
     */
    public ChessGameHistory getGameHistory()
    {
        return gameHistory;
    }
    
    /**
     * game history usage not yet implemented
     */
    protected void initializeHistory()
    {
        gameHistory = new ChessGameHistory();
    }
    
    /**
     * A Chess Game for 2 local human players on same screen
     */
    public ChessGame()
    {
        super();
    }

    /**
     * A Chess Game for 1 player against a Phantom CPU AI-controlled player<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */    
    public static void newChessAIGame()
    {
        ChessGame game = new ChessGame();
        
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
    public static void newChessNetworkGame(PlayerColor color, Client networkClient)
    {
        ChessGame game = new ChessGame();
        
        // order of initialization is important . . .
        game.initializeHistory();
        game.initializeBoard();
        game.initializeGUI();
        game.initializePlayersNetworkGame(game.gui,color,networkClient);
        game.initializeProperties();
        game.initializePieces();
        game.initializeGame();
    }
    
    /**
     * A Chess Game for 2 players on the same local screen: initializes the players<br>
     * - colors are indexed as red or black in the code<br>
     * - the properties class map colors to any java.awt.Color in java canvas graphics
     */
    public static void newChessLocalGame()
    {
        ChessGame game = new ChessGame();
        
        // order of initialization is important . . .
        game.initializeHistory();
        game.initializeBoard();
        game.initializeGUI();                      // needs board
        game.initializePlayersLocalGame(game.gui); // needs gui
        game.initializeProperties();               // needs players
        game.initializePieces();                   // needs players, properties
        game.initializeGame();                     // needs players, gui
    }

    /**
     * initializes the GUI for a board game
     */
    @Override
    public void initializeGUI()
    {
        gui = new ChessGraphicsBoard("Chess");
    }
   
    /**
     * initializes the Properties(color,directions,dimensions) for a chess game
     */
    @Override
    public void initializeProperties()
    {
        if (players[0].getColor().compareTo(RED) == 0)
            properties = ChessProperties.init(Direction.UP,Direction.DOWN);
        else
            properties = ChessProperties.init(Direction.DOWN,Direction.UP);
    }
    
    /**
     * initializes 2 players
     * - this method is a shell:
     *   the three methods, LocalGame, AIGame, NetworkGame need to be called instead
     */
    @Override
    public void initializePlayers()
    {
        String msg = "ChessGame::initializePlayers() called\n";
               msg+= "instead, call appropriate method instead: local,AI,network...";
        String loggerMsg = msg;
        Logger.getLogger(ChessGame.class.getName()).log(Level.SEVERE,loggerMsg);
        /*
        initializePlayersLocalGame(gui);
        initializePlayersAIGame(gui);
        initializePlayersNetworkGame(gui,color,client);
        */
    }
    
    protected void initializePlayersLocalGame(ChessGraphicsBoard gui)
    {
        players            = new Player[2];
        players[0]         = ChessPlayerHuman.create(this,gui,RED);
        players[1]         = ChessPlayerHuman.create(this,gui,BLACK);
    }
    
    protected void initializePlayersAIGame(ChessGraphicsBoard gui)
    {
        PlayerColor humanPlayerColor;
        PlayerColor CPUPlayerColor;
        if (Math.random() > 0.5)
            humanPlayerColor = PlayerColor.RED;
        else
            humanPlayerColor = PlayerColor.BLACK;
        
        CPUPlayerColor     = ChessProperties.oppositeColor(humanPlayerColor);
        
        players            = new Player[2];
        players[0]         = ChessPlayerHuman.create(this,gui,humanPlayerColor);
        players[1]         = ChessPlayerCPU.create(this,CPUPlayerColor);
    }
    
    protected void initializePlayersNetworkGame(ChessGraphicsBoard gui, PlayerColor color, Client networkClient)
    {
        
        PlayerColor localPlayerColor  = color;
        PlayerColor remotePlayerColor = ChessProperties.oppositeColor(color);
        
        players = new Player[2];
        players[0] = ChessPlayerHuman.create(this,gui,localPlayerColor);
        players[1] = ChessPlayerNetwork.create(this,networkClient,remotePlayerColor);
    }
    
    /**
     * sets up the pieces on the game board
     */
    @Override
    public void initializeBoard()
    {
        currentBoardState = new ChessBoardState();
    }
    
    @Override
    public void initializeGame()
    {
        currentPlayerColor = ChessProperties.INITIAL_PLAYER_COLOR;
        if (currentPlayerColor == players[0].getColor())
            currentPlayer = players[0];
        else
            currentPlayer = players[1];
        
        gui.init(currentBoardState,properties);
        
        if ((currentPlayer == players[1]) && (players[1] instanceof ChessPlayerCPU))
        {
            ((ChessPlayerCPU)currentPlayer).makeNextMove();
        }
    }
    
    @Override
    public void initializePieces()
    {        
        if (players[0].getColor() == RED)
            initializePiecesRedControl();
        else
            initializePiecesBlackControl();
        setPiecesProperties((ChessProperties)properties);
    }
    
    private void setPiecesProperties(ChessProperties properties)
    {
        for (Piece piece : currentBoardState.getPieces())
        {
            ((ChessPiece)piece).setProperties(properties);
        }
    }
    
    private void initializePiecesBlackControl()
    {
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(0,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(1,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(2,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(3,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(4,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(5,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(6,6));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(7,6));
        
        currentBoardState.setPiece(PieceRook.create(BLACK),Location.at(0,7));
        currentBoardState.setPiece(PieceKnight.create(BLACK),Location.at(1,7));
        currentBoardState.setPiece(PieceBishop.create(BLACK),Location.at(2,7));
        currentBoardState.setPiece(PieceKing.create(BLACK),Location.at(3,7));
        currentBoardState.setPiece(PieceQueen.create(BLACK),Location.at(4,7));
        currentBoardState.setPiece(PieceBishop.create(BLACK),Location.at(5,7));
        currentBoardState.setPiece(PieceKnight.create(BLACK),Location.at(6,7));
        currentBoardState.setPiece(PieceRook.create(BLACK),Location.at(7,7));
        
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(0,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(1,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(2,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(3,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(4,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(5,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(6,1));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(7,1));
        
        currentBoardState.setPiece(PieceRook.create(RED),Location.at(0,0));
        currentBoardState.setPiece(PieceKnight.create(RED),Location.at(1,0));
        currentBoardState.setPiece(PieceBishop.create(RED),Location.at(2,0));
        currentBoardState.setPiece(PieceKing.create(RED),Location.at(3,0));
        currentBoardState.setPiece(PieceQueen.create(RED),Location.at(4,0));
        currentBoardState.setPiece(PieceBishop.create(RED),Location.at(5,0));
        currentBoardState.setPiece(PieceKnight.create(RED),Location.at(6,0));
        currentBoardState.setPiece(PieceRook.create(RED),Location.at(7,0));
    }
    
    private void initializePiecesRedControl()
    {
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(0,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(1,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(2,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(3,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(4,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(5,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(6,6));
        currentBoardState.setPiece(PiecePawn.create(RED),Location.at(7,6));
        
        currentBoardState.setPiece(PieceRook.create(RED),Location.at(0,7));
        currentBoardState.setPiece(PieceKnight.create(RED),Location.at(1,7));
        currentBoardState.setPiece(PieceBishop.create(RED),Location.at(2,7));
        currentBoardState.setPiece(PieceQueen.create(RED),Location.at(3,7));
        currentBoardState.setPiece(PieceKing.create(RED),Location.at(4,7));
        currentBoardState.setPiece(PieceBishop.create(RED),Location.at(5,7));
        currentBoardState.setPiece(PieceKnight.create(RED),Location.at(6,7));
        currentBoardState.setPiece(PieceRook.create(RED),Location.at(7,7));
        
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(0,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(1,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(2,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(3,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(4,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(5,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(6,1));
        currentBoardState.setPiece(PiecePawn.create(BLACK),Location.at(7,1));
        
        currentBoardState.setPiece(PieceRook.create(BLACK),Location.at(0,0));
        currentBoardState.setPiece(PieceKnight.create(BLACK),Location.at(1,0));
        currentBoardState.setPiece(PieceBishop.create(BLACK),Location.at(2,0));
        currentBoardState.setPiece(PieceQueen.create(BLACK),Location.at(3,0));
        currentBoardState.setPiece(PieceKing.create(BLACK),Location.at(4,0));
        currentBoardState.setPiece(PieceBishop.create(BLACK),Location.at(5,0));
        currentBoardState.setPiece(PieceKnight.create(BLACK),Location.at(6,0));
        currentBoardState.setPiece(PieceRook.create(BLACK),Location.at(7,0));
    }
    
    /**
     * called after a move is made: changes the player turn to who has the current move
     */
    @Override
    public void togglePlayer()
    {
        currentPlayerColor = ChessProperties.oppositeColor(currentPlayerColor);
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
        if (player != currentPlayer)      return;
        if (move == null)                 return;
        if (!(move instanceof ChessMove)) return;
        
        ChessMove chessMove = (ChessMove)move;
        if (move instanceof MovePromotion)
        {
            PieceType pieceType = pawnPromotionMenu();
            ((MovePromotion)chessMove).setNewPieceType(pieceType);
        }
        
        if (true)
        {   // modify this code block after implementing algebraic notation usage
            Location moveLocation = chessMove.getFromLocation();
            ChessPiece movePiece = (ChessPiece)currentBoardState.getPiece(moveLocation);
            gameHistory.setMostRecentMove(chessMove);
            movePiece.setMostRecentMove(chessMove);
            movePiece.setNumMovesMade(movePiece.getNumMovesMade()+1);
        }
        
        ((ChessMove)move).commitMove(currentBoardState);

        togglePlayer();

        persistMove(player,move);
        
        if (checkGameOver())
        {
            if (gameOverWindow()) terminate();
        }
    }
    
    private boolean gameOverWindow()
    {
        PlayerColor winnerColor = ChessProperties.oppositeColor(currentPlayerColor);
        String winnerColorText = "";
        String gameOverText;
        
        if (winnerColor == RED) winnerColorText = "WHITE";
        if (winnerColor == BLACK) winnerColorText = "BLACK";
        
        gameOverText = "game over: ";
        gameOverText+= winnerColorText;
        gameOverText+=" wins!";
        
        boolean result = gameOverWindow(gameOverText);
        return result;
    }
    
    private PieceType pawnPromotionMenu()
    {
        Object[] possibleValues = { "queen", "bishop", "knight", "rook" };
        Object selectedValue = JOptionPane.showInputDialog(null,"pawn promotion", "pawn promotion",
            JOptionPane.PLAIN_MESSAGE,null,possibleValues, possibleValues[0]);
        
        if (selectedValue.equals("queen"))
            return PieceType.QUEEN;
        if (selectedValue.equals("bishop"))
            return PieceType.BISHOP;
        if (selectedValue.equals("knight"))
            return PieceType.KNIGHT;
        if (selectedValue.equals("rook"))
            return PieceType.ROOK;
        return PieceType.QUEEN;
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
     * - Chess ends when a King cannot legally move to another space<br>
     * @return True if the game is over, False otherwise
     */
    @Override
    public boolean checkGameOver()
    {
        boolean validMovePossible = false;

        BoardState boardState = BoardState.copy(currentBoardState);
        
        for (Location location : Location.allLocations())
        {
            if (!boardState.isEmpty(location))
            {
                ChessPiece piece = (ChessPiece)boardState.getPiece(location);
                if (piece.getColor() == currentPlayerColor)
                {
                    List<Move> moves = piece.getValidMoves(location,boardState,gameHistory);
                    if (moves.size() > 0)
                        validMovePossible = true;
                }
            }
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
    public ChessBoardState getBoardState()
    {
        return currentBoardState;
    }
}
