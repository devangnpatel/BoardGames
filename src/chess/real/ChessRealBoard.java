package chess.real;

import chess.gui.ChessGraphicsBoard;
import chess.pieces.ChessPiece;
import game.boardgame.BoardState;
import game.boardgame.graphics.GraphicsSpace;
import game.boardgame.pieces.Piece;
import game.utility.Geometry;
import game.utility.Location;
import game.utility.Point;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * contains information necessary to draw a Chess board, its spaces, and the pieces
 * @author devang
 */
public class ChessRealBoard extends ChessGraphicsBoard {
    
    private ChessPhotos   photos;
    private BufferedImage bufferedImage;
    
    /**
     * creates and initializes a Chess game board
     * @param gameName String name to place at top of game window
     */
    public ChessRealBoard(String gameName)
    {
        super(gameName);
    }
    
    /**
     * initializes the java drawing canvas, it's properties and all 
     * state-information for a game board, and then adds this canvas to a Frame
     * @param boardState the official current board state of a game
     * @param properties the initialize properties (color, dimensions, directions) necessary for a game
     */
    @Override
    public void init(BoardState boardState, Properties properties)
    {
        this.boardState = boardState;
        this.properties = properties;
        
        initializeSpaces();
        
        setHoveredSpace(Location.of(5,5));
        setSelectedSpace(Location.of(5,5));
        removeSelections();
        
        int width = properties.get_BOARD_WIDTH();
        int height = properties.get_BOARD_HEIGHT();
        setSize(width-190,height-110);
        
        Dimension size = getSize();
        
        bufferedImage = new BufferedImage((int)size.getWidth(),
                                             (int)size.getHeight(),
                                             BufferedImage.TYPE_INT_ARGB);
        
        //setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * gets the data for a Chess piece at the location argument (if this location holds a piece)
     * @param location Location at which to identify a piece and for which to create a piece-graphics object
     * @return piece-graphics object representing a Chess piece
     */
    @Override
    public ChessRealPiece getGraphicsPiece(Location location)
    {
        if (location == null) return null;
        
        ChessRealPiece graphicsPiece = null;
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            
            Piece piece = boardState.getPiece(location);
            if ((piece != null) && (piece instanceof ChessPiece))
                graphicsPiece = ChessRealPiece.create(photos,piece,c,r);
        }
        
        return graphicsPiece;
    }
    
    private ChessRealSpace getGraphicsSpace(GraphicsSpace space)
    {
        return (ChessRealSpace)space;
    }
    
    private void drawBufferedImage()
    {
        
        Graphics g = bufferedImage.getGraphics();
        
        paintBackgroundPhoto(g);
        paintBoardBackground(g);
        
        updatePieces();
        
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                ChessRealSpace space = getGraphicsSpace(spaces[c][r]);
                space.paintCloudyBackground(g);
            }
        }    
        
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                ChessRealSpace space = getGraphicsSpace(spaces[c][r]);
                space.paint(g);
            }
        }
        
        g.dispose();
    }
    
    /**
     * paints this board, its spaces and the contents of the space to this canvas
     * @param g graphics object on which to paint this space
     */
    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    @Override
    public void update(Graphics g)
    {
        drawBufferedImage();
        g.drawImage(bufferedImage, 0, 0, null);
    }
    
    /**
     * paints the Chess Board (real photo) to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    public void paintBoardBackground(Graphics g)
    {
        BufferedImage boardImage = photos.getBoardImage();
        g.drawImage(boardImage,0,0,null);
    }
    
    /**
     * paints the Chess Board (real photo) to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    public void paintBackgroundPhoto(Graphics g)
    {
        BufferedImage backgroundImage = photos.getBackgroundImage();
        g.drawImage(backgroundImage,0,0,null);
    }
    
    /**
     * gets the Space that contains the input (x,y) coordinate
     * @param x input x of coordinate
     * @param y input y of coordinate
     * @return GraphicsSpace that contains the x,y coordinate
     */
    @Override
    public Location getLocationOfSpaceAt(int x, int y)
    {
        Point p = new Point(x,y);
        
        for (int c=0;c<properties.NUM_COLS;c++)
        {
            for (int r=0;r<properties.NUM_ROWS;r++)
            {
                Location location = Location.of(c,r);
                
                ChessRealSpace space = (ChessRealSpace)spaces[c][r];
                Point tl = space.topLft;
                Point tr = space.topRgt;
                Point br = space.botRgt;
                Point bl = space.botLft;
                
                if (Geometry.isBelow(p,tl,tr) && Geometry.isAbove(p,bl,br) && Geometry.isToRight(p,tl,bl) && Geometry.isToLeft(p,tr,br))
                {
                    return location;
                }
            }
        }
        return null;
    }
    
    @Override
    protected void initializeSpaces()
    {
        spaces = new GraphicsSpace[Properties.NUM_COLS][Properties.NUM_ROWS];
        photos = new ChessPhotos();
        
        for (int col = 0; col < properties.NUM_COLS; col++)
        {
            for (int row = 0; row < properties.NUM_ROWS; row++)
            {
                Color spaceColor;
                if ((((col % 2) == 0) && ((row % 2) == 0)) || (((col % 2) == 1) && ((row % 2) == 1)))
                {
                    spaceColor = properties.get_LIGHT_SPACE_COLOR();
                }
                else // ((((w % 2) == 1) && ((h % 2) == 0)) || (((w % 2) == 1) && ((h % 2) == 0)))
                {
                    spaceColor = properties.get_DARK_SPACE_COLOR();
                }
                Location location = Location.of(col,row);
                spaces[col][row] = createSpace(properties,location,spaceColor);
            }
        }
    }

    protected GraphicsSpace createSpace(Properties properties, Location location,Color spaceColor)
    {
        GraphicsSpace space = new ChessRealSpace(properties,location,spaceColor);
        return space;
    }
    
    /**
     * updates the pieces and the contents of the spaces locations
     */
    public void updatePieces()
    {
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                Location location = Location.at(c,r);
                ChessRealPiece graphicsPiece = getGraphicsPiece(location);
                ChessRealSpace space = getGraphicsSpace(spaces[c][r]);
                space.setContents(graphicsPiece);
                // space.paint(g);
            }
        }
    }
    
    public ChessRealPiece getPieceAt(Location location)
    {
        ChessRealPiece graphicsPiece = getGraphicsPiece(location);
        return graphicsPiece;
    }
}
