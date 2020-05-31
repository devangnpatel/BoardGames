package chess.gui;

import chess.ChessProperties;
import chess.pieces.ChessPiece;
import game.boardgame.graphics.GraphicsBoard;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.pieces.Piece;
import game.utility.Location;

/**
 * contains information necessary to draw a Chess board, its spaces, and the pieces
 * @author devang
 */
public class ChessGraphicsBoard extends GraphicsBoard {
    
    /**
     * creates and initializes a Chess game board
     * @param gameName String of name to place at top of Game Window
     */
    public ChessGraphicsBoard(String gameName)
    {
        super("Chess");
    }
    
    private ChessProperties getProperties()
    {
        return (ChessProperties)properties;
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
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            int spaceWidth  = ChessProperties.BOARD_WIDTH  / ChessProperties.NUM_COLS;
            int spaceHeight = ChessProperties.BOARD_HEIGHT / ChessProperties.NUM_ROWS;
            
            Piece piece = boardState.getPiece(location);
            if ((piece != null) && (piece instanceof ChessPiece))
            {
                graphicsPiece = ChessGraphicsPiece.create(piece,c,r,spaceWidth,spaceHeight);
                graphicsPiece.setGameProperties(getProperties());
            }
        }
        
        return graphicsPiece;
    }
}
