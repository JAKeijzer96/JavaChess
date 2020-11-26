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
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = "-";
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
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
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = "-";
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
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
    }

    /**
     * Method to make a move on the Board
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
        
        Move move = new Move(startSquare, endSquare);
        this.moveList.add(move);

        if (startPiece instanceof Pawn || endSquare.getPiece() != null)
            this.halfMoveCounter = 0;
        else
            this.halfMoveCounter++;
        
        endSquare.setPiece(startPiece);
        startSquare.setPiece(null);
        switchTurn();
        return true;
    }

    /**
     * Switch whose turn it is
     */
    public void switchTurn() {
        if (currentTurn.equals(whitePlayer))
            currentTurn = blackPlayer;
        else { // increment fullMovecounter after black move
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
