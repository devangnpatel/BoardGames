package othello.gui;

import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.awt.Graphics;
import othello.OthelloProperties;

/**
 * contains information necessary to draw an Othello piece and its colors
 * @author devang
 */
public class OthelloGraphicsPiece extends GraphicsPiece {

    protected int margin = 2;
    private final int boardMargin;
    
    private int applyInset(int value)
    {
        return value + boardMargin;
    }
    
    private OthelloGraphicsPiece(PlayerColor color, int x, int y, int width, int height, int boardMargin)
    {
        super(color,x,y,width,height);
        this.boardMargin = boardMargin;
    }
    
    /**
     * Creates a new Object used in painting on graphics object of a Canvas
     * @param piece Othello piece (white or black), (red or black) etc...
     * @param x Canvas pixel location for x-coordinate of this piece
     * @param y Canvas pixel location for y-coordinate of this piece
     * @param width Canvas pixel width of space on which this piece should be drawn
     * @param height Canvas pixel height of space on which this piece should be drawn
     * @param boardMargin a margin/border around the Othello board in between the edges of the window
     * @return newly-created Othello piece graphics object
     */
    public static OthelloGraphicsPiece create(Piece piece, int x, int y, int width, int height, int boardMargin)
    {
        return new OthelloGraphicsPiece(piece.getColor(),x,y,width,height,boardMargin);
    }
    
    /**
     * gets the static variables for color-scheme for an Othello game
     * @return Properties object that holds static values for Othello game attributes
     */
    @Override
    public Properties getGameProperties()
    {
        return OthelloProperties.init();
    }
    
    /**
     * paints this piece to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        int marginInset = margin+1;
        
        // paint the piece
        g.setColor(getGraphicsColor());
        g.fillOval(applyInset(x*width) + 2*marginInset, applyInset(y*height) + 2*marginInset, width - marginInset*4, height - marginInset*4);

        // paint an outline around the piece
        g.setColor(OthelloProperties.PIECE_BORDER_COLOR);
        g.drawOval(applyInset(x*width) + 2*marginInset, applyInset(y*height) + 2*marginInset, width - marginInset*4, height - marginInset*4);
        //g.fillOval(applyInset(x*width) + 2*marginInset, applyInset(y*height) + 2*marginInset, width - marginInset*4, height - marginInset*4);
    }
}
