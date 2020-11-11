package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Knight extends Piece {
    
    public Knight (char color) {
        super(color);
        this.name = "Knight";
    }

    /**
     * legalMove method for the Knight class.
     * Knights can move two Squares horizontally and one Square vertically,
     * or two Squares vertically and one Square Horizontally, in the shape of
     * an L. Knights are the only Pieces that can jump over other Pieces, so
     * there is no need for collision checking. A Knight move is legal if
     * Math.abs(difference in file * difference in rank) == 2
     * @param board the Board the game is played on
     * @param startSquare the Square the Knight is on
     * @param endSquare the Square the Knight tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square start, Square end) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (start.equals(end) || (end.getPiece() != null && this.color == end.getPiece().getColor()))
            return false;
        if ( Math.abs( (start.getFile() - end.getFile()) * (start.getRank() - end.getRank()) ) == 2 )
            return true;
        return false;
    }

    /**
     * Convenience method, gets the squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Knight is on
     * @param endString String representation of the Square the Knight tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }
}
