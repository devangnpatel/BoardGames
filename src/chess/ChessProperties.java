package chess;

import game.utility.Properties;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.CYAN;
import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.ORANGE;
import static java.awt.Color.PINK;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.YELLOW;

/**
 * This class extends from Properties (which has only static members)<br>
 * This class (CheckersProperties, ChessProperties, OthelloProperties...)<br>
 * allows different graphics, sizes and colors for any extended game<br>
 * - also, adjusting num_rows, num_cols, an atypical game can be created<br>
 *   i.e.) 10x10 board with different chess pieces (4 knights instead of just 2)
 * 
 * @author devang
 */
public class ChessProperties extends Properties {
    
    public static final int BOARD_WIDTH  = 400;
    public static final int BOARD_HEIGHT = 400;
    
    protected Direction RED_DIRECTION;   // = Direction.UP;
    protected Direction BLACK_DIRECTION; // = Direction.DOWN;
    
    private static final int BOARD_MARGIN = 0;
    
    public static final PlayerColor INITIAL_PLAYER_COLOR = PlayerColor.RED;
    
    public static final Color RED_PIECE_GRAPHICS_COLOR        = RED;
    public static final Color BLACK_PIECE_GRAPHICS_COLOR      = BLACK;
    
    public static final Color RED_PIECE_TEXT_COLOR            = BLACK;
    public static final Color BLACK_PIECE_TEXT_COLOR          = WHITE;
    
    public static final Color LIGHT_SPACE_COLOR               = LIGHT_GRAY;
    public static final Color DARK_SPACE_COLOR                = DARK_GRAY;
    public static final Color HIGHLIGHTED_SPACE_COLOR         = ORANGE;
    public static final Color HIGHLIGHTED_SPACE_BORDER_COLOR  = RED;
    public static final Color HOVERED_OVER_SPACE_COLOR        = PINK;
    public static final Color HOVERED_OVER_SPACE_BORDER_COLOR = ORANGE;
    public static final Color SELECTED_SPACE_COLOR            = YELLOW;
    public static final Color SELECTED_SPACE_BORDER_COLOR     = RED;

    public int get_NUM_ROWS() { return NUM_ROWS; }
    public int get_NUM_COLS() { return NUM_COLS; }
    
    public int get_BOARD_WIDTH() { return BOARD_WIDTH; }
    public int get_BOARD_HEIGHT() { return BOARD_HEIGHT; }
    
    public Direction get_RED_DIRECTION() { return RED_DIRECTION; }
    public Direction get_BLACK_DIRECTION() { return BLACK_DIRECTION; }
    
    public PlayerColor get_INITIAL_PLAYER_COLOR() { return INITIAL_PLAYER_COLOR; }
    
    public Color get_RED_PIECE_GRAPHICS_COLOR() { return RED_PIECE_GRAPHICS_COLOR; }
    public Color get_BLACK_PIECE_GRAPHICS_COLOR() { return BLACK_PIECE_GRAPHICS_COLOR; }
    
    public Color get_RED_PIECE_TEXT_COLOR() { return RED_PIECE_TEXT_COLOR; }
    public Color get_BLACK_PIECE_TEXT_COLOR() { return BLACK_PIECE_TEXT_COLOR; }
    
    public Color get_LIGHT_SPACE_COLOR() { return LIGHT_SPACE_COLOR; }
    public Color get_DARK_SPACE_COLOR() { return DARK_SPACE_COLOR; }
    public Color get_HIGHLIGHTED_SPACE_COLOR() { return HIGHLIGHTED_SPACE_COLOR; }
    public Color get_HIGHLIGHTED_SPACE_BORDER_COLOR() { return HIGHLIGHTED_SPACE_BORDER_COLOR; }
    public Color get_HOVERED_OVER_SPACE_COLOR() { return HOVERED_OVER_SPACE_COLOR; }
    public Color get_HOVERED_OVER_SPACE_BORDER_COLOR() { return HOVERED_OVER_SPACE_BORDER_COLOR; }
    public Color get_SELECTED_SPACE_COLOR() { return SELECTED_SPACE_COLOR; }
    public Color get_SELECTED_SPACE_BORDER_COLOR() { return SELECTED_SPACE_BORDER_COLOR; }
    
    public ChessProperties() { }
    
    public void setColorDirections(Direction redDirection, Direction blackDirection)
    {
        RED_DIRECTION   = redDirection;
        BLACK_DIRECTION = blackDirection;
    }

    public static ChessProperties init(Direction redDirection, Direction blackDirection)
    {
        ChessProperties chessProperties = new ChessProperties();
        chessProperties.setColorDirections(redDirection, blackDirection);
        return chessProperties;
    }

    @Override
    public int get_BOARD_MARGIN()
    {
        return BOARD_MARGIN;
    }
    
    /**
     * Gets the java.awt.Color of this player, for drawing text on a Canvas
     * @param color Color of the player for which to get the text color
     * @return the java color used to draw text on a Canvas
     */
    @Override
    public Color getTextColor(PlayerColor color)
    {
        return getTextColor(color,RED_PIECE_TEXT_COLOR,BLACK_PIECE_TEXT_COLOR);
    }
    
    /**
     * Gets the java.awt.Color of this player, for screen drawing on a Canvas
     * @param color Color of the player for which to get the drawing color
     * @return the java color used in drawing on Canvas
     */
    @Override
    public Color getGraphicsColor(PlayerColor color)
    {
        return getGraphicsColor(color,RED_PIECE_GRAPHICS_COLOR,BLACK_PIECE_GRAPHICS_COLOR);
    }
    
    /**
     * Gets the direction (up or down, forwards or backwards) the PlayerColor is assigned <br>
     * - With theses properties, you can set white to move up, or black to move up
     *   for a local game screen
     * @param color Color of which to get the direction
     * @return directions that this color is moving
     */    
    public Direction getColorDirection(PlayerColor color)
    {
        return getColorDirection(color,RED_DIRECTION,BLACK_DIRECTION);
    }
}
