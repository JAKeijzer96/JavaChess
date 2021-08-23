package Chess;

import Exceptions.EmptySquareException;
import Pieces.Piece;

/**
 * Square object to be used in a game of Chess.
 * A Square is contained in a Board and has a file (a-h) and rank (1-8)
 * A Square can have either 0 or 1 Pieces on it.
 */
public class Square {
    private int file;
    private int rank;
    private Piece piece;

    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
        this.piece = null;
    }

    public Square(int file, int rank, Piece piece) {
        this.file = file;
        this.rank = rank;
        this.piece = piece;
    }

    /**
     * Compares two Squares, they are equal if they have the same file and rank
     * @param other the Object to compare to
     * @return true if this Square is equal to the Object argument, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Square))
            return false;
        Square s = (Square) other;
        return ( (this.getFile() == s.getFile()) && (this.getRank() == s.getRank()) );
    }

    /**
     * Get the default chess notation of this Square as a String
     * @return the chess notation of the Square
     */
    @Override
    public String toString() {
        char file = (char) (this.file + 'a');
        char rank = (char) (this.rank + '1');
        return Character.toString(file) + Character.toString(rank);
    }

    /**
     * Get the color of the Piece on the Square
     * @return the color of the Piece
     * @throws EmptySquareException if there is no Piece on the Square
     */
    public char getPieceColor() throws EmptySquareException {
        if(this.piece != null) {
            return this.piece.getColor();
        }
        throw new EmptySquareException("Square " + this + " is empty");
    }

    public char getPieceName() throws EmptySquareException {
        if (this.piece != null) {
            return this.piece.getColor();
        }
        throw new EmptySquareException("Square " + this + " is empty");
    }

    public boolean isEmpty() {
        return this.piece == null;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
