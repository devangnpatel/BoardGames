package checkers.warped;

import checkers.warped.CheckersWarper.WarpedSpace;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import java.awt.Color;
import java.awt.Graphics;

/**
 * contains information necessary to draw a King Checkers piece and its colors
 * @author devang
 */
public class WarpedKing extends CheckersWarpedPiece {

    protected WarpedKing(PlayerColor color, Location location, WarpedSpace warpedSpace)
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
        int margin = 4;
        
        int leftX = Math.max(warpedSpace.topLeft.point.x,warpedSpace.bottomLeft.point.x);
        int rightX = Math.min(warpedSpace.topRight.point.x,warpedSpace.bottomRight.point.x);
        int xCoord = Math.min(leftX,rightX);
        int pieceWidth = Math.abs(rightX-leftX);
        
        int topY = Math.max(warpedSpace.topLeft.point.y,warpedSpace.topRight.point.y);
        int bottomY = Math.min(warpedSpace.bottomLeft.point.y,warpedSpace.bottomRight.point.y);
        int yCoord = Math.min(topY,bottomY);
        int pieceHeight = Math.abs(bottomY-topY);
        
        Color screenColorPrimary;
        Color screenColorSecondary;
        Color outlineColor;

        if (getColor().equals(PlayerColor.RED))
            screenColorPrimary = warpedSpace.redPieceColor.color;
        else
            screenColorPrimary = warpedSpace.blackPieceColor.color;
        
        screenColorSecondary = warpedSpace.secondaryPieceColor.color;
        outlineColor = warpedSpace.outlinePieceColor.color;
        
        g.setColor(screenColorPrimary);
        g.fillOval(xCoord + margin, yCoord + margin, pieceWidth - margin*2, pieceHeight - margin*2);
        g.setColor(screenColorSecondary);
        g.fillOval(xCoord + 2*margin, yCoord + 2*margin, pieceWidth - margin*4, pieceHeight - margin*4);
        g.setColor(screenColorPrimary);
        g.fillOval(xCoord + 3*margin, yCoord + 3*margin, pieceWidth - margin*6, pieceHeight - margin*6);
        g.setColor(screenColorSecondary);
        g.fillOval(xCoord + 4*margin, yCoord + 4*margin, pieceWidth - margin*8, pieceHeight - margin*8);

        g.setColor(outlineColor);
        g.drawOval(xCoord + margin, yCoord + margin,pieceWidth - margin*2, pieceHeight - margin*2);
    }
}
