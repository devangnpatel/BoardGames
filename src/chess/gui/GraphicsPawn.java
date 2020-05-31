package chess.gui;

import game.utility.Properties.PlayerColor;
import java.awt.Graphics;

/**
 * contains information necessary to draw a Pawn piece and its colors
 * @author devang
 */
public class GraphicsPawn extends ChessGraphicsPiece {
            
    protected GraphicsPawn(PlayerColor color, int x, int y, int width, int height) {
        super(color,x,y,width,height);
    }
        
    /**
     * paints this Pawn to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(getGraphicsColor());
        g.fillOval(x*width + 2*margin, y*height + 2*margin, width - margin*4, height - margin*4);

        g.setFont(sansSerifFont);
        g.setColor(getTextColor());
        g.drawString("P", x*width + width/2 - 6,y*height + height/2 + 7);
    }
}
