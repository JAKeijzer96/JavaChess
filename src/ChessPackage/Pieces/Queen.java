package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Queen extends Piece {

    /**
     * Queen object for a game of Chess
     * @param color the color of the Queen, either 'W' or 'B'
     */
    public Queen (char color) {
        super(color);
        this.name = (color == 'W') ? 'Q' : 'q';
    }

    /**
     * Checks if a move from startSquare to endSquare is legal for a Queen. <p>
     * Queens move along diagonals, ranks and files.
     * If a move is legal for a Rook or Bishop, it's legal for a Queen
     * @param board the Board the game is played on
     * @param startSquare the Square the Queen is on
     * @param endSquare the Square the Queen tries to move to
     * @return true if the move from startSquare to endSquare is legal, false otherwise
     */
    public boolean legalMove(Board board, Square startSquare, Square endSquare) {
        // Disallow 'moving' to the startSquare square or to a square with a friendly piece
        if (startSquare.equals(endSquare) || (endSquare.getPiece() != null && this.color == endSquare.getPiece().getColor()))
            return false;
        byte startFile = startSquare.getFile();
        byte startRank = startSquare.getRank();
        byte endFile = endSquare.getFile();
        byte endRank = endSquare.getRank();
        // Check for Rook-like moves
        // Going up the board, check if there are any pieces in the way
        if (startFile == endFile && endRank > startRank)
            return Rook.checkForObstructions(board, startFile, startRank, endFile, endRank);
        // Going down the board, check if there are any pieces in the way
        if (startFile == endFile && startRank > endRank)
            return Rook.checkForObstructions(board, startFile, endRank, endFile, startRank);
        // Going right on the board, check if there are any pieces in the way
        if (startRank == endRank && endFile > startFile)
            return Rook.checkForObstructions(board, startFile, startRank, endFile, endRank);
        // Going left on the board, check if there are any pieces in the way
        if (startRank == endRank && startFile > endFile)
            return Rook.checkForObstructions(board, endFile, startRank, startFile, endRank);
        // Check for Bishop-like moves
        // Check if move is a proper diagonal move
        if ( Math.abs( (startFile - endFile) / (startRank - endRank) ) != 1 )
            return false;
        // Moving right and up
        if (endFile > startFile && endRank > startRank)
            return Bishop.checkForObstructions(board, startFile, startRank, endFile, endRank, 1);
        // Moving left and down
        if (startFile > endFile && startRank > endRank)
            return Bishop.checkForObstructions(board, endFile, endRank, startFile, startRank, 1);
        // Moving right and down
        if (endFile > startFile && startRank > endRank)
            return Bishop.checkForObstructions(board, startFile, endRank, endFile, startRank, -1);
        // Moving left and up
        if (startFile > endFile && endRank > startRank)
            return Bishop.checkForObstructions(board, endFile, startRank, startFile, endRank, -1);
        return false;
    }
    
    /**
     * Convenience method, gets the Squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Queen is on
     * @param endString String representation of the Square the Queen tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }
}
