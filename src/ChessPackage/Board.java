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

    public Board(String fenNotation) {
        this.newBoard(fenNotation);
    }

    /**
     * Method to get the Square at [file][rank]
     * @param file the file of the Square
     * @param rank the rank of the Square
     * @return the Square at board[file][rank]
     */
    public Square getSquare(int file, int rank) {
        if (file < 0 || file > 7)
            throw new ArrayIndexOutOfBoundsException("File index out of bounds");
        if (rank < 0 || rank > 7)
            throw new ArrayIndexOutOfBoundsException("Rank index out of bounds");
        return board[file][rank];
    }

    /**
     * Method to get a Square using its String representation
     * @param squareString the String representation of the Square
     * @return the Boards Square described by squareString
     */
    public Square getSquare(String squareString) {
        if (squareString.length() != 2)
            throw new IllegalArgumentException("Please provide a valid square on a chessboard");
        // Convert the chars to file and rank ints using ASCII and getNumericValue
        int file = Character.toLowerCase(squareString.charAt(0)) - 97;
        int rank = Character.getNumericValue(squareString.charAt(1)) - 1;
        if (file < 0 || file > 7)
            throw new ArrayIndexOutOfBoundsException("File index out of bounds");
        if (rank < 0 || rank > 7)
            throw new ArrayIndexOutOfBoundsException("Rank index out of bounds");
        return board[file][rank];
    }

    /**
     * Convenience method to get a Piece from a Square directly using file and
     * rank instead of having to call board.getSquare(file, rank).getPice()
     * @param file the file of the Square
     * @param rank the rank of the Square
     * @return the Piece on Square(file, rank)
     */
    public Piece getPiece(int file, int rank) {
        return this.getSquare(file, rank).getPiece();
    }

    /**
     * Convenience method to get a piece from a Square directly using the String
     * representation of the Square instead of having to call the board.getSquare.getPiece()
     * @param squareString String representation of the Square
     * @return the Piece on the given Square
     */
    public Piece getPiece(String squareString) {
        return this.getSquare(squareString).getPiece();
    }
    
    /**
     * Initializes a new board with the default starting position
     */
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

    /**
     * Initializes a new board based on the given FEN notation.
     * Currently only supports piece placement
     * @param fenNotation a String containing (part of) a valid FEN notation
     */
    void newBoard(String fenNotation) {
        // FEN notation moves backwards from rank 8 to rank 1
        // Each rank is described from the a-file through the h-file
        // Uppercase letters are white pieces, lowercase == black
        // Numbers are used to describe the amount of empty squares
        // To seperate ranks, '/' is used
        board = new Square[boardSize][boardSize];
        char currentChar;
        int rank = 7;
        int file = 0;
        
        for(int i = 0; i < fenNotation.length(); i++) {
            currentChar = fenNotation.charAt(i);
            // Go to next rank and reset file on '/' character
            if (currentChar == '/') {
                rank--;
                file = 0;
            }
            // If currentChar represents a number, add that many empty squares
            else if (Character.isDigit(currentChar)) {
                for(int j = 0; j < Character.getNumericValue(currentChar); j++, file++)
                    board[file][rank] = new Square(file, rank);
            }
            // If currentChar is a valid lowercase letter, add a black piece
            else if(Character.isLowerCase(currentChar)) {
                addPieceFromFenNotation(currentChar, 'B', file, rank);
                file++;
            }
            // If currentChar is a valid uppercase letter, add a white piece
            else if (Character.isUpperCase(currentChar)) {
                addPieceFromFenNotation(currentChar, 'W', file, rank);
                file++;
            }
            else {
                throw new IllegalArgumentException("Unknown or extra character " + currentChar);
            }
        }
    }

    /**
     * Method used when constructing a new Board using FEN notation.
     * This method adds a new Square with the piece corresponding to
     * pieceChar and pieceColor to the board
     * @param pieceChar char describing the type of Piece
     * @param pieceColor char describing the Piece color
     * @param file the file of the new Square
     * @param rank the rank of the new Square
     * @throws IllegalArgumentException if pieceChar does not describe a valid Piece
     */
    private void addPieceFromFenNotation(char pieceChar, char pieceColor,
      int file, int rank) throws IllegalArgumentException{
        switch (Character.toUpperCase(pieceChar)) {
            case 'B':
                board[file][rank] = new Square(file, rank, new Bishop(pieceColor));
                return;
            case 'K':
                board[file][rank] = new Square(file, rank, new King(pieceColor));
                return;
            case 'N':
                board[file][rank] = new Square(file, rank, new Knight(pieceColor));
                return;
            case 'P':
                board[file][rank] = new Square(file, rank, new Pawn(pieceColor));
                return;
            case 'Q':
                board[file][rank] = new Square(file, rank, new Queen(pieceColor));
                return;
            case 'R':
                board[file][rank] = new Square(file, rank, new Rook(pieceColor));
                return;
            default:
                throw new IllegalArgumentException(
                    "Invalid letter " + pieceChar + " in FEN notation at rank " +
                    (rank+1) + " and file " + (file+1));
        }
    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }
}
