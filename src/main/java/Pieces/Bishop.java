package Pieces;

import Chess.Board;
import Chess.Square;

public class Bishop extends Piece {

    /**
     * Bishop object for a game of Chess
     * @param color the color of the Bishop, either 'W' or 'B'
     */
    public Bishop (char color) {
        super(color);
        this.name = (this.color == 'W') ? 'B' : 'b';
    }
    
    /**
     * <p> Checks if a move from startSquare to endSquare is legal for a Bishop. </p>
     * 
     * <p> Bishops move diagonally. A Bishop move is legal if
     * Math.abs( (difference in file) / (difference in rank) ) == 1 </p>
     * @param board the Board the game is played on
     * @param startSquare the Square the Bishop is on
     * @param endSquare the Square the Bishop tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        // Disallow 'moving' to the start square or to a square with a friendly piece
        if (startSquare.equals(endSquare) || (!endSquare.isEmpty() && this.color == endSquare.getPieceColor()))
            return false;
        int startFile = startSquare.getFile();
        int startRank = startSquare.getRank();
        int endFile = endSquare.getFile();
        int endRank = endSquare.getRank();
        // Check if move is a proper diagonal move while avoiding division by 0
        if ( (startRank == endRank) || Math.abs( (startFile - endFile) / (startRank - endRank) ) != 1 )
            return false;
        // Moving right and up
        if (endFile > startFile && endRank > startRank)
            return checkForObstructions(board, startFile, startRank, endFile, endRank, 1);
        // Moving left and down
        if (startFile > endFile && startRank > endRank)
            return checkForObstructions(board, endFile, endRank, startFile, startRank, 1);
        // Moving right and down
        if (endFile > startFile && startRank > endRank)
            return checkForObstructions(board, startFile, endRank, endFile, startRank, -1);
        // Moving left and up
        if (startFile > endFile && endRank > startRank)
            return checkForObstructions(board, endFile, startRank, startFile, endRank, -1);
        return false;
    }

    /**
     * Convenience method, gets the Squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Bishop is on
     * @param endString String representation of the Square the Bishop tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }

    /**
     * <p> Checks if there are any Pieces in between the in the way </p>
     * 
     * <p> Loop over the Board from left to right, meaning from lowRank to highRank.
     * Loop from lowFile to highFile if fileIncrement == 1, loop from highFile
     * to lowFile if fileIncrement == -1 </p>
     * @param board the Board the Game is played on
     * @param lowFile the moves file with the lowest numerical value
     * @param lowRank the moves rank with the lowest numerical value
     * @param highFile the moves file with the highest numerical value
     * @param highRank the moves rank with the highest numerical value
     * @param fileIncrement 1 if moving up the Board, -1 if moving down the Board
     * @return true if there are no Pieces in the way, false otherwise
     */
    public static boolean checkForObstructions(Board board, int lowFile,
      int lowRank, int highFile, int highRank, int fileIncrement) {
        int file = (fileIncrement == 1) ? lowFile : highFile;
        int rank;
        for (file += fileIncrement, rank = lowRank + 1;
          file > lowFile && file < highFile; file += fileIncrement, rank++) {
            if (board.getPiece(file, rank) != null)
                return false;
        }
        return rank == highRank;
    }
}

