package othello.pieces;

import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

import java.util.List;

/**
 * Represents a standard piece for Othello games
 * 
 * @author devang
 */
public class OthelloPiece extends Piece {
    
    private OthelloPiece(PlayerColor pieceColor)
    {
        super(pieceColor);
    }
    
    /**
     * gets a list of valid moves for the piece at the given location on the given board state
     * @param location location to a piece on the board for which to determine all valid moves
     * @param boardState state of the board to analyze for current valid moves
     * @return List of valid moves for the piece at the given location on the given board state
     */
    @Override
    public List<Move> getValidMoves(Location location, BoardState boardState)
    {
        return null;
    }
        
    /**
     * Creates a deep-copied new Othello piece from this<br>
     * - useful for evaluating possible moves<br>
     * - useful in serialization for sending objects in data streams in socket communication<br>
     * 
     * @return Piece a newly constructed Othello piece created as a deep-copy from this
     */
    @Override
    public Piece createCopy()
    {
        return new OthelloPiece(pieceColor);
    }
    
    /**
     * Creates a new Othello piece with parameter color
     * @param pieceColor color for this Othello piece
     * @return Piece a newly constructed Othello piece
     */
    public static Piece create(PlayerColor pieceColor)
    {
        return new OthelloPiece(pieceColor);
    }
}
