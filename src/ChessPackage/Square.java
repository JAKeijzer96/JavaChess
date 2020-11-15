package ChessPackage;

import ChessPackage.Pieces.Piece;

public class Square {
    /**
     * Color
     *      How do we represent a color?
     *          Use Strings ("white", "black")
     *          Use ints (bytes) (0, 1)
     *          Use chars ('w', 'b' / 'W', 'B')
     *          Use booleans (true, false)
     *      Does color of the square matter for game functionality
     *      or is it just for GUI?
     * 
     * Position in board
     *      How do we represent the position on the board?
     *          Use ints (bytes) (0,4)
     *          Use chars + bytes ('a', 4)
     *          Use Strings ("a4")
     *      Our board will be a 2D array
     *      Using [][] referencing Strings are a bad idea or just impossible
     *      Use bytes 0-7
     *      Files and ranks; file = column, rank = row.
     *      Might seem unintuitive, but we use [file][rank]
     *      This is to match standard chess notation for squares
     *      e4 would be the e-file (index 4) and 4th rank (index 3) 
     *      Exception handling happens in board
     * Piece currently on square
     */
    // Use ints instead of bytes for now so we don't have to cast everytime
    // we construct a new Square.
    byte file;
    byte rank;
    Piece piece;
    // something color;

    public Square(int file, int rank) {
        this.file = (byte)file;
        this.rank = (byte)rank;
        this.piece = null;
    }

    public Square(int file, int rank, Piece piece) {
        this.file = (byte)file;
        this.rank = (byte)rank;
        this.piece = piece;
    }

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
     * Return the chess notation of the Square
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
