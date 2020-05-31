package game.boardgame.pieces;

import game.boardgame.BoardState;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

import java.util.List;

/**
 * overarching interface/class for all pieces in board games
 * @author devang
 */
public abstract class Piece {       
    public abstract List<Move> getValidMoves(Location location, BoardState boardState);
    public abstract Piece createCopy();

    protected final PlayerColor pieceColor;
    
    public Piece(PlayerColor pieceColor)
    {
        this.pieceColor = pieceColor;
    }
    
    public PlayerColor getColor()
    {
        return pieceColor;
    }

    /**
     * initiates deep-copy creation of a piece
     * @param piece piece of which to create a deep-copy
     * @return newly-created deep copy of the piece in the argument
     */
    public static Piece copy(Piece piece)
    {
        return piece.createCopy();
    }
}
