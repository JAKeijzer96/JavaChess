package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class King extends Piece {

    public King (char color) {
        super(color);
        this.name = "King";
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
