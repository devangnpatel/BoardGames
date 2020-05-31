package checkers.classic;

import checkers.pieces.PieceKing;
import checkers.pieces.PieceRegular;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;

/**
 * contains information necessary to draw a Checkers piece and its colors
 * @author devang
 */
public abstract class CheckersClassicPiece extends GraphicsPiece {
    protected int margin = 5;
    
    private final int boardMargin;
    
    protected int applyInset(int value)
    {
        return value + boardMargin;
    }

    protected CheckersClassicPiece(PlayerColor color, int x, int y, int width, int height,int boardMargin) {
        super(color,x,y,width,height);
        this.boardMargin = boardMargin;
    }
    
    /**
     * gets the static variables for color-scheme for a Checkers game
     * @return Properties object that holds static values for Checkers game attributes
     */
    @Override
    public Properties getGameProperties()
    {
        return CheckersClassicProperties.init();
    }
        
    /**
     * Creates a new Object used in painting on graphics object of a Canvas
     * @param piece Checkers piece (white or black), (red or black) etc...
     * @param x Canvas pixel location for x-coordinate of this piece
     * @param y Canvas pixel location for y-coordinate of this piece
     * @param width Canvas pixel width of space on which this piece should be drawn
     * @param height Canvas pixel height of space on which this piece should be drawn
     * @return newly-created Checkers piece graphics object
     */
    public static CheckersClassicPiece create(Piece piece,int x,int y,int width,int height,int boardMargin)
    {
        if (piece instanceof PieceRegular)
            return new ClassicPieceRegular(piece.getColor(),x,y,width,height,boardMargin);
        if (piece instanceof PieceKing)
            return new ClassicPieceKing(piece.getColor(),x,y,width,height,boardMargin);
        return null;
    }
}
