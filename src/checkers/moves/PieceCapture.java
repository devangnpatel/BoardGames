package checkers.moves;

import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public class PieceCapture extends CheckerMove implements Serializable {

    private final Location from;
    private final Location to;
    private final Location opponent;

    private PieceCapture(Location from, Location opponent, Location to, CheckerMove next)
    {
        this.from     = from;
        this.to       = to;
        this.opponent = opponent;
        this.next     = next;
    }

    public PieceCapture(Location from, Location opponent, Location to)
    {
        this(from,opponent,to,null);
    }
    
    /**
     * creates and returns a deep-copy of this capturing Checkers move<br>
     * - will recursively deep-copy captures in a chained multi-hop capturing
     * @return newly-created deep copy of this
     */
    @Override
    public PieceCapture getCopy()
    {
        CheckerMove newNext     = null;
        Location    newFrom     = Location.copy(from);
        Location    newOpponent = Location.copy(opponent);
        Location    newTo       = Location.copy(to);
        
        if (next != null)
            newNext = next.getCopy();
        
        return new PieceCapture(newFrom,newOpponent,newTo,newNext);
    }

    public Location getFromLocation()
    {
        return from;
    }
    
    public Location getOpponentLocation()
    {
        return opponent;
    }
    
    public Location getToLocation()
    {
        return to;
    }
    
    public boolean validateMove(BoardState boardState)
    {
        if ((from != null) && (opponent != null) && (to != null))
        {
            if (!boardState.isEmpty(from) && !boardState.isEmpty(opponent) && boardState.isEmpty(to))
            {
                Piece piece                    = boardState.getPiece(from);
                Piece opponentPiece            = boardState.getPiece(opponent);
                PlayerColor pieceColor         = piece.getColor();
                PlayerColor opponentPieceColor = opponentPiece.getColor();
                
                if (opponentPieceColor != pieceColor)
                    return true;
            }
        }
        return false;
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
        boardState.removePiece(opponent);
        boardState.setPiece(piece,to);

        if (next != null)
            next.commitMove(boardState);
    }
}
