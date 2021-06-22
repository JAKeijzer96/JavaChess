package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public abstract class Piece {
    char color;
    char name;

    /**
     * Abstract Piece object for a game of Chess
     * @param color the color of the Piece, either 'W' or 'B'
     */
    public Piece(char color) {
        this.color = Character.toUpperCase(color);
    }

    /**
     * Abstract method to check if a move from startSquare to endSquare is legal
     * @param board the Board the piece is on
     * @param startSquare the moves starting Square
     * @param endSquare the Square to move to
     * @return true if the move is a legal move, false otherwise
     */
    public abstract boolean legalMove(Board board, Square startSquare, Square endSquare);

    /**
     * <p> Abstract method to check if a move from startSquare to endSquare is legal </p>
     * This convenience method gets the Squares described by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the piece is on
     * @param startString a String representation of the moves starting Square
     * @param endString a String representation of the Square to move to
     * @return true if the move is a legal move, false otherwise
     */
    public abstract boolean legalMove(Board board, String startString, String endString);

    /**
     * Tests if two Pieces have the same color
     * @param other the Piece to compare to
     * @return true if the color is the same, false otherwise
     */
    public boolean isSameColorAs(Piece other) {
        return (this.color == other.color);
    }

    /**
     * Compares two Pieces, checks if they have the same color and name
     * @return true if this Piece is equal to the Ojbect argument, false otherwise
     */
     @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Piece))
            return false;
        Piece p = (Piece) other;
        return (this.getColor() == p.getColor() && this.getName() == p.getName());
    }

    /**
     * Get the char variable 'name' as a String
     * @return the name of the Piece as a String
     */
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
    
    public char getUpperCaseName() {
        return Character.toUpperCase(name);
    }

    public void setName(char name) {
        this.name = name;
    }
}
