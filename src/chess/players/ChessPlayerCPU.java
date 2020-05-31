package chess.players;

import chess.ChessBoardState;
import chess.ChessGame;
import chess.moves.ChessMove;
import chess.pieces.ChessPiece;
import chess.players.ai.ChessAI;
import game.Game;
import game.boardgame.BoardState;
import game.boardgame.players.BoardGamePlayerCPU;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Inherits from a PlayerCPU, a Board Game Player CPU to provide same
 * interface for a phantom AI player
 * and same interface across any game: Checkers, Chess, Othello, Poker, Chinese-Checkers, etc.
 * @author devang
 */
public class ChessPlayerCPU extends BoardGamePlayerCPU {
    
    private ChessPlayerCPU(Game game, PlayerColor color)
    {
        super(game,color);
    }
    
    /**
     * Creates a Phantom AI Player, in Object factory fashion
     * @param game the game state representation
     * @param color the color for this AI-player
     * @return a newly constructed Phantom AI CPU Player
     */
    public static ChessPlayerCPU create(Game game, PlayerColor color)
    {
        return new ChessPlayerCPU(game,color);
    }
    
    /**
     * Sends move to update this Phantom AI's game state Game state
     * and indicates it is this Phantom AI's turn to Move
     * @param move Move that is made by the Human Playeron the current game-state
     */
    public void persistMove(Move move)
    {
        String loggerMsg = "human persist move: AI makes next move";
        Logger.getLogger(ChessPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        //super.persistMove(move);
        makeNextMove();
        
    }
    
    /**
     * Called after this Phantom AI picks a move
     * @param move Move that this phantom AI made
     */
    public void commitMove(Move move)
    {
        String loggerMsg = "Phantom AI commit move";
        Logger.getLogger(ChessPlayerCPU.class.getName()).log(Level.FINE,loggerMsg);
        super.commitMove(move);
    }
    
    public Move makeNextMove()
    {
        Move move = evaluateBestMove();
        if (move == null)
        {
            System.err.println("ERROR: no valid moves possible");
        }
        else
        {
            commitMove(move);
        }
        return move;
    }
    
    private int evaluateScore(ChessBoardState boardState)
    {
        return ChessAI.evaluate(boardState,getColor());
    }
    
    public class MoveScore {
        public Move move;
        public int score;
        
        public MoveScore(Move move, int score)
        {
            this.move  = move;
            this.score = score;
        }
    }
    
    private MoveScore alphaBeta(int depth, int alpha, int beta, PlayerColor playerColor, ChessBoardState boardState)
    {
        if (depth == 0) return new MoveScore(null,evaluateScore(boardState));
        
        List<Move> validMoves = getValidMoves(boardState,playerColor);
        if (validMoves == null) return new MoveScore(null,evaluateScore(boardState));
        
        // maximizing
        if (playerColor == this.getColor())
        {
            int bestScore = Integer.MIN_VALUE;
            Move bestMove = null;
            
            for (Move move : validMoves)
            {
                ChessBoardState tempBoardState = (ChessBoardState)BoardState.copy(boardState);
                ((ChessMove)move).commitMove(tempBoardState);
                
                MoveScore moveScorePair = alphaBeta(depth-1,alpha,beta,Properties.oppositeColor(playerColor),tempBoardState);
                int score = moveScorePair.score;
                
                if (score > bestScore)
                {
                    bestScore = score;
                    bestMove  = move;
                }
                alpha = Math.max(alpha,score);
                
                if (alpha >= beta) break;
            }
            return new MoveScore(bestMove,bestScore);
        }
        // minimizing
        else //playerColor == Properties.oppositeColor(this.getColor())
        {
            int bestScore = Integer.MAX_VALUE;
            Move bestMove = null;
            
            for (Move move : validMoves)
            {
                ChessBoardState tempBoardState = (ChessBoardState)BoardState.copy(boardState);
                ((ChessMove)move).commitMove(tempBoardState);
                
                MoveScore moveScorePair = alphaBeta(depth-1,alpha,beta,Properties.oppositeColor(playerColor),tempBoardState);
                int score = moveScorePair.score;
                
                if (score < bestScore)
                {
                    bestScore = score;
                    bestMove  = move;
                }
                
                beta = Math.min(beta,score);
                
                if (alpha >= beta) break;
            }
            return new MoveScore(bestMove,bestScore);
        }
    }
    
    private Move evaluateBestMove()
    {
        MoveScore moveScorePair = alphaBeta(ChessAI.maxDepth,Integer.MIN_VALUE,Integer.MAX_VALUE,this.getColor(),((ChessGame)game).getBoardState());
        return moveScorePair.move;
    }

        
    private List<Move> getValidMoves(ChessBoardState boardState,PlayerColor playerColor)
    {
        List<Move> validMoves = new ArrayList<>();
        List<Move> moves      = null;
        
        for (Location location : Location.allLocations())
        {
            ChessPiece piece = (ChessPiece)boardState.getPiece(location);
            if (piece != null && playerColor.equals(piece.getColor()))
            {
                moves = piece.getValidMoves(location,boardState,((ChessGame)game).getGameHistory());
                if (moves != null && !moves.isEmpty())
                    validMoves.addAll(moves);
            }
        }
        
        return validMoves;
    }
}
