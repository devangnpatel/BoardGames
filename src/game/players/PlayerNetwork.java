package game.players;

import game.Game;
import game.network.Client;
import game.utility.Properties.PlayerColor;

/**
 * Overarching class for any players that use Network-Clients
 * @author devang
 */
public abstract class PlayerNetwork extends Player {

    protected Client client;
    
    public PlayerNetwork(Game game, Client networkClient, PlayerColor color)
    {
        super(game,color);
        client = networkClient;
        client.setPlayer(this);
    }

}
