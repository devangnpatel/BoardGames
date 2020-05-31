package chess;

import chess.pieces.PieceKing;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.util.HashMap;
import java.util.Map;

/**
 * Maintains the state of the board for a game<br>
 * - Chess uses this to keep track of move history and king locations<br>
 *   for fast access to the King locations<br>
 * - Move history is necessary to validate castling, en-passant and king in check<br>
 * - a copy can be created to test Moves, as in testing if a King is in Check<br>
 * - copies of this board state (deep-copy) can also be made to evaluated<br>
 *   moves in an AI Player (i.e. alpha-beta pruning and tree-representation<br>
 * 
 * @author devang
 */
public class ChessBoardState extends BoardState {
    
    private Map<PlayerColor,PieceKing> kings;
    private Map<PieceKing,Location>    kingLocations;
    
    /**
     * constructor: <br>
     * after calling superclass, initializes the mapping to the king and location of the king
     */
    public ChessBoardState()
    {
        super();
        kings         = new HashMap<>();
        kingLocations = new HashMap<>();
    }
    
    /**
     * sets a piece location on the board<br>
     * - this class inherits from the super-class to intervene and
     *   simultaneously make the mapping to the king location consistent and up-to-date
     * @param piece the piece to set on the board
     * @param location the location on the board, at which to set the piece
     */
    @Override
    public void setPiece(Piece piece, Location location)
    {
        if (piece instanceof PieceKing)
        {
            PlayerColor kingColor = ((PieceKing)piece).getColor();
            kings.put(kingColor,(PieceKing)piece);
            if (kingLocations.containsKey((PieceKing)piece))
                kingLocations.replace((PieceKing)piece,location);
            else
                kingLocations.put((PieceKing)piece,location);
        }
        super.setPiece(piece,location);
    }
    
    /**
     * deep-copy of this board state, for analyzing Moves offline
     * @return the copy of the board
     */
    @Override
    protected BoardState getCopy()
    {
        ChessBoardState newBoardState = new ChessBoardState();
        for (Location location : pieceLocations.keySet())
        {
            Piece piece = pieceLocations.get(location);
            Location newLocation = Location.copy(location);
            Piece newPiece = Piece.copy(piece);
            newBoardState.setPiece(newPiece,newLocation);
        }
        return newBoardState;
    }
    
    /**
     * Tests if the king (of the parameter player's color) is in Check
     * @param color the Player whose king is tested in check
     * @return True if the king is in Check, False otherwise
     */
    @Override
    public boolean check(PlayerColor color)
    {
        PlayerColor playerColor   = color;
        PlayerColor opponentColor = Properties.oppositeColor(playerColor);
        
        PieceKing kingPiece   = kings.get(playerColor);
        Location kingLocation = kingLocations.get(kingPiece);

        return kingPiece.check(kingLocation,this);
    }
}
