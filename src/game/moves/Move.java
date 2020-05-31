package game.moves;

import java.io.Serializable;

/**
 * Overarching interface for all moves in games
 * - valuable in serialization when transmitting Moves over network in client-server
 *   socket communication
 * @author devang
 */
public abstract class Move implements Serializable {
    
    // meant to override for specific game-type
    public Move rotateMove()
    {
        return this;
    }
    
    public Move()
    {
        
    }
}
