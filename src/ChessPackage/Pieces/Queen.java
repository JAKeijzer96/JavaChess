package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Queen extends Piece {
    
    public Queen (char color) {
        super(color);
        this.name = "Queen";
    }

    public boolean legalMove(Board board, Square start, Square end) {
        /**
         * Queens move along diagonals, ranks and files
         * If a move is legal for a bishop or a rook, it's legal for a Queen
         */
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (start.equals(end) || (end.getPiece() != null && this.color == end.getPiece().getColor()))
            return false;
        // Allow horizontal and vertical moves
        if ( (start.getFile() == end.getFile()) || (start.getRank() == end.getRank()) )
            return true;
        // Allow diagonal moves
        if ( Math.abs( (start.getFile() - end.getFile()) / (start.getRank() - end.getRank()) ) == 1 )
            return true;
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}
