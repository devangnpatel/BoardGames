package chess.moves;

import game.boardgame.BoardState;
import game.moves.Move;
import game.utility.Location;
import java.io.Serializable;

/**
 * represents a move for a Chess board
 * @author devang
 */
public abstract class ChessMove extends Move implements Serializable {
    public abstract ChessMove getCopy();
    public abstract void      commitMove(BoardState boardState);
    public abstract Location  getToLocation();
    public abstract Location  getFromLocation();
    @Override
    public abstract ChessMove rotateMove();
}
