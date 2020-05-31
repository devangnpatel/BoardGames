package othello.gui;

import game.boardgame.graphics.GraphicsBoard;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.graphics.GraphicsSpace;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import othello.OthelloProperties;
import othello.pieces.OthelloPiece;

/**
 * contains information necessary to draw an Othello board, its spaces, and the pieces
 * @author devang
 */
public class OthelloGraphicsBoard extends GraphicsBoard {
    
    Image bufferImage;
    Graphics bufferGraphics;
    
    /**
     * creates and initializes an Othello game board
     * @param gameName String to place at top of game window
     */
    public OthelloGraphicsBoard(String gameName)
    {
        super(gameName);
    }
    
    /**
     * gets the data for an Othello piece at the location argument (if this location holds a piece)
     * @param location Location at which to identify a piece and for which to create a piece-graphics object
     * @return piece-graphics object representing an Othello piece
     */
    @Override
    public GraphicsPiece getGraphicsPiece(Location location)
    {
        if (location == null) return null;
        
        OthelloGraphicsPiece graphicsPiece = null;
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            int spaceWidth  = OthelloProperties.BOARD_WIDTH/OthelloProperties.NUM_COLS;
            int spaceHeight = OthelloProperties.BOARD_HEIGHT/OthelloProperties.NUM_ROWS;
            
            Piece piece = boardState.getPiece(location);
            int boardMargin = properties.get_BOARD_MARGIN();
            if ((piece != null) && (piece instanceof OthelloPiece))
                graphicsPiece = OthelloGraphicsPiece.create(piece,c,r,spaceWidth,spaceHeight,boardMargin);
        }
        
        return graphicsPiece;
    }
    
    @Override
    protected GraphicsSpace createSpace(Properties properties, Location location,int spaceWidth,int spaceHeight,Color spaceColor)
    {
        Color color = OthelloProperties.SPACE_COLOR;
        int boardMargin = properties.get_BOARD_MARGIN();
        GraphicsSpace space = new OthelloGraphicsSpace(properties,location,spaceWidth,spaceHeight,boardMargin,color);
        return space;
    }

    /**
     * draws the Othello board, its spaces, and the space contents, to the graphics object argument
     * @param g Graphics object to which to draw the Othello board and pieces
     */
    @Override
    public void paint(Graphics g)
    {
        int boardWidth  = OthelloProperties.BOARD_WIDTH;
        int boardHeight = OthelloProperties.BOARD_HEIGHT;
        int boardMargin = properties.get_BOARD_MARGIN();
        int shadowSize  = boardMargin / 4;
        
        int boardLeftX = boardMargin;
        int boardRightX = boardLeftX + boardWidth;
        int boardTopY = boardMargin;
        int boardBottomY = boardTopY + boardHeight;

        if (bufferImage == null)
        {
            bufferImage = createImage(boardWidth+2*boardMargin,boardHeight+2*boardMargin);
            bufferGraphics = bufferImage.getGraphics();
        }
        
        setSize(boardWidth+2*boardMargin,boardHeight+2*boardMargin);
        setBackground(OthelloProperties.WINDOW_BACKGROUND_COLOR);
        
        // draw background
        bufferGraphics.setColor(OthelloProperties.WINDOW_BACKGROUND_COLOR);
        bufferGraphics.fillRect(0,0,getWidth(),getHeight());
        
        // draw black shadow
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(boardLeftX+2*shadowSize,boardTopY+2*shadowSize,boardWidth,boardHeight);
        
        // draw board frame (right side)
        Polygon framePolygonRight = new Polygon();
        framePolygonRight.addPoint(boardRightX,boardTopY);
        framePolygonRight.addPoint(boardRightX+shadowSize,boardTopY+shadowSize);
        framePolygonRight.addPoint(boardRightX+shadowSize,boardBottomY+shadowSize);
        framePolygonRight.addPoint(boardRightX,boardBottomY);
        bufferGraphics.setColor(OthelloProperties.BOARD_BORDER_COLOR);
        bufferGraphics.fillPolygon(framePolygonRight);

        // draw board frame (bottom)
        Polygon framePolygonBottom = new Polygon();
        framePolygonBottom.addPoint(boardLeftX,boardBottomY);
        framePolygonBottom.addPoint(boardRightX,boardBottomY);
        framePolygonBottom.addPoint(boardRightX+shadowSize,boardBottomY+shadowSize);
        framePolygonBottom.addPoint(boardLeftX+shadowSize,boardBottomY+shadowSize);
        bufferGraphics.setColor(OthelloProperties.BOARD_BORDER_COLOR);
        bufferGraphics.fillPolygon(framePolygonBottom);

        // draw frame edges
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.drawLine(boardLeftX,boardBottomY,boardRightX,boardBottomY);
        bufferGraphics.drawLine(boardLeftX+shadowSize,boardBottomY+shadowSize,boardRightX+shadowSize,boardBottomY+shadowSize);
        bufferGraphics.drawLine(boardRightX,boardTopY,boardRightX,boardBottomY);
        bufferGraphics.drawLine(boardRightX+shadowSize,boardTopY+shadowSize,boardRightX+shadowSize,boardBottomY+shadowSize);
        
        bufferGraphics.drawLine(boardRightX,boardTopY,boardRightX+shadowSize,boardTopY+shadowSize);
        bufferGraphics.drawLine(boardRightX,boardBottomY,boardRightX+shadowSize,boardBottomY+shadowSize);
        bufferGraphics.drawLine(boardLeftX,boardBottomY,boardLeftX+shadowSize,boardBottomY+shadowSize);
        
        bufferGraphics.drawLine(boardLeftX,boardTopY,boardLeftX,boardBottomY);
        bufferGraphics.drawLine(boardLeftX,boardTopY,boardRightX,boardTopY);
        
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                Location location = Location.at(c,r);
                GraphicsPiece graphicsPiece = getGraphicsPiece(location);
                GraphicsSpace space = spaces[c][r];
                space.setContents(graphicsPiece);
                space.paint(bufferGraphics);
            }
        }
        
        g.drawImage(bufferImage,0,0,null);
    }
}
