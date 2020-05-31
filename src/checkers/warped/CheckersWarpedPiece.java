package checkers.warped;

import checkers.warped.CheckersWarper.WarpedSpace;
import checkers.pieces.PieceKing;
import checkers.pieces.PieceRegular;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;

/**
 * contains information necessary to draw a Checkers piece and its colors
 * @author devang
 */
public abstract class CheckersWarpedPiece extends GraphicsPiece {

    protected WarpedSpace warpedSpace;
        
    protected CheckersWarpedPiece(PlayerColor color, Location location, WarpedSpace warpedSpace)
    {
        super(color,Location.getCol(location),Location.getRow(location),warpedSpace.topRight.point.x-warpedSpace.topLeft.point.x,warpedSpace.bottomLeft.point.y-warpedSpace.topLeft.point.y);
        
        this.warpedSpace = warpedSpace;
    }
    
    /**
     * gets the static variables for color-scheme for a Checkers game
     * @return Properties object that holds static values for Checkers game attributes
     */
    @Override
    public Properties getGameProperties()
    {
        return CheckersWarpedProperties.init();
    }
        
    /**
     * Creates a new Object used in painting on graphics object of a Canvas
     * @param piece Checkers piece (white or black), (red or black) etc...
     * @param location Col,Row location on the Checker Board
     * @param warpedSpace warping parameters and attributes
     * @return newly-created Warped Checkers piece graphics object
     */
    public static CheckersWarpedPiece create(Piece piece, Location location, WarpedSpace warpedSpace)
    {
        if (piece instanceof PieceRegular)
            return new WarpedRegular(piece.getColor(),location,warpedSpace);
        if (piece instanceof PieceKing)
            return new WarpedKing(piece.getColor(),location,warpedSpace);
        return null;
    }
}
