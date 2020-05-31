package chess.players.ai;

import chess.ChessBoardState;
import chess.pieces.ChessPiece;
import chess.pieces.PieceBishop;
import chess.pieces.PieceKnight;
import chess.pieces.PiecePawn;
import chess.pieces.PieceQueen;
import chess.pieces.PieceRook;
import chess.pieces.PieceKing;
import game.utility.Location;
import game.utility.Properties.PlayerColor;

/**
 *
 * @author devang
 */
public class ChessAI {
    public static int maxDepth = 3;
    public static int maxTime = 10000;
    
    public static int evaluate(ChessBoardState boardState,PlayerColor playerColor)
    {
        int score = 0;
        
        for (Location location : Location.allLocations())
        {
            ChessPiece piece = (ChessPiece)boardState.getPiece(location);
            score += scorePieceValue(piece,playerColor);
        }
        
        return score;
    }
    
    private static int scorePieceValue(ChessPiece piece,PlayerColor playerColor)
    {
        int score = 0;
        
        if (piece == null)
        {
            // do nothing
        }
        else if (!piece.getColor().equals(playerColor))
        {
            if (piece instanceof PiecePawn) score -= 10;
            if (piece instanceof PieceKnight) score -= 30;
            if (piece instanceof PieceBishop) score -= 30;
            if (piece instanceof PieceRook) score -= 50;
            if (piece instanceof PieceQueen) score -= 90;
            if (piece instanceof PieceKing) score -= 900;
        }
        else if (piece.getColor().equals(playerColor))
        {
            if (piece instanceof PiecePawn) score += 10;
            if (piece instanceof PieceKnight) score += 30;
            if (piece instanceof PieceBishop) score += 30;
            if (piece instanceof PieceRook) score += 50;
            if (piece instanceof PieceQueen) score += 90;
            if (piece instanceof PieceKing) score += 900;
        }
        
        return score;
    }
}
