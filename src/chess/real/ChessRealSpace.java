package chess.real;

import game.boardgame.graphics.GraphicsSpace;
import game.utility.Location;
import game.utility.Point;
import game.utility.Properties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * contains information necessary to paint a graphical space, it's colors, and the piece
 * located on that space, as well as the operations used to actually paint that space
 * @author devang
 */
public class ChessRealSpace extends GraphicsSpace {
    public Point topLft;
    public Point topRgt;
    public Point botLft;
    public Point botRgt;
    
    private final int randomInset = 16;
    
    /**
     * paints this space and its contents to a canvas
     * @param g graphics object on which to paint this space
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
     * paints the piece at this space, if there is one
     * - and paints a grayed out shadow effect at the base of the piece
     * @param g graphics object on which to paint this space piece contents
     */
    @Override
    protected void paintContents(Graphics g)
    {
        // if the space has contents
        if ((contents != null) && (contents instanceof ChessRealPiece))
            ((ChessRealPiece)contents).paint(g,topLft,topRgt,botRgt,botLft);
    }
    
    /**
     * paints the background color/picture for this space
     * @param g graphics object on which to paint this space
     */
    @Override
    protected void paintBackground(Graphics g)
    {
        // does nothing in this subclass
    }
    
    public void paintCloudyBackground(Graphics g)
    {
        // paint a randomized underlay
        if (isSelected() || isHighlighted() || isHoveredOver())
            paintRandomMargin(g);
    }
    
    private int outline(int scale)
    {
        int inset = randomInset + scale;
        return (int)Math.floor(Math.random()*inset - inset/2);
    }
    
    private void paintRandomMargin(Graphics g)
    {
        // paint grayed out square underlay
        for (int i=0;i<75;i++)
        {
            int alpha = 5; // 50% transparent
            Color grayedColor = new Color(0, 0, 0, alpha);
            int[] xGrayed = {topLft.x + outline(2),topRgt.x + outline(6),botRgt.x + outline(8),botLft.x + outline(4)};
            int[] yGrayed = {topLft.y + outline(2),topRgt.y + outline(6),botRgt.y + outline(8),botLft.y + outline(4)};
            g.setColor(grayedColor);
            g.fillPolygon(xGrayed,yGrayed,4);
        }
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
            // x,y coordinates for this space
            int[] xCoordinates = {topLft.x,topRgt.x,botRgt.x,botLft.x};
            int[] yCoordinates = {topLft.y,topRgt.y,botRgt.y,botLft.y};
                        
            // paint selected space: filled color
            g.setColor(selectedColor);
            g.fillPolygon(xCoordinates,yCoordinates,4);

            // paint selected space border
            g.setColor(selectedBorderColor);
            ((Graphics2D)g).setStroke(new BasicStroke(2));
            g.drawPolygon(xCoordinates,yCoordinates,4);
            ((Graphics2D)g).setStroke(new BasicStroke(1));
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
            // x,y coordinates for this space
            int[] xCoordinates = {topLft.x,topRgt.x,botRgt.x,botLft.x};
            int[] yCoordinates = {topLft.y,topRgt.y,botRgt.y,botLft.y};
            
            // paint highlighted space: border with inset
            g.setColor(highlightedColor);
            g.fillPolygon(xCoordinates,yCoordinates,4);

            // paint highlighted space: inset from border
            g.setColor(highlightedBorderColor);
            ((Graphics2D)g).setStroke(new BasicStroke(2));
            g.drawPolygon(xCoordinates,yCoordinates,4);
            ((Graphics2D)g).setStroke(new BasicStroke(1));
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
            // x,y coordinates for this space
            int[] xCoordinates = {topLft.x,topRgt.x,botRgt.x,botLft.x};
            int[] yCoordinates = {topLft.y,topRgt.y,botRgt.y,botLft.y};
            
            // paint highlighted space: border with inset
            g.setColor(hoveredOverColor);
            g.fillPolygon(xCoordinates,yCoordinates,4);

            // paint highlighted space: inset from border
            g.setColor(hoveredOverBorderColor);
            ((Graphics2D)g).setStroke(new BasicStroke(2));
            g.drawPolygon(xCoordinates,yCoordinates,4);
            ((Graphics2D)g).setStroke(new BasicStroke(1));
        }
    }
        
    /**
     * Creates an object to hold, modify and display graphical properties for a game space and game piece
     * @param properties game properties objects from which to get color-scheme attributes
     * @param location col,row-indexed position for this graphical space
     * @param spaceColor background color (light,dark), (white,black), (red,black) for this space
     */
    public ChessRealSpace(Properties properties, Location location, Color spaceColor)
    {
        super(properties,location,spaceColor);
        
        topLft = ChessPhotos.getTopLeftCoordinate(location);
        topRgt = ChessPhotos.getTopRightCoordinate(location);
        botRgt = ChessPhotos.getBottomRightCoordinate(location);
        botLft = ChessPhotos.getBottomLeftCoordinate(location);
    }
    
}
