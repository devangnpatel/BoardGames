package game.boardgame.graphics;

import game.boardgame.BoardState;
import game.utility.Location;
import game.utility.Properties;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * contains information necessary to paint a game board, graphical space, it's colors, and the pieces
 * located on those spaces, as well as the operations used to actually paint 
 * the board, spaces and pieces
 * @author devang
 */
public abstract class GraphicsBoard extends Canvas {   
    protected abstract GraphicsPiece getGraphicsPiece(Location location);
    
    protected BoardState boardState;
    protected Properties properties;
    
    protected Frame             frame;
    protected GraphicsSpace[][] spaces;
    protected Location          hoveredSpace;
    protected Location          selectedSpace;
    
    /**
     * destroys this canvas and frame so a game can officially be terminated
     */
    public void dispose()
    {
        spaces        = null;
        boardState    = null;
        properties    = null;
        hoveredSpace  = null;
        selectedSpace = null;

        frame.dispose();
    }
    
    /**
     * initializes the java frame and this gui object:
     * a player needs a reference to this gui, but this gui finalizes initialization
     * after the players are initialized, so after this constructor and the players
     * are initialized, then the game class must call "init" in this class
     * @param gameName string used to display in the title bar of a frame
     */
    public GraphicsBoard(String gameName)
    {
        frame = new Frame(gameName);
        frame.setIgnoreRepaint(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                frame.dispose();
            }
        });
        int xLocation = 50 + (int)Math.floor(Math.random()*600);
        int yLocation = 100 + (int)Math.floor(Math.random()*500);
        frame.setLocation(xLocation,yLocation);
        // frame.add(this);
        // frame.pack();
        frame.setResizable(false);
        frame.setVisible(false);
    }
    
    /**
     * initializes the java drawing canvas, it's properties and all 
     * state-information for a game board, and then adds this canvas to a Frame
     * @param boardState the official current board state of a game
     * @param properties the initialize properties (color, dimensions, directions) necessary for a game
     */
    public void init(BoardState boardState, Properties properties)
    {
        this.boardState = boardState;
        this.properties = properties;
        
        initializeSpaces();
        
        setHoveredSpace(Location.of(5,5));
        setSelectedSpace(Location.of(5,5));
        removeSelections();
        
        int width = properties.get_BOARD_WIDTH() + 2*properties.get_BOARD_MARGIN();
        int height = properties.get_BOARD_HEIGHT() + 2*properties.get_BOARD_MARGIN();
        setSize(width,height);
        
        //setBackground(Color.LIGHT_GRAY);
        setVisible(true);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * sets the space at the given location to highlighted state
     * @param location location of space that should be highlighted
     */
    public void setHighlightedSpace(Location location)
    {
        int x = Location.getCol(location);
        int y = Location.getRow(location);
        spaces[x][y].setHighlighted(true);
    }
    
    /**
     * removes highlighted status from all spaces
     */
    public void removeHighlights()
    {
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                if (spaces != null && spaces[c][r] != null)
                    spaces[c][r].setHighlighted(false);
            }
        }
    }
    
    /**
     * sets the space at the given location to hovered-over state
     * @param newLocation location of space that should display hovered-over color-scheme
     */
    public void setHoveredSpace(Location newLocation)
    {
        removeHovers();
        hoveredSpace = newLocation;
        int x = Location.getCol(hoveredSpace);
        int y = Location.getRow(hoveredSpace);
        if (spaces != null && spaces[x][y] != null)
            spaces[x][y].setHoveredOver(true);
    }
    
    /**
     * removes hovered-over status from all spaces
     */
    public void removeHovers()
    {
        hoveredSpace = null;
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                if (spaces != null && spaces[c][r] != null)
                    spaces[c][r].setHoveredOver(false);
            }
        }
    }
    
    /**
     * sets the space at the given location to selected state
     * @param newLocation location of space that should display selected-state color-scheme
     */
    public void setSelectedSpace(Location newLocation)
    {
        
        removeSelections();
        selectedSpace = newLocation;
        int x = Location.getCol(selectedSpace);
        int y = Location.getRow(selectedSpace);
        if (spaces != null && spaces[x][y] != null)
            spaces[x][y].setSelected(true);
    }
    
    /**
     * remove selected-state status from all spaces
     */
    public void removeSelections()
    {
        selectedSpace = null;
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                if (spaces != null && spaces[c][r] != null)
                    spaces[c][r].setSelected(false);
            }
        }
    }
    
    /**
     * gets the currently hovered-over space
     * @return location of currently hovered-over space
     */
    public Location getHoveredSpace()
    {
        return hoveredSpace;
    }
    
    /**
     * gets the currently selected space
     * @return location of currently selected space
     */
    public Location getSelectedSpace()
    {
        return selectedSpace;
    }
    
    protected void initializeSpaces()
    {
        spaces = new GraphicsSpace[Properties.NUM_COLS][Properties.NUM_ROWS];
        
        int spaceWidth = properties.get_BOARD_WIDTH()/Properties.NUM_COLS;
        int spaceHeight = properties.get_BOARD_HEIGHT()/Properties.NUM_ROWS;
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
                spaces[col][row] = createSpace(properties,location,spaceWidth,spaceHeight,spaceColor);
            }
        }
    }
    
    protected GraphicsSpace createSpace(Properties properties, Location location,int spaceWidth,int spaceHeight,Color spaceColor)
    {
        GraphicsSpace space = new GraphicsSpace(properties,location,spaceWidth,spaceHeight,spaceColor);
        return space;
    }

    /**
     * paints this board, its spaces and the contents of the space to this canvas
     * @param g graphics object on which to paint this space
     */
    @Override
    public void paint(Graphics g)
    {
        for (int c = 0; c < properties.NUM_COLS; c++)
        {
            for (int r = 0; r < properties.NUM_ROWS; r++)
            {
                Location location = Location.at(c,r);
                GraphicsPiece graphicsPiece = getGraphicsPiece(location);
                GraphicsSpace space = spaces[c][r];
                space.setContents(graphicsPiece);
                space.paint(g);
            }
        }
    }
    
    /**
     * gets the Space that contains the input (x,y) coordinate
     * @param x input x of coordinate
     * @param y input y of coordinate
     * @return GraphicsSpace that contains the x,y coordinate
     */
    public Location getLocationOfSpaceAt(int x, int y)
    {
        int boardMargin = properties.get_BOARD_MARGIN();
        for (int c=0;c<properties.NUM_COLS;c++)
        {
            for (int r=0;r<properties.NUM_ROWS;r++)
            {
                Location location = Location.of(c,r);
                
                GraphicsSpace space = spaces[c][r];
                
                int spaceX = space.x;
                int spaceY = space.y;
                int spaceWidth = space.width;
                int spaceHeight = space.height;
                
                if ((boardMargin + (spaceX*spaceWidth))     < x &&
                    (boardMargin + (spaceX+1)*spaceWidth)   > x &&
                    (boardMargin + (spaceY*spaceHeight))    < y &&
                    (boardMargin + ((spaceY+1)*spaceHeight) > y))
                {
                    return location;
                }
            }
        }
        return null;
    }
}
