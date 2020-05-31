package chess.chess2D;

import com.sun.media.jfxmedia.logging.Logger;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Contains paths and performs File I/O for sprite images of pieces
 * @author devang
 */
public class Chess2DGraphics {
    //public static String  PHOTO_FILE_ROOT = "/Users/devang/Desktop/gamesImages/chess/";
    //private static int x_size = 75;
    //private static int y_size = 75;
    
    private final static String GRAPHICS_FILE_ROOT = "chess2D/";
    
    private final HashMap<String,BufferedImage> pieceImages;
    
    public BufferedImage getPieceImage(String pieceType,String pieceColor)
    {
        String filenameHash = getPieceFilename(pieceType,pieceColor);
        if (!pieceImages.containsKey(filenameHash))
        {
            BufferedImage tmpImage = loadPieceImage(pieceType,pieceColor);
            if (tmpImage != null)
            {
                pieceImages.put(filenameHash,tmpImage);
            }
        }
        return pieceImages.get(filenameHash);
    }
    
    public Chess2DGraphics()
    {
        pieceImages = new HashMap<>();
    }
    
    private String getPieceFilename(String pieceType, String pieceColor)
    {
        String graphicsFileName = "";
        
        graphicsFileName += GRAPHICS_FILE_ROOT;
        graphicsFileName += pieceColor + "_";
        graphicsFileName += pieceType;
        graphicsFileName += ".png";
        
        return graphicsFileName;
    }
        
    private BufferedImage loadPieceImage(String pieceType, String pieceColor)
    {
        BufferedImage pieceImage = null;
        
        try {
            pieceImage = ImageIO.read(getClass().getClassLoader().getResource(getPieceFilename(pieceType,pieceColor)));
        } catch (IOException e) {
            Logger.logMsg(0, "Error loading Piece Image: " + pieceColor + " " + pieceType);
        }
        
        if (pieceImage == null) return null;
        
        return pieceImage;
    }
}
