package Pieces;

import Chess.Board;
import Chess.Square;

public class King extends Piece {
    boolean isFirstMove;

    /**
     * King object for a game of Chess
     * @param color the color of the King, either 'W' or 'B'
     */
    public King (char color) {
        super(color);
        this.name = (this.color == 'W') ? 'K' : 'k';
        this.isFirstMove = true;
    }

    /**
     * <p> Checks if a move from startSquare to endSquare is legal for a King. </p>
     * 
     * <p> Kings can move along diagonals, files and ranks, but only one Square at
     * a time. A King move is legal if the difference in file is 0 or 1 and
     * the difference in rank is 0 or 1. Castling is handled in the ChessGame class
     * or any of its subclasses </p>
     * @param board the Board the game is played on
     * @param startSquare the Square the Rook is on
     * @param endSquare the Square the Rook tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        Piece endPiece = endSquare.getPiece();
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) || (endPiece != null && this.color == endPiece.getColor()))
            return false;
        if(Math.abs(startSquare.getFile() - endSquare.getFile()) <= 1 &&
           Math.abs(startSquare.getRank() - endSquare.getRank()) <= 1 ) {
            this.isFirstMove = false;
            return true;
        }
        return false;
    }

    /**
     * Convenience method, gets the Squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param start String representation of the Square the King is on
     * @param end String representation of the Square the King tries to move to
     */
    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }
}
