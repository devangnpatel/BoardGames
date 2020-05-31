package checkers.players;

import checkers.CheckersGame;
import checkers.players.ai.CheckersAI;
import game.Game;
import game.boardgame.players.BoardGamePlayerCPU;
import game.moves.Move;
import game.utility.Properties.PlayerColor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Inherits from a PlayerCPU, a Board Game Player CPU to provide same
 * interface for a phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class CheckersPlayerCPU extends BoardGamePlayerCPU {
    
    private CheckersPlayerCPU(Game game, PlayerColor color)
    {
        super(game,color);
    }
    
    /**
     * Creates a Phantom AI Player, in Object factory fashion
     * @param game the game state representation
     * @param color the color for this AI-player
     * @return a newly constructed Phantom AI CPU Player
     */
    public static CheckersPlayerCPU create(Game game, PlayerColor color)
    {
        return new CheckersPlayerCPU(game,color);
    }
    
    /**
     * Sends move to update this Phantom AI's game state Game state
     * and indicates it is this Phantom AI's turn to Move
     * @param move Move that is made by the Human Player on the current game-state
     */
    public void persistMove(Move move)
    {
        String loggerMsg = "human persist move: AI makes next move";
        Logger.getLogger(CheckersPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
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
        Logger.getLogger(CheckersPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        if (move == null)
        {
            System.err.println("ALERT: no valid moves possible");
            return;
        }
        super.commitMove(move);
    }
    
    public void evaluateNextMove()
    {
        CheckersAI checkersAI = new CheckersAI(this,((CheckersGame)game).getBoardState());
        Thread evaluateMoveThread = new Thread(checkersAI);
        evaluateMoveThread.start();
    }
}
