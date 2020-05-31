package othello.moves;

import game.boardgame.BoardState;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import othello.pieces.OthelloPiece;

/**
 * represents an action that an Othello player can take
 * - validates and commits a placement of a piece from the player who has the current move
 * @author devang
 */
public class OthelloMove extends Move implements Serializable {
    private final Location    location;
    private final PlayerColor color;
    
    /**
     * constructor for a new Move<br>
     * - useful in applying to a board state<br>
     * - useful in socket-communication persisting of Moves to a remote-opponent
     * @param color the current player's color
     * @param location the location at which to place the new Othello piece
     */
    public OthelloMove(PlayerColor color, Location location)
    {
        this.color    = color;
        this.location = location;
    }
    
    /**
     * creates and returns a deep-copy of this Othello piece placement (move)
     * @return newly-created deep copy of this
     */
    public OthelloMove getCopy()
    {
        return new OthelloMove(color,Location.copy(location));
    }
    
    private Location nextLocation(Location start, int i, BiFunction<Location,Integer,Location> locationMethod) {
        Location newLocation = locationMethod.apply(start, i);
        return newLocation;
    }
    
    /**
     * commits this Move to the board state in the argument
     * @param boardState state of a board of a game against to which to apply this move
     */
    public void commitMove(BoardState boardState)
    {
        if (!boardState.isEmpty(location)) return;
        
        PlayerColor pieceColor = color;
        PlayerColor opponentColor = Properties.oppositeColor(color);
        
        boardState.setPiece(OthelloPiece.create(pieceColor),location);

        List<BiFunction<Location,Integer,Location>> locationDirections;
        locationDirections = new ArrayList<>();
        locationDirections.add(Location::leftX);
        locationDirections.add(Location::rightX);
        locationDirections.add(Location::upX);
        locationDirections.add(Location::downX);
        locationDirections.add(Location::upLeftX);
        locationDirections.add(Location::upRightX);
        locationDirections.add(Location::downLeftX);
        locationDirections.add(Location::downRightX);

        for (BiFunction<Location,Integer,Location> function : locationDirections)
        {
            int next = 1;
            Location startLocation = Location.copy(location);
            Location nextLocation = nextLocation(startLocation,next,function);
            while ((nextLocation != null ) && !boardState.isEmpty(nextLocation) && (boardState.getPiece(nextLocation).getColor()==opponentColor))
            {
                nextLocation = nextLocation(startLocation,next++,function);
            }
            if ((nextLocation != null) && !boardState.isEmpty(nextLocation) && boardState.getPiece(nextLocation).getColor()==pieceColor)
            {
                for (int n = 1; n < next; n++)
                {
                    Location intLocation = nextLocation(startLocation,n,function);
                    boardState.removePiece(intLocation);
                    boardState.setPiece(OthelloPiece.create(pieceColor),intLocation);
                }
            }
        }
    }
    
    /**
     *
     * @param boardState
     * @return
     */
    public boolean validateMove(BoardState boardState)
    {
        if (!boardState.isEmpty(location)) return false;
        
        int numMoveDirections = 0;
        PlayerColor pieceColor = color;
        PlayerColor opponentColor = Properties.oppositeColor(color);
        
        List<BiFunction<Location,Integer,Location>> locationDirections;
        locationDirections = new ArrayList<>();
        locationDirections.add(Location::leftX);
        locationDirections.add(Location::rightX);
        locationDirections.add(Location::upX);
        locationDirections.add(Location::downX);
        locationDirections.add(Location::upLeftX);
        locationDirections.add(Location::upRightX);
        locationDirections.add(Location::downLeftX);
        locationDirections.add(Location::downRightX);

        for (BiFunction<Location,Integer,Location> function : locationDirections)
        {
            int next = 1;
            Location startLocation = Location.copy(location);
            Location nextLocation = nextLocation(startLocation,next,function);
            while ((nextLocation != null ) && !boardState.isEmpty(nextLocation) && (boardState.getPiece(nextLocation).getColor()==opponentColor))
            {
                nextLocation = nextLocation(startLocation,next++,function);
            }
            if ((nextLocation != null) && !boardState.isEmpty(nextLocation) && boardState.getPiece(nextLocation).getColor()==pieceColor)
            {
                if (next > 1) numMoveDirections++;
            }
        }
        
        return (numMoveDirections > 0);
    }
}
