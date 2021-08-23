package Pieces;

import Chess.Board;
import Chess.Square;

public class Knight extends Piece {
    
    /**
     * Knight object for a game of Chess
     * @param color the color of the Knight, either 'W' or 'B'
     */
    public Knight (char color) {
        super(color);
        this.name = (this.color == 'W') ? 'N' : 'n';
    }

    /**
     * <p> Checks if a move from startSquare to endSquare is legal for a Knight. </p>
     * 
     * <p> Knights can move two Squares horizontally and one Square vertically,
     * or two Squares vertically and one Square Horizontally, in the shape of
     * an L. Knights are the only Pieces that can jump over other Pieces, so
     * there is no need for collision checking. A Knight move is legal if
     * Math.abs(difference in file * difference in rank) == 2 </p>
     * @param board the Board the game is played on
     * @param startSquare the Square the Knight is on
     * @param endSquare the Square the Knight tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) || (endSquare.isOccupied() && this.color == endSquare.getPieceColor()))
            return false;
        return Math.abs(
            (startSquare.getFile() - endSquare.getFile()) *
            (startSquare.getRank() - endSquare.getRank()) ) == 2;
    }

    /**
     * Convenience method, gets the Squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Knight is on
     * @param endString String representation of the Square the Knight tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }
}
