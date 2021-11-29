package Chess;

import Pieces.Pawn;
import Pieces.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
@Getter
@AllArgsConstructor
public class Move {
    private Square startSquare;
    private Piece startPiece;
    private Square endSquare;
    private Piece endPiece;
    private boolean isCheck;
    private boolean isMate;
    private boolean isCastlingMove;

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
        else if (this.endPiece != null)
            piece = Character.toString(this.startSquare.getFile() + 'a');
        String takes = (this.endPiece != null) ? "x" : "";
        String check = (this.isCheck) ? "+" : "";
        String mate = (this.isMate) ? "#" : "";
        return piece + takes + this.endSquare + check + mate;
    }
}
