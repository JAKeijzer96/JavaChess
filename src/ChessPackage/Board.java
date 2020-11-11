package ChessPackage;

import ChessPackage.Pieces.*;

public class Board {
    /**
     * 2D array of Squares
     */

    Square[][] board;
    private static final int boardSize = 8;

    public Board() {
        this.newBoard();
    }

    public Square getSquare(int file, int rank) {
        if (file < 0 || file > 7)
            throw new ArrayIndexOutOfBoundsException("File index out of bounds");
        if (rank < 0 || rank > 7)
            throw new ArrayIndexOutOfBoundsException("Rank index out of bounds");
        return board[file][rank];
    }

    public Square getSquare(String squareString) {
        if (squareString.length() != 2)
            throw new IllegalArgumentException("Please provide a valid square on a chessboard");
        // Convert the chars to file and rank ints using their ASCII value
        int file = Character.toLowerCase(squareString.charAt(0)) - 97;
        int rank = squareString.charAt(1) - 49;
        if (file < 0 || file > 7)
            throw new ArrayIndexOutOfBoundsException("File index out of bounds");
        if (rank < 0 || rank > 7)
            throw new ArrayIndexOutOfBoundsException("Rank index out of bounds");
        return board[file][rank];
    }

    public Piece getPiece(int file, int rank) {
        return this.getSquare(file, rank).getPiece();
    }

    /**
     * Method to get a piece from a square directly instead of having to invoke
     * the lenghtier board.getSquare.getPiece()
     * @param squareString String representation of the Square
     * @return the Piece on the given Square
     */
    public Piece getPiece(String squareString) {
        return this.getSquare(squareString).getPiece();
    }
    
    void newBoard() {
        board = new Square[boardSize][boardSize];
        // Initialize a1 through h1 with the white pieces
        board[0][0] = new Square(0, 0, new Rook('W'));
        board[1][0] = new Square(1, 0, new Knight('W'));
        board[2][0] = new Square(2, 0, new Bishop('W'));
        board[3][0] = new Square(3, 0, new Queen('W'));
        board[4][0] = new Square(4, 0, new King('W'));
        board[5][0] = new Square(5, 0, new Bishop('W'));
        board[6][0] = new Square(6, 0, new Knight('W'));
        board[7][0] = new Square(7, 0, new Rook('W'));
        // Initialize a2 through h2 with the white pawns
        for (int file = 0; file < 8; file++) {
            board[file][1] = new Square(file, 1, new Pawn('W'));
        }
        // Initialize the 3rd, 4th, 5th and 6th rank without any pieces
        for (int rank = 2; rank < 6; rank++) {
            for(int file = 0; file < 8; file++) {
                board[file][rank] = new Square(file, rank);
            }
        }
        // Initialize a7 through h7 with the black pawns
        for (int file = 0; file < 8; file++) {
            board[file][6] = new Square(file, 6, new Pawn('B'));
        }
        // Initialize a8 through h8 with the black pieces
        board[0][7] = new Square(0, 7, new Rook('B'));
        board[1][7] = new Square(1, 7, new Knight('B'));
        board[2][7] = new Square(2, 7, new Bishop('B'));
        board[3][7] = new Square(3, 7, new Queen('B'));
        board[4][7] = new Square(4, 7, new King('B'));
        board[5][7] = new Square(5, 7, new Bishop('B'));
        board[6][7] = new Square(6, 7, new Knight('B'));
        board[7][7] = new Square(7, 7, new Rook('B'));
    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }
}
