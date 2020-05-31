package checkers.classic;

import game.utility.Properties.PlayerColor;
import java.awt.Graphics;

/**
 * contains information necessary to draw a regular Checkers piece and its colors
 * @author devang
 */
public class ClassicPieceRegular extends CheckersClassicPiece {
            
    protected ClassicPieceRegular(PlayerColor color, int x, int y, int width, int height,int boardMargin) {
        super(color,x,y,width,height,boardMargin);
    }
    
    /**
     * paints this piece to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(CheckersClassicProperties.PIECE_BORDER_COLOR);
        g.fillOval(applyInset(x*width + 2*margin - 1),applyInset(y*height + 2*margin - 1),width - margin*4 + 1, height - margin*4 + 1);
        
        g.setColor(getGraphicsColor());
        g.fillOval(applyInset(x*width + 2*margin), applyInset(y*height + 2*margin), width - margin*4, height - margin*4);
    }
}
