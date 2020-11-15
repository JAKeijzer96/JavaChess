package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Rook extends Piece {

    public Rook (char color) {
        super(color);
        this.name = (color == 'W') ? 'R' : 'r';
    }

    /**
     * legalMove method for the Rook class.
     * Rooks move horizontally along ranks and vertically along files
     * A Rook move is legal if startFile == endFile or startRank == endRank
     * @param board the Board the game is played on
     * @param startSquare the Square the Rook is on
     * @param endSquare the Square the Rook tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) ||
            (endSquare.getPiece() != null && this.color == endSquare.getPiece().getColor()))
            return false;
        byte startFile = startSquare.getFile();
        byte startRank = startSquare.getRank();
        byte endFile = endSquare.getFile();
        byte endRank = endSquare.getRank();
        // Going down the board, check if there are any pieces in the way
        if (startFile == endFile && startRank > endRank)
            return checkSameFileObstructions(board, startFile, startRank, endRank);
        // Going up the board, check if there are any pieces in the way
        if (startFile == endFile && endRank > startRank)
            return checkSameFileObstructions(board, startFile, endRank, startRank);
        // Going left on the board, check if there are any pieces in the way
        if (startRank == endRank && startFile > endFile)
            return checkSameRankObstructions(board, startRank, startFile, endFile);
        // Going right on the board, check if there are any pieces in the way
        if (startRank == endRank && endFile > startFile)
            return checkSameRankObstructions(board, startRank, endFile, startFile);
        return false;
    }

    /**
     * Convenience method, gets the squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Rook is on
     * @param endString String representation of the Square the Rook tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }

    /**
     * Check if there are any pieces in the way when moving vertically,
     * starting at the rank with the lowest numerical value, then moving up
     * Note that if the rook is moving down, we still check ranks from the lowest
     * to the highest value, this for code reusability and for-loop readability
     * @param board the Board the Game is played on
     * @param file the file the Rook is moving on
     * @param highRank the rank with the highest numerical value
     * @param lowRank the rank with the lowest numerical value
     * @return true if there are no pieces in the way, false otherwise
     */
    public static boolean checkSameFileObstructions(Board board, byte file, byte highRank, byte lowRank) {
        // Add 1 to lowRank so we don't collide with ourselves. If the endSquare
        // is at lowRank, we already checked for friendly pieces.
        for (byte i = (byte) (lowRank + 1); i < highRank; i++) {
            if(board.getSquare(file, i).getPiece() != null)
                return false;
        }
        return true;
    }

    /**
     * Check if there are any pieces in the way when moving horizontally,
     * starting at the file with the lowest numerical value, then moving right
     * Note that if the rook is moving left, we still check files from the lowest
     * to the highest value, this for code reusability and for-loop readability
     * @param board the Board the Game is played on
     * @param rank the rank the Rook is moving on
     * @param highFile the file with the highest numerical value
     * @param lowFile the file with the lowest numerical value
     * @return true if there are no pieces in the way, false otherwise
     */
    public static boolean checkSameRankObstructions(Board board, byte rank, byte highFile, byte lowFile) {
        // Add 1 to lowFile so we don't collide with ourselves. If the endSquare
        // is at lowFile, we already checked for friendly pieces.
        for(byte i = (byte) (lowFile + 1); i < highFile; i++) {
            if(board.getSquare(i, rank).getPiece() != null)
                return false;
        }
        return true;
    }
}
