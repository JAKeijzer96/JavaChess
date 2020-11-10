package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Rook extends Piece {

    public Rook (char color) {
        super(color);
        this.name = "Rook";
    }

    public boolean legalMove(Board board, Square start, Square end) {
        /**
         * Rooks move along files and ranks
         * if start.file == end.file its gucci
         * if start.rank == end.rank its gucci
         * 'moving' to the same square is not allowed
         */
        if (start.equals(end))
            return false;
        if ( (start.getFile() == end.getFile()) || (start.getRank() == end.getRank()) )
            return true;
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}
