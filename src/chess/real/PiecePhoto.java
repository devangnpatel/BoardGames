package chess.real;

import chess.pieces.PieceBishop;
import chess.pieces.PieceKing;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import static chess.real.ChessPhotos.PHOTO_FILE_ROOT;
import com.sun.media.jfxmedia.logging.Logger;
import game.boardgame.pieces.Piece;
import game.utility.Properties;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author devang
 */
public class PiecePhoto {
            
    //private static String FILE_PATH = "/Users/devang/NetBeansProjects/Game/";
    private String FILE_PATH;
    
    String photoFilename;
    String photoOffsetFilename;
    BufferedImage photo;
    int row;
    int col;
    int xOffset;
    int yOffset;

    private void checkFolders()
    {
        String tmpColors[] = {"black","white"};
        String tmpTypes[]  = {"pawns","queens","kings","bishops","knights","rooks"};
        String tmpPath = Paths.get("photo_offset_files/gamesImages/chess").toAbsolutePath().toString();
        File tmpFile = new File(tmpPath);
        if (!tmpFile.exists()) 
        {
            if (!tmpFile.mkdirs()) System.out.println("failure creating photo_offset_files");
        }
        for (String tmpColor : tmpColors)
        {
            for (String tmpType : tmpTypes)
            {
                String tmpPathString = "photo_offset_files/gamesImages/chess/" + tmpColor + "/" + tmpType;
                tmpPath = Paths.get(tmpPathString).toAbsolutePath().toString();
                tmpFile = new File(tmpPath);
                if (!tmpFile.exists())
                {
                    if (!tmpFile.mkdirs()) System.out.println("failure creating folder: " + tmpPathString);
                }
            }
        }
    }
    
    public PiecePhoto(String pieceType, String pieceColor, int x, int y)
    { 
        //FILE_PATH = "/";
        //FILE_PATH = getClass().getResource("/").getPath();
        checkFolders();
        FILE_PATH = Paths.get("photo_offset_files").toAbsolutePath().toString() + "/";
        
        photoFilename = getPhotoFilename(pieceType,pieceColor,x,y);
        photoOffsetFilename = getPhotoOffsetFilename(pieceType,pieceColor,x,y);
        col = x;
        row = y;
        load();
    }

    public PiecePhoto(Piece piece,int x,int y)
    {
        //FILE_PATH = "/";
        //FILE_PATH = getClass().getResource("/").getPath();
        /*Path currentRelativePath = Paths.get("photo_offset_files");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path() is: " + s);
        Path currentRelativePath2 = Paths.get("/");
        String s2 = currentRelativePath2.toAbsolutePath().toString();
        System.out.println("Current relative path(/) is: " + s2);*/
        
        checkFolders();
        FILE_PATH = Paths.get("photo_offset_files").toAbsolutePath().toString() + "/";
        photoFilename = getPhotoFilename(piece,x,y);
        photoOffsetFilename = getPhotoOffsetFilename(piece,x,y);
        col = x;
        row = y;
        load();
    }

    public static String getPhotoFilename(String pieceType, String pieceColor, int x, int y)
    {
        if ((pieceType == null) || (pieceColor == null) || (pieceType.isEmpty()) || (pieceColor.isEmpty()))
            return null;

        String photoFileName = "";

        photoFileName += PHOTO_FILE_ROOT;
        photoFileName += pieceColor + "/";
        photoFileName += pieceType + "s" + "/";

        photoFileName += pieceColor + "_";
        photoFileName += pieceType  + "_";
        photoFileName += y +"_" + x;
        photoFileName += ".png";

        return photoFileName;
    }

    private String getPhotoOffsetFilename(String pieceType, String pieceColor, int x, int y)
    {
        if ((pieceType == null) || (pieceColor == null) || (pieceType.isEmpty()) || (pieceColor.isEmpty()))
            return null;

        String photoFileName = "";

        photoFileName += PHOTO_FILE_ROOT;
        photoFileName += pieceColor + "/";
        photoFileName += pieceType + "s" + "/";

        photoFileName += pieceColor + "_";
        photoFileName += pieceType  + "_";
        photoFileName += y +"_" + x;
        photoFileName += ".txt";

        return photoFileName;
    }

    public static String getPhotoFilename(Piece piece, int x, int y)
    {
        if (piece == null) return null;

        String pieceType = null;
        String pieceColor = null;

        if (piece.getColor() == Properties.PlayerColor.BLACK) pieceColor = "black";
        if (piece.getColor() == Properties.PlayerColor.RED)   pieceColor = "white";

        if (piece instanceof PieceBishop) pieceType = "bishop";
        if (piece instanceof PieceKing)   pieceType = "king";
        if (piece instanceof PieceKnight) pieceType = "knight";
        if (piece instanceof PiecePawn)   pieceType = "pawn";
        if (piece instanceof PieceQueen)  pieceType = "queen";
        if (piece instanceof PieceRook)   pieceType = "rook";

        if ((pieceType == null) || (pieceColor == null)) return null;

        return getPhotoFilename(pieceType,pieceColor,x,y);
    }

    private String getPhotoOffsetFilename(Piece piece, int x, int y)
    {
        if (piece == null) return null;

        String pieceType = null;
        String pieceColor = null;

        if (piece.getColor() == Properties.PlayerColor.BLACK) pieceColor = "black";
        if (piece.getColor() == Properties.PlayerColor.RED)   pieceColor = "white";

        if (piece instanceof PieceBishop) pieceType = "bishop";
        if (piece instanceof PieceKing)   pieceType = "king";
        if (piece instanceof PieceKnight) pieceType = "knight";
        if (piece instanceof PiecePawn)   pieceType = "pawn";
        if (piece instanceof PieceQueen)  pieceType = "queen";
        if (piece instanceof PieceRook)   pieceType = "rook";

        if ((pieceType == null) || (pieceColor == null)) return null;

        return getPhotoOffsetFilename(pieceType,pieceColor,x,y);
    }

    private void writePhotoOffsetFile()
    {
        String filename = FILE_PATH + photoOffsetFilename;
        if (filename == null) return;

        File file = null;
        FileWriter fileWriter = null;

        try {
            file = new File(filename);
            if (file.exists()) file.delete();
            file.createNewFile();
            fileWriter = new FileWriter(file);
            fileWriter.write(photoOffsetFilename + "\n");
            fileWriter.write(String.valueOf(xOffset) + " " + String.valueOf(yOffset));
            fileWriter.close();
            /*fileWriter.println(photoOffsetFilename);
            printWriter.println(String.valueOf(xOffset));
            printWriter.println(String.valueOf(yOffset));
            printWriter.close();*/
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void loadPhotoOffsetFile()
    {
        String filename = FILE_PATH + photoOffsetFilename;
        if (filename == null) return;

        Scanner fileScanner = null;
        File file = null;

        try {
            file = new File(filename);
            file.createNewFile();
            fileScanner = new Scanner(file);
            if (fileScanner.hasNext())
            {
                fileScanner.next(); // should equal filename.value()
                if (fileScanner.hasNext()) xOffset = fileScanner.nextInt();
                else xOffset = 0;
                if (fileScanner.hasNext()) yOffset = fileScanner.nextInt();
                else yOffset = 0;
            }
            else
            {
                xOffset = 0;
                yOffset = 0;
            }
            writePhotoOffsetFile();
        } catch (Exception e) {
            // FileNotFoundException
        }

    }

    private void loadPhotoFile()
    {
        String filename = photoFilename;
        if (filename == null) return;

        BufferedImage pieceImage = null;
        photo = null;

        try {
            // pieceImage = ImageIO.read(new File(getPieceFilename(pieceType,pieceColor,x,y)));
            pieceImage = ImageIO.read(getClass().getClassLoader().getResource(filename));
        } catch (IOException e) {
            Logger.logMsg(0, "Error loading Piece Image: " + filename);
        }
        photo = pieceImage;
    }

    public final void load()
    {
        loadPhotoFile();
        loadPhotoOffsetFile();
    }

    public final void save()
    {
        writePhotoOffsetFile();
    }

    public int getXOffset()
    {
        return xOffset;
    }

    public int getYOffset()
    {
        return yOffset;
    }

    public void shiftOffset(int x, int y)
    {
        xOffset += x;
        yOffset += y;
    }

    public BufferedImage getPhoto()
    {
        return photo;
    }

}
