package game.boardgame;

import game.boardgame.board.Board;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * the state of the board, and the pieces laid out on that board state
 * 
 * @author devang
 */
public class BoardState {
    protected Board               board;

    // set of all pieces on the board
    protected Set<Piece>          pieces;

    // mapping from all locations on the board, to a piece at the location, if not empty
    protected Map<Location,Piece> pieceLocations;
    
    public BoardState()
    {
        board          = new Board();
        pieces         = new HashSet<>();
        pieceLocations = new HashMap<>();
    }

    /**
     * sets a piece to a location on the board
     * @param piece piece to set on the board
     * @param location location at which to set the piece
     */
    public void setPiece(Piece piece, Location location)
    {
        pieces.add(piece);
        pieceLocations.put(location,piece);
    }
    
    /**
     * gets the piece at the location on the board
     * @param location location at which to get a reference to a piece
     * @return piece at the location, if not empty, null otherwise
     */
    public Piece getPiece(Location location)
    {
        Piece piece = pieceLocations.get(location);
        return piece;
    }
    
    /**
     * removes the piece at the location on the board
     * @param location location at which to remove the piece
     */
    public void removePiece(Location location)
    {
        Piece piece = pieceLocations.remove(location);
        if (piece != null) pieces.remove(piece);
    }
    
    /**
     * determines whether the space at location on the board is empty
     * @param location location on the board at which to determine emptiness
     * @return true if the space on the board is empty, false otherwise
     */
    public boolean isEmpty(Location location)
    {
        if (pieceLocations.containsKey(location))
        {
            if (pieceLocations.get(location) != null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * checks whether the player of color in this board state is under attack
     * @param color color of player for which to check for attacks
     * @return true if this player is under attack in this board state
     */
    public boolean check(PlayerColor color)
    {
        // does nothing in this super-class
        return false;
    }
    
    /**
     * returns a board state of deep-copy of this board state
     * @return newly-created board state from a deep-copy of this
     */
    protected BoardState getCopy()
    {
        BoardState newBoardState = new BoardState();
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
     * returns a new board state that is a deep-copy of the input argument
     * @param boardState board state of which to make a copy
     * @return newly-created deep-copied board state
     */
    public static BoardState copy(BoardState boardState)
    {
        return boardState.getCopy();
    }
    
    /**
     * returns a set of all the pieces on the board
     * @return set of all pieces on the board
     */
    public Set<Piece> getPieces()
    {
        return pieces;
    }
}
