package othello.gui.debugmode;

import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.graphics.GraphicsSpace;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;
import othello.gui.OthelloGraphicsBoard;
import othello.pieces.OthelloPiece;

/**
 *
 * @author devang
 */
public class OthelloDebugGraphicsBoard extends OthelloGraphicsBoard {
    
    /**
     * creates and initializes an Othello game board
     * @param gameName String to place at top of game window
     */
    public OthelloDebugGraphicsBoard(String gameName)
    {
        super(gameName);
    }
    
    @Override
    public GraphicsPiece getGraphicsPiece(Location location)
    {
        if (location == null) return null;
        
        OthelloDebugGraphicsPiece graphicsPiece = null;
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            int spaceWidth  = OthelloDebugProperties.BOARD_WIDTH/OthelloDebugProperties.NUM_COLS;
            int spaceHeight = OthelloDebugProperties.BOARD_HEIGHT/OthelloDebugProperties.NUM_ROWS;
            
            Piece piece = boardState.getPiece(location);
            if ((piece != null) && (piece instanceof OthelloPiece))
                graphicsPiece = OthelloDebugGraphicsPiece.create(piece,c,r,spaceWidth,spaceHeight);
        }
        
        return graphicsPiece;
    }
    
    @Override
    protected GraphicsSpace createSpace(Properties properties, Location location,int spaceWidth,int spaceHeight,Color spaceColor)
    {
        Color color = OthelloDebugProperties.SPACE_COLOR;
        int boardMargin = properties.get_BOARD_MARGIN();
        GraphicsSpace space = new OthelloDebugGraphicsSpace(properties,location,spaceWidth,spaceHeight,boardMargin,color);
        return space;
    }

    @Override
    public void paint(Graphics g)
    {
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
