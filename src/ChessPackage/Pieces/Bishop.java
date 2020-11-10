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
        if (start.equals(end))
            return false;
        if ( Math.abs( (start.getFile() - end.getFile()) / (start.getRank() - end.getRank()) ) == 1 )
            return true;
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}

