package checkers.players.ai;

import checkers.moves.CheckerMove;
import checkers.pieces.PieceKing;
import checkers.pieces.PieceRegular;
import checkers.players.CheckersPlayerCPU;
import game.boardgame.BoardState;
import game.boardgame.pieces.Piece;
import game.moves.Move;
import game.utility.Location;
import game.utility.Properties;
import game.utility.Properties.PlayerColor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devang
 */
public class CheckersAI extends Thread {
    public static int maxDepth = 5; // needs to be an even integer?
    public static int maxTime = 10000;
    
    private CheckersPlayerCPU checkersPlayerCPU;
    private PlayerColor       playerCPUColor;
    private BoardState        currentBoardState;
    
    public CheckersAI(CheckersPlayerCPU player, BoardState boardState)
    {
        checkersPlayerCPU = player;
        playerCPUColor    = player.getColor();
        currentBoardState = boardState;
    }
    
    public void run()
    {
        Move move = evaluateBestMove(currentBoardState,playerCPUColor);
        checkersPlayerCPU.commitMove(move);
    }
    
    private Move evaluateBestMove(BoardState boardState, PlayerColor playerColor)
    {
        MoveScore moveScorePair = alphaBeta(maxDepth,Integer.MIN_VALUE,Integer.MAX_VALUE,playerColor,boardState);
        return moveScorePair.move;
    }
        
    private int evaluateScore(BoardState boardState, PlayerColor playerColor)
    {
        int playerScore = 0;
        int opponentScore = 0;
        int score = 0;
        
        for (Location location : Location.allLocations())
        {
            Piece piece = (Piece)boardState.getPiece(location);
            if (piece != null)
            {
                playerScore += scorePlayerPieceValue(piece,playerColor);
                opponentScore += scoreOpponentPieceValue(piece,playerColor);
            }
        }
        
        score+=playerScore;
        score+=opponentScore;
        
        /*if (playerScore == 0) score += -1000;
        if (opponentScore == 0) score += 1000;*/
        return score;
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
        if (depth == 0) return new MoveScore(null,evaluateScore(boardState,playerCPUColor));
        
        List<Move> validMoves = getValidMoves(boardState,playerColor);
        if (validMoves == null || (validMoves.isEmpty()))
        {
            int score = evaluateScore(boardState,playerCPUColor);
            return new MoveScore(null,score);
            /*if (playerCPUColor.equals(playerColor))
                return new MoveScore(null,-1000);
            else
                return new MoveScore(null,1000);*/
        }
        
        // maximizing
        if (playerCPUColor.equals(playerColor))
        {
            int bestScore = Integer.MIN_VALUE;
            Move bestMove = null;
            
            for (Move move : validMoves)
            {
                BoardState tempBoardState = (BoardState)BoardState.copy(boardState);
                ((CheckerMove)move).commitMove(tempBoardState);
                
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
                BoardState tempBoardState = (BoardState)BoardState.copy(boardState);
                ((CheckerMove)move).commitMove(tempBoardState);
                
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
    
    private List<Move> getValidMoves(BoardState boardState,PlayerColor playerColor)
    {
        List<Move> validMoves = new ArrayList<>();
        List<Move> moves      = null;
        
        for (Location location : Location.allLocations())
        {
            Piece piece = (Piece)boardState.getPiece(location);
            if (piece != null && playerColor.equals(piece.getColor()))
            {
                moves = piece.getValidMoves(location,boardState);
                if (moves != null && !moves.isEmpty())
                    validMoves.addAll(moves);
            }
        }
        
        return validMoves;
    }
 
    private static int scorePlayerPieceValue(Piece piece,PlayerColor playerColor)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else if (piece.getColor() == playerColor)
        {
            if (piece instanceof PieceRegular) score += 15;
            if (piece instanceof PieceKing) score += 40;
        }
        
        return score;
    }
    
    private static int scoreOpponentPieceValue(Piece piece,PlayerColor playerColor)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else if (piece.getColor() != playerColor)
        {
            if (piece instanceof PieceRegular) score -= 10;
            if (piece instanceof PieceKing) score -= 50;
        }
        
        return score;
    }
}
