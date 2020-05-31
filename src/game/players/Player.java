package game.players;

import game.Game;
import game.moves.Move;
import game.utility.Properties.PlayerColor;

/**
 * Overarching superclass for all players: local, network, AI, board-game, card-game
 * @author devang
 */
public abstract class Player {    
    public abstract void terminate();
    public abstract void persistMove(Move move);
    public abstract void commitMove(Move move);
    protected final Game        game;
    protected final PlayerColor color;
    
    protected Player(Game game, PlayerColor color)
    {
        this.game  = game;
        this.color = color;
    }
    
    public PlayerColor getColor()
    {
        return color;
    }

}
