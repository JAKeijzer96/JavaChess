package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class King extends Piece {

    public King (char color) {
        super(color);
        this.name = "King";
    }

    public boolean legalMove(Board board, Square start, Square end) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (start.equals(end) || (end.getPiece() != null && this.color == end.getPiece().getColor()))
            return false;
        // If the difference in file is 0 or 1 and the difference in rank is 0 or 1
        if ( Math.abs(start.getFile() - end.getFile()) <= 1 &&
             Math.abs(start.getRank() - end.getRank()) <= 1 )
            return true;
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}
