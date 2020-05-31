package game.boardgame.players;

import game.Game;
import game.moves.Move;
import game.network.Client;
import game.players.PlayerNetwork;
import game.utility.Properties.PlayerColor;

/**
 * Players that use Network-Clients in a board game
 * @author devang
 */
public class BoardGamePlayerNetwork extends PlayerNetwork {
    
    /**
     * constructor for this player
     * @param game official game [containing current player's turn and board state]
     * @param networkClient client used in socket communication for network game playing
     * @param color identifying color for the player identified by this class (red,black), (red,white),etc.
     */
    public BoardGamePlayerNetwork(Game game, Client networkClient, PlayerColor color)
    {
        super(game,networkClient,color);
    }

    /**
     * terminates the client, gui and game
     */
    @Override
    public void terminate()
    {
        if (client != null) client.endClientListener();
    }

    /**
     * when this player makes a move on the local game-state, this method transfers the Move
     * to the server, which then persists the move to the remote client, and then
     * to the remote player's game state
     * @param move Move which to make
     */
    @Override
    public void persistMove(Move move)
    {
        if (client != null) client.sendMoveMessage(move);
    }

    /**
     * when the remote player persists a move to the server, and then to this player's 
     * client, this method applies that move to this local game player's game state
     * @param move
     */
    @Override
    public void commitMove(Move move)
    {
        game.commitMove(this,move);
    }
}
