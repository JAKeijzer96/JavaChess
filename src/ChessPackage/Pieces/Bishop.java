package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Bishop extends Piece {
    
    public Bishop (char color) {
        super(color);
        this.name = "Bishop";
    }

    public boolean legalMove(Board board, Square start, Square end) {
        /**
         * Bishops move along diagonals. Move is legal when:
         * (difference in file) / (difference in rank) == 1 or -1
         */
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (start.equals(end) || (end.getPiece() != null && this.color == end.getPiece().getColor()))
            return false;
        if ( Math.abs( (start.getFile() - end.getFile()) / (start.getRank() - end.getRank()) ) == 1 )
            return true;
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}

