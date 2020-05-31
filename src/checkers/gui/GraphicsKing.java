package checkers.gui;

import game.utility.Properties.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw a King Checkers piece and its colors
 * @author devang
 */
public class GraphicsKing extends CheckersGraphicsPiece {
            
    protected GraphicsKing(PlayerColor color, int x, int y, int width, int height) {
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
        g.fillOval(x*width + margin, y*height + margin, width - margin*2, height - margin*2);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x*width + 2*margin, y*height + 2*margin, width - margin*4, height - margin*4);
        g.setColor(getGraphicsColor());
        g.fillOval(x*width + 3*margin, y*height + 3*margin, width - margin*6, height - margin*6);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(x*width + 4*margin, y*height + 4*margin, width - margin*8, height - margin*8);
    }
}
