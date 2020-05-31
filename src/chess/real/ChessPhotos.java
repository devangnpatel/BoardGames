package chess.real;

import com.sun.media.jfxmedia.logging.Logger;
import game.utility.Geometry;
import game.utility.Location;
import game.utility.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * manages a set of all ~800 potential piece images for the space they occupy
 * - loads a piece image on its first use, and keeps that image in memory for future use
 * - does not load all ~800 images on construction, because that is unnecessary
 *   and could bog down the initial load time of the game
 * @author devang
 */
public class ChessPhotos {
    
    private final static int[][] top_xy = {{2014,577},{2185,628},{2366,678},{2561,729},{2763,786},{2978,848},{3200,911},{3434,979},{3674,1052}};
    private final static int[][] bot_xy = {{568,1458},{749,1554},{948,1656},{1161,1767},{1394,1886},{1641,2013},{1914,2149},{2208,2295},{2525,2453}};
    private final static int[][] lft_xy = {{2014,577},{1871,660},{1721,748},{1558,846},{1386,949},{1202,1064},{1004,1184},{791,1317},{568,1458}};
    private final static int[][] rgt_xy = {{3674,1052},{3577,1172},{3467,1307},{3347,1451},{3215,1615},{3068,1793},{2905,1990},{2724,2208},{2525,2453}};
    
    //public static String  PHOTO_FILE_ROOT = "/Users/devang/Desktop/gamesImages/chess/";
    public static String PHOTO_FILE_ROOT = "gamesImages/chess/";
            
    public static int[][] TOP_COORDS;
    public static int[][] BOT_COORDS;
    public static int[][] LFT_COORDS;
    public static int[][] RGT_COORDS;
    
    private Point scalePoint(Point inPoint)
    {
        double scaleFactor = ChessRealProperties.PHOTO_SCALE_FACTOR;
        Point outPoint = new Point(inPoint.x*scaleFactor,inPoint.y*scaleFactor);
        return outPoint;
    }
    
    private int scale(double inVal)
    {
        double scaleFactor = ChessRealProperties.PHOTO_SCALE_FACTOR;
        int outVal = (int)Math.floor(inVal*scaleFactor);
        return outVal;
    }
    
    public BufferedImage boardImage = null;
    public BufferedImage backgroundImage = null;
    public HashMap<String,PiecePhoto> pieceImages = null;
    
    /**
     * Gets the already loaded image photo of the Chess Board
     * @return Image of the appropriately-sized Chess Board photo
     */
    public BufferedImage getBoardImage()
    {
        return boardImage;
    }
    
    /**
     * Gets the already loaded image photo of the Background to put behind the Chess Board
     * @return Image of a background/wallpaper to place behind the Chess Board
     */
    public BufferedImage getBackgroundImage()
    {
        return backgroundImage;
    }
    
    public PiecePhoto getPieceImage(String pieceType,String pieceColor,int x,int y)
    {
        String filenameHash = PiecePhoto.getPhotoFilename(pieceType,pieceColor,x,y);
        if (filenameHash == null) return null;
        
        if (!pieceImages.containsKey(filenameHash))
        {
            PiecePhoto tmpImage = new PiecePhoto(pieceType,pieceColor,x,y);
                pieceImages.put(filenameHash,tmpImage);
        }
        PiecePhoto piecePhoto = pieceImages.get(filenameHash);
        return piecePhoto;
    }
    
    public void savePiecePhotoOffset(PiecePhoto piecePhoto)
    {
        if (piecePhoto == null) return;
        piecePhoto.save();
    }
    
    public void shiftPieceOffset(PiecePhoto piecePhoto, int xOffset, int yOffset)
    {
        if (piecePhoto == null) return;
        piecePhoto.shiftOffset(xOffset, yOffset);
    }
    
    /**
     * Manages File I/O for photos of the Board and all Chess Pieces at every possible
     * location on the Board. Photos are loaded when they are first required, and stored
     * in a HashMap for any future needs.
     */
    public ChessPhotos()
    {
        pieceImages = new HashMap<>();
        
        TOP_COORDS = new int[9][2];
        BOT_COORDS = new int[9][2];
        LFT_COORDS = new int[9][2];
        RGT_COORDS = new int[9][2];
        
        double photoHeight = ChessRealProperties.PHOTO_HEIGHT;
        
        for (int i = 0; i < 9; i++)
        {
            TOP_COORDS[i][0] = scale(top_xy[i][0]);
            TOP_COORDS[i][1] = scale(photoHeight - top_xy[i][1]);
            
            BOT_COORDS[i][0] = scale(bot_xy[i][0]);
            BOT_COORDS[i][1] = scale(photoHeight - bot_xy[i][1]);
            
            LFT_COORDS[i][0] = scale(lft_xy[i][0]);
            LFT_COORDS[i][1] = scale(photoHeight - lft_xy[i][1]);
            
            RGT_COORDS[i][0] = scale(rgt_xy[i][0]);
            RGT_COORDS[i][1] = scale(photoHeight - rgt_xy[i][1]);
        }
        
        boardImage = loadBoardImage();
        backgroundImage = loadBackgroundImage();
        
        /*
        String[] pieceColors = {"white","black"};
        String[] pieceTypes = {"pawn","rook","knight","bishop","queen","king"};
        int[] xValues = {0,1,2,3,4,5,6,7};
        int[] yValues = {0,1,2,3,4,5,6,7};
        
        for (String type : pieceTypes)
        {
            for (String color : pieceColors)
            {
                for (int x : xValues)
                {
                    for (int y: yValues)
                    {
                        String tmpFileName = getPieceFilename(type,color,x,y);
                        BufferedImage tmpImage = loadPieceImage(type,color,x,y);
                        if (tmpImage != null)
                        {
                            pieceImages.put(tmpFileName,tmpImage);
                        }
                    }
                }
            }
        }
        */
    }
    
    /*private String getPieceFilename(String pieceType, String pieceColor,int x,int y)
    {
        String photoFileName = "";
        
        photoFileName += PHOTO_FILE_ROOT;
        photoFileName += pieceColor + "/";
        photoFileName += pieceType + "s" + "/";
        
        photoFileName += pieceColor + "_";
        photoFileName += pieceType  + "_";
        photoFileName += y +"_" + x;
        photoFileName += ".png";
        
        return photoFileName;
    }*/
    
    private String getBoardFilename()
    {
        String photoFileName = "";
        
        photoFileName += PHOTO_FILE_ROOT;
        photoFileName += "board" + "/";
        // "chessboard","board-1"
        // "board-cutout","board-cutout-no-background","board-cutout-solid-background"
        photoFileName += "board-cutout-no-background";
        photoFileName += ".png";
        
        return photoFileName;
    }
    
        
    private String getBackgroundFilename()
    {
        String photoFileName = "";
        
        photoFileName += PHOTO_FILE_ROOT;
        photoFileName += "background" + "/";
        // "golden-forest","trees-1","trees-2","trees-3"
        // "clouds","sunrise","sunset"
        // "drawing","lava-lamp","space-needle"
        photoFileName += "golden-forest";
        photoFileName += ".png";
        
        return photoFileName;
    }
    
    private BufferedImage loadBackgroundImage()
    {
        BufferedImage backgroundImage = null;
        
        try {
            // backgroundImage = ImageIO.read(new File(getBackgroundFilename()));
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource(getBackgroundFilename()));
        } catch (IOException e) {
            Logger.logMsg(0, "Error loading Background Image");
        }
        
        if (backgroundImage == null) return null;
        
        return backgroundImage;
    }
    
    private BufferedImage loadBoardImage()
    {
        BufferedImage boardImage = null;
        
        try {
            // boardImage = ImageIO.read(new File(getBoardFilename()));
            boardImage = ImageIO.read(getClass().getClassLoader().getResource(getBoardFilename()));
        } catch (IOException e) {
            Logger.logMsg(0, "Error loading Board Image");
        }
        
        if (boardImage == null) return null;
        
        return boardImage;
    }
    
    /*private BufferedImage loadPieceImage(String pieceType, String pieceColor,int x,int y)
    {
        if (pieceType.equalsIgnoreCase("pawn"))
        {
            if ((y==0) || (y==7)) return null;
        }
        
        BufferedImage pieceImage = null;
        
        try {
            // pieceImage = ImageIO.read(new File(getPieceFilename(pieceType,pieceColor,x,y)));
            pieceImage = ImageIO.read(getClass().getClassLoader().getResource(getPieceFilename(pieceType,pieceColor,x,y)));
        } catch (IOException e) {
            Logger.logMsg(0, "Error loading Piece Image: " + pieceColor + " " + pieceType);
        }
        
        if (pieceImage == null) return null;
        
        return pieceImage;
    }*/
    
    /**
     * Gets top-left coordinate of board space at input location
     * - Expects the slope is not equal to zero
     * - Expects the slopes are not the same
     * @param location COL,ROW Location to get four-corners (x,y) coordinates
     * @return (x,y) pair of top-left coordinate
     */
    public static Point getTopLeftCoordinate(Location location)
    {
        int boardHeight = ChessRealProperties.BOARD_HEIGHT;
        
        int col = Location.getCol(location);
        int row = Location.getRow(location);
        
        Point p1 = new Point(TOP_COORDS[col][0],TOP_COORDS[col][1]);
        Point p2 = new Point(BOT_COORDS[col][0],BOT_COORDS[col][1]);
        
        Point p3 = new Point(LFT_COORDS[row][0],LFT_COORDS[row][1]);
        Point p4 = new Point(RGT_COORDS[row][0],RGT_COORDS[row][1]);
        
        Point intersection = Geometry.getIntersectionPoint(p1,p2,p3,p4);
        intersection.y = boardHeight - intersection.y;
        return intersection;
    }

    /**
     * Gets top-right coordinate of board space at input location
     * - Expects the slope is not equal to zero
     * - Expects the slopes are not the same
     * @param location COL,ROW Location to get four-corners (x,y) coordinates
     * @return (x,y) pair of top-right coordinate
     */
    public static Point getTopRightCoordinate(Location location)
    {
        int boardHeight = ChessRealProperties.BOARD_HEIGHT;
        
        int col = Location.getCol(location);
        int row = Location.getRow(location);
        
        Point p1 = new Point(TOP_COORDS[col+1][0],TOP_COORDS[col+1][1]);
        Point p2 = new Point(BOT_COORDS[col+1][0],BOT_COORDS[col+1][1]);
        
        Point p3 = new Point(LFT_COORDS[row][0],LFT_COORDS[row][1]);
        Point p4 = new Point(RGT_COORDS[row][0],RGT_COORDS[row][1]);
        
        Point intersection = Geometry.getIntersectionPoint(p1,p2,p3,p4);
        intersection.y = boardHeight - intersection.y;
        return intersection;
    }
    
    /**
     * Gets bottom-right coordinate of board space at input location
     * - Expects the slope is not equal to zero
     * - Expects the slopes are not the same
     * @param location COL,ROW Location to get four-corners (x,y) coordinates
     * @return (x,y) pair of bottom-right coordinate
     */
    public static Point getBottomRightCoordinate(Location location)
    {
        int boardHeight = ChessRealProperties.BOARD_HEIGHT;
        
        int col = Location.getCol(location);
        int row = Location.getRow(location);
        
        Point p1 = new Point(TOP_COORDS[col+1][0],TOP_COORDS[col+1][1]);
        Point p2 = new Point(BOT_COORDS[col+1][0],BOT_COORDS[col+1][1]);
        
        Point p3 = new Point(LFT_COORDS[row+1][0],LFT_COORDS[row+1][1]);
        Point p4 = new Point(RGT_COORDS[row+1][0],RGT_COORDS[row+1][1]);
        
        Point intersection = Geometry.getIntersectionPoint(p1,p2,p3,p4);
        intersection.y = boardHeight - intersection.y;
        return intersection;
    }
    
    /**
     * Gets bottom-left coordinate of board space at input location
     * - Expects the slope is not equal to zero
     * - Expects the slopes are not the same
     * @param location COL,ROW Location to get four-corners (x,y) coordinates
     * @return (x,y) pair of bottom-left coordinate
     */
    public static Point getBottomLeftCoordinate(Location location)
    {
        int boardHeight = ChessRealProperties.BOARD_HEIGHT;
        
        int col = Location.getCol(location);
        int row = Location.getRow(location);
        
        Point p1 = new Point(TOP_COORDS[col][0],TOP_COORDS[col][1]);
        Point p2 = new Point(BOT_COORDS[col][0],BOT_COORDS[col][1]);
        
        Point p3 = new Point(LFT_COORDS[row+1][0],LFT_COORDS[row+1][1]);
        Point p4 = new Point(RGT_COORDS[row+1][0],RGT_COORDS[row+1][1]);
        
        Point intersection = Geometry.getIntersectionPoint(p1,p2,p3,p4);
        intersection.y = boardHeight - intersection.y;
        return intersection;
    }
}
