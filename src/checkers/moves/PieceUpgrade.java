package checkers.moves;

import checkers.pieces.PieceKing;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class PieceUpgrade extends CheckerMove implements Serializable {
    private final Location location;

    private PieceUpgrade(Location location, CheckerMove next)
    {
        this.location = location;
        this.next     = next;
    }

    public PieceUpgrade(Location location)
    {
        this(location,null);
    }
    
    /**
     * creates and returns a deep-copy of this Move, which will include an upgrade to a King
     * @return newly-created deep copy of this
     */
    @Override
    public PieceUpgrade getCopy()
    {
        CheckerMove newNext     = null;
        Location    newLocation = Location.copy(location);
        
        if (next != null)
            newNext = next.getCopy();
        
        return new PieceUpgrade(newLocation,newNext);
    }

    /**
     * commits this piece-upgrading move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    @Override
    public void commitMove(BoardState boardState)
    {
        Piece oldPiece         = boardState.getPiece(location);
        PlayerColor pieceColor = oldPiece.getColor();
        Piece newPiece         = PieceKing.create(pieceColor);
        
        boardState.removePiece(location);
        boardState.setPiece(newPiece, location);

        if (next != null)
            next.commitMove(boardState);
    }
}
