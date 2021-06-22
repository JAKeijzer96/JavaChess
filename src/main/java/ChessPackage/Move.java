package ChessPackage;

import ChessPackage.Pieces.Pawn;
import ChessPackage.Pieces.Piece;

/**
 * This class is mainly used to keep track of the moves done in the game
 * Logic for checking if a move is allowed is located in classes that
 * extend the abstract Piece class
 * 
 * Properties:
 * Player making the move (keep track in game?)
 * Start position
 * End position
 * Piece that's moving?
 * Piece that's captured?
 * ...
 */
public class Move {
    Square startSquare;
    Square endSquare;
    Piece startPiece;
    Piece endPiece;
    boolean isCheck;
    boolean isMate;
    boolean isCastlingMove;

    public Move(Square startSquare, Piece startPiece, Square endSquare, Piece endPiece, boolean isCheck, boolean isMate, boolean isCastlingMove) {
        this.startSquare = startSquare;
        this.startPiece = startPiece;
        this.endSquare = endSquare;
        this.endPiece = endPiece;
        this.isCheck = isCheck;
        this.isMate = isMate;
        this.isCastlingMove = isCastlingMove;
    }

    /**
     * <p> Get the standard chess notation of a chess move. </p>
     * 
     * <p> Generally speaking this includes the Piece that's moving,
     * the Square it's moving to, and extra characters to indicate if it has
     * captured a Piece (x), given check (+) or delivered checkmate (#) </p>
     * @return String containing standard chess notation of the Move
     */
    @Override
    public String toString() {
        if (isCastlingMove)
            return startSquare.getFile() < endSquare.getFile() ? "O-O" : "O-O-O";
        String piece = "";
        // Don't add Piece notation if the Piece is a Pawn ...
        if (!(this.startPiece instanceof Pawn))
            piece = Character.toString(this.startPiece.getName()).toUpperCase();
        // .. except when the Pawn captures a Piece, in that case add file
        else if (this.startPiece instanceof Pawn && this.endPiece != null)
            piece = Character.toString(this.startSquare.getFile() + 'a');
        String takes = (this.endPiece != null) ? "x" : "";
        String check = (this.isCheck) ? "+" : "";
        String mate = (this.isMate) ? "#" : "";
        return piece + takes + this.endSquare + check + mate;
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public void setStartSquare(Square startSquare) {
        this.startSquare = startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    public void setEndSquare(Square endSquare) {
        this.endSquare = endSquare;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isMate() {
        return isMate;
    }

    public void setMate(boolean isMate) {
        this.isMate = isMate;
    }

    public Piece getStartPiece() {
        return startPiece;
    }

    public void setStartPiece(Piece startPiece) {
        this.startPiece = startPiece;
    }

    public Piece getEndPiece() {
        return endPiece;
    }

    public void setEndPiece(Piece endPiece) {
        this.endPiece = endPiece;
    }

    public boolean isCastlingMove() {
        return isCastlingMove;
    }
}
