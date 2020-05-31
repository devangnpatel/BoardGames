package chess.chess2D;

import chess.ChessProperties;
import chess.gui.ChessGraphicsBoard;
import chess.gui.ChessGraphicsPiece;
import chess.pieces.ChessPiece;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Location;

/**
 * contains information necessary to draw a Chess board, its spaces, and the pieces
 * @author devang
 */
public class Chess2DBoard extends ChessGraphicsBoard {
    
    private Chess2DGraphics piecesGraphics = null;
    
    /**
     * creates and initializes a Chess game board
     * @param gameName String of name to place at top of Game Window
     */
    public Chess2DBoard(String gameName)
    {
        super("Chess");
    }
    
    private ChessProperties getProperties()
    {
        return (Chess2DProperties)properties;
    }
    
    /**
     * gets the data for a Chess piece at the location argument (if this location holds a piece)
     * @param location Location at which to identify a piece and for which to create a piece-graphics object
     * @return piece-graphics object representing a Chess piece
     */
    @Override
    public GraphicsPiece getGraphicsPiece(Location location)
    {
        if (location == null) return null;
        
        ChessGraphicsPiece graphicsPiece = null;
        if (piecesGraphics == null) piecesGraphics = new Chess2DGraphics();
        
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            int spaceWidth  = Chess2DProperties.BOARD_WIDTH  / Chess2DProperties.NUM_COLS;
            int spaceHeight = Chess2DProperties.BOARD_HEIGHT / Chess2DProperties.NUM_ROWS;
            
            Piece piece = boardState.getPiece(location);
            if ((piece != null) && (piece instanceof ChessPiece))
            {
                graphicsPiece = Chess2DPiece.create(piecesGraphics,piece,c,r,spaceWidth,spaceHeight);
                graphicsPiece.setGameProperties(getProperties());
            }
        }
        
        return graphicsPiece;
    }
}
