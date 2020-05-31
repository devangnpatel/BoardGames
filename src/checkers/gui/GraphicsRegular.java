package checkers.gui;

import game.utility.Properties.PlayerColor;
import java.awt.Graphics;

/**
 * contains information necessary to draw a regular Checkers piece and its colors
 * @author devang
 */
public class GraphicsRegular extends CheckersGraphicsPiece {
            
    protected GraphicsRegular(PlayerColor color, int x, int y, int width, int height) {
        super(color,x,y,width,height);
    }
    
    /**
     * paints this piece to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(getGraphicsColor());
        g.fillOval(x*width + 2*margin, y*height + 2*margin, width - margin*4, height - margin*4);
    }
}
