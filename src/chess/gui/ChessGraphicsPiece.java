package chess.gui;

import chess.ChessProperties;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKing;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.awt.Font;

/**
 * contains information necessary to draw a Chess piece and its colors
 * @author devang
 */
public abstract class ChessGraphicsPiece extends GraphicsPiece {

    protected Font sansSerifFont = new Font("SanSerif", Font.PLAIN, 20);
    protected int  margin        = 5;
    
    protected Properties properties =  null;
        
    protected ChessGraphicsPiece(PlayerColor color, int x, int y, int width, int height)
    {
        super(color,x,y,width,height);
    }
    
    protected ChessGraphicsPiece(PlayerColor color, int x, int y)
    {
        super(color,x,y);
    }
    
    /**
     * gets the static variables for color-scheme for a Chess game
     * @return Properties object that holds static values for Chess game attributes
     */
    @Override
    public Properties getGameProperties()
    {
        return properties;
    }
        
    /**
     * sets the static variables for color-scheme for a Chess game
     * @param properties game properties object that holds static values for Chess game attributes
     */
    public void setGameProperties(ChessProperties properties)
    {
        this.properties = properties;
    }
    
    /**
     * Creates a new Object used in painting on graphics object of a Canvas
     * @param piece Chess piece (white or black), (red or black) etc...
     * @param x Canvas pixel location for x-coordinate of this piece
     * @param y Canvas pixel location for y-coordinate of this piece
     * @param width Canvas pixel width of space on which this piece should be drawn
     * @param height Canvas pixel height of space on which this piece should be drawn
     * @return newly-created Chess piece graphics object
     */
    public static ChessGraphicsPiece create(Piece piece,int x,int y,int width,int height)
    {
        if (piece instanceof PieceBishop)
            return new GraphicsBishop(piece.getColor(),x,y,width,height);
        if (piece instanceof PieceKnight)
            return new GraphicsKnight(piece.getColor(),x,y,width,height);
        if (piece instanceof PieceRook)
            return new GraphicsRook(piece.getColor(),x,y,width,height);
        if (piece instanceof PiecePawn)
            return new GraphicsPawn(piece.getColor(),x,y,width,height);
        if (piece instanceof PieceQueen)
            return new GraphicsQueen(piece.getColor(),x,y,width,height);
        if (piece instanceof PieceKing)
            return new GraphicsKing(piece.getColor(),x,y,width,height);
        return null;
    }
}
