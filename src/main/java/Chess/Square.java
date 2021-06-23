package Chess;

import Pieces.Piece;

public class Square {
    // No color variable as that's for GUI only, might add later if necessary
    int file;
    int rank;
    Piece piece;

    /**
     * Square object to be used in a game of Chess with no Piece on it
     * @param file int index of the Boards file the Square is on
     * @param rank int index of the Boards rank the Square is on
     */
    public Square(int file, int rank) {
        this.file = file;
        this.rank = rank;
        this.piece = null;
    }

    /**
     * Square object to be used in a game of Chess with the given Piece on it
     * @param file int index of the Boards file the Square is on
     * @param rank int index of the Boards rank the Square is on
     * @param piece the Piece that's on the Square
     */
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
     * @throws NullPointerException if there is no Piece on the Square
     */
    public char getPieceColor() throws NullPointerException{
        if(this.piece != null)
            return this.piece.getColor();
        throw new NullPointerException("Square " + this
            + " has no Piece to get the color of");
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
