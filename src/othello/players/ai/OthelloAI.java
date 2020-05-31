package othello.players.ai;

import game.boardgame.BoardState;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.util.ArrayList;
import java.util.List;
import othello.moves.OthelloMove;
import othello.pieces.OthelloPiece;
import othello.players.OthelloPlayerCPU;

/**
 *
 * @author devang
 */
public class OthelloAI extends Thread {
    public static int maxDepth = 7; // does this work differently for even,odd integers?
    public static int maxTime = 10000;
    
    public static int[][] weights = {{ 4,-3, 2, 2, 2, 2,-3, 4},
                                     {-3,-4,-1,-1,-1,-1,-4,-3},
                                     { 2,-1, 1, 0, 0, 1,-1, 2},
                                     { 2,-1, 1, 0, 0, 1,-2, 2},
                                     { 2,-1, 1, 0, 0, 1,-2, 2},
                                     { 2,-1, 1, 0, 0, 1,-1, 2},
                                     {-3,-4,-1,-1,-1,-1,-4,-3},
                                     { 4,-3, 2, 2, 2, 2,-3, 4}};
    
    private OthelloPlayerCPU othelloPlayerCPU;
    private PlayerColor      playerCPUColor;
    private BoardState       currentBoardState;
    
    public OthelloAI(OthelloPlayerCPU player, BoardState boardState)
    {
        othelloPlayerCPU = player;
        playerCPUColor = player.getColor();
        currentBoardState = boardState;
    }
    
    public void run()
    {
        Move move = evaluateBestMove(currentBoardState);
        othelloPlayerCPU.commitNextMove(move);
    }

    private int evaluateScore(BoardState boardState)
    {
        return evaluate(boardState,playerCPUColor);
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
    
    private MoveScore alphaBeta(int depth, int alpha, int beta, PlayerColor playerColor, BoardState boardState)
    {
        if (depth == 0) return new MoveScore(null,evaluateScore(boardState));
        
        List<Move> validMoves = getValidMoves(boardState,playerColor);
        if (validMoves == null || (validMoves.size() <= 0)) /* return new MoveScore(null,evaluateScore(boardState));*/
        {
            if (playerCPUColor.equals(playerColor))
                return new MoveScore(null,Integer.MIN_VALUE);
            else
                return new MoveScore(null,Integer.MAX_VALUE);
        }
        
        // maximizing
        if (playerCPUColor.equals(playerColor))
        {
            int bestScore = Integer.MIN_VALUE;
            Move bestMove = null;
            
            for (Move move : validMoves)
            {
                BoardState tempBoardState = (BoardState)BoardState.copy(boardState);
                ((OthelloMove)move).commitMove(tempBoardState);
                
                MoveScore moveScorePair = alphaBeta(depth-1,alpha,beta,Properties.oppositeColor(playerColor),tempBoardState);
                int score = moveScorePair.score;
                
                if (score >= bestScore)
                {
                    bestScore = score;
                    bestMove  = move;
                }
                alpha = Math.max(alpha,score);
                //alpha = Math.max(alpha,bestScore);
                
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
                BoardState tempBoardState = (BoardState)BoardState.copy(boardState);
                ((OthelloMove)move).commitMove(tempBoardState);
                
                MoveScore moveScorePair = alphaBeta(depth-1,alpha,beta,Properties.oppositeColor(playerColor),tempBoardState);
                int score = moveScorePair.score;
                
                if (score <= bestScore)
                {
                    bestScore = score;
                    bestMove  = move;
                }
                beta = Math.min(beta,score);
                //beta = Math.min(beta,bestScore);
                
                if (alpha >= beta) break;
            }
            return new MoveScore(bestMove,bestScore);
        }
    }
    
    public Move evaluateBestMove(BoardState boardState)
    {
        MoveScore moveScorePair = alphaBeta(maxDepth,Integer.MIN_VALUE,Integer.MAX_VALUE,playerCPUColor,boardState);
        return moveScorePair.move;
    }
        
    private List<Move> getValidMoves(BoardState boardState,PlayerColor playerColor)
    {
        List<Move> validMoves = new ArrayList<>();
        
        for (Location location : Location.allLocations())
        {
            Move move = new OthelloMove(playerCPUColor,location);
            if (((OthelloMove)move).validateMove(boardState))
                validMoves.add(move);
        }
        
        return validMoves;
    }
    
    public static int evaluate(BoardState boardState,PlayerColor playerColor)
    {
        int score = 0;
        
        for (Location location : Location.allLocations())
        {
            OthelloPiece piece = (OthelloPiece)boardState.getPiece(location);
            if (piece != null)
            {
                int y = Location.getCol(location);
                int x = Location.getRow(location);
                
                if (piece.getColor().compareTo(playerColor) == 0)
                    score += 10*weights[y][x];
                else
                    score -= 10*weights[y][x];
            }
        }
        
        return score;
    }

    public static int evaluate_old(BoardState boardState,PlayerColor playerColor)
    {
        int score = 0;
        int playerScore = 0;
        int opponentScore = 0;
        
        for (Location location : Location.allLocations())
        {
            OthelloPiece piece = (OthelloPiece)boardState.getPiece(location);
            if (piece != null)
            {
                playerScore += scorePlayerPieceValue(piece,playerColor);
                opponentScore += scoreOpponentPieceValue(piece,playerColor);
                // score += scoreCornerValue(piece,playerColor,location);
            }
        }
        score += playerScore;
        score += opponentScore;
        
        /*if (playerScore == 0) score = Integer.MIN_VALUE;
        if (opponentScore == 0) score = Integer.MAX_VALUE;*/
        
        return score;
    }
    
    private static int scorePlayerPieceValue(OthelloPiece piece,PlayerColor playerColor)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else if (piece.getColor().equals(playerColor))
        {
            score += 10;
        }
        
        return score;
    }
    
    private static int scoreOpponentPieceValue(OthelloPiece piece,PlayerColor playerColor)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else if (!piece.getColor().equals(playerColor))
        {
            score -= 10;
        }
        
        return score;
    }
    
    private static int scoreCornerValue(OthelloPiece piece,PlayerColor playerColor,Location location)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else if (!piece.getColor().equals(playerColor))
        {
            if (((Location.getCol(location)==0) && (Location.getRow(location)==0)) ||
                ((Location.getCol(location)==0) && (Location.getRow(location)==7)) ||
                ((Location.getCol(location)==7) && (Location.getRow(location)==0)) ||
                ((Location.getCol(location)==7) && (Location.getRow(location)==7)))
            score -= 100;
        }
        else if (piece.getColor().equals(playerColor))
        {
            if (((Location.getCol(location)==0) && (Location.getRow(location)==0)) ||
                ((Location.getCol(location)==0) && (Location.getRow(location)==7)) ||
                ((Location.getCol(location)==7) && (Location.getRow(location)==0)) ||
                ((Location.getCol(location)==7) && (Location.getRow(location)==7)))
            score += 100;
        }
        
        return score;
    }
}
