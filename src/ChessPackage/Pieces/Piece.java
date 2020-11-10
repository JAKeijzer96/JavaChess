package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public abstract class Piece {
    /**
     * Color
     *      How do we represent a color?
     *          Use Strings ("white", "black")
     *          Use ints (bytes) (0, 1)
     *          Use chars ('w', 'b' / 'W', 'B')
     *          Use booleans (true, false)
     */

    char color;
    String name;

    public Piece(char color) {
        this.color = color;
    }

    /**
     * Abstract Piece method
     * @param board the Board the piece is on
     * @param start the moves starting Square
     * @param end the Square to move to
     * @return true if the move is a legal move, false otherwise
     */
    public abstract boolean legalMove(Board board, Square start, Square end);

    /**
     * Abstract Piece method, re-implementation of legalMove(Board, Square, Square)
     * This method calls board.getSquare with the strings start and end, then
     * returns legalMove(Board, Square, Square) with the found Squares as arguments
     * @param board the Board the piece is on
     * @param start a String representation of the moves starting Square
     * @param end a String representation of the Square to move to
     * @return true if the move is a legal move, false otherwise
     */
    public abstract boolean legalMove(Board board, String start, String end);

    public boolean isSameColor(Piece other) {
        return (this.color == other.color);
    } 

    @Override
    public String toString() {
        return name;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }
}
