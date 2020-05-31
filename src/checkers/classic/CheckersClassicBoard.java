package checkers.classic;

import checkers.CheckersProperties;
import checkers.gui.CheckersGraphicsBoard;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.graphics.GraphicsSpace;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw a Checker board, its spaces, and the pieces
 * @author devang
 */
public class CheckersClassicBoard extends CheckersGraphicsBoard {
    
    /**
     * creates and initializes a Checkers game board
     * @param gameName String name to put at top of Game Window
     */
    public CheckersClassicBoard(String gameName)
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
        
        CheckersClassicPiece graphicsPiece = null;
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            int spaceWidth  = properties.get_BOARD_WIDTH()/CheckersProperties.NUM_COLS;
            int spaceHeight = properties.get_BOARD_HEIGHT()/CheckersProperties.NUM_ROWS;
            
            Piece piece = boardState.getPiece(location);
            int boardMargin = properties.get_BOARD_MARGIN();
            if (piece != null)
            {
                graphicsPiece = CheckersClassicPiece.create(piece,c,r,spaceWidth,spaceHeight,boardMargin);
            }
        }
        
        return graphicsPiece;
    }

    @Override
    protected GraphicsSpace createSpace(Properties properties, Location location,int spaceWidth,int spaceHeight,Color spaceColor)
    {
        int boardMargin = properties.get_BOARD_MARGIN();
        GraphicsSpace space = new CheckersClassicSpace(properties,location,spaceWidth,spaceHeight,boardMargin,spaceColor);
        return space;
    }
    
    /**
     * paints this board, its spaces and the contents of the space to this canvas
     * @param g graphics object on which to paint this space
     */
    @Override
    public void paint(Graphics g)
    {
        ((CheckersGraphicsBoard)this).setBackground(CheckersClassicProperties.WINDOW_BACKGROUND_COLOR);
        
        int boardWidth  = CheckersClassicProperties.BOARD_WIDTH;
        int boardHeight = CheckersClassicProperties.BOARD_HEIGHT;
        int boardMargin = properties.get_BOARD_MARGIN();
                
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                Location location = Location.at(c,r);
                GraphicsPiece graphicsPiece = getGraphicsPiece(location);
                GraphicsSpace space = spaces[c][r];
                space.setContents(graphicsPiece);
                space.paint(g);
            }
        }
    }

}
