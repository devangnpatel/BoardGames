package othello.gui.debugmode;

import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.awt.Graphics;

/**
 *
 * @author devang
 */
public class OthelloDebugGraphicsPiece extends GraphicsPiece {

    protected int margin = 5;

    public OthelloDebugGraphicsPiece(PlayerColor color, int x, int y, int width, int height)
    {
        super(color,x,y,width,height);
    }
    
    public static OthelloDebugGraphicsPiece create(Piece piece, int x, int y, int width, int height)
    {
        return new OthelloDebugGraphicsPiece(piece.getColor(),x,y,width,height);
    }
    
    @Override
    public Properties getGameProperties()
    {
        return OthelloDebugProperties.init();
    }
    
    @Override
    public void paint(Graphics g)
    {
        g.setColor(getGraphicsColor());
        g.fillOval(x*width + 2*margin, y*height + 2*margin, width - margin*4, height - margin*4);
        //int marginInset = margin+1;
        //g.fillOval(x*width + 2*marginInset,y*height + 2*marginInset, width - marginInset*4, height - marginInset*4);
    }
}
