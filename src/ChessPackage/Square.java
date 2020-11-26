package ChessPackage;

import ChessPackage.Pieces.Piece;

public class Square {
    // No color variable as that's for GUI only, might add later if necessary
    byte file;
    byte rank;
    Piece piece;

    /**
     * Constructor for Squares that have no Piece on them
     * @param file int index of the Boards file the Square is on
     * @param rank int index of the Boards rank the Square is on
     */
    public Square(int file, int rank) {
        this.file = (byte)file;
        this.rank = (byte)rank;
        this.piece = null;
    }

    /**
     * Constructor for Squares that have a Piece on them
     * @param file int index of the Boards file the Square is on
     * @param rank int index of the Boards rank the Square is on
     * @param piece the Piece that's on the Square
     */
    public Square(int file, int rank, Piece piece) {
        this.file = (byte)file;
        this.rank = (byte)rank;
        this.piece = piece;
    }

    /**
     * Compares two Squares, they are equal if they have the same file and rank
     * @param other the Object to compare to
     * @return true if this Square is equal to the Object argument, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Square)) {
            return false;
        }
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

    public byte getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = (byte)rank;
    }

    public byte getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = (byte)file;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
