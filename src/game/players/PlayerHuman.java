package game.players;

import game.Game;
import game.utility.Properties.PlayerColor;

/**
 * Overarching class for any Human Players
 * @author devang
 */
public abstract class PlayerHuman extends Player {

    public PlayerHuman(Game game, PlayerColor color)
    {
        super(game,color);
    }
}
