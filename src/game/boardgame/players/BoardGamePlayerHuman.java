package game.boardgame.players;

import game.Game;
import game.boardgame.graphics.GraphicsBoard;
import game.moves.Move;
import game.players.PlayerHuman;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * human players on a local screen for board games
 * @author devang
 */
public abstract class BoardGamePlayerHuman extends PlayerHuman implements KeyListener, MouseListener, MouseMotionListener {
    protected abstract List<Move> getValidMoves(Location location);
    
    protected final GraphicsBoard gui;

    /**
     * constructor for a local player
     * @param game official game state [holding current player's turn and current board state]
     * @param gui Java Canvas object and GUI for this local player's use
     * @param color identifying color for this player (red,black), (white,black), etc...
     */
    public BoardGamePlayerHuman(Game game, GraphicsBoard gui, PlayerColor color)
    {
        super(game,color);
        this.gui = gui;
        if (this.gui != null)
        {
            this.gui.addKeyListener(this);
            this.gui.addMouseListener(this);
            this.gui.addMouseMotionListener(this);
        }
    }
    
    /**
     * disposes this player's gui and destroys all objects for this player
     */
    @Override
    public void terminate()
    {
        if (gui != null)
            gui.dispose();
    }
    
    /**
     * erases all indications of a hovered space<br>
     * - only one space can be hovered-over at a time<br>
     * - a hovered-over space is where the player will select a piece
     *   or location to move a piece
     */
    public void removeHoveredSpace()
    {
        if (gui != null)
            gui.removeHovers();
    }

    /**
     * gets the space that is currently hovered-over
     * @return Location of the currently hovered-over space
     */
    public Location getHoveredSpace()
    {
        if (gui != null)
            return gui.getHoveredSpace();
        return null;
    }
    
    /**
     * sets the space, in the gui, that is currently hovered over
     * @param location Location of the newly hovered-over space
     */
    public void setHoveredSpace(Location location)
    {
        if (gui != null)
            gui.setHoveredSpace(location);
    }
    
    /**
     * gets the currently selected space<br>
     * - a selected space is the initiation for a new move<br>
     * - after a space is selected, the game can evaluate valid moves
     * @return Location of the currently selected space
     */
    public Location getSelectedSpace()
    {
        if (gui != null)
            return gui.getSelectedSpace();
        return null;
    }
    
    /**
     * sets the location for a selected space<br>
     * - this is the initiation for a new move
     * @param location Location of the newly-selected space
     */
    public void setSelectedSpace(Location location)
    {
        if (gui != null)
            gui.setSelectedSpace(location);
    }
    
    /**
     * removes the selection of a space from the board [and then no spaces will be selected]
     */
    public void removeSelectedSpace()
    {
        if (gui != null)
            gui.removeSelections();
    }
    
    /**
     * removes highlights from all spaces
     */
    public void removeHighlights()
    {
        if (gui != null)
            gui.removeHighlights();
    }
    
    /**
     * sets a space to be highlighted<br>
     * - after a space is selected, and then possible moves are evaluated,
     *   valid move locations are set to be highlighted to let the player
     *   know what spaces they can move to
     * @param location Location of a space to highlight
     */
    public void setHighlightedSpace(Location location)
    {
        if (gui != null)
            gui.setHighlightedSpace(location);
    }

    /**
     * After an opponent makes a move, the local player's game state calls this
     * method to update this player's gui with the new move
     * @param move Move that the opponent has just made
     */
    public void persistMove(Move move)
    {   // call this method after Game state updated with a new move
        // GUI screen refresh
        if (gui != null)
            gui.repaint();
    }
    
    /**
     * When this player performs a move on their local GUI canvas, this method
     * will apply that move to the current player's game state, which will then
     * send that move to this player's opponent
     * @param move
     */
    public void commitMove(Move move)
    {   // call this method after GUI move selection
        game.commitMove(this,move);
    }

    public void repaint()
    {
        if (gui != null) gui.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (game.getCurrentPlayer() != this) return;

        Location hoveredSpace  = Location.copy(getHoveredSpace());
        Location selectedSpace = Location.copy(getSelectedSpace());

        handleSpaceSelection(hoveredSpace,selectedSpace);
        
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
            
        if (game.getCurrentPlayer() != this) return;

        Location hoveredSpace  = Location.copy(getHoveredSpace());
        Location selectedSpace = Location.copy(getSelectedSpace());
        
        int mouseX = e.getX();
        int mouseY = e.getY();
        
        hoveredSpace = getLocationOfSpaceAt(mouseX,mouseY);
        
        if (hoveredSpace == null)
            hoveredSpace = Location.copy(getHoveredSpace());
        
        if (!hoveredSpace.equals(getHoveredSpace()))
        {
            setHoveredSpace(hoveredSpace);
            repaint();
        }
    }
    
    public Location getLocationOfSpaceAt(int x, int y)
    {
        return gui.getLocationOfSpaceAt(x,y);
    }
    
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    
    /**
     * Handles user input of the game board:<br>
     * - if it is not this player's turn: does nothing<br>
     * - if no space is hovered, highlighted or selected, when Enter is pressed 
     *   the hovered-over space will be evaluated for possible legal moves<br>
     * - if a space has been selected and legal moves are possible, when Enter 
     *   is pressed on a highlighted Space (indicating a legal move), that Move 
     *   will be committed to the current Game state, and then persisted to an
     *   opponent's representation of the current Game state<br>
     * - up,left,down,right moves the hovered-over space
     * 
     * @param e the KeyEvent that was triggered: looking for up,down,left,right, and enter
     */
    public void keyPressed(KeyEvent e) {
        
        if (game.getCurrentPlayer() != this) return;

        Location hoveredSpace  = Location.copy(getHoveredSpace());
        Location selectedSpace = Location.copy(getSelectedSpace());
        
        switch (e.getKeyCode())
        {
            case VK_DOWN:
                hoveredSpace = Location.down(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copy(getHoveredSpace());
                setHoveredSpace(hoveredSpace);
                break;
            case VK_UP:
                hoveredSpace = Location.up(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copy(getHoveredSpace());
                setHoveredSpace(hoveredSpace);
                break;
            case VK_LEFT:
                hoveredSpace = Location.left(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copy(getHoveredSpace());
                setHoveredSpace(hoveredSpace);
                break;
            case VK_RIGHT:
                hoveredSpace = Location.right(hoveredSpace);
                if (hoveredSpace == null)
                    hoveredSpace = Location.copy(getHoveredSpace());
                setHoveredSpace(hoveredSpace);
                break;
            case VK_ENTER:
                handleSpaceSelection(hoveredSpace,selectedSpace);
        }
        
        repaint();
    }
    
    protected abstract void handleSpaceSelection(Location hoveredSpace, Location selectedSpace);

}
