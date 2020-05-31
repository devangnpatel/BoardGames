package checkers.warped;

import checkers.warped.CheckersWarper.WarpedSpace;
import game.boardgame.graphics.GraphicsSpace;
import game.utility.Location;
import game.utility.Properties;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 * contains information necessary to paint a warped Checkers space, it's colors, and the piece
 * located on that space, as well as the operations used to actually paint that space
 * @author devang
 */
public class CheckersWarpedSpace extends GraphicsSpace {

    protected final Location location;
    public WarpedSpace warpedSpace;
            
    /**
     * Creates an object to hold, modify and display graphical properties for a game space and game piece
     * @param properties game properties objects from which to get color-scheme attributes
     * @param location col,row-indexed position for this graphical space
     * @param warpedSpace warping attributes for drawing space
     */
    public CheckersWarpedSpace(Properties properties, Location location, WarpedSpace warpedSpace)
    {
        super(properties,location,warpedSpace.topRight.point.x-warpedSpace.topLeft.point.x,warpedSpace.bottomLeft.point.y-warpedSpace.topLeft.point.y,warpedSpace.spaceColor.color);
        this.warpedSpace = warpedSpace;
        this.location = location;
        this.x = Location.getCol(location);
        this.y = Location.getRow(location);
        
        /*setSpaceColor(warpedSpace.spaceColor.color);
        setHighlightedColor(warpedSpace.highlightedColor.color);
        setHighlightedBorderColor(warpedSpace.highlightedBorderColor.color);
        setHoveredOverColor(warpedSpace.hoveredOverColor.color);
        setHoveredOverBorderColor(warpedSpace.hoveredOverBorderColor.color);
        setSelectedColor(warpedSpace.selectedColor.color);
        setSelectedBorderColor(warpedSpace.selectedBorderColor.color);*/

        isSelected    = false;
        isHighlighted = false;
        isHoveredOver = false;
        contents      = null;
    }
    
    /**
     * paints the piece at this space, if there is one
     * @param g graphics object on which to paint this space piece contents
     */
    @Override
    protected void paintContents(Graphics g)
    {
        // if the space has contents
        if (contents != null)
            ((CheckersWarpedPiece)contents).paint(g);
    }
    
    /**
     * paints the background color/picture for this space
     * @param g graphics object on which to paint this space
     */
    @Override
    protected void paintBackground(Graphics g)
    {
        Polygon polygon = new Polygon();
        polygon.addPoint(warpedSpace.topLeft.point.x,warpedSpace.topLeft.point.y);
        polygon.addPoint(warpedSpace.topRight.point.x,warpedSpace.topRight.point.y);
        polygon.addPoint(warpedSpace.bottomRight.point.x,warpedSpace.bottomRight.point.y);
        polygon.addPoint(warpedSpace.bottomLeft.point.x,warpedSpace.bottomLeft.point.y);
        
        // paint plain space
        g.setColor(warpedSpace.spaceColor.color);
        g.fillPolygon(polygon);
    }
    
    /**
     * paints the color-scheme for a selected space, if this space is currently selected
     * @param g graphics object on which to paint selected-status color-scheme
     */
    @Override
    protected void paintSelected(Graphics g)
    {
        if (isSelected())
        {        
            Polygon polygon = new Polygon();
            polygon.addPoint(warpedSpace.topLeft.point.x,warpedSpace.topLeft.point.y);
            polygon.addPoint(warpedSpace.topRight.point.x,warpedSpace.topRight.point.y);
            polygon.addPoint(warpedSpace.bottomRight.point.x,warpedSpace.bottomRight.point.y);
            polygon.addPoint(warpedSpace.bottomLeft.point.x,warpedSpace.bottomLeft.point.y);

            // paint highlighted space: inset from border
            g.setColor(warpedSpace.selectedColor.color);
            g.fillPolygon(polygon);

            // paint highlighted space: border with inset
            g.setColor(warpedSpace.selectedBorderColor.color);
            g.drawPolygon(polygon);

        }
    }
    
    /**
     * paints the color-scheme for a highlighted space, if this space is currently highlighted
     * @param g graphics object on which to paint highlighted-status color-scheme
     */
    @Override
    protected void paintHighlight(Graphics g)
    {
        if (isHighlighted())
        {
            Polygon polygon = new Polygon();
            polygon.addPoint(warpedSpace.topLeft.point.x,warpedSpace.topLeft.point.y);
            polygon.addPoint(warpedSpace.topRight.point.x,warpedSpace.topRight.point.y);
            polygon.addPoint(warpedSpace.bottomRight.point.x,warpedSpace.bottomRight.point.y);
            polygon.addPoint(warpedSpace.bottomLeft.point.x,warpedSpace.bottomLeft.point.y);
            
            // paint highlighted space: inset from border
            g.setColor(warpedSpace.highlightedColor.color);
            g.fillPolygon(polygon);

            // paint highlighted space: border with inset
            g.setColor(warpedSpace.highlightedBorderColor.color);
            g.drawPolygon(polygon);
        }
    }
    
    /**
     * paints the color-scheme for a hovered-over space, if this space is currently hovered-over
     * @param g graphics object on which to paint hovered-over-status color-scheme
     */
    @Override
    protected void paintHover(Graphics g)
    {
        if (isHoveredOver())
        {
            Polygon polygon = new Polygon();
            polygon.addPoint(warpedSpace.topLeft.point.x,warpedSpace.topLeft.point.y);
            polygon.addPoint(warpedSpace.topRight.point.x,warpedSpace.topRight.point.y);
            polygon.addPoint(warpedSpace.bottomRight.point.x,warpedSpace.bottomRight.point.y);
            polygon.addPoint(warpedSpace.bottomLeft.point.x,warpedSpace.bottomLeft.point.y);
            
            // paint highlighted space: inset from border
            g.setColor(warpedSpace.hoveredOverColor.color);
            g.fillPolygon(polygon);
            
            // paint highlighted space: border with inset
            g.setColor(warpedSpace.hoveredOverBorderColor.color);
            g.drawPolygon(polygon);

        }
    }

}
