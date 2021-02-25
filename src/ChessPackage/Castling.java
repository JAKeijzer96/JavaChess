// package ChessPackage;

// public class Castling {


//     /**
//      * <p> Calculate if a given King can castle from startSquare towards endSquare.
//      * Note that the endSquare is not necessarily the Square the King ends up on,
//      * but rather the Square the King has been told to move towards. The file of
//      * endSquare indicates if it's a queenside or kingside castling move. </p>
//      * This method assumes that the King is not currently in check.
//      * @param startSquare the starting Square with the King on it
//      * @param endSquare the end Square
//      * @param king the King
//      * @return true if castling is allowed, false otherwise
//      */
//     public boolean calculateCastling(Square startSquare, Square endSquare, King king) {
//         // Method is called from makeMove() after verifying that the startPiece
//         // is a King, the King is not in check and that
//         // (difference in file >= 2 OR endSquare has a rook on it)
//         int rank = startSquare.getRank();
//         if (rank != endSquare.getRank() || ! (rank == 0 || rank == 7) )
//             return false;
//         if (! king.isFirstMove())
//             return false;
//         int startFile = startSquare.getFile();
//         int endFile = endSquare.getFile();
//         int file = startFile;
//         Piece piece = null;
//         Square rookSquare = null;
//         Square rookDestinationSquare = null;
//         Rook rook = null;
//         Square newKingSquare = null;
//         Square tempSquare = null;

//         // Castling kingside
//         // Both in regular chess and chess960, the king ends up on the g-file
//         // when castling kingside while the rook ends up on the f-file.
//         // This means that index 5 (f-file) and index 6 (g-file) can be hardcoded
//         if (startFile < endFile) {
//             while(piece == null) {
//                 file++;
//                 piece = board.getPiece(file, rank);
//                 tempSquare = board.getSquare(file, rank);
//                 // Test if King would be in check on the current Square
//                 if (file <= 6 && isPinned(startSquare, startSquare, tempSquare))
//                     return false;
//             }
//             if (piece instanceof Rook && piece.isSameColorAs(king)) {
//                 rook = (Rook) piece;
//                 rookSquare = board.getSquare(file, rank);
//                 if (! rook.isFirstMove())
//                     return false;
//                 // Test if the rook can move to its destination Square
//                 rookDestinationSquare = board.getSquare(5, rank);
//                 startSquare.setPiece(null);
//                 if (! rook.legalMove(board, rookSquare, rookDestinationSquare)) {
//                     startSquare.setPiece(king);
//                     return false;
//                 }
//                 // Get the new kingSquare and place the Rook on its destination Square
//                 newKingSquare = board.getSquare(6, rank);
//             } else
//                 return false;
//         }
//         // Castling queenside
//         // Both in regular chess and chess960, the king ends up on the c-file
//         // when castling queenside while the rook ends up on the d-file.
//         // This means that index 2 (c-file) and index 3 (d-file) can be hardcoded
//         else {
//             // Edgecase in chess960 where the rook is on the a-file and the king on the b-file.
//             // Castling queenside is still possible by dragging the king left to the a-file,
//             // but the King will end up moving right to the c-file if castling is permitted.
//             int fileOffset = (startFile < 2) ? 1 : -1;
//             while (piece == null) {
//                 file += fileOffset;
//                 piece = board.getPiece(file, rank);
//                 tempSquare = board.getSquare(file, rank);
//                 // Test if King would be in check on the current Square
//                 if (file != 2 && isPinned(startSquare, startSquare, tempSquare))
//                     return false;
//             }
//             if (fileOffset == -1 && piece instanceof Rook && piece.isSameColorAs(king)) {
//                 rook = (Rook) piece;
//                 rookSquare = board.getSquare(file, rank);
//                 if (! rook.isFirstMove())
//                     return false;
//                 // Test if the Rook can move to its destination Square
//                 rookDestinationSquare = board.getSquare(3, rank);
//                 startSquare.setPiece(null);
//                 if (! rook.legalMove(board, rookSquare, rookDestinationSquare)) {
//                     startSquare.setPiece(king);
//                     return false;
//                 }
//                 newKingSquare = board.getSquare(2, rank);
            
//             // In the edgecase as described above, if we find a rook on the c-file
//             // that's the wrong rook.
//             } else if (fileOffset == 1) {
//                 rook = (Rook) endSquare.getPiece();
//                 rookSquare = endSquare;
//                 if (! (rook.isFirstMove() && rook.isSameColorAs(king)))
//                     return false;
//                 // Test if the Rook can move to its destination Square
//                 rookDestinationSquare = board.getSquare(3, rank);
//                 startSquare.setPiece(null);
//                 if (! rook.legalMove(board, rookSquare, rookDestinationSquare)) {
//                     startSquare.setPiece(king);
//                     return false;
//                 }
//                 newKingSquare = board.getSquare(2, rank);
//             } else
//                 return false;
//         }
//         startSquare.setPiece(null);
//         newKingSquare.setPiece(king);
//         if (currentTurn.equals(whitePlayer))
//             whiteKingSquare = newKingSquare;
//         if (currentTurn.equals(blackPlayer))
//             blackKingSquare = newKingSquare;
//         rookSquare.setPiece(null);
//         rookDestinationSquare.setPiece(rook);
//         king.setFirstMove(false);
//         rook.setFirstMove(false);
//         return true;
//     }
    // /**
    //  * Calculate the castling availability and return it as a String for use in
    //  * FEN notation. At the start of a Game this would be "KQkq".
    //  * Currently doesn't support chess960 FEN notation
    //  * @return a String containing the castling availability in FEN notation
    //  */
    // public String getCastlingAvailability() {
    //     String white = "";
    //     String black = "";
    //     if (((King) whiteKingSquare.getPiece()).isFirstMove() ) {
    //         for (Square square : whitePiecesSquares) {
    //             Piece piece = square.getPiece();
    //             if (piece instanceof Rook && ((Rook) piece).isFirstMove()) {
    //                 if (square.getFile() > whiteKingSquare.getFile())
    //                     white += "K";
    //                 else
    //                     white += "Q";
    //             }
    //         }
    //     }
    //     char[] whiteArray = white.toCharArray();
    //     java.util.Arrays.sort(whiteArray);
    //     white = new String(whiteArray);
    //     if (((King) blackKingSquare.getPiece()).isFirstMove() ) {
    //         for (Square square : blackPiecesSquares) {
    //             Piece piece = square.getPiece();
    //             if (piece instanceof Rook && ((Rook) piece).isFirstMove()) {
    //                 if (square.getFile() > blackKingSquare.getFile())
    //                     black += "k";
    //                 else
    //                     black += "q";
    //             }
    //         }
    //     }
    //     char[] blackArray = black.toCharArray();
    //     java.util.Arrays.sort(blackArray);
    //     black = new String(blackArray);
    //     if ((white + black).length() == 0)
    //         return "-";
    //     return white + black;
    // }
    
    // // todo: fix chess960 generation first
    // public void setCastlingAvailability(String castlingAvailability) {
    //     King whiteKing = (King) whiteKingSquare.getPiece();
    //     King blackKing = (King) blackKingSquare.getPiece();
    //     if (castlingAvailability.equals("-")) {
    //         whiteKing.setFirstMove(false);
    //         blackKing.setFirstMove(false);
    //         return;
    //     }
    //     if (castlingAvailability.indexOf('K') == -1 && castlingAvailability.indexOf('Q') == -1)
    //         whiteKing.setFirstMove(false);
    //     else {
    //         List<Rook> whiteRooks = new ArrayList<Rook>();
    //         for (Square square : whitePiecesSquares) {
    //             if (square.getRank() == whiteKingSquare.getRank()
    //               && square.getPiece() instanceof Rook)
    //                 whiteRooks.add((Rook) square.getPiece());
    //         }
    //         if (castlingAvailability.indexOf('K') != -1) {
                
    //         }
    //         if (castlingAvailability.indexOf('Q') != -1) {
    //             for (Square square : whitePiecesSquares) {
    //                 if (square.getRank() == whiteKingSquare.getRank()
    //                   && square.getFile() < whiteKingSquare.getFile()
    //                   && square.getPiece() instanceof Rook)
    //                     ((Rook) square.getPiece()).setFirstMove(true);
    //             }
    //         }
    //     }
    //     if (castlingAvailability.indexOf('k') == -1 && castlingAvailability.indexOf('q') == -1)
    //         blackKing.setFirstMove(false);
    //     else {

    //     }
    // }
// }
