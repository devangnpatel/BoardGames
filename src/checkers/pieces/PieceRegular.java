package checkers.pieces;

import checkers.CheckersProperties;
import checkers.moves.CheckerMove;

import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.moves.Move;

import game.utility.Location;
import game.utility.Properties.PlayerColor;
import game.utility.Properties.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Regular piece for Checkers games
 * 
 * @author devang
 */
public class PieceRegular extends Piece {
    
    private PieceRegular(PlayerColor pieceColor)
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
        Direction direction = CheckersProperties.getColorDirection(getColor());
        Set<Direction> directions = new HashSet<>();
        directions.add(direction);
        
        List<CheckerMove> validMoves    = CheckerMove.getValidMoves(location, directions, boardState);
        List<CheckerMove> validCaptures = CheckerMove.getValidCaptures(location, directions, boardState);
        
        List<Move> moves = new ArrayList<>();
        for (CheckerMove move : validMoves)
            moves.add(move);
        
        for (CheckerMove capture : validCaptures)
            moves.add(capture);
        
        return moves;
    }
        
    /**
     * Creates a deep-copied new regular Checkers from this<br>
     * - useful for evaluating possible moves<br>
     * - useful in serialization for sending objects in data streams in socket communication<br>
     * 
     * @return Piece a newly constructed regular Checkers piece created as a deep-copy from this
     */
    @Override
    public Piece createCopy()
    {
        return new PieceRegular(pieceColor);
    }
    
    /**
     * Creates a new regular Checkers piece with parameter color
     * @param pieceColor color for this Checkers piece
     * @return a newly constructed Checkers piece
     */
    public static Piece create(PlayerColor pieceColor)
    {
        return new PieceRegular(pieceColor);
    }
}
