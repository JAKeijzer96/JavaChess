package ChessPackage;

import java.util.ArrayList;
import java.util.List;

import ChessPackage.Pieces.*;

public class Board {
    Square[][] board;
    public static final int BOARDSIZE = 8;
    // 2D array with all 8 possible offsets to find the surrounding Squares
    // that a Knight can be on to attack the current Square
    public static final byte[][] knightOffsets = {{1, 2}, {2, 1}, {2, -1},
        {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
    // 2D array with all 8 possible offsets of the surrounding Squares
    // compared to the current Square
    public static final byte[][] squareOffsets = {{1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}};

    /**
     * Board object on which a game of Chess can be played <p>
     * This constructor creates a new chessboard with the default starting
     * position. The Board is a 2D 8x8 Array of Squares. Pieces on the Board
     * can be accessed through the Squares they are on.
     */
    public Board() {
        this.newBoard();
    }

    /**
     * Board object on which a game of Chess can be played <p>
     * This constructor creates a new chessboard based on a String containing
     * partial FEN notation of a position. This String should only contain the
     * first part of a proper FEN notation. For the starting position, this
     * would be rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR.
     * The Board is a 2D 8x8 Array of Squares. Pieces on the Board can be
     * accessed through the Squares they are on
     * @param partialFenNotation a String containing the part of the FEN notation
     */
    public Board(String partialFenNotation) {
        this.newBoard(partialFenNotation);
    }

    /**
     * Gets the Square at [file][rank]
     * @param file the file of the Square
     * @param rank the rank of the Square
     * @return the Square at board[file][rank]
     * @throws ArrayIndexOutOfBoundsException if the Square is not on the Board
     */
    public Square getSquare(int file, int rank) throws ArrayIndexOutOfBoundsException {
        if (file < 0 || file > 7)
            throw new ArrayIndexOutOfBoundsException("File index [" + file + "]out of bounds");
        if (rank < 0 || rank > 7)
            throw new ArrayIndexOutOfBoundsException("Rank index [" + rank + "]out of bounds");
        return board[file][rank];
    }

    /**
     * Method to get a Square using its String representation
     * @param squareString the String representation of the Square
     * @return the Boards Square described by squareString
     * @throws IllegalArgumentException if the String is not of length 2
     * @throws ArrayIndexOutOfBoundsException if the Square is not on the Board
     */
    public Square getSquare(String squareString) {
        if (squareString.length() != 2)
            throw new IllegalArgumentException("Please provide a valid square on a chessboard");
        // Convert the chars to ints using ASCII value and getNumericValue
        int file = Character.toLowerCase(squareString.charAt(0)) - 97;
        int rank = Character.getNumericValue(squareString.charAt(1)) - 1;
        if (file < 0 || file > 7)
            throw new ArrayIndexOutOfBoundsException("File index [" + file + "]out of bounds");
        if (rank < 0 || rank > 7)
            throw new ArrayIndexOutOfBoundsException("Rank index [" + rank + "]out of bounds");
        return board[file][rank];
    }

    /**
     * Convenience method to directly get the Piece on Square(file, rank)
     * @param file the file of the Square
     * @param rank the rank of the Square
     * @return the Piece on Square(file, rank)
     */
    public Piece getPiece(int file, int rank) {
        return this.getSquare(file, rank).getPiece();
    }

    /**
     * Convenience method to directly get the Piece on Square(squareString)
     * @param squareString String representation of the Square
     * @return the Piece on the given Square
     */
    public Piece getPiece(String squareString) {
        return this.getSquare(squareString).getPiece();
    }
    
    /**
     * Get a List of all Squares that have a Piece of the given playerColor
     * @param playerColor color of the Players Pieces, either 'W' or 'B'
     * @return a List<Square> containing the Squares the Player has Pieces on
     */
    public List<Square> getSquaresOfPlayerColor(char playerColor) {
        List<Square> squareList = new ArrayList<Square>();
        for(Square[] file : this.board) {
            for(Square square : file) {
                Piece piece = square.getPiece();
                if (piece != null && piece.getColor() == playerColor)
                    squareList.add(square);
            }
        }
        return squareList;
    }

    /**
     * Initializes the Board with the default starting position
     */
    void newBoard() {
        board = new Square[BOARDSIZE][BOARDSIZE];
        // Initialize a1 through h1 with the white Pieces
        board[0][0] = new Square(0, 0, new Rook('W'));
        board[1][0] = new Square(1, 0, new Knight('W'));
        board[2][0] = new Square(2, 0, new Bishop('W'));
        board[3][0] = new Square(3, 0, new Queen('W'));
        board[4][0] = new Square(4, 0, new King('W'));
        board[5][0] = new Square(5, 0, new Bishop('W'));
        board[6][0] = new Square(6, 0, new Knight('W'));
        board[7][0] = new Square(7, 0, new Rook('W'));
        // Initialize a2 through h2 with the white Pawns
        for (int file = 0; file < BOARDSIZE; file++)
            board[file][1] = new Square(file, 1, new Pawn('W'));
        // Initialize the 3rd, 4th, 5th and 6th rank without any Pieces
        for (int rank = 2; rank < 6; rank++) {
            for(int file = 0; file < BOARDSIZE; file++)
                board[file][rank] = new Square(file, rank);
        }
        // Initialize a7 through h7 with the black Pawns
        for (int file = 0; file < BOARDSIZE; file++)
            board[file][6] = new Square(file, 6, new Pawn('B'));
        // Initialize a8 through h8 with the black Pieces
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
     * Initializes a new board based on the given String. <p>
     * This method initializes a new chessboard based on a String containing
     * partial FEN notation of a position. This String should only contain the
     * first part of a proper FEN notation. For the starting position, this
     * would be rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR.
     * As the partialFenNotation does not hold info regarding castling,
     * castling availability is not handled here. See Game(String fenNotation) instead.
     * @param partialFenNotation a String containing the part of the FEN notation
     * @throws IllegalArgumentException if the String contains an invalid character
     */
    void newBoard(String partialFenNotation) {
        // FEN notation moves backwards from rank 8 to rank 1
        // Each rank is described from the a-file through the h-file
        // Uppercase letters are white pieces, lowercase == black
        // Numbers are used to describe the amount of empty squares
        // To seperate ranks, '/' is used
        board = new Square[BOARDSIZE][BOARDSIZE];
        char currentChar;
        int rank = 7;
        int file = 0;
        for(int i = 0; i < partialFenNotation.length(); i++) {
            currentChar = partialFenNotation.charAt(i);
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
            else
                throw new IllegalArgumentException("Unknown or extra character " + currentChar);
        }
    }

    /**
     * Adds a Piece to the Board based on previously given FEN notation. <p>
     * This method adds a new Square with the Piece corresponding to
     * pieceChar and pieceColor to the board
     * @param pieceChar the kind of Piece to add
     * @param pieceColor the color of the Piece
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
                boolean isFirstMove = (rank == 1 || rank == 6);
                Pawn pawn = new Pawn(pieceColor, isFirstMove);
                board[file][rank] = new Square(file, rank, pawn);
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

    /**
     * Returns part of the FEN notation of the Board
     * @return a String containing the FEN notation of the Board
     */
    @Override
    public String toString() {
        String rankString = "";
        String endString = "";
        int emptySquares;
        for(int rank = BOARDSIZE - 1; rank >= 0; rank--) {
            emptySquares = 0;
            rankString = "";
            for(int file = 0; file < BOARDSIZE; file++) {
                Piece piece = this.getPiece(file, rank);
                if (piece == null)
                    emptySquares++;
                else {
                    if (emptySquares > 0) {
                        rankString += emptySquares;
                        emptySquares = 0;
                    }
                    rankString += piece;
                }
            }
            if (emptySquares > 0)
                rankString += emptySquares;
            if (rank == 7)
                endString = rankString;
            else
                endString += "/" + rankString;
        }
        return endString;
    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }
}
