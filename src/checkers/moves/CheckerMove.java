package checkers.moves;

import checkers.CheckersProperties;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import game.utility.Properties.Direction;
import static game.utility.Properties.Direction.DOWN;
import static game.utility.Properties.Direction.UP;
import static checkers.CheckersProperties.NUM_ROWS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * represents a move for a Checkers board
 * @author devang
 */
public abstract class CheckerMove extends Move implements Serializable {
    public abstract CheckerMove getCopy();
    public abstract void commitMove(BoardState boardState);

    protected CheckerMove next;
    
    public void setNext(CheckerMove move)
    {
        next = move;
    }
    
    public CheckerMove getNext()
    {
        return next;
    }
    
    public static List<CheckerMove> getValidMoves(Location location, Set<Direction> directions, BoardState boardState)
    {
        List<CheckerMove> validMoves = new ArrayList<>();
        List<Location> moveLocations = new ArrayList<>();
        
        int upgradeRow = -1;
        Piece piece = boardState.getPiece(location);
        PlayerColor pieceColor = piece.getColor();
        Direction pieceDirection = CheckersProperties.getColorDirection(pieceColor);
        switch (pieceDirection)
        {
            case UP:
                upgradeRow = 0;
                break;
            case DOWN:
                upgradeRow = NUM_ROWS - 1;
                break;
        }
        
        if (directions.contains(UP))
        {
            Location upLeft = Location.upLeft(location);
            if (upLeft != null) moveLocations.add(upLeft);
            Location upRight = Location.upRight(location);
            if (upRight != null) moveLocations.add(upRight);
        }
        
        if (directions.contains(DOWN))
        {
            Location downLeft = Location.downLeft(location);
            if (downLeft != null) moveLocations.add(downLeft);
            Location downRight = Location.downRight(location);
            if (downRight != null) moveLocations.add(downRight);
        }
        
        for (Location moveLocation : moveLocations)
        {
            if (boardState.isEmpty(moveLocation))
            {
                PieceMove move = new PieceMove(location,moveLocation);
                if (Location.getRow(moveLocation) == upgradeRow)
                {
                    PieceUpgrade nextMove = new PieceUpgrade(moveLocation);
                    move.setNext(nextMove);
                }
                validMoves.add(move);
            }
        }
        
        return validMoves;
    }
    
    public static List<CheckerMove> getValidCaptures(Location location, Set<Direction> directions, BoardState boardState)
    {
        List<CheckerMove> validCaptures = new ArrayList<>();
        List<PieceCapture> tempCaptures = new ArrayList<>();
        
        int upgradeRow = -1;
        Piece piece = boardState.getPiece(location);
        PlayerColor pieceColor = piece.getColor();
        Direction pieceDirection = CheckersProperties.getColorDirection(pieceColor);
        switch (pieceDirection)
        {
            case UP:
                upgradeRow = 0;
                break;
            case DOWN:
                upgradeRow = NUM_ROWS - 1;
                break;
        }
        
        if (directions.contains(UP))
        {
            Location tempFrom     = Location.copy(location);
            Location tempOpponent = Location.upLeft(location);
            Location tempTo       = Location.upLeft2(location);
            PieceCapture tempCapture = new PieceCapture(tempFrom,tempOpponent,tempTo);
            tempCaptures.add(tempCapture);
        }
        
        if (directions.contains(UP))
        {
            Location tempFrom     = Location.copy(location);
            Location tempOpponent = Location.upRight(location);
            Location tempTo       = Location.upRight2(location);
            PieceCapture tempCapture = new PieceCapture(tempFrom,tempOpponent,tempTo);
            tempCaptures.add(tempCapture);
        }
        
        if (directions.contains(DOWN))
        {
            Location tempFrom     = Location.copy(location);
            Location tempOpponent = Location.downLeft(location);
            Location tempTo       = Location.downLeft2(location);
            PieceCapture tempCapture = new PieceCapture(tempFrom,tempOpponent,tempTo);
            tempCaptures.add(tempCapture);
        }
        
        if (directions.contains(DOWN))
        {
            Location tempFrom     = Location.copy(location);
            Location tempOpponent = Location.downRight(location);
            Location tempTo       = Location.downRight2(location);
            PieceCapture tempCapture = new PieceCapture(tempFrom,tempOpponent,tempTo);
            tempCaptures.add(tempCapture);
        }
        
        for (PieceCapture tempCapture : tempCaptures)
        {
            PieceCapture newCapture = tempCapture.getCopy();
            BoardState newBoardState = BoardState.copy(boardState);
            
            if (newCapture.validateMove(newBoardState))
            {
                newCapture.commitMove(newBoardState);
                PieceCapture copyCapture = newCapture.getCopy();
                Location newLocation = newCapture.getToLocation();
                PieceUpgrade newUpgrade = null;
                    
                if (Location.getRow(newLocation) == upgradeRow)
                {
                    newUpgrade = new PieceUpgrade(newLocation);
                    newUpgrade.commitMove(newBoardState);
                    PieceUpgrade copyUpgrade = newUpgrade.getCopy();
                    copyCapture.setNext(copyUpgrade);
                }
                validCaptures.add(copyCapture);
                
                Set<Direction> newDirections = new HashSet<>();
                newDirections.add(UP);
                newDirections.add(DOWN);
                List<CheckerMove> nextCaptures = getValidCaptures(newLocation,newDirections,newBoardState);

                for (CheckerMove nextCapture : nextCaptures)
                {
                    CheckerMove copyNewCapture = newCapture.getCopy();
                    CheckerMove copyNextCapture = nextCapture.getCopy();
                    if (newUpgrade != null)
                    {
                        PieceUpgrade copyNewUpgrade = newUpgrade.getCopy();
                        copyNewUpgrade.setNext(copyNextCapture);
                        copyNewCapture.setNext(copyNewUpgrade);
                        validCaptures.add(copyNewCapture);
                    }
                    else
                    {
                        copyNewCapture.setNext(copyNextCapture);
                        validCaptures.add(copyNewCapture);
                    }
                }
            }
        }
        
        return validCaptures;
    }
}
