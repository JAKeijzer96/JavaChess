package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Knight extends Piece {
    
    public Knight (char color) {
        super(color);
        this.name = "Knight";
    }

    public boolean legalMove(Board board, Square start, Square end) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (start.equals(end) || (end.getPiece() != null && this.color == end.getPiece().getColor()))
            return false;
        // If Math.abs(difference in file * difference in rank) == 2, it's a legal knight move
        if ( Math.abs( (start.getFile() - end.getFile()) * (start.getRank() - end.getRank()) ) == 2 )
            return true;
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}
