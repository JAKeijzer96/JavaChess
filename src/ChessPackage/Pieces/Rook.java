package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Rook extends Piece {

    /**
     * Rook object for a game of Chess
     * @param color the color of the Rook, either 'W' or 'B'
     */
    public Rook (char color) {
        super(color);
        this.name = (color == 'W') ? 'R' : 'r';
    }

    /**
     * Checks if a move from startSquare to endSquare is legal for a Rook. <p>
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
        int startFile = startSquare.getFile();
        int startRank = startSquare.getRank();
        int endFile = endSquare.getFile();
        int endRank = endSquare.getRank();
        // Going up the board, check if there are any pieces in the way
        if (startFile == endFile && endRank > startRank)
            return checkForObstructions(board, startFile, startRank, endFile, endRank);
        // Going down the board, check if there are any pieces in the way
        if (startFile == endFile && startRank > endRank)
            return checkForObstructions(board, startFile, endRank, endFile, startRank);
        // Going right on the board, check if there are any pieces in the way
        if (startRank == endRank && endFile > startFile)
            return checkForObstructions(board, startFile, startRank, endFile, endRank);
        // Going left on the board, check if there are any pieces in the way
        if (startRank == endRank && startFile > endFile)
            return checkForObstructions(board, endFile, startRank, startFile, endRank);
        return false;
    }

    /**
     * Convenience method, gets the Squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Rook is on
     * @param endString String representation of the Square the Rook tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }

    /**
     * Check if there are any Pieces in the way <p>
     * Loop from the Square closest to a1 to the Square closest to h8. This method
     * doesn't check the startSquare and endSquare as that is already handled in
     * legalMove, instead it checks all the Squares in between them.
     * @param board the Board the Game is played on
     * @param lowFile the moves file with the lowest numerical value
     * @param lowRank the moves rank with the lowest numerical value
     * @param highFile the moves file with the highest numerical value
     * @param highRank the moves rank with the highest numerical value
     * @return true if there are no Pieces in the way, false otherwise
     */
    public static boolean checkForObstructions(Board board, int lowFile, int lowRank, int highFile, int highRank) {
        int fileIncrement = 0;
        int rankIncrement = 0;
        // Going up or down the Board
        if (lowFile == highFile)
            rankIncrement = (lowRank < highRank) ? 1 : -1;
        // Going left or right on the Board
        if (lowRank == highRank)
            fileIncrement = (lowFile < highFile) ? 1 : -1;
        for(int file = lowFile + fileIncrement, rank = lowRank + rankIncrement;
          (file > lowFile && file < highFile) || (rank > lowRank && rank < highRank);
          file += fileIncrement, rank += rankIncrement) {
            if (board.getPiece(file, rank) != null)
                return false;
        }
        return true;
    }
}
