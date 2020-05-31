package othello.original;

import game.boardgame.graphics.GraphicsSpace;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw Othello board space, colors, and its contents
 * @author devang
 */
public class OthelloOriginalSpace extends GraphicsSpace {
    //private OthelloOriginalPiece contents;
    
    private final int boardInset;
    
    protected int applyInset(int value)
    {
        return value + boardInset;
    }
    
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
        int borderPixelSize = 1;
        
        // paint plain space outline
        g.setColor(OthelloOriginalProperties.SPACE_BORDER_COLOR);
        g.fillRect(applyInset(x*width), applyInset(y*height), width, height);

        // paint plain space
        g.setColor(OthelloOriginalProperties.SPACE_COLOR);
        g.fillRect(applyInset(x*width)+borderPixelSize, applyInset(y*height)+borderPixelSize, width - 2*borderPixelSize, height - 2*borderPixelSize);
    }

    /**
     * draws the this Space with color-scheme if this Space is selected in the GUI
     * @param g Graphics object to which to draw the selected-space color-scheme
     */
    @Override    
    public void paintSelected(Graphics g)
    {
        if (isSelected())
        {
            // paint highlighted space: border with inset
            g.setColor(selectedBorderColor);
            g.fillRect(applyInset(x*width), applyInset(y*height), width, height);

            // paint highlighted space: inset from border
            int outline = 1;
            g.setColor(selectedColor);
            g.fillRect(applyInset(x*width + outline), applyInset(y*height + outline), width - outline*2, height - outline*2);
        }
    }

    /**
     * draws the this Space with color-scheme if this Space is highlighted in the GUI
     * @param g Graphics object to which to draw the highlighted-space color-scheme
     */
    @Override    
    public void paintHighlight(Graphics g)
    {
        if (isHighlighted())
        {
            // paint highlighted space: border with inset
            g.setColor(highlightedBorderColor);
            g.fillRect(applyInset(x*width), applyInset(y*height), width, height);

            // paint highlighted space: inset from border
            int outline = 1;
            g.setColor(highlightedColor);
            g.fillRect(applyInset(x*width) + outline, applyInset(y*height) + outline, width - outline*2, height - outline*2);
        }
    }
    
    /**
     * draws the this Space with color-scheme if this Space is hovered-over in the GUI
     * @param g Graphics object to which to draw the hovered-over-space color-scheme
     */
    @Override
    public void paintHover(Graphics g)
    {
        if (isHoveredOver())
        {
            // paint highlighted space: border with inset
            g.setColor(hoveredOverBorderColor);
            g.fillRect(applyInset(x*width), applyInset(y*height), width, height);

            // paint highlighted space: inset from border
            int outline = 1;
            g.setColor(hoveredOverColor);
            g.fillRect(applyInset(x*width) + outline, applyInset(y*height) + outline, width - outline*2, height - outline*2);
        }
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
    public OthelloOriginalSpace(Properties properties, Location location, int width, int height, int boardInset, Color spaceColor)
    {
        super(properties,location,width,height,spaceColor);
        this.boardInset = boardInset;
    }
}
