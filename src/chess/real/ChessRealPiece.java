package chess.real;

import chess.gui.ChessGraphicsPiece;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKing;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import game.boardgame.pieces.Piece;
import game.utility.Point;
import game.utility.Properties.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * contains information necessary to draw a Chess piece and its colors
 * @author devang
 */
public class ChessRealPiece extends ChessGraphicsPiece {

    private String pieceType;
    private String pieceColor;
    private final ChessPhotos photos;

    private final int randomInset = 16;
    
    /**
     * paints this Chess Piece (real photo) to the graphics object in arguments
     * - also prints a grayed out shadow effect at the base of the piece
     * @param g Graphics object on which to draw this piece
     * @param topLft top-left Point coordinate of space
     * @param topRgt top-right Point coordinate of space
     * @param botRgt bottom-right Point coordinate of space
     * @param botLft bottom-left Point coordinate of space
     */
    public void paint(Graphics g, Point topLft, Point topRgt, Point botRgt, Point botLft)
    {
        PiecePhoto piecePhoto = photos.getPieceImage(pieceType, pieceColor, x, y);
        if (piecePhoto == null) return;
        
        BufferedImage pieceImage = piecePhoto.getPhoto();
        int xOffset = piecePhoto.getXOffset();
        int yOffset = piecePhoto.getYOffset();
        if (pieceImage == null) return;

        paintShadowEffectBackground(g,topLft,topRgt,botRgt,botLft);
        g.drawImage(pieceImage,xOffset,yOffset,null);
        paintShadowEffectForeground(g,topLft,topRgt,botRgt,botLft);
    }
    
    /**
     * paints this Chess Piece (real photo) to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        PiecePhoto piecePhoto = photos.getPieceImage(pieceType, pieceColor, x, y);
        if (piecePhoto == null) return;
                
        BufferedImage pieceImage = piecePhoto.getPhoto();
        int xOffset = piecePhoto.getXOffset();
        int yOffset = piecePhoto.getYOffset();
        if (pieceImage == null) return;

        g.drawImage(pieceImage,xOffset,yOffset,null);
    }
    
    public void shiftOffset(int xOffset, int yOffset)
    {
        PiecePhoto piecePhoto = photos.getPieceImage(pieceType,pieceColor,x,y);
        piecePhoto.shiftOffset(xOffset,yOffset);
        piecePhoto.save();
    }
    
    private int margin(int scale)
    {
        /*int inset = randomInset + scale;
        return (int)Math.floor(Math.random()*inset - inset/2);*/
        return 0;
    }    
    
    private void paintShadowEffectBackground(Graphics g,Point topLft,Point topRgt,Point botRgt,Point botLft)
    {
        // paint grayed out square underlay
        double numLayers = 20.0;
        double inc = 1.0/numLayers;
        for (double i=inc;i<1.0;i=i+inc)
        {
            // oval
            // x,y,width,height
            int margin = 4;
            
            int xAvg = (int)Math.floor((double)(topLft.x + topRgt.x + botRgt.x + botLft.x)/4.0);
            int yAvg = (int)Math.floor((double)(topLft.y + topRgt.y + botRgt.y + botLft.y)/4.0);
            
            int xMax= Math.max(Math.max(topLft.x,topRgt.x),Math.max(botLft.x,botRgt.x));
            int yMax= Math.max(Math.max(topLft.y,topRgt.y),Math.max(botLft.y,botRgt.y));
            int xMin= Math.min(Math.min(topLft.x,topRgt.x),Math.min(botLft.x,botRgt.x));
            int yMin= Math.min(Math.min(topLft.y,topRgt.y),Math.min(botLft.y,botRgt.y));
            
            int spaceWidth = xMax - xMin;
            int spaceHeight = yMax - yMin;
            
            int pieceWidth = (int)Math.floor(spaceWidth*.75*i);
            int pieceHeight = (int)Math.floor(spaceHeight*.75*i);
            
            int xCoord = xAvg - (int)Math.floor((double)pieceWidth/2.0) + 5;
            int yCoord = yAvg - (int)Math.floor((double)pieceHeight/2.0) - 11;
            
            int alpha = 10; // 10% transparent
            Color grayedColor = new Color(0, 0, 0, alpha);

            g.setColor(grayedColor);
            g.fillOval(xCoord - margin(6),yCoord - margin(8),pieceWidth + margin(8),pieceHeight + margin(8));
        }
    }
    
    private void paintShadowEffectForeground(Graphics g,Point topLft,Point topRgt,Point botRgt,Point botLft)
    {
        // paint grayed out square underlay
        double numLayers = 10.0;
        double inc = 1.0/numLayers;
        for (double i=inc;i<1.0;i+=inc)
        {
            // oval
            // x,y,width,height
            int margin = 4;
            
            int xAvg = (int)Math.floor((double)(topLft.x + topRgt.x + botRgt.x + botLft.x)/4.0);
            int yAvg = (int)Math.floor((double)(topLft.y + topRgt.y + botRgt.y + botLft.y)/4.0);
            
            int xMax= Math.max(Math.max(topLft.x,topRgt.x),Math.max(botLft.x,botRgt.x));
            int yMax= Math.max(Math.max(topLft.y,topRgt.y),Math.max(botLft.y,botRgt.y));
            int xMin= Math.min(Math.min(topLft.x,topRgt.x),Math.min(botLft.x,botRgt.x));
            int yMin= Math.min(Math.min(topLft.y,topRgt.y),Math.min(botLft.y,botRgt.y));
            
            int spaceWidth = xMax - xMin;
            int spaceHeight = yMax - yMin;
            
            int pieceWidth = (int)Math.floor(spaceWidth*.6*i);
            int pieceHeight = (int)Math.floor(spaceHeight*.6*i);
            
            int xCoord = xAvg - (int)Math.floor((double)pieceWidth/2.0) + 10;
            int yCoord = yAvg - (int)Math.floor((double)pieceHeight/2.0) + 10;
            
            int alpha = 5; // 50% transparent
            Color grayedColor = new Color(0, 0, 0, alpha);

            g.setColor(grayedColor);
            g.fillOval(xCoord - margin(6),yCoord - margin(8),pieceWidth + margin(8),pieceHeight + margin(8));
        }
    }
    
    protected ChessRealPiece(ChessPhotos photos,Piece piece, int x, int y)
    {
        super(piece.getColor(),x,y);
        
        this.photos = photos;
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
     * @param piece Chess piece (white or black), (red or black) etc...
     * @param x Canvas pixel location for x-coordinate of this piece
     * @param y Canvas pixel location for y-coordinate of this piece
     * @return newly-created Chess piece graphics object
     */
    public static ChessRealPiece create(ChessPhotos photos,Piece piece,int x,int y)
    {
        return new ChessRealPiece(photos,piece,x,y);
    }
}
