package othello.gui.debugmode;

import othello.gui.OthelloGraphicsSpace;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw Othello board space, colors, and its contents
 * @author devang
 */
public class OthelloDebugGraphicsSpace extends OthelloGraphicsSpace {
    private OthelloDebugGraphicsPiece contents;
    
    /**
     * draws the Othello board Space, and its contents, to the graphics object argument
     * @param g Graphics object to which to draw this Space and contents
     */
    @Override    
    public void paint(Graphics g)
    {
        paintBackground(g);
        paintHighlight(g);
        paintSelected(g);
        paintHover(g);
        paintContents(g);
    }

    /**
     * draws the background of this Space, and it's border
     * @param g Graphics object to which to draw the background of this space
     */
    @Override    
    public void paintBackground(Graphics g)
    {
        int borderPixelSize = 0;
        
        // paint plain space
        g.setColor(OthelloDebugProperties.SPACE_COLOR);
        g.fillRect(applyInset(x*width)+borderPixelSize, applyInset(y*height)+borderPixelSize, width - 2*borderPixelSize, height - 2*borderPixelSize);

        // paint plain space outline
        g.setColor(OthelloDebugProperties.SPACE_BORDER_COLOR);
        g.drawRect(applyInset(x*width), applyInset(y*height), width, height);


    }
    
    /**
     * constructor for this space, it's location, dimensions, and color-scheme
     * @param properties Othello game properties from which to extract color-scheme attributes
     * @param location col,row for this space on an Othello board
     * @param width pixel width for this space
     * @param height pixel height for this space
     * @param boardInset indentation/margin around the board from the edge of the canvas
     * @param spaceColor general color for this space (light-dark), (red,black), (white,black)
     */
    public OthelloDebugGraphicsSpace(Properties properties, Location location, int width, int height, int boardInset, Color spaceColor)
    {
        super(properties,location,width,height,boardInset,spaceColor);
    }
}
