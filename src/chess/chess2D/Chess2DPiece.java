package chess.chess2D;

import chess.gui.ChessGraphicsPiece;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKing;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import game.boardgame.pieces.Piece;
import game.utility.Properties.PlayerColor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * contains information necessary to draw a Chess piece and its colors
 * @author devang
 */
public class Chess2DPiece extends ChessGraphicsPiece {

    private String pieceType;
    private String pieceColor;
    private final Chess2DGraphics pieceGraphics;

    /**
     * paints this Chess Piece (real photo) to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        BufferedImage pieceImage = pieceGraphics.getPieceImage(pieceType, pieceColor);
        g.drawImage(pieceImage,x*width,y*height,null);
    }    
    
    protected Chess2DPiece(Chess2DGraphics pieceGraphics,Piece piece, int x, int y,int width,int height)
    {
        super(piece.getColor(),x,y,width,height);
        
        this.pieceGraphics = pieceGraphics;
        PlayerColor color = piece.getColor();
        
        if (color.compareTo(PlayerColor.BLACK) == 0) this.pieceColor = "black";
        if (color.compareTo(PlayerColor.RED)   == 0) this.pieceColor = "white";
        
        if (piece instanceof PieceBishop) this.pieceType = "bishop";
        if (piece instanceof PieceKnight) this.pieceType = "knight";
        if (piece instanceof PieceRook)   this.pieceType = "rook";
        if (piece instanceof PiecePawn)   this.pieceType = "pawn";
        if (piece instanceof PieceQueen)  this.pieceType = "queen";
        if (piece instanceof PieceKing)   this.pieceType = "king";

    }
    
    /**
     * Creates a new Object used in painting on graphics object of a Canvas
     * @param pieceGraphics object that contains file I/O for sprite images of pieces
     * @param piece Chess piece (white or black), (red or black) etc...
     * @param x Canvas pixel location for x-coordinate of this piece
     * @param y Canvas pixel location for y-coordinate of this piece
     * @param spaceWidth number of pixels for width of space and sprite
     * @param spaceHeight number of pixels for height of space and sprite
     * @return newly-created Chess piece graphics object
     */
    public static Chess2DPiece create(Chess2DGraphics pieceGraphics,Piece piece,int x,int y,int spaceWidth,int spaceHeight)
    {
        return new Chess2DPiece(pieceGraphics,piece,x,y,spaceWidth,spaceHeight);
    }
}
