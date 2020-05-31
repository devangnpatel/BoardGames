package checkers.warped;

import checkers.warped.CheckersWarper.WarpedSpace;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw a regular Checkers piece and its colors
 * @author devang
 */
public class WarpedRegular extends CheckersWarpedPiece {
    
    protected WarpedRegular(PlayerColor color, Location location, WarpedSpace warpedSpace)
    {
        super(color,location,warpedSpace);
    }
    
    /**
     * paints this piece to the graphics object in arguments
     * @param g Graphics object on which to draw this piece
     */
    @Override
    public void paint(Graphics g)
    {
        int margin = 5;
        
        int leftX = Math.max(warpedSpace.topLeft.point.x,warpedSpace.bottomLeft.point.x);
        int rightX = Math.min(warpedSpace.topRight.point.x,warpedSpace.bottomRight.point.x);
        int xCoord = Math.min(leftX,rightX);
        int pieceWidth = Math.abs(rightX-leftX) - 2*margin;
        
        int topY = Math.max(warpedSpace.topLeft.point.y,warpedSpace.topRight.point.y);
        int bottomY = Math.min(warpedSpace.bottomLeft.point.y,warpedSpace.bottomRight.point.y);
        int yCoord = Math.min(topY,bottomY);
        int pieceHeight = Math.abs(bottomY-topY) - 2*margin;
        
        Color screenColorPrimary;
        Color outlineColor;

        if (getColor().equals(PlayerColor.RED))
            screenColorPrimary = warpedSpace.redPieceColor.color;
        else
            screenColorPrimary = warpedSpace.blackPieceColor.color;
        
        outlineColor = warpedSpace.outlinePieceColor.color;
        
        g.setColor(screenColorPrimary);
        g.fillOval(xCoord + margin,yCoord + margin,pieceWidth,pieceHeight);

        g.setColor(outlineColor);
        g.drawOval(xCoord + margin,yCoord + margin,pieceWidth,pieceHeight);

    }
}
