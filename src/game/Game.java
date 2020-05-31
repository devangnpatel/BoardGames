package game;

import game.moves.Move;
import game.players.Player;
import javax.swing.JOptionPane;

/**
 * Primary object for a game
 * - holds a list of players:
 *   2 players for Chess, Checkers and Othello
 * - maintains the index of the player who has the current move
 * - requires methods to initialize a game object, player objects and gui
 * - identifies method to toggle the player who has the current move
 * - identifies method to check if the game is over
 * - identifies "commitMove" which applies a move to the current board state
 * - identifies "persistMove" which sends a newly-committed move to an opponent,
 *   where an opponent can be local, remote, or AI-CPU controlled
 * 
 * @author devang
 */
public abstract class Game {
    public abstract void    commitMove(Player player, Move move);
    public abstract void    persistMove(Player player, Move move);
    public abstract void    initializeGame();
    public abstract void    initializePieces();
    public abstract void    initializeProperties();
    public abstract void    initializePlayers();
    public abstract void    initializeGUI();
    protected abstract void initializeBoard();
    public abstract void    togglePlayer();
    public abstract boolean checkGameOver();

    protected Player   currentPlayer;
    protected Player[] players;
    
    public Game()
    {

    }
     
    public final Player getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    protected boolean gameOverWindow(String gameOverText)
    {
        JOptionPane.showMessageDialog(null,
            gameOverText,
            "game over",
            JOptionPane.PLAIN_MESSAGE);
        return true;
    }
    
    protected void terminate()
    {
        for (Player player : players)
        {
            if (player != null) player.terminate();
        }
        if (currentPlayer != null)
        {
            currentPlayer.terminate();
        }
    }
}
