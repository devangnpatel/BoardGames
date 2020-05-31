package checkers.players;

import game.Game;
import game.boardgame.players.BoardGamePlayerNetwork;
import game.moves.Move;
import game.network.Client;
import game.utility.Properties.PlayerColor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Inherits from a Player, a Board Game Player to provide same
 * interface for a local human player, network player, or phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class CheckersPlayerNetwork extends BoardGamePlayerNetwork {
    
    private CheckersPlayerNetwork(Game game, Client networkClient, PlayerColor color)
    {
        super(game,networkClient,color);
    }
    
    /**
     * Creates a client-server Network Player, in Object factory fashion
     * @param game the game state representation
     * @param networkClient network client, with server connection
     * @param color the color for this networked-player
     * @return a newly constructed Network Player
     */
    public static CheckersPlayerNetwork create(Game game, Client networkClient, PlayerColor color)
    {
        return new CheckersPlayerNetwork(game,networkClient,color);
    }
    
    /**
     * Sends move to server via this network-client, which then sends the move
     * to the opponent's network-client, which then updates the remote Game state
     * 
     * @param move Move that is made on the current game-state
     */
    public void persistMove(Move move)
    {
        String loggerMsg = "network client persist move";
        Logger.getLogger(CheckersPlayerNetwork.class.getName()).log(Level.FINE,loggerMsg);
        super.persistMove(move);
    }
    
    /**
     * Called after this network-client receives a move from the Server, 
     * which received the move from the remote player's network client, after
     * the remote networked player has made it's move
     * @param move Move that the remote opponent made
     */
    public void commitMove(Move move)
    {
        String loggerMsg = "network client commit move";
        Logger.getLogger(CheckersPlayerNetwork.class.getName()).log(Level.FINE,loggerMsg);
        super.commitMove(move);
    }

}
