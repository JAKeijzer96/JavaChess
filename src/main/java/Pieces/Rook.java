package Pieces;

import Chess.Board;
import Chess.Square;

public class Rook extends Piece {
    boolean isFirstMove;

    /**
     * Rook object for a game of Chess
     * @param color the color of the Rook, either 'W' or 'B'
     */
    public Rook (char color) {
        super(color);
        this.name = (this.color == 'W') ? 'R' : 'r';
        this.isFirstMove = true;
    }

    /**
     * <p> Checks if a move from startSquare to endSquare is legal for a Rook. </p>
     * 
     * <p> Rooks move horizontally along ranks and vertically along files
     * A Rook move is legal if startFile == endFile or startRank == endRank </p>
     * @param board the Board the game is played on
     * @param startSquare the Square the Rook is on
     * @param endSquare the Square the Rook tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) || (endSquare.isOccupied() && this.color == endSquare.getPieceColor()))
            return false;
        int startFile = startSquare.getFile();
        int startRank = startSquare.getRank();
        int endFile = endSquare.getFile();
        int endRank = endSquare.getRank();
        // Going up the board, check if there are any pieces in the way
        if (startFile == endFile && endRank > startRank &&
          checkForObstructions(board, startFile, startRank, endFile, endRank)) {
            this.isFirstMove = false;
            return true;
        }
        // Going down the board, check if there are any pieces in the way
        if (startFile == endFile && startRank > endRank &&
          checkForObstructions(board, startFile, endRank, endFile, startRank)) {
            this.isFirstMove = false;
            return true;
        }
        // Going right on the board, check if there are any pieces in the way
        if (startRank == endRank && endFile > startFile &&
          checkForObstructions(board, startFile, startRank, endFile, endRank)) {
            this.isFirstMove = false;
            return true;
        }
        // Going left on the board, check if there are any pieces in the way
        if (startRank == endRank && startFile > endFile &&
          checkForObstructions(board, endFile, startRank, startFile, endRank)) {
            this.isFirstMove = false;
            return true;
        }
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
     * <p> Check if there are any Pieces in the way </p>
     * 
     * <p> Loop from the Square closest to a1 to the Square closest to h8. This method
     * doesn't check the startSquare and endSquare as that is already handled in
     * legalMove, instead it checks all the Squares in between them. </p>
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

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }
}
