package chess.moves;

import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import java.io.Serializable;

/**
 * represents a king-castling move for a Chess board
 * @author devang
 */
public class MoveCastle extends ChessMove implements Serializable {
    private final Location kingFrom;
    private final Location kingTo;
    private final Location rookFrom;
    private final Location rookTo;

    /**
     * creates a representation of a castling move for a king
     * @param kingFrom the original location of the king
     * @param kingTo the destination location of the king after the castle
     * @param rookFrom the original location of the rook in this castling-move
     * @param rookTo the destination location of the rook after the castle
     */
    public MoveCastle(Location kingFrom, Location kingTo, Location rookFrom, Location rookTo)
    {
        this.kingFrom = kingFrom;
        this.kingTo   = kingTo;
        this.rookFrom = rookFrom;
        this.rookTo   = rookTo;
    }
    
    /**
     * gets the destination location for the king in this castling move
     * @return Location to which the king will move
     */
    public Location getToLocation()
    {
        return kingTo;
    }
    
    /**
     * gets the origin location for the king in this castling move
     * @return Location from which the king will move
     */
    public Location getFromLocation()
    {
        return kingFrom;
    }
    
    /**
     * gets the origin location for the king in this castling move
     * @return Location from which the king will move
     */
    private Location getKingFromLocation()
    {
        return kingFrom;
    }
    
    /**
     * gets the destination location for the king in this castling move
     * @return Location to which the king will move
     */
    private Location getKingToLocation()
    {
        return kingTo;
    }
    
    /**
     * gets the origin location for the rook in this castling move
     * @return Location from which the rook will move
     */
    private Location getRookFromLocation()
    {
        return rookFrom;
    }
    
    /**
     * gets the destination location for the rook in this castling move
     * @return Location to which the rook will move
     */
    private Location getRookToLocation()
    {
        return rookTo;
    }
    
    /**
     * creates and returns a deep-copy of this castling
     * @return newly-created deep copy of this
     */
    @Override
    public MoveCastle getCopy()
    {
        Location    newKingFrom = Location.copy(kingFrom);
        Location    newKingTo   = Location.copy(kingTo);
        Location    newRookFrom = Location.copy(rookFrom);
        Location    newRookTo   = Location.copy(rookTo);
        
        return new MoveCastle(newKingFrom,newKingTo,newRookFrom,newRookTo);
    }
    
    /**
     * commits this castling move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece kingPiece = boardState.getPiece(kingFrom);
        Piece rookPiece = boardState.getPiece(rookFrom);
        boardState.removePiece(kingFrom);
        boardState.removePiece(rookFrom);
        boardState.setPiece(kingPiece,kingTo);
        boardState.setPiece(rookPiece,rookTo);
    }
    
    @Override 
    public ChessMove rotateMove()
    {
        Location rKingFrom = Location.rotate(kingFrom);
        Location rKingTo   = Location.rotate(kingTo);
        Location rRookFrom = Location.rotate(rookFrom);
        Location rRookTo   = Location.rotate(rookTo);
        
        return new MoveCastle(rKingFrom,rKingTo,rRookFrom,rRookTo);
    }
}

