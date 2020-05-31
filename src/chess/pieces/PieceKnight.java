package chess.pieces;

import chess.ChessBoardState;
import chess.ChessGameHistory;
import chess.moves.ChessMove;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Knight for Chess games
 * 
 * @author devang
 */
public class PieceKnight extends ChessPiece {
    
    private PieceKnight(PlayerColor pieceColor)
    {
        super(pieceColor);
    }
    
    /**
     * gets a list of valid moves for the piece at the given location on the given board state<br>
     * - the piece at this location will be this knight<br>
     * @param location location for this knight on the board for which to determine all valid moves
     * @param boardState state of the board to analyze for current valid moves
     * @param gameHistory not-yet implemented, but intended for algebraic-chess-notation game recording
     * @return List of valid moves for this knight at the given location on the given board state
     */
    @Override
    public List<Move> getValidMoves(Location location, BoardState boardState, ChessGameHistory gameHistory)
    {
        List<Move> validMoves = new ArrayList<>();
        if (location == null) return validMoves;
        Piece thisPiece = boardState.getPiece(location);
        if (!(thisPiece instanceof PieceKnight)) return validMoves;
        PlayerColor playerColor = thisPiece.getColor();
        PlayerColor opponentColor = Properties.oppositeColor(playerColor);
        
        Location startLocation = Location.copy(location);
        
        Location[] moveLocations = { Location.left(Location.up2(startLocation)),
                                     Location.right(Location.up2(startLocation)),
                                     Location.left(Location.down2(startLocation)),
                                     Location.right(Location.down2(startLocation)),
                                     Location.up(Location.left2(startLocation)),
                                     Location.down(Location.left2(startLocation)),
                                     Location.up(Location.right2(startLocation)),
                                     Location.down(Location.right2(startLocation))};
        
        for (Location nextLocation : moveLocations)
        {
            startLocation = Location.copy(location);
            ChessMove newMove = validateMove(startLocation,nextLocation,(ChessBoardState)boardState);
            if (newMove != null)
            {
                validMoves.add(newMove);
            }
        }
        
        return validMoves;
    }
        
    /**
     * Creates a new knight from deep-copy of this
     * @return deep-copy of this knight
     */
    @Override
    public Piece createCopy()
    {
        PieceKnight newKnight = new PieceKnight(pieceColor);
        newKnight.setProperties(getProperties());
        newKnight.setNumMovesMade(getNumMovesMade());
        newKnight.setMostRecentMove(getMostRecentMove());
        return newKnight;
    }
    
    /**
     * Creates a new knight with parameter color
     * @param pieceColor color for this knight
     * @return a newly constructed knight
     */
    public static Piece create(PlayerColor pieceColor)
    {
        return new PieceKnight(pieceColor);
    }
}
