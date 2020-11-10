package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Pawn extends Piece {

    private boolean isFirstMove;
    
    public Pawn (char color) {
        super(color);
        this.name = "Pawn";
        isFirstMove = true;
    }

    public boolean legalMove(Board board, Square start, Square end) {
        // We forward the arguments to private function pawnMove, while
        // adding an int 'direction' which helps calculate if pawns move up or
        // down. This direction is based on the color of the pawn
        if (this.color == 'W')
            return pawnMove(board, start, end, 1);
        if (this.color == 'B')
            return pawnMove(board, start, end, -1);
        throw new UnsupportedOperationException(
            "Something has gone wrong; pawn is a currently unsupported color");
    }

    private boolean pawnMove(Board board, Square start, Square end, int direction) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (start.equals(end) || (end.getPiece() != null && this.color == end.getPiece().getColor()))
            return false;
        // we leave en passant for later
        int startFile = start.getFile();
        int startRank = start.getRank();
        int endFile = end.getFile();
        int endRank = end.getRank();
        Piece endPiece = end.getPiece();

        // Move 1 ahead
        if (startFile == endFile && endRank - startRank == 1 * direction) {
            this.isFirstMove = false;
            return true;
        }
        // Move 2 ahead at the first move
        if (startFile == endFile && isFirstMove && endRank - startRank == 2 * direction) {
            this.isFirstMove = false;
            return true;
        }
        // Move 1 diagonally by taking a piece of the opposite color
        if (endPiece != null && this.color != endPiece.getColor() && 
            endFile - startFile == 1 * direction && endRank - startRank == 1 * direction) {
            this.isFirstMove = false;
            return true;
        }
        return false;
    }

    public boolean legalMove(Board board, String start, String end) {
        return this.legalMove(board, board.getSquare(start), board.getSquare(end));
    }
}
