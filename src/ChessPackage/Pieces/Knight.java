package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Knight extends Piece {
    
    public Knight (char color) {
        super(color);
        this.name = "Knight";
    }

    public boolean legalMove(Board board, Square start, Square end) {
        if (start.equals(end)) {
            return false;
        }
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}
