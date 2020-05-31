package game.boardgame.graphics;

import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to paint a board game piece
 * @author devang
 */
public abstract class GraphicsPiece {    

    /**
     * paints this piece, it's color scheme, or sprite to a java canvas through the argument graphics object
     * @param g graphics object on which to paint this piece
     */
    public abstract void paint(Graphics g);

    public abstract Properties getGameProperties();

    private final PlayerColor pieceColor;
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    /**
     * constructor for a new object that displays graphical items of a piece
     * @param pieceColor color of this piece
     * @param x java canvas pixel location
     * @param y java canvas pixel location
     * @param width java canvas pixel-width dimension for a piece/space
     * @param height java canvas pixel-height dimension for a piece/space
     */
    public GraphicsPiece(PlayerColor pieceColor, int x, int y, int width, int height)
    {
        this.pieceColor = pieceColor;
        this.x = x;
        this.y = y;
        this.width  = width;
        this.height = height;
    }
    
    /**
     * constructor for a new object that displays graphical items of a piece
     * @param pieceColor color of this piece
     * @param x java canvas pixel location
     * @param y java canvas pixel location
     */
    public GraphicsPiece(PlayerColor pieceColor, int x, int y)
    {
        this.pieceColor = pieceColor;
        this.x = x;
        this.y = y;
    }
    
    /**
     * The player-color (not java.awt.Color) of this piece, i.e. red-black
     * @return color of this piece's player
     */
    protected PlayerColor getColor()
    {
        return pieceColor;
    }
    
    /**
     * the custom graphics-color property stored in a game properties object
     * @return color property associated with this kind of piece
     */
    protected Color getGraphicsColor()
    {
        Properties properties = getGameProperties();
        return properties.getGraphicsColor(pieceColor);
    }
    
    /**
     * the custom text-color property stored in a game properties object 
     * if a game uses text when drawing a piece
     * @return color property associated with this kind of piece
     */
    protected Color getTextColor()
    {
        Properties properties = getGameProperties();
        return properties.getTextColor(pieceColor);
    }
}
