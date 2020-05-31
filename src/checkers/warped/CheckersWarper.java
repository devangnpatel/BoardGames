package checkers.warped;

import game.utility.Geometry;
import game.utility.Location;
import game.utility.Point;
import game.utility.Properties;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author devang
 */
public class CheckersWarper {

    int marginColor = 16;
    int marginPixel = 5;
    
    double dMarginColor = 6.0;
    double dMarginPixel = 3.0;
    
    int iMarginColor = 10;
    int iMarginPixel = 5;
    
    public class HorizontalBorderSegment extends BorderSegment {
        Corner c1,c2;
        public HorizontalBorderSegment(Corner c1, Corner c2)
        {
            super();
            this.c1 = c1;
            this.c2 = c2;
        }
                
        public void paint(Graphics g)
        {
            Point p1 = c1.point;
            Point p2 = c2.point;

            int width = Math.abs(p1.x - p2.x);
            int height = Math.abs(p1.y - p2.y);
            
            int x,y;
            
            int startAngle,arcAngle;
            
            Point topPoint,bottomPoint;
            Point leftPoint,rightPoint;
            
            if (p1.x > p2.x)
            {
                leftPoint = p2;
                rightPoint = p1;
            }
            else
            {
                leftPoint = p1;
                rightPoint = p2;
            }
            
            if (leftPoint.y < rightPoint.y)
            {
                startAngle = 180;
                arcAngle = 90;
                x = leftPoint.x - width/2;
                y = leftPoint.y - height/2;
            }
            else
            {
                startAngle = 0;
                arcAngle = -90;
                x = leftPoint.x - width/2;
                y = leftPoint.y - height/2;
            }
                    
            height+=bottomOffset;
            y+=topOffset;
            width+=rightOffset;
            x+=leftOffset;

            g.drawArc(x,y,width*2,height,startAngle,arcAngle);
            
        }

    }
    
    public class VerticalBorderSegment extends BorderSegment {
        Corner c1,c2;
        public VerticalBorderSegment(Corner c1, Corner c2)
        {
            super();
            this.c1 = c1;
            this.c2 = c2;
        }
                
        public void paint(Graphics g)
        {
            Point p1 = c1.point;
            Point p2 = c2.point;
            
            int width = Math.abs(p1.x - p2.x);
            int height = Math.abs(p1.y - p2.y);
            
            int x,y;
            
            int startAngle,arcAngle;
            
            Point topPoint,bottomPoint;
            Point leftPoint,rightPoint;
            
            if (p1.y > p2.y)
            {
                topPoint = p2;
                bottomPoint = p1;
            }
            else
            {
                topPoint = p1;
                bottomPoint = p2;
            }
            
            if (topPoint.x > bottomPoint.x)
            {
                startAngle = 0;
                arcAngle = -90;
                x = topPoint.x - width/2;
                y = topPoint.y - height/2;
            }
            else
            {
                startAngle = 180;
                arcAngle = 90;
                x = topPoint.x + width/2;
                y = topPoint.y - height/2;
            }
            
            height+=bottomOffset;
            y+=topOffset;
            width+=rightOffset;
            x+=leftOffset;
            
            g.drawArc(x,y,width,height*2,startAngle,arcAngle);
            
        }
    }
    
    public abstract class BorderSegment {
        int topOffset,bottomOffset;
        int leftOffset,rightOffset;
        
        int maxOffset,minOffset;
        int dTop,dBottom;
        int dLeft,dRight;
        
        public abstract void paint(Graphics g);
        
        public BorderSegment()
        {
            minOffset = -5;
            maxOffset = 5;
            
            topOffset = randomSign()*(int)Math.floor(Math.random()*5.0);
            bottomOffset = randomSign()*(int)Math.floor(Math.random()*5.0);
            leftOffset = randomSign()*(int)Math.floor(Math.random()*5.0);
            rightOffset = randomSign()*(int)Math.floor(Math.random()*5.0);
            
            dTop = randomSign();
            dBottom = randomSign();
            dLeft = randomSign();
            dRight = randomSign();
        }
        
        public void update()
        {
            topOffset += dTop;
            bottomOffset += dBottom;
            leftOffset += dLeft;
            rightOffset += dRight;
            
            if (topOffset > maxOffset)
            {
                topOffset = maxOffset;
                dTop*=-1;
            }
            
            if (topOffset < minOffset)
            {
                topOffset = minOffset;
                dTop*=-1;
            }
            
            if (bottomOffset > maxOffset)
            {
                bottomOffset = maxOffset;
                dBottom*=-1;
            }
            
            if (bottomOffset < minOffset)
            {
                bottomOffset = minOffset;
                dBottom*=-1;
            }
            
            if (leftOffset > maxOffset)
            {
                leftOffset = maxOffset;
                dLeft*=-1;
            }
            
            if (leftOffset < minOffset)
            {
                leftOffset = minOffset;
                dLeft*=-1;
            }
            
            if (rightOffset > maxOffset)
            {
                rightOffset = maxOffset;
                dRight*=-1;
            }
            
            if (rightOffset < minOffset)
            {
                rightOffset = minOffset;
                dRight*=-1;
            }
        }
    }
    
    public class BoardBorder {
        SpaceColor borderColor;
        
        BorderSegment[] topBorders;
        BorderSegment[] rightBorders;
        BorderSegment[] bottomBorders;
        BorderSegment[] leftBorders;
        
        public BoardBorder(Corner[][] corners)
        {
            int tr = (int)Math.floor(Math.random()*105.0);
            int tg = (int)Math.floor(Math.random()*105.0);
            int tb = (int)Math.floor(Math.random()*105.0);
            
            int dr = randomSign()*(int)Math.floor(Math.random()*iMarginColor);
            int dg = randomSign()*(int)Math.floor(Math.random()*iMarginColor);
            int db = randomSign()*(int)Math.floor(Math.random()*iMarginColor);
            
            borderColor = new SpaceColor(15+tr,25+tg,65+tb);
            borderColor.setDelta(dr, dg, db);
            
            topBorders = new BorderSegment[8];
            rightBorders = new BorderSegment[8];
            bottomBorders = new BorderSegment[8];
            leftBorders = new BorderSegment[8];
            
            for (int b=0;b<8;b++)
            {
                topBorders[b] = new HorizontalBorderSegment(corners[b][0],corners[b+1][0]);
            }
            
            for (int b=0;b<8;b++)
            {
                bottomBorders[b] = new HorizontalBorderSegment(corners[b][8],corners[b+1][8]);
            }
                        
            for (int b=0;b<8;b++)
            {
                leftBorders[b] = new VerticalBorderSegment(corners[0][b],corners[0][b+1]);
            }
                        
            for (int b=0;b<8;b++)
            {
                rightBorders[b] = new VerticalBorderSegment(corners[8][b],corners[8][b+1]);
            }
        }
        
        public void update()
        {
            borderColor.update();
            for (BorderSegment segment : topBorders)
                segment.update();
                
            for (BorderSegment segment : rightBorders)
                segment.update();
            
            for (BorderSegment segment : bottomBorders)
                segment.update();
            
            for (BorderSegment segment : leftBorders)
                segment.update();
        }
        
        public void paint(Graphics g)
        {
            for (BorderSegment segment : topBorders)
                segment.paint(g);
                
            for (BorderSegment segment : rightBorders)
                segment.paint(g);
            
            for (BorderSegment segment : bottomBorders)
                segment.paint(g);
            
            for (BorderSegment segment : leftBorders)
                segment.paint(g);
        }
    }
    
    public class OutlinePieceColor extends SpaceColor {
        public OutlinePieceColor()
        {
            this(125,125,35);
        }
        public OutlinePieceColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }  
    
    public class SecondaryPieceColor extends SpaceColor {
        public SecondaryPieceColor()
        {
            this(175,175,175);
        }
        public SecondaryPieceColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }
    public class RedPieceColor extends SpaceColor {
        public RedPieceColor()
        {
            this(205,50,50);
        }
        public RedPieceColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }
    
    public class BlackPieceColor extends SpaceColor {
        public BlackPieceColor()
        {
            this(25,25,25);
        }
        public BlackPieceColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }
    
    public class SelectedBorderColor extends SpaceColor {
        public SelectedBorderColor(int r, int g, int b)
        {
            super(r,g,b);
        }
        public SelectedBorderColor()
        {
            this(205,55,0);
        }
    }   
    
    public class SelectedColor extends SpaceColor {
        public SelectedColor(int r, int g, int b)
        {
            super(r,g,b);
        }
        public SelectedColor()
        {
            this(155,0,0);
        }
    }    
        
    public class HoveredOverBorderColor extends SpaceColor {
        public HoveredOverBorderColor(int r, int g, int b)
        {
            super(r,g,b);
        }
        public HoveredOverBorderColor()
        {
            this(255,100,0);
        }
    }   
    
    public class HoveredOverColor extends SpaceColor {
        public HoveredOverColor(int r, int g, int b)
        {
            super(r,g,b);
        }
        public HoveredOverColor()
        {
            this(255,155,0);
        }
    }    
        
    public class HighlightedBorderColor extends SpaceColor {
        public HighlightedBorderColor(int r, int g, int b)
        {
            super(r,g,b);
        }
        public HighlightedBorderColor()
        {
            this(255,0,0);
        }
    }    
        
    public class HighlightedColor extends SpaceColor {
        public HighlightedColor()
        {
            this(255,255,0);
        }
        public HighlightedColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }
    
    public class SpaceColor {
        Color color;
        
        int r,g,b;
        int dr,dg,db;
        
        int minR,maxR;
        int minG,maxG;
        int minB,maxB;
        
        int dMargin;
        
        public SpaceColor(int r, int g, int b)
        {
            dMargin = marginColor;
            
            this.r = r;
            this.g = g;
            this.b = b;
            
            dr = 0;
            dg = 0;
            db = 0;
            
            int rFactor;
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            minR = Math.max(r-dMargin-rFactor,0);
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            maxR = Math.min(255,r+dMargin+rFactor);
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            minG = Math.max(g-dMargin-rFactor,0);
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            maxG = Math.min(255,g+dMargin+rFactor);
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            minB = Math.max(b-dMargin-rFactor,0);
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            maxB = Math.min(255,b+dMargin+rFactor);
            
            color = new Color(r,g,b);

        }
                
        public void setDelta(int dr, int dg, int db)
        {
            this.dr = dr;
            this.dg = dg;
            this.db = db;
        }
        
        public void update()
        {
            r += dr;
            g += dg;
            b += db;
            
            if (r < minR)
            {
                r = minR;
                dr = -1*dr;
            }
            
            if (r > maxR)
            {
                r = maxR;
                dr = -1*dr;
            }
            
            if (g < minG)
            {
                g = minG;
                dg = -1*dg;
            }
            
            if (g > maxG)
            {
                g = maxG;
                dg = -1*dg;
            }
            
            if (b < minB)
            {
                b = minB;
                db = -1*db;
            }
            
            if (b > maxB)
            {
                b = maxB;
                db = -1*db;
            }
            
            color = new Color(r,g,b);
        }

    }
    public class DarkSpaceColor extends SpaceColor {
        public DarkSpaceColor()
        {
            this(65,65,65);
        }
        public DarkSpaceColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }

    public class LightSpaceColor extends SpaceColor {
        public LightSpaceColor()
        {
            this(165,165,165);
        }
        
        public LightSpaceColor(int r, int g, int b)
        {
            super(r,g,b);
        }
    }

    
    public class Corner {
        Point point;
        int dx,dy;
        int minX,maxX;
        int minY,maxY;
        int dMargin;
        
        public Corner(int x, int y)
        {
            dMargin = marginPixel;
            
            point = new Point(x,y);
            dx = 0;
            dy = 0;
            
            int rFactor;
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            minX = x - dMargin - rFactor;
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            maxX = x + dMargin + rFactor;
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            minY = y - dMargin - rFactor;
            rFactor = (int)Math.floor(Math.random()*iMarginColor);
            maxY = y + dMargin + rFactor;
        }
        
        public void setDelta(int dx, int dy)
        {
            this.dx = dx;
            this.dy = dy;
        }
        
        public void update()
        {
            point.x += dx;
            point.y += dy;
            
            if (point.x < minX)
            {
                point.x = minX;
                dx = -1*dx;
            }
            
            if (point.x > maxX)
            {
                point.x = maxX;
                dx = -1*dx;
            }
            
            if (point.y < minY)
            {
                point.y = minY;
                dy = -1*dy;
            }
            
            if (point.y > maxY)
            {
                point.y = maxY;
                dy = -1*dy;
            }
        }
        
    }
    
    public class WarpedSpace {
        Location location;
        SpaceColor spaceColor;
        RedPieceColor redPieceColor;
        BlackPieceColor blackPieceColor;
        SecondaryPieceColor secondaryPieceColor;
        OutlinePieceColor outlinePieceColor;

        HighlightedColor highlightedColor;
        HighlightedBorderColor highlightedBorderColor;
        HoveredOverColor hoveredOverColor;
        HoveredOverBorderColor hoveredOverBorderColor;
        SelectedColor selectedColor;
        SelectedBorderColor selectedBorderColor;

        Corner topLeft;
        Corner topRight;
        Corner bottomLeft;
        Corner bottomRight;
        
        public void update()
        {
            spaceColor.update();
            redPieceColor.update();
            blackPieceColor.update();
            secondaryPieceColor.update();
            outlinePieceColor.update();
            
            highlightedColor.update();
            highlightedBorderColor.update();
            hoveredOverColor.update();
            hoveredOverBorderColor.update();
            selectedColor.update();
            selectedBorderColor.update();
            
        }
        
        public WarpedSpace(Location location, SpaceColor spaceColor, Corner topLeft, Corner topRight, Corner bottomLeft, Corner bottomRight)
        {
            this.location = location;
            this.spaceColor = spaceColor;
            
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
            
            highlightedColor = new HighlightedColor();
            highlightedBorderColor = new HighlightedBorderColor();
            hoveredOverColor = new HoveredOverColor();
            hoveredOverBorderColor = new HoveredOverBorderColor();
            selectedColor = new SelectedColor();
            selectedBorderColor = new SelectedBorderColor();
            
            redPieceColor = new RedPieceColor();
            blackPieceColor = new BlackPieceColor();
            
            secondaryPieceColor = new SecondaryPieceColor();
            outlinePieceColor = new OutlinePieceColor();
        }
    }

    
    public  Corner[][]  corners;
    public BoardBorder[] boardBorders;
    public  WarpedSpace[][] spaces;
    private Properties properties;
        
    public void paint(Graphics g)
    {
        /*for (BoardBorder border : boardBorders)
            border.paint(g);*/
    }
    
    public void update()
    {
        for (int c=0;c<properties.NUM_COLS+1;c++)
        {
            for (int r=0;r<properties.NUM_ROWS+1;r++)
            {
                corners[c][r].update();
            }
        }
        for (int c=0;c<properties.NUM_COLS;c++)
        {
            for (int r=0;r<properties.NUM_ROWS;r++)
            {
                spaces[c][r].update();
            }
        }
        
        for (BoardBorder border : boardBorders)
            border.update();
    }
    
    public CheckersWarper(Properties properties)
    {
        this.properties = properties;
        corners = new Corner[properties.NUM_COLS+1][properties.NUM_ROWS+1];
        spaces = new WarpedSpace[properties.NUM_COLS][properties.NUM_ROWS];
        
        initializeCorners();
        initializeSpaces(corners);
        initializeRandomness();
        initializeBorders(corners);
    }
    
    public WarpedSpace getWarpedSpace(Location location)
    {
        int x = Location.getCol(location);
        int y = Location.getRow(location);
        
        return spaces[x][y];
    }
    
    public void initializeSpaces(Corner[][] corners)
    {
        for (int col=0;col<properties.NUM_COLS;col++)
        {
            for (int row=0;row<properties.NUM_ROWS;row++)
            {
                SpaceColor spaceColor;
                if ((((col % 2) == 0) && ((row % 2) == 0)) || (((col % 2) == 1) && ((row % 2) == 1)))
                {
                    spaceColor = new LightSpaceColor();
                }
                else // ((((w % 2) == 1) && ((h % 2) == 0)) || (((w % 2) == 1) && ((h % 2) == 0)))
                {
                    spaceColor = new DarkSpaceColor();
                }
                Location location = Location.of(col,row);

                spaces[col][row] = new WarpedSpace(location,spaceColor,corners[col][row],corners[col+1][row],corners[col][row+1],corners[col+1][row+1]);
            }
        }
    }
    
    public void initializeCorners()
    {
        for (int c=0;c<properties.NUM_COLS+1;c++)
        {
            for (int r=0;r<properties.NUM_ROWS+1;r++)
            {
                int tempBoardMargin = 50;
                int tempSpaceWidth  = 50;
                int tempSpaceHeight = 50;
                
                /*int x = tempBoardMargin + tempSpaceWidth*c;
                int y = tempBoardMargin + tempSpaceHeight*r;*/
                
                int x = tempBoardMargin + tempSpaceWidth*c + (int)Math.floor(Math.random()*iMarginPixel);
                int y = tempBoardMargin + tempSpaceHeight*r + (int)Math.floor(Math.random()*iMarginPixel);
                
                corners[c][r] = new Corner(x,y);
            }
        }
    }
    
    public void initializeBorders(Corner[][] corners)
    {
        boardBorders = new BoardBorder[25];
        for (int b=0;b<boardBorders.length;b++)
        {
            boardBorders[b] = new BoardBorder(corners);
        }
    }
    
    private int randomSign()
    {
        if (Math.random() > 0.5) return 1;
        else return -1;
    }
    
    public void initializeRandomness()
    {
        for (int c=0;c<properties.NUM_COLS+1;c++)
        {
            for (int r=0;r<properties.NUM_ROWS+1;r++)
            {
                int dx = randomSign()*(int)Math.floor(Math.random()*iMarginPixel);
                int dy = randomSign()*(int)Math.floor(Math.random()*iMarginPixel);
                
                corners[c][r].setDelta(dx,dy);
            }
        }
        for (int c=0;c<properties.NUM_COLS;c++)
        {
            for (int r=0;r<properties.NUM_ROWS;r++)
            {
                int[] dr = new int[11];
                int[] dg = new int[11];
                int[] db = new int[11];
                
                for (int i=0;i<11;i++)
                {
                    dr[i] = randomSign()*(int)Math.floor(Math.random()*iMarginColor);
                    dg[i] = randomSign()*(int)Math.floor(Math.random()*iMarginColor);
                    db[i] = randomSign()*(int)Math.floor(Math.random()*iMarginColor);
                }
                
                spaces[c][r].spaceColor.setDelta(dr[0],dg[0],db[0]);
                spaces[c][r].redPieceColor.setDelta(dr[1],dg[1],db[1]);
                spaces[c][r].blackPieceColor.setDelta(dr[2],dg[2],db[2]);
                spaces[c][r].secondaryPieceColor.setDelta(dr[3],dg[3],db[3]);
                spaces[c][r].highlightedColor.setDelta(dr[4],dg[4],db[4]);
                spaces[c][r].highlightedBorderColor.setDelta(dr[5],dg[5],db[5]);
                spaces[c][r].hoveredOverColor.setDelta(dr[6],dg[6],db[6]);
                spaces[c][r].hoveredOverBorderColor.setDelta(dr[7],dg[7],db[7]);
                spaces[c][r].selectedColor.setDelta(dr[8],dg[8],db[8]);
                spaces[c][r].selectedBorderColor.setDelta(dr[9],dg[9],db[9]);
                spaces[c][r].outlinePieceColor.setDelta(dr[10],dg[10],db[10]);
            }
        }
    }
    
    public Location getLocationOfSpaceAt(int x, int y)
    {
        Point p = new Point(x,y);
        
        for (int c=0;c<properties.NUM_COLS;c++)
        {
            for (int r=0;r<properties.NUM_ROWS;r++)
            {
                Location location = Location.of(c,r);
                
                Point tl = corners[c][r].point;
                Point tr = corners[c+1][r].point;
                Point bl = corners[c][r+1].point;
                Point br = corners[c+1][r+1].point;
                
                if (Geometry.isBelow(p,tl,tr) && 
                    Geometry.isAbove(p,bl,br) &&
                    Geometry.isToRight(p,tl,bl) &&
                    Geometry.isToLeft(p,tr,br))
                {
                    return location;
                }
            }
        }
        
        return null;
    }
}
