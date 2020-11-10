package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Rook extends Piece {

    public Rook (char color) {
        super(color);
        this.name = "Rook";
    }

    /**
     * legalMove method for the Rook class
     * Rooks move horizontally along ranks and vertically along files
     * A Rook move is legal if startFile == endFile or startRank == endRank
     * @param board the board the game is played on
     * @param startSquare the square the Rook is on
     * @param endSquare the square the Rook tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) ||
            (endSquare.getPiece() != null && this.color == endSquare.getPiece().getColor()))
            return false;
        int startFile = startSquare.getFile();
        int startRank = startSquare.getRank();
        int endFile = endSquare.getFile();
        int endRank = endSquare.getRank();
        // Going down the board, check if there are any pieces in the way
        if (startFile == endFile && startRank > endRank)
            return checkSameFileObstructions(board, startFile, startRank, endRank);
        // Going up the board, check if there are any pieces in the way
        if (startFile == endFile && endRank > startRank)
            checkSameFileObstructions(board, startFile, endRank, startRank);
        // Going left on the board, check if there are any pieces in the way
        if (startRank == endRank && startFile > endFile)
            return sameRank(board, startRank, startFile, endFile);
        // Going right on the board, check if there are any pieces in the way
        if (startRank == endRank && endFile > startFile)
            return sameRank(board, startRank, endFile, startFile);
        return false;
    }

    /**
     * Convenience method, gets the squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the board the game is played on
     * @param startSquare String representation of the square the Rook is on
     * @param endSquare String representation of the square the Rook tries to move to
     */
    public boolean legalMove(Board board, String startSquare, String endSquare) {
        return this.legalMove(board, board.getSquare(startSquare), board.getSquare(endSquare));
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
    private boolean checkSameFileObstructions(Board board, int file, int highRank, int lowRank) {
        // Check if there are any pieces in the way when moving vertically,
        // starting at the rank with the highest numerical value, then moving down
        for (int i = lowRank; i < highRank; i++) {
            if(board.getBoard()[file][i].getPiece() != null)
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
    private boolean sameRank(Board board, int rank, int highFile, int lowFile) {
        // Check if there are any pieces in the way when moving horizontally,
        // starting at the file with the highest numerical value, then moving left
        for(int i = lowFile; i < highFile; i++) {
            if(board.getBoard()[i][rank].getPiece() != null)
                return false;
        }
        return true;
    }
}
