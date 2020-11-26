package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Bishop extends Piece {
    public Bishop (char color) {
        super(color);
        this.name = (color == 'W') ? 'B' : 'b';
    }
    
    /**
     * legalMove method for the Bishop class.
     * Bishops move diagonally. A Bishop move is legal if
     * Math.abs( (difference in file) / (difference in rank) ) == 1
     * @param board the Board the game is played on
     * @param startSquare the Square the Bishop is on
     * @param endSquare the Square the Bishop tries to move to
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
        // Check if move is a proper diagonal move
        if ( Math.abs( (startFile - endFile) / (startRank - endRank) ) != 1 )
            return false;
        // Moving right and up
        if (endFile > startFile && endRank > startRank)
            return checkRightLeaningDiagObstructions(board, endFile, startFile, startRank);
        // Moving left and down
        if (startFile > endFile && startRank > endRank)
            return checkRightLeaningDiagObstructions(board, startFile, endFile, endRank);
        // Moving right and down
        if (endFile > startFile && startRank > endRank)
            return checkLeftLeaningDiagObstructions(board, startRank, endFile, endRank);
        // Moving left and up
        if (startFile > endFile && endRank > startRank)
            return checkLeftLeaningDiagObstructions(board, endRank, startFile, startRank);
        return false;
    }

    /**
     * Convenience method, gets the squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Bishop is on
     * @param endString String representation of the Square the Bishop tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }

    /**
     * Method to check if there are any pieces in the way along right-leaning diagonals.
     * Checks squares starting at the lowest numerical file and rank values in the
     * proposed move, then moves towards the highest numerical file and rank values
     * @param board the Board the game is played on
     * @param highFile value used to terminate the for-loop. Comparing lowFile
     * and highFile vs lowRank and highRank makes technically makes no difference.
     * In this implementation checking files was chosen
     * @param lowFile the moves file with the lowest numerical value
     * @param lowRank the moves rank with the lowest numerical value
     * @return true if there are no pieces obstructing the move, false otherwise
     */
    public static boolean checkRightLeaningDiagObstructions(Board board, byte highFile, byte lowFile, byte lowRank) {
        // Add 1 to lowFile and lowRank so we don't collide with ourselves. If the 
        // endSquare is at lowFile or lowRank, we already checked for friendly pieces.
        for(byte file = (byte)(lowFile + 1), rank = (byte)(lowRank + 1); file < highFile; file++, rank++) {
            if(board.getSquare(file, rank).getPiece() != null)
                return false;
        }
        return true;
    }

    /**
     * Method to check if there are any pieces in the way along left-leaning diagonals.
     * Checks squares starting at the lowest numerical file and rank values in the
     * proposed move, then moves towards the highest numerical file and rank values
     * @param board the Board the game is played on
     * @param highRank value used to terminate the for-loop. 
     * @param highFile the moves file with the highest numerical value
     * @param lowRank the moves rank with the lowest numerical value
     * @return true if there are no pieces obstructing the move, false otherwise
     */
    public static boolean checkLeftLeaningDiagObstructions(Board board, byte highRank, byte highFile, byte lowRank) {
        // Start at bottom right, move to top left. this means we go lowRank to highRank
        // but highFile to lowFile. Sub 1 from highFile and add 1 to lowRank so
        // we don't collide with ourselves. If the endSquare is at lowRank, we
        // already checked for friendly pieces
        for(byte file = (byte)(highFile - 1), rank = (byte)(lowRank + 1); rank < highRank; file--, rank++) {
            if(board.getSquare(file, rank).getPiece() != null)
                return false;
        }
        return true;
    }
}

