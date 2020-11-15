package ChessPackage;

import ChessPackage.Pieces.Pawn;
import ChessPackage.Pieces.Piece;

public class Move {
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
     */

    // Don't think keeping track of Player here is necessary
    Square startSquare;
    Square endSquare;
    Piece startPiece;
    Piece endPiece;
    boolean isCheck;
    boolean isCheckMate;

    public Move(Square startSquare, Square endSquare) {
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.startPiece = this.startSquare.getPiece();
        this.endPiece = this.endSquare.getPiece();
    }

    @Override
    public String toString() {
        String piece = "";
        if (!(this.startPiece instanceof Pawn))
            piece = Character.toString(this.startPiece.getName()).toUpperCase();
        String takes = (this.endPiece != null) ? "x" : "";
        String check = (isCheck) ? "+" : "";
        String checkMate = (isCheckMate) ? "#" : "";
        return piece + takes + this.endSquare.toString() + check + checkMate;
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

    public boolean isCheckMate() {
        return isCheckMate;
    }

    public void setCheckMate(boolean isCheckMate) {
        this.isCheckMate = isCheckMate;
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
}
