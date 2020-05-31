package chess.real;

import chess.ChessGame;
import chess.gui.ChessGraphicsBoard;
import chess.players.ChessPlayerHuman;
import game.Game;
import game.boardgame.pieces.Piece;
import game.utility.Location;
import game.utility.Properties.PlayerColor;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.MouseEvent;

/**
 * Inherits from a Player, a Board Game Player to provide same
 * interface for a local human player, network player, or phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class PlayerPhotosEditor extends ChessPlayerHuman {
    private boolean        editingEnabled;
    private ChessRealPiece activeGraphicsPiece;
    
    private PlayerPhotosEditor(Game game, ChessGraphicsBoard gui, PlayerColor color)
    {
        super(game,gui,color);
        editingEnabled = false;
        activeGraphicsPiece = null;
    }
    
    /**
     * Creates a Human Player object, with the game state, GUI, and this player's color
     * @param game official game state that maintains a board and the current player's turn
     * @param gui canvas drawing object to draw the graphics
     * @param color color of this player (red, black), (white, red), etc.
     * @return Newly created Human Player object
     */
    public static ChessPlayerHuman create(Game game, ChessGraphicsBoard gui, PlayerColor color)
    {
        return new PlayerPhotosEditor(game,gui,color);
    }

    @Override
    protected void handleSpaceSelection(Location hoveredSpace, Location selectedSpace)
    {
        super.handleSpaceSelection(hoveredSpace, selectedSpace);
    }
    
    private void shiftPhotoPosition(ChessRealPiece graphicsPiece, int x, int y)
    {
        if (graphicsPiece == null) return;
        graphicsPiece.shiftOffset(x, y);
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {        
        switch (e.getKeyChar())
        {
            case 'd':
                if (editingEnabled) return;
                
                Location hoveredSpace  = Location.copy(getHoveredSpace());
                
                if (((ChessGame)game).getBoardState().isEmpty(hoveredSpace)) return;

                Piece piece = ((ChessGame)game).getBoardState().getPiece(hoveredSpace);
                ChessRealPiece graphicsPiece = ((ChessRealBoard)gui).getGraphicsPiece(hoveredSpace);
                if ((piece == null) || (graphicsPiece == null)) return;
                activeGraphicsPiece = graphicsPiece;
                System.out.println("edit ENABLED: " + activeGraphicsPiece.toString());
                editingEnabled = true;
                return;
                
            case 'f':
                System.out.println("edit DISABLED: " + activeGraphicsPiece.toString());
                activeGraphicsPiece = null;
                editingEnabled = false;
                return;
        }
        if (editingEnabled) return;
        
        super.keyTyped(e);
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (editingEnabled)
        {
            switch (e.getKeyCode())
            {
                case VK_LEFT:
                    shiftPhotoPosition(activeGraphicsPiece,-1, 0);
                    gui.repaint();
                    break;
                case VK_RIGHT:
                    shiftPhotoPosition(activeGraphicsPiece, 1, 0);
                    gui.repaint();
                    break;
                case VK_UP:
                    shiftPhotoPosition(activeGraphicsPiece, 0,-1);
                    gui.repaint();
                    break;
                case VK_DOWN:
                    shiftPhotoPosition(activeGraphicsPiece, 0, 1);
                    gui.repaint();
                    break;
            }
        }
        else
        {
            super.keyPressed(e);
        }
    }    
    
    @Override
    public void keyReleased(KeyEvent e) { super.keyReleased(e); }

    @Override
    public void mouseClicked(MouseEvent e) { if (!editingEnabled) super.mouseClicked(e); }

    @Override
    public void mouseReleased(MouseEvent e) { if (!editingEnabled) super.mouseReleased(e); }

    @Override
    public void mouseEntered(MouseEvent e) { if (!editingEnabled) super.mouseEntered(e); }

    @Override
    public void mouseExited(MouseEvent e) { if (!editingEnabled) super.mouseExited(e); }

    @Override
    public void mouseDragged(MouseEvent e) { if (!editingEnabled) super.mouseDragged(e); }

    @Override
    public void mousePressed(MouseEvent e) { if (!editingEnabled) super.mousePressed(e);}

    @Override
    public void mouseMoved(MouseEvent e) { if (!editingEnabled) super.mouseMoved(e); }    
}
