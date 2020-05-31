package game.players;

import game.Game;
import game.utility.Properties.PlayerColor;

/**
 * Overarching class for any Human Players
 * @author devang
 */
public abstract class PlayerCPU extends Player {

    public PlayerCPU(Game game, PlayerColor color)
    {
        super(game,color);
    }
}
