package game.boardgame;

import game.Game;
import game.utility.Properties;

/**
 * Primary object for a board game
 * - adds ownership of a board-state object that maintains the current state of the game board
 * 
 * @author devang
 */
public abstract class BoardGame extends Game {

    protected abstract BoardState getBoardState();

    protected Properties properties;
    
    public BoardGame()
    {
        super();
    }
}
