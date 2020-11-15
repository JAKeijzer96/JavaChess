package ChessPackage.Pieces;

import ChessPackage.Board;
import ChessPackage.Square;

public class Queen extends Piece {
    
    public Queen (char color) {
        super(color);
        this.name = (color == 'W') ? 'Q' : 'q';
    }

    /**
     * legalMove method for the Queen class.
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
        // Going down the board, check if there are any pieces in the way
        if (startFile == endFile && startRank > endRank)
            return Rook.checkSameFileObstructions(board, startFile, startRank, endRank);
        // Going up the board, check if there are any pieces in the way
        if (startFile == endFile && endRank > startRank)
            return Rook.checkSameFileObstructions(board, startFile, endRank, startRank);
        // Going left on the board, check if there are any pieces in the way
        if (startRank == endRank && startFile > endFile)
            return Rook.checkSameRankObstructions(board, startRank, startFile, endFile);
        // Going right on the board, check if there are any pieces in the way
        if (startRank == endRank && endFile > startFile)
            return Rook.checkSameRankObstructions(board, startRank, endFile, startFile);
        // Check for Bishop-like moves
        // Check if move is a proper diagonal move
        if ( Math.abs( (startFile - endFile) / (startRank - endRank) ) != 1 )
            return false;
        // Moving right and up
        if (endFile > startFile && endRank > startRank)
            return Bishop.checkRightLeaningDiagObstructions(board, endFile, startFile, startRank);
        // Moving left and down
        if (startFile > endFile && startRank > endRank)
            return Bishop.checkRightLeaningDiagObstructions(board, startFile, endFile, endRank);
        // Moving right and down
        if (endFile > startFile && startRank > endRank)
            return Bishop.checkLeftLeaningDiagObstructions(board, startRank, endFile, endRank);
        // Moving left and up
        if (startFile > endFile && endRank > startRank)
            return Bishop.checkLeftLeaningDiagObstructions(board, endRank, startFile, startRank);
        return false;
    }
    
    /**
     * Convenience method, gets the squares indicated by the Strings,
     * then calls legalMove(Board, Square, Square)
     * @param board the Board the game is played on
     * @param startString String representation of the Square the Queen is on
     * @param endString String representation of the Square the Queen tries to move to
     */
    public boolean legalMove(Board board, String startString, String endString) {
        return this.legalMove(board, board.getSquare(startString), board.getSquare(endString));
    }
}
