package checkers.gui;

import checkers.CheckersProperties;
import game.boardgame.graphics.GraphicsBoard;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Location;

/**
 * contains information necessary to draw a Checker board, its spaces, and the pieces
 * @author devang
 */
public class CheckersGraphicsBoard extends GraphicsBoard {
    
    /**
     * creates and initializes a Checkers game board
     * @param gameName String name to put at top of Game Window
     */
    public CheckersGraphicsBoard(String gameName)
    {
        super(gameName);
    }
    
    /**
     * gets the data for a Checkers piece at the location argument (if this location holds a piece)
     * @param location Location at which to identify a piece and for which to create a piece-graphics object
     * @return piece-graphics object representing a Checkers piece
     */
    @Override
    public GraphicsPiece getGraphicsPiece(Location location)
    {
        if (location == null) return null;
        
        CheckersGraphicsPiece graphicsPiece = null;
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            int spaceWidth  = properties.get_BOARD_WIDTH()/CheckersProperties.NUM_COLS;
            int spaceHeight = properties.get_BOARD_HEIGHT()/CheckersProperties.NUM_ROWS;
            
            Piece piece = boardState.getPiece(location);
            if (piece != null)
            {
                graphicsPiece = CheckersGraphicsPiece.create(piece,c,r,spaceWidth,spaceHeight);
            }
        }
        
        return graphicsPiece;
    }
}
