package ChessPackage;

import java.util.ArrayList;
import java.util.List;

import ChessPackage.Pieces.*;

public class Game {
    /**
     * Player white
     * Player black
     * Board (which is a 2D array of Squares, which in thurn have Pieces on them)
     * List of Moves so we can go back
     * Current state? (ongoing, checkmate, stalemate, (check?))
     * 
     */

    Player whitePlayer;
    Player blackPlayer;
    Player currentTurn;
    List<Move> moveList;
    Board board;
    Square[] whitePiecesSquares; // list of all Squares with white Pieces
    Square[] blackPiecesSquares; // list of all Squares with black Pieces
    Square whiteKingSquare;
    Square blackKingSquare;
    int halfMoveCounter; // halfMoveCounter used for fifty-move rule
    int fullMoveCounter; // fullMoveCounter to track amount of full Moves
    // Might want to change way to save castling and en passant later
    String castlingAvailability; // String for FEN notation
    String enPassantSquare; // String for FEN notation


    /**
     * Constructor to make a new Game with default Players
     */
    public Game() {
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<Move>();
        this.board = new Board();
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = "-";
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
    }

    /**
     * Constructor to make a new Game with custom Players
     * @param white Player with the white pieces
     * @param black Player with the black pieces
     */
    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        this.moveList = new ArrayList<Move>();
        this.board = new Board();
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = "-";
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
    }

    /**
     * Constructor to make the game represented by the given FEN notation.
     * This uses default Players for white and black.
     * @param fenNotation String containing valid FEN notation
     */
    public Game(String fenNotation) {
        String[] arrOfStr = fenNotation.split(" ");
        if (arrOfStr.length != 6)
            throw new IllegalArgumentException("Invalid FEN notation");
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<Move>();
        this.board = new Board(arrOfStr[0]);
        this.currentTurn = (arrOfStr[1].toLowerCase().equals("w")) ? this.whitePlayer : this.blackPlayer;
        this.castlingAvailability = arrOfStr[2];
        this.enPassantSquare = arrOfStr[3];
        this.halfMoveCounter = Integer.parseInt(arrOfStr[4]);
        this.fullMoveCounter = Integer.parseInt(arrOfStr[5]);
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        for(Square s : whitePiecesSquares) {
            if(s.getPiece().getName() == 'K') {
                this.whiteKingSquare = s;
                break;
            }
        }
        for(Square s : blackPiecesSquares) {
            if(s.getPiece().getName() == 'k') {
                this.blackKingSquare = s;
                break;
            }
        }
    }

    /**
     * Makes a move on the Board <p>
     * This method checks if the move is valid, moves the Piece, adds the Move
     * to the moveList, increments halfMoveCounter if needed and switches turns
     * @param startString String representation of the start Square
     * @param endString String representation of the end Square
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(String startString, String endString) {
        Square startSquare = board.getSquare(startString);
        Square endSquare = board.getSquare(endString);
        Piece startPiece = startSquare.getPiece();
        if (startPiece == null)
            return false;
        if (startPiece.getColor() != currentTurn.getPlayerColor())
            return false;
        if (!startPiece.legalMove(board, startSquare, endSquare))
            return false;
        // might need to move (haha) this down to allow adding check notation
        Move move = new Move(startSquare, endSquare);
        this.moveList.add(move);

        if (startPiece instanceof Pawn || endSquare.getPiece() != null)
            this.halfMoveCounter = 0;
        else
            this.halfMoveCounter++;
        endSquare.setPiece(startPiece);
        startSquare.setPiece(null);
        int amountOfCheckingPieces = calculateChecks();
        if(amountOfCheckingPieces > 0) {
            System.out.println(amountOfCheckingPieces + " check(s)! " + startString + "->" + endString);
        }

        switchTurn();
        return true;
    }

    /**
     * Calculates how many Pieces are giving check <p>
     * Calculation starts at the Kings Square, then loops outwards in all
     * 8 directions. If an unobstructed opponents Piece is found with a
     * legalMove to the Kings Square, it is checking the King. After the
     * 8 directions, look for possible Knights around the King.
     * @return the amount of Pieces giving check, can be 0, 1 or 2
     */
    public int calculateChecks() {
        // lets say white has moved. then we need to test if black King is in check
        Square kingSquare = (currentTurn.equals(whitePlayer)) ? blackKingSquare : whiteKingSquare;
        int file = kingSquare.getFile();
        int rank = kingSquare.getRank();
        // return amount of Pieces giving check, either 0, 1 or 2
        // if it's 2, no need to check if Piece capture is possible on the next move
        int nrOfCheckingPieces = 0;

        // check for checks in all 8 directions, starting with straight up
        // and moving clockwise
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, 0, 1);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, 1, 1);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, 1, 0);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, 1, -1);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, 0, -1);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, -1, -1);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, -1, 0);
        nrOfCheckingPieces += checkLoop(kingSquare, file, rank, -1, 1);
        // check for any Knights giving check
        for(byte[] offset : Board.knightOffsets) {
            if (file + offset[0] < 0 || file + offset[0] > 7
                || rank + offset[1] < 0 || rank + offset[1] > 7)
                continue;
            Piece p = board.getPiece(file+offset[0], rank+offset[1]);
            // There can only be one Knight at a time giving check. Because we
            // already checked for other Pieces, we can return immediately
            if (p != null && p.getUpperCaseName() == 'N' && p.getColor() != kingSquare.getPieceColor())
                return nrOfCheckingPieces + 1;
        }
        return nrOfCheckingPieces;
    }

    /**
     * Loop from the Kings Square outwards in a given direction until a Piece
     * is found, or until the edge of the Board is hit. <p>
     * Below is a grid showing the file/rank offsets viewed from the kingSquare
     * <pre>
     *+---+---+---+
     *|-/+|0/+|+/+|
     *+---+---+---+
     *|-/0| K |+/0|
     *+---+---+---+
     *|-/-|0/-|+/-|
     *+---+---+---+
     * </pre>
     * @param kingSquare the Square the King is on
     * @param file the file of the kingSquare
     * @param rank the rank of the kingSquare
     * @param fileOffset the amount to offset the file by every iteration
     * @param rankOffset the amount to offset the rank by every iteration
     * @return 1 if there is a Piece checking the King, 0 otherwise
     */
    private int checkLoop(Square kingSquare, int file, int rank, int fileOffset, int rankOffset) {
        // add offsets to file and rank so we dont check starting Square
        for(int f = file + fileOffset, r = rank + rankOffset;
         f > -1 && f < Board.boardSize && r > -1 && r < Board.boardSize;
         f += fileOffset, r += rankOffset) {
            Square s = board.getSquare(f, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // return 0 if we find a Piece of the same color as the King we are checking for,
            // any Pieces behind it will be blocked and unable to check the King
            if(p.getColor() == kingSquare.getPieceColor())
                return 0;
            // return 1 if there is a Piece with a legalMove to the kingSquare
            if (p.legalMove(board, s, kingSquare)) {
                return 1;
            }
            // This part is only reached when there is an opponents Piece in the
            // way with no valid move to the kingSquare. This Piece blocks any
            // Pieces behind it, so no need to check further in this direction
            return 0;
        }
        return 0;
    }

    /**
     * Switch whose turn it is
     * Increment fullMoveCounter after black move
     */
    public void switchTurn() {
        if (currentTurn.equals(whitePlayer))
            currentTurn = blackPlayer;
        else {
            currentTurn = whitePlayer;
            this.fullMoveCounter++;
        }
    }

    /**
     * Converts the current state of the Game to FEN notation.
     * Needs more work
     * @return the FEN notation of the current gamestate
     */
    @Override
    public String toString() {
        char turn = (currentTurn.equals(whitePlayer)) ? 'w' : 'b';
        return (board.toString() + " " + turn + " " + castlingAvailability + " "
            + enPassantSquare + " " + halfMoveCounter + " " + fullMoveCounter);
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moves) {
        this.moveList = moves;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Player currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getHalfMoveCounter() {
        return halfMoveCounter;
    }

    public void setHalfMoveCounter(int halfMoveCounter) {
        this.halfMoveCounter = halfMoveCounter;
    }

    public int getFullMoveCounter() {
        return fullMoveCounter;
    }

    public void setFullMoveCounter(int fullMoveCounter) {
        this.fullMoveCounter = fullMoveCounter;
    }

    public String getCastlingAvailability() {
        return castlingAvailability;
    }

    public void setCastlingAvailability(String castlingAvailability) {
        this.castlingAvailability = castlingAvailability;
    }

    public String getEnPassantSquare() {
        return enPassantSquare;
    }

    public void setEnPassantSquare(String enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }
}
