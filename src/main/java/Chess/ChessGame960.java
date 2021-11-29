package Chess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import Pieces.*;
// Credit for regex-related code: https://rosettacode.org/wiki/Generate_Chess960_starting_position#Java

@Deprecated
public class ChessGame960 extends ChessGame {
    private static final List<Character> pieces = Arrays.asList('R','B','N','Q','K','N','B','R');
    /**
     * Lowercase String of length 2, containing the files on which the Rooks
     * were placed in the starting position.
     */
    private String initialRookPositions = null;

    public ChessGame960() {
        // Make new method in superclass which initializes variables that are not consistent across chess variants?
        // Override said method in subclasses?
        super();
    }

    public ChessGame960(Player white, Player black) {
        super(white, black);
    }

    public ChessGame960(String fenNotation) {
        super(fenNotation);
    }

    public String generatePosition() {
        String firstRank = generateFirstRank();
        int queensideIndex = firstRank.indexOf("R");
        char queensideRook = (char) (queensideIndex + 97);
        char kingsideRook = (char) (firstRank.indexOf("R", queensideIndex + 1) + 97);
        this.initialRookPositions = String.valueOf(kingsideRook) + String.valueOf(queensideRook);
        return firstRank.toLowerCase() + "/pppppppp/8/8/8/8/PPPPPPPP/" + firstRank;
    }

    private String generateFirstRank(){
        String firstRank = "";
        do {
            Collections.shuffle(pieces);
            // Remove brackets and commas from toString result
            firstRank = pieces.toString().replaceAll("[^A-Z]", "");
        } while(!isValid(firstRank));
        return firstRank;
    }

    private boolean isValid(String firstRank){
        // Check that King is in between Rooks and Bishops have valid opposite-color places
        return firstRank.matches(".*R.*K.*R.*") && firstRank.matches(".*B(..|....|......|)B.*");
    }

    /**
     * <p> Calculates if a given King can castle from startSquare towards endSquare
     * and makes the move if it is allowed. </p>
     * <p> Note that the endSquare is not necessarily the Square the King ends up on,
     * but rather the Square the King has been told to move towards. The file of
     * endSquare indicates if it's a queenside or kingside castling move. </p>
     * 
     * <p> This method works similar to the super implementation, but is more flexible
     * with regards to the Squares it looks for a Rook on, enforces Rook legalMove to
     * it's end Square (which is not needed in super implementation) and takes into
     * account an edge case when castling queenside with the King on the b-file. </p>
     * @param startSquare the starting Square with the King on it
     * @param endSquare the Square to move towards
     * @param king the King
     * @return true if castling is successful, false otherwise
     */
    @Override
    protected boolean calculateCastling(Square startSquare, Square endSquare, King king) {
        // Method is called from makeMove() after verifying that the startPiece
        // is a King and that (difference in file >= 2 || endSquare has a Rook on it)
        if (isCheck || ! king.isFirstMove())
            return false;
        int rank = startSquare.getRank();
        if (! (rank == endSquare.getRank() && (rank == 0 || rank == 7) ))
            return false;
        int file = startSquare.getFile();
        // Default values for castling kingside
        int kingEndFile = 6;
        int rookStartFile = initialRookPositions.charAt(1) - 97;
        int rookEndFile = 5;
        int offset = 1;
        // Values when castling queenside
        if (file > endSquare.getFile()) {
            kingEndFile = 2;
            rookStartFile = initialRookPositions.charAt(0) - 97;
            rookEndFile = 3;
            // Edgecase for when the King is on the b-file when castling queenside
            offset = (endSquare.getFile() == 1) ? 1 : -1;
        }
        // Get Rook information
        Piece piece = board.getPiece(rookStartFile, rank);
        if( ! (piece instanceof Rook && piece.isSameColorAs(king) && ((Rook) piece).isFirstMove()))
            return false;
        // Loop towards destination Square while checking for checks and obstructions
        while (file != kingEndFile) {
            file += offset;
            if ((board.getPiece(file, rank) != null))
                return false;
            if (isPinned(startSquare, startSquare, board.getSquare(file, rank)))
                return false;
        }
        startSquare.setPiece(null);
        if ( ! ((Rook) piece).legalMove(board, startSquare, board.getSquare(rookEndFile, rank))) {
            startSquare.setPiece(king);
            return false;
        }
        board.getSquare(kingEndFile, rank).setPiece(king);
        king.setFirstMove(false);
        if (currentTurn.equals(whitePlayer))
            whiteKingSquare = board.getSquare(kingEndFile, rank);
        if (currentTurn.equals(blackPlayer))
            blackKingSquare = board.getSquare(kingEndFile, rank);
        board.getSquare(rookStartFile, rank).setPiece(null);
        board.getSquare(rookEndFile, rank).setPiece(piece);
        ((Rook) piece).setFirstMove(false);
        return true;
    }

    /**
     * <p> Calculate the castling availability and return it as a String for
     * use in FEN notation. </p>
     * 
     * <p> In Chess960, the FEN notation for the castling availability contains
     * the letters of the files the Rooks were on in the starting position. If
     * the Rooks would start at the edge of the board like in regular chess, the
     * notation would be "HAha" </p>
     * @return a String containing the castling availability in FEN notation
     */
    @Override
    public String getCastlingAvailability() {
        String res = "";
        char queensideFile = initialRookPositions.charAt(0);
        char kingsideFile = initialRookPositions.charAt(1);
        int queensideIndex = queensideFile - 97;
        int kingsideIndex = kingsideFile - 97;
        if ( ((King) whiteKingSquare.getPiece() ).isFirstMove()) {
            if (board.getPiece(kingsideIndex, 0) instanceof Rook
              && ((Rook) board.getPiece(kingsideIndex, 0)).isFirstMove())
                res += Character.toUpperCase(kingsideFile);
            if (board.getPiece(queensideIndex, 0) instanceof Rook
              && ((Rook) board.getPiece(queensideIndex, 0)).isFirstMove())
                res += Character.toUpperCase(queensideFile);
        }
        if ( ((King) blackKingSquare.getPiece() ).isFirstMove()) {
            if (board.getPiece(kingsideIndex, 7) instanceof Rook
              && ((Rook) board.getPiece(kingsideIndex, 7)).isFirstMove())
                res += kingsideFile;
            if (board.getPiece(queensideIndex, 7) instanceof Rook
              && ((Rook) board.getPiece(queensideIndex, 7)).isFirstMove())
                res += queensideFile;
        }
        if (res.length() == 0)
            return "-";
        return res;
    }

    /**
     * <p> Sets the firstMove property of Kings and Rooks according to the given String.
     * If there is no Rook on an expected location, that part of the String is
     * ignored </p>
     * 
     * <p> Note that in Chess960, the FEN notation for the castling availability
     * contains the letters of the files the Rooks were on in the starting
     * position. If the Rooks would start at the edge of the board like in
     * regular chess, the notation would be "HAha" </p>
     * @param castlingAvailability String containing FEN representation of castling availability
     */
    @Override
    public void setCastlingAvailability(String castlingAvailability) {
        // Setting King firstMoves
        if (castlingAvailability.equals("-")) {
            ((King) whiteKingSquare.getPiece()).setFirstMove(false);
            ((King) blackKingSquare.getPiece()).setFirstMove(false);
            return;
        }
        if (castlingAvailability.equals(castlingAvailability.toLowerCase()))
            ((King) whiteKingSquare.getPiece()).setFirstMove(false);
        if (castlingAvailability.equals(castlingAvailability.toUpperCase()))
            ((King) blackKingSquare.getPiece()).setFirstMove(false);
        // Setting Rook firstMoves
        char queensideFile = initialRookPositions.charAt(0);
        char kingsideFile = initialRookPositions.charAt(1);
        int queensideIndex = queensideFile - 97;
        int kingsideIndex = kingsideFile - 97;
        Piece piece = board.getPiece(kingsideIndex, 0);
        if (piece instanceof Rook && piece.getColor() == 'W') {
            if (castlingAvailability.indexOf(Character.toUpperCase(kingsideFile)) != -1)
                ((Rook) piece).setFirstMove(true);
            else
                ((Rook) piece).setFirstMove(false);
        }
        piece = board.getPiece(queensideIndex, 0);
        if (piece instanceof Rook && piece.getColor() == 'W') {
            if (castlingAvailability.indexOf(Character.toUpperCase(queensideFile)) != -1)
                ((Rook) piece).setFirstMove(true);
            else
                ((Rook) piece).setFirstMove(false);
        }
        piece = board.getPiece(kingsideIndex, 7);
        if (piece instanceof Rook && piece.getColor() == 'B') {
            if (castlingAvailability.indexOf(kingsideFile) != -1)
                ((Rook) piece).setFirstMove(true);
            else
                ((Rook) piece).setFirstMove(false);
        }
        piece = board.getPiece(queensideIndex, 7);
        if (piece instanceof Rook && piece.getColor() == 'B') {
            if (castlingAvailability.indexOf(queensideFile) != -1)
                ((Rook) piece).setFirstMove(true);
            else
                ((Rook) piece).setFirstMove(false);
        }
    }
}
