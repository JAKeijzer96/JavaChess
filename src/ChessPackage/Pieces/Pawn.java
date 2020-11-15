package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Pawn extends Piece {

    private boolean isFirstMove;
    
    public Pawn (char color) {
        super(color);
        if (color == 'W')
            this.name = 'P';
        else
            this.name = 'p';
        this.isFirstMove = true;
    }

    /**
     * legalMove method for the Pawn class.
     * This method only checks the color of the Pawn, then calls the
     * PawnMove method, adding an extra argument 'direction' which helps
     * calculate if the Pawn moves up or down the board. This direction
     * is based on the color of the Pawn
     * @param board the Board the game is played on
     * @param startSquare the Square the Pawn is on
     * @param endSquare the Square the Pawn tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        if (this.color == 'W')
            return pawnMove(board, startSquare, endSquare, (byte) 1);
        if (this.color == 'B')
            return pawnMove(board, startSquare, endSquare, (byte) -1);
        throw new UnsupportedOperationException(
            "Something has gone wrong; pawn is a currently unsupported color");
    }
    
    /**
     * Convenience method, gets the squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Pawn is on
     * @param endString String representation of the Square the Pawn tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }

    /**
     * A Pawn can move one square ahead, two squares ahead if it's the first
     * move, or one square diagonally ahead by capturing an opponents Piece
     * @param board the Board the game is played on
     * @param startSquare the Square the Pawn is on
     * @param endSquare the Square the Pawn tries to move to
     * @param direction the direction the Pawn moves in; 1 if white Pawn, -1 if black
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    private boolean pawnMove(Board board, Square startSquare, Square endSquare, byte direction) {
        Piece endPiece = endSquare.getPiece();
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) || (endPiece != null && this.color == endPiece.getColor()))
        return false;
        // we leave en passant for later
        byte startFile = startSquare.getFile();
        byte startRank = startSquare.getRank();
        byte endFile = endSquare.getFile();
        byte endRank = endSquare.getRank();
        // Move 1 ahead
        if (startFile == endFile && endRank - startRank == 1 * direction
            && endPiece == null) {
            this.isFirstMove = false;
            return true;
        }
        // Move 2 ahead at the first move, check for pieces in the way
        if (isFirstMove && startFile == endFile && endRank - startRank == 2 * direction
            && endPiece == null && board.getPiece(startFile, startRank + direction) == null) {
            this.isFirstMove = false;
            return true;
        }
        // Move 1 diagonally forward by taking a piece of the opposite color
        // We already checked that if endPiece exists, it's opposite color
        // so no need to check again
        if (endPiece != null && Math.abs(endFile - startFile) == 1
            && endRank - startRank == 1 * direction) {
            this.isFirstMove = false;
            return true;
        }
        return false;
    }

}
