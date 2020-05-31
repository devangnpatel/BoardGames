package game.utility;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author devang
 */
public abstract class Properties implements Serializable {

    //////////////////////////////////////////
    //////////////////////////////////////////
    ////// NO NEED TO MODIFY BELOW THIS //////
    //////////////////////////////////////////
    
    public static int NUM_ROWS = 8;
    public static int NUM_COLS = 8;
    
    //public abstract int get_NUM_ROWS();
    //public abstract int get_NUM_COLS();
    
    public abstract int get_BOARD_WIDTH();
    public abstract int get_BOARD_HEIGHT();
    
    public abstract int get_BOARD_MARGIN();
    
    public abstract Direction get_RED_DIRECTION();
    public abstract Direction get_BLACK_DIRECTION();
    
    public abstract PlayerColor get_INITIAL_PLAYER_COLOR();
    
    public abstract Color get_RED_PIECE_GRAPHICS_COLOR();
    public abstract Color get_BLACK_PIECE_GRAPHICS_COLOR();
    
    public abstract Color get_RED_PIECE_TEXT_COLOR();
    public abstract Color get_BLACK_PIECE_TEXT_COLOR();
    
    public abstract Color get_LIGHT_SPACE_COLOR();
    public abstract Color get_DARK_SPACE_COLOR();
    public abstract Color get_HIGHLIGHTED_SPACE_COLOR();
    public abstract Color get_HIGHLIGHTED_SPACE_BORDER_COLOR();
    public abstract Color get_HOVERED_OVER_SPACE_COLOR();
    public abstract Color get_HOVERED_OVER_SPACE_BORDER_COLOR();
    public abstract Color get_SELECTED_SPACE_COLOR();
    public abstract Color get_SELECTED_SPACE_BORDER_COLOR();
    
    public enum PlayerColor implements Serializable
    {
        RED,
        BLACK
    }
   
    public enum Direction implements Serializable
    {
        UP,
        DOWN
    }
    
    public abstract Color getGraphicsColor(PlayerColor color);
    public abstract Color getTextColor(PlayerColor color);
    
    public static Color getGraphicsColor(PlayerColor color, Color redGraphics, Color blackGraphics)
    {
        switch (color)
        {
            case RED:
                return redGraphics;
            case BLACK:
                return blackGraphics;
        }
        return null;
    }
        
    public static Color getTextColor(PlayerColor color, Color redText, Color blackText)
    {
        switch (color)
        {
            case RED:
                return redText;
            case BLACK:
                return blackText;
        }
        return null;
    }
        
    public static PlayerColor oppositeColor(PlayerColor color)
    {
        switch (color)
        {
            case RED:
                return PlayerColor.BLACK;
            case BLACK:
                return PlayerColor.RED;
        }
        return null;
    }
    
    public static Direction getColorDirection(PlayerColor color, Direction RED_DIRECTION, Direction BLACK_DIRECTION)
    {
        switch (color)
        {
            case RED:
                return RED_DIRECTION;
            case BLACK:
                return BLACK_DIRECTION;
        }
        return null;
    }
}
