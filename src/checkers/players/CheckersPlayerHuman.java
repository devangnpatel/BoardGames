package checkers.players;

import checkers.CheckersGame;
import checkers.gui.CheckersGraphicsBoard;
import checkers.moves.CheckerMove;
import checkers.moves.PieceCapture;
import checkers.moves.PieceMove;
import game.Game;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.boardgame.players.BoardGamePlayerHuman;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inherits from a Player, a Board Game Player to provide same
 * interface for a local human player, network player, or phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class CheckersPlayerHuman extends BoardGamePlayerHuman {
    private Map<Location,Move> possibleMoves;
    private boolean            moveInitiated;
    
    private CheckersPlayerHuman(Game game, CheckersGraphicsBoard gui, PlayerColor color)
    {
        super(game,gui,color);
        moveInitiated = false;
        possibleMoves = null;
    }
    
    /**
     * Creates a Human Player object, with the game state, GUI, and this player's color
     * @param game official game state that maintains a board and the current player's turn
     * @param gui canvas drawing object to draw the graphics
     * @param color color of this player (red, black), (white, red), etc.
     * @return Newly created Human Player object
     */
    public static CheckersPlayerHuman create(Game game, CheckersGraphicsBoard gui, PlayerColor color)
    {
        return new CheckersPlayerHuman(game,gui,color);
    }
        
    @Override
    protected List<Move> getValidMoves(Location location)
    {
        BoardState boardState = ((CheckersGame)game).getBoardState();
        Piece      piece      = boardState.getPiece(location);
        
        return piece.getValidMoves(location, boardState);
    }
    
    protected Map<Location,Move> getValidMoves(List<Move> moves)
    {
        Map<Location,Move> moveLocations = new HashMap<>();
        
        for (Move move : moves)
        {
            if (move instanceof CheckerMove)
            {
                CheckerMove checkerMove = (CheckerMove)move;
                if (checkerMove instanceof PieceMove)
                {
                    PieceMove pieceMove = (PieceMove)checkerMove.getCopy();
                    Location toLocation = Location.copy(pieceMove.getToLocation());
                    moveLocations.put(toLocation,move);
                }
                else if (checkerMove instanceof PieceCapture)
                {
                    CheckerMove pieceCapture = (PieceCapture)checkerMove.getCopy();
                    Location    toLocation   = Location.copy(((PieceCapture)pieceCapture).getToLocation());
                    while (pieceCapture.getNext() != null)
                    {
                        pieceCapture = pieceCapture.getNext();
                        if (pieceCapture instanceof PieceCapture)
                        {
                            toLocation = Location.copy(((PieceCapture)pieceCapture).getToLocation());
                        }
                    }
                    moveLocations.put(toLocation,move);
                }
            }   
        }
        return moveLocations;
    }
    
    private void setHighlightedSpaces(Map<Location,Move> moveLocations)
    {
        removeHighlights();
        for (Location location : moveLocations.keySet())
        {
            setHighlightedSpace(location);
        }
    }
    
    private void saveMoveMap(Map<Location,Move> moveLocations)
    {
        possibleMoves = moveLocations;
    }
    
    private void eraseMoveMap()
    {
        possibleMoves.clear();
    }
    
    private Move getMoveFromMap(Location location)
    {
        if (possibleMoves.containsKey(location))
        {
            return possibleMoves.get(location);
        }
        return null;
    }

    @Override
    protected void handleSpaceSelection(Location hoveredSpace, Location selectedSpace)
    {
        if (moveInitiated)
        {
            Location moveLocation = Location.copy(hoveredSpace);
            Move     move         = getMoveFromMap(moveLocation);

            commitMove(move);

            removeHighlights();
            removeSelectedSpace();
            eraseMoveMap();

            moveInitiated = false;
        }
        else if (!moveInitiated)
        {
            if (((CheckersGame)game).getBoardState().isEmpty(hoveredSpace))
                return;

            Piece piece = ((CheckersGame)game).getBoardState().getPiece(hoveredSpace);
            if (piece.getColor() != this.getColor())
                return;

            List<Move> possibleMovesList = getValidMoves(hoveredSpace);
            Map<Location,Move> moveLocations = getValidMoves(possibleMovesList);
            if (moveLocations.isEmpty())
                return;

            selectedSpace = Location.copy(hoveredSpace);
            setSelectedSpace(selectedSpace);
            saveMoveMap(moveLocations);
            setHighlightedSpaces(moveLocations);
            moveInitiated = true;
        }
    }

}
