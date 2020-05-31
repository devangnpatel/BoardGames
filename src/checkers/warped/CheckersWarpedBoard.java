package checkers.warped;

import checkers.gui.CheckersGraphicsBoard;
import checkers.warped.CheckersWarper.WarpedSpace;
import game.boardgame.BoardState;
import game.boardgame.graphics.GraphicsPiece;
import game.boardgame.graphics.GraphicsSpace;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * contains information necessary to draw a Checker board, its spaces, and the pieces
 * @author devang
 */
public class CheckersWarpedBoard extends CheckersGraphicsBoard {
    BufferedImage         bufferedImage;
    Thread                animator;
    public CheckersWarper warper;
    
    /**
     * creates and initializes a Checkers game board
     * @param gameName String of game name to put at top of window
     */
    public CheckersWarpedBoard(String gameName)
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
        
        int width = properties.get_BOARD_WIDTH() + 2*((CheckersWarpedProperties)properties).get_BOARD_BORDER();;
        int height = properties.get_BOARD_HEIGHT() + 2*((CheckersWarpedProperties)properties).get_BOARD_BORDER();;
        
        setBackground(Color.MAGENTA);
        
        Dimension size = getSize();
        bufferedImage = new BufferedImage(width, height,
                                          BufferedImage.TYPE_INT_ARGB);
        
        setSize(width,height);

        //setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        
        animator = new Thread(new Animator(this,warper));
        animator.start();
    }
    
    /**
     * destroys animation timer
     */
    @Override
    public void dispose()
    {
        animator.stop();
        super.dispose();
    }
    
    /**
     * gets the data for a Checkers piece at the location argument (if this location holds a piece)
     * @param location Location at which to identify a piece and for which to create a piece-graphics object
     * @return piece-graphics object representing a Checkers piece
     */
    @Override
    public GraphicsPiece getGraphicsPiece(Location location)
    {
        if (location == null) return null;
        
        CheckersWarpedPiece graphicsPiece = null;
        if (!boardState.isEmpty(location))
        {
            int c = Location.getCol(location);
            int r = Location.getRow(location);
            Piece piece = boardState.getPiece(location);
            WarpedSpace warpedSpace = warper.getWarpedSpace(location);
            
            if (piece != null)
            {
                graphicsPiece = CheckersWarpedPiece.create(piece, location, warpedSpace);
            }

        }
        
        return graphicsPiece;
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
        return warper.getLocationOfSpaceAt(x,y);
    }
    
    @Override
    protected void initializeSpaces()
    {
        spaces = new GraphicsSpace[Properties.NUM_COLS][Properties.NUM_ROWS];
        warper = new CheckersWarper(properties);
        
        for (int col = 0;col<properties.NUM_COLS;col++)
        {
            for (int row = 0;row<properties.NUM_ROWS;row++)
            {
                Location location = Location.of(col,row);
                WarpedSpace warpedSpace = warper.getWarpedSpace(location);
                spaces[col][row] = new CheckersWarpedSpace(properties,location,warpedSpace);
            }
        }
        /*Thread animator = new Thread(new Animator(this,warper));
        animator.start();*/
    }

    public void update(Graphics g)
    {
        drawBufferedImage();
        g.drawImage(bufferedImage, 0, 0, null);
    }
    
    public void drawBufferedImage()
    {
        Graphics g = bufferedImage.getGraphics();
                
        g.setColor(Color.MAGENTA);
        g.fillRect(0,0,getWidth(),getHeight());
        
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                Location location = Location.at(c,r);
                CheckersWarpedPiece graphicsPiece = (CheckersWarpedPiece)getGraphicsPiece(location);
                CheckersWarpedSpace space = (CheckersWarpedSpace)spaces[c][r];
                
                space.setContents(graphicsPiece);
                space.paint(g);
            }
        }
        
        warper.paint(g);
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

}
