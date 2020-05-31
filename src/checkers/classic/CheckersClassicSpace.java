package checkers.classic;

import game.boardgame.graphics.GraphicsSpace;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to paint a graphical space, it's colors, and the piece
 * located on that space, as well as the operations used to actually paint that space
 * @author devang
 */
public class CheckersClassicSpace extends GraphicsSpace {
    
    private final int boardInset;
    
    protected int applyInset(int value)
    {
        return value + boardInset;
    }

    /**
     * paints the background color/picture for this space
     * @param g graphics object on which to paint this space
     */
    @Override
    protected void paintBackground(Graphics g)
    {
        // paint plain space
        g.setColor(spaceColor);
        g.fillRect(applyInset(x*width), applyInset(y*height), width, height);

        g.setColor(CheckersClassicProperties.SPACE_BORDER_COLOR);
        g.drawRect(applyInset(x*width), applyInset(y*height), width, height);
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
     * Creates an object to hold, modify and display graphical properties for a game space and game piece
     * @param properties game properties objects from which to get color-scheme attributes
     * @param location col,row-indexed position for this graphical space
     * @param width java Canvas pixel-width dimension for a single space
     * @param height java Canvas pixel-height dimension for a single space
     * @param boardInset border size around spaces inside window
     * @param spaceColor background color (light,dark), (white,black), (red,black) for this space
     */
    public CheckersClassicSpace(Properties properties, Location location, int width, int height,int boardInset, Color spaceColor)
    {
        super(properties,location,width,height,spaceColor);
        this.boardInset = boardInset;
    }
    /**
     * Creates an object to hold, modify and display graphical properties for a game space and game piece
     * @param properties game properties objects from which to get color-scheme attributes
     * @param location col,row-indexed position for this graphical space
     * @param spaceColor background color (light,dark), (white,black), (red,black) for this space
     */
    public CheckersClassicSpace(Properties properties, Location location, Color spaceColor)
    {
        super(properties,location,spaceColor);
        this.boardInset = 0;
    }

}
