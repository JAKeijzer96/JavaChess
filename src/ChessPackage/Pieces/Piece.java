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
    char name;

    public Piece(char color) {
        this.color = color;
    }

    /**
     * Abstract Piece method
     * @param board the Board the piece is on
     * @param startSquare the moves starting Square
     * @param endSquare the Square to move to
     * @return true if the move is a legal move, false otherwise
     */
    public abstract boolean legalMove(Board board, Square startSquare, Square endSquare);

    /**
     * Abstract Piece method
     * This convenience method gets the Squares described by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the piece is on
     * @param startString a String representation of the moves starting Square
     * @param endString a String representation of the Square to move to
     * @return true if the move is a legal move, false otherwise
     */
    public abstract boolean legalMove(Board board, String startString, String endString);

    public boolean isSameColor(Piece other) {
        return (this.color == other.color);
    } 

    @Override
    public String toString() {
        return Character.toString(name);
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }
}
