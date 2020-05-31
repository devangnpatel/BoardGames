package othello.players;

import othello.OthelloGame;
import othello.gui.OthelloGraphicsBoard;
import othello.moves.OthelloMove;
import game.Game;
import game.boardgame.BoardState;
import game.boardgame.players.BoardGamePlayerHuman;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

import java.util.List;
import java.util.Map;


/**
 * Inherits from a Player, a Board Game Player to provide same
 * interface for a local human player, network player, or phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class OthelloPlayerHuman extends BoardGamePlayerHuman {
    
    private OthelloPlayerHuman(Game game, OthelloGraphicsBoard gui, PlayerColor color)
    {
        super(game,gui,color);
    }
    
    /**
     * Creates a Human Player object, with the game state, GUI, and this player's color
     * @param game official game state that maintains a board and the current player's turn
     * @param gui canvas drawing object to draw the graphics
     * @param color color of this player (red, black), (white, red), etc.
     * @return Newly created Human Player object
     */
    public static OthelloPlayerHuman create(Game game, OthelloGraphicsBoard gui, PlayerColor color)
    {
        return new OthelloPlayerHuman(game,gui,color);
    }
        
    /**
     * NOT USED in this subclass<br>
     * - would get a list of all valid moves for the pieces at the given location<br>
     * - unlike Checkers and Chess, valid Moves are not analyzed for any specific location
     * @param location Location on the board to get the piece to evaluate for moves
     * @return Null, otherwise it would return a list of valid Moves
     */
    @Override
    protected List<Move> getValidMoves(Location location)
    {
        return null;
    }
    
    /**
     * NOTE USED for Othello<br>
     * - would be a list of possible move-destinations so that the GUI can highlight for player convenience
     * @param moveLocations mapping of locations to the Move for which the location would be legal
     */
    public void setHighlightedSpaces(Map<Location,Move> moveLocations)
    {
        removeHighlights();
        for (Location location : moveLocations.keySet())
        {
            setHighlightedSpace(location);
        }
    }

    @Override
    protected void handleSpaceSelection(Location hoveredSpace, Location selectedSpace)
    {
                Location   moveLocation = Location.copy(hoveredSpace);
                Move       move         = new OthelloMove(getColor(),moveLocation);
                BoardState boardState   = ((OthelloGame)game).getBoardState();
                
                if (((OthelloMove)move).validateMove(boardState))
                    commitMove(move);
    }
}
