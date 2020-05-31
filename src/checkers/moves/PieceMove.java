package checkers.moves;

import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class PieceMove extends CheckerMove implements Serializable {
    private final Location from;
    private final Location to;

    private PieceMove(Location from, Location to, CheckerMove next)
    {
        this.from = from;
        this.to   = to;
        this.next = next;
    }

    public PieceMove(Location from, Location to)
    {
        this(from,to,null);
    }
    
    public Location getFromLocation()
    {
        return from;
    }
    
    public Location getToLocation()
    {
        return to;
    }
    
    /**
     * creates and returns a deep-copy of this standard Checkers move
     * @return newly-created deep copy of this
     */
    @Override
    public PieceMove getCopy()
    {
        CheckerMove newNext = null;
        Location    newFrom = Location.copy(from);
        Location    newTo   = Location.copy(to);
        
        if (next != null)
            newNext = next.getCopy();
        
        return new PieceMove(newFrom,newTo,newNext);
    }
    
    /**
     * commits this Move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece piece = boardState.getPiece(from);
        boardState.removePiece(from);
        boardState.setPiece(piece,to);

        if (next != null)
            next.commitMove(boardState);
    }
}

