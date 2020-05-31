package othello.players;

import game.Game;
import game.boardgame.players.BoardGamePlayerCPU;
import game.moves.Move;
import game.utility.Properties.PlayerColor;
import java.util.logging.Level;
import java.util.logging.Logger;
import othello.OthelloGame;
import othello.players.ai.OthelloAI;

/**
 * Inherits from a PlayerCPU, a Board Game Player CPU to provide same
 * interface for a phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class OthelloPlayerCPU extends BoardGamePlayerCPU {
        
    private OthelloPlayerCPU(Game game, PlayerColor color)
    {
        super(game,color);
    }
    
    /**
     * Creates a Phantom AI Player, in Object factory fashion
     * @param game the game state representation
     * @param color the color for this AI-player
     * @return a newly constructed Phantom AI CPU Player
     */
    public static OthelloPlayerCPU create(Game game, PlayerColor color)
    {
        return new OthelloPlayerCPU(game,color);
    }
    
    /**
     * Sends move to update this Phantom AI's game state Game state
     * and indicates it is this Phantom AI's turn to Move
     * @param move Move that is made by the Human Player on the current game-state
     */
    public void persistMove(Move move)
    {
        String loggerMsg = "human persist move: AI makes next move";
        Logger.getLogger(OthelloPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        super.persistMove(move);

        evaluateNextMove();
    }
    
    /**
     * Called after this Phantom AI picks a move
     * @param move Move that this phantom AI made
     */
    public void commitMove(Move move)
    {
        String loggerMsg = "Phantom AI commit move";
        Logger.getLogger(OthelloPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        super.commitMove(move);
    }
    
    public void evaluateNextMove()
    {
        OthelloAI othelloAI = new OthelloAI(this,((OthelloGame)game).getBoardState());
        Thread evaluateMoveThread = new Thread(othelloAI);
        evaluateMoveThread.start();
    }
    
    public Move commitNextMove(Move move)
    {
        if (move == null)
        {
            System.err.println("ALERT: no valid moves possible");
        }
        else
        {
            commitMove(move);
        }
        return move;
    }
}
