package chess.players;

import chess.ChessBoardState;
import chess.ChessGame;
import chess.gui.ChessGraphicsBoard;
import chess.moves.ChessMove;
import chess.pieces.ChessPiece;
import game.Game;
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
public class ChessPlayerHuman extends BoardGamePlayerHuman {
    private Map<Location,Move> possibleMoves;
    private boolean            moveInitiated;
    
    public ChessPlayerHuman(Game game, ChessGraphicsBoard gui, PlayerColor color)
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
    public static ChessPlayerHuman create(Game game, ChessGraphicsBoard gui, PlayerColor color)
    {
        return new ChessPlayerHuman(game,gui,color);
    }
    
    @Override
    protected List<Move> getValidMoves(Location location)
    {
        ChessBoardState boardState = (ChessBoardState)((ChessGame)game).getBoardState();
        ChessPiece      piece      = (ChessPiece)boardState.getPiece(location);
        return piece.getValidMoves(location, boardState, ((ChessGame)game).getGameHistory());
    }
    
    protected Map<Location,Move> getValidMoves(List<Move> moves)
    {
        Map<Location,Move> moveLocations = new HashMap<>();
        
        for (Move move : moves)
        {
            Location toLocation = Location.copy(((ChessMove)move).getToLocation());
            moveLocations.put(toLocation,move);
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
        if (possibleMoves != null)
            possibleMoves.clear();
    }
    
    private Move getMoveFromMap(Location location)
    {
        if ((possibleMoves != null) && possibleMoves.containsKey(location))
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

            removeHighlights();
            removeSelectedSpace();
            eraseMoveMap();

            commitMove(move);

            moveInitiated = false;
        }
        else if (!moveInitiated)
        {
            if (((ChessGame)game).getBoardState().isEmpty(hoveredSpace))
                return;

            Piece piece = ((ChessGame)game).getBoardState().getPiece(hoveredSpace);
            if (piece.getColor() != this.getColor())
                return;

            List<Move> possibleMovesList     = getValidMoves(hoveredSpace);
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
