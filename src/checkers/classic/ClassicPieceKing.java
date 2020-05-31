package checkers.classic;

import game.utility.Properties.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw a King Checkers piece and its colors
 * @author devang
 */
public class ClassicPieceKing extends CheckersClassicPiece {
            
    protected ClassicPieceKing(PlayerColor color, int x, int y, int width, int height,int boardMargin) {
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
        g.fillOval(applyInset(x*width + margin - 1),applyInset(y*height + margin - 1),width - margin*2 + 1, height - margin*2 + 1);
        g.setColor(getGraphicsColor());
        g.fillOval(applyInset(x*width + margin), applyInset(y*height + margin), width - margin*2, height - margin*2);
        g.setColor(Color.YELLOW);
        g.fillOval(applyInset(x*width + 2*margin), applyInset(y*height + 2*margin), width - margin*4, height - margin*4);
        g.setColor(getGraphicsColor());
        g.fillOval(applyInset(x*width + 3*margin), applyInset(y*height + 3*margin), width - margin*6, height - margin*6);
        g.setColor(Color.YELLOW);
        g.fillOval(applyInset(x*width + 4*margin), applyInset(y*height + 4*margin), width - margin*8, height - margin*8);
    }
}
