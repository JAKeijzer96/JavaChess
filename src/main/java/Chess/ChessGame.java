package Chess;

import java.util.ArrayList;
import java.util.List;

import Pieces.*;
import lombok.Getter;

/*
Castling works in standard ChessGames
Castling doesn't work yet in ChessGame960

TODO list:
- Fix castling in ChessGame960
- Implement en passant
- Find a proper way to reverse move which allows for setting firstMove property
  to the previous value.
- Implement end of game: checkmate, stalemate, insufficient material, 50 move rule,
  repetition. Not needed (for now): resignation, draw by agreement, timeout
- Implement GUI

Things to think about
- updatePieceLists now basically loops through the board twice, could be just once
- is the current way of calculating checks efficient or not? Feels like it's
  only slightly more efficient than checking every opponent Piece in the opening,
  while being drastically more inefficient in the endgame
- GUI: Do we integrate it into the Chess classes, or keep it completely seperate?
  Both have their pros and cons




*/


@Getter
public class ChessGame {
    Board board;
    ArrayList<Move> moveList;
    Player whitePlayer;
    Player blackPlayer;
    Player currentTurn;
    ArrayList<Square> whitePiecesSquares; // list of all Squares with white Pieces
    ArrayList<Square> blackPiecesSquares; // list of all Squares with black Pieces
    Square whiteKingSquare;
    Square blackKingSquare;
    boolean isCheck;
    boolean isMate;
    int halfMoveCounter; // halfMoveCounter used for fifty-move rule
    int fullMoveCounter; // fullMoveCounter to track amount of full Moves
    Square enPassantSquare; // For FEN notation

    /**
     * Constructs a ChessGame with default Players and starting position
     */
    public ChessGame() {
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<>();
        this.board = new Board();
        this.enPassantSquare = null;
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = whitePlayer;
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
        this.updatePieceLists();
    }

    /**
     * Constructs a new ChessGame with the standard starting position
     * @param white Player with the white pieces
     * @param black Player with the black pieces
     */
    public ChessGame(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        this.moveList = new ArrayList<>();
        this.board = new Board();
        this.enPassantSquare = null;
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = whitePlayer;
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
        this.updatePieceLists();
    }

    /**
     * <p> Constructor to make the ChessGame represented by the given FEN notation. </p>
     * This uses default Players for white and black.
     * @param fenNotation String containing valid FEN notation
     * @throws IllegalArgumentException if the FEN notation is not valid
     */
    public ChessGame(String fenNotation) {
        String[] arrOfStr = fenNotation.split(" ");
        if (arrOfStr.length != 6)
            throw new IllegalArgumentException("Invalid FEN notation: " +
                "too many or too few spaces");
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<>();
        this.board = new Board(arrOfStr[0]);
        String turn = arrOfStr[1].toLowerCase();
        if (turn.equals("w"))
            this.currentTurn = whitePlayer;
        else if (turn.equals("b"))
            this.currentTurn = blackPlayer;
        else
            throw new IllegalArgumentException("Invalid turn indicator in FEN");
        String enPassant = arrOfStr[3];
        this.enPassantSquare = (enPassant.equals("-")) ? null : board.getSquare(enPassant);
        this.halfMoveCounter = Integer.parseInt(arrOfStr[4]);
        this.fullMoveCounter = Integer.parseInt(arrOfStr[5]);
        this.updatePieceLists();
        for(Square square : whitePiecesSquares) {
            if(square.getPieceName() == 'K') {
                this.whiteKingSquare = square;
                break;
            }
        }
        for(Square square : blackPiecesSquares) {
            if(square.getPieceName() == 'k') {
                this.blackKingSquare = square;
                break;
            }
        }
        if (this.whiteKingSquare == null)
            throw new IllegalArgumentException("No white king found in FEN notation");
        if (this.blackKingSquare == null)
            throw new IllegalArgumentException("No black king found in FEN notation");
        // Do this after verifying that the kingSquares are set properly
        this.setCastlingAvailability(arrOfStr[2]);
    }

    /**
     * <p> Makes a move on the Board </p>
     * This method checks if the move is valid, moves the Piece, adds the Move
     * to the moveList, increments halfMoveCounter if needed and switches turns
     * @param startSquare the Square to move from
     * @param endSquare the Square to move to
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(Square startSquare, Square endSquare) {
        if (isMate)
            return false;
        Piece startPiece = startSquare.getPiece();
        Piece endPiece = endSquare.getPiece();
        Square kingSquare = (currentTurn.equals(whitePlayer)) ? whiteKingSquare : blackKingSquare;
        if (startPiece == null || startPiece.getColor() != currentTurn.getPlayerColor())
            return false;
        
        boolean castled = false;
        // If startPiece is a King and tries to move >= 2 Squares or to a Rook Square, it may try to castle
        if (startPiece instanceof King && (endPiece instanceof Rook
          || Math.abs(startSquare.getFile() - endSquare.getFile()) >= 2) )
            castled = calculateCastling(startSquare, endSquare, (King) startPiece);
        
        if (!castled && (!startPiece.legalMove(board, startSquare, endSquare)
          || isPinned(kingSquare, startSquare, endSquare)))
            return false;
        if (startPiece instanceof Pawn || endPiece != null)
            this.halfMoveCounter = 0;
        else
            this.halfMoveCounter++;
        // Move the Piece and update the relevant kingSquare
        if (! castled) {
            endSquare.setPiece(startPiece);
            startSquare.setPiece(null);
            if(startPiece instanceof King) {
                if (currentTurn.equals(whitePlayer))
                    whiteKingSquare = endSquare;
                if(currentTurn.equals(blackPlayer))
                    blackKingSquare = endSquare;
            }
        }
        updatePieceLists();
        updateCheck();

        Move move = new Move(startSquare, startPiece, endSquare, endPiece, isCheck, isMate, castled);
        this.moveList.add(move);

        switchTurn();
        return true;
    }

    /**
     * <p> Makes a move on the Board </p>
     * Convenience method, calls and returns makeMove(startSquare, endSquare)
     * @param startString String representation of the start Square
     * @param endString String representation of the end Square
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(String startString, String endString) {
        return makeMove(board.getSquare(startString), board.getSquare(endString));
    }

    protected void updatePieceLists() {
        // more efficient piecelist update?
        // Always update both lists in case of capture
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
    }

    private void updateCheck() {
        // Test if the other Players King is in check
        Square kingSquare = (currentTurn.equals(whitePlayer)) ? blackKingSquare : whiteKingSquare;
        Square checkingSquare = calculateChecks(kingSquare);
        if (checkingSquare != null) {
            this.isCheck = true;
            if (! canRemoveCheck(kingSquare, checkingSquare)) {
                this.isCheck = false;
                this.isMate = true;
            }
        }
        else {
            this.isCheck = false;
            this.isMate = false;
        }
    }

    /**
     * <p> Tests if there are any legalMoves that can remove check </p>
     * Check can be removed in three different ways:
     * <ul>
     *  <li> By moving the King away </li>
     *  <li> By capturing the Piece giving check - except if there are two attackers </li>
     *  <li> By blocking the Piece giving check - except if there are two attackers
     *       or if the attacker is a Knight </li>
     * </ul>
     * If none of these are possible, it is checkmate
     * @param kingSquare the Square the king is on
     * @param checkingSquare the Square from which check is given. This can be:
     * <ul>
     *  <li> null - no check given
     *  <li> a Square on the Board from which check is given
     *  <li> kingSquare - meaning there are two Pieces giving check at the same time
     * </ul>
     * @return true if there is a move which removes check, false otherwise
     */
    private boolean canRemoveCheck(Square kingSquare, Square checkingSquare) {
        if (checkingSquare == null)
            return true;
        Piece king = kingSquare.getPiece();
        int kingFile = kingSquare.getFile();
        int kingRank = kingSquare.getRank();
        // Calculate if the King can move to a different Square
        for (byte[] offsets : Board.SQUARE_OFFSETS) {
            if (kingFile + offsets[0] < 0 || kingFile + offsets[0] > 7
            || kingRank + offsets[1] < 0 || kingRank + offsets[1] > 7)
                continue;
            Square tempSquare = board.getSquare(kingFile+offsets[0], kingRank+offsets[1]);
            if (king.legalMove(board, kingSquare, tempSquare)) {
                if (isPinned(kingSquare, kingSquare, tempSquare))
                    continue;
                return true;
            }
        }
        // If we reach this when there are two attackers, return false
        if (checkingSquare.equals(kingSquare))
            return false;
        // Calculate if the checking Piece can be captured
        ArrayList<Square> friendlyPiecesSquares =
            (currentTurn.equals(whitePlayer)) ? blackPiecesSquares: whitePiecesSquares;
        for(Square friendlySquare : friendlyPiecesSquares) {
            if (friendlySquare.getPiece().legalMove(board, friendlySquare, checkingSquare)) {
                if (isPinned(kingSquare, friendlySquare, checkingSquare))
                    continue;
                return true;
            }
        }
        // If the attacker is a Knight and it can't be captured or moved away from
        if (checkingSquare.getPiece() instanceof Knight)
            return false;
        // Calculate offsets used in for loop
        int attackerFile = checkingSquare.getFile();
        int attackerRank = checkingSquare.getRank();
        int fileOffset = 0, rankOffset = 0;
        if (kingFile > attackerFile)
            fileOffset = 1;
        else if (kingFile < attackerFile)
            fileOffset = -1;
        if (kingRank > attackerRank)
            rankOffset = 1;
        else if (kingRank < attackerRank)
            rankOffset = -1;
        // Calculate if any friendly Piece can move in between the checking Piece and the King
        for(Square friendlySquare : friendlyPiecesSquares) {
            if (friendlySquare.equals(kingSquare))
                continue;
            Piece friendlyPiece = friendlySquare.getPiece();
            for(int f = attackerFile + fileOffset, r = attackerRank + rankOffset;
              !(f == kingFile && r == kingRank); f += fileOffset, r += rankOffset) {
                Square tempSquare = board.getSquare(f, r);
                if (friendlyPiece.legalMove(board, friendlySquare, tempSquare)) {
                    if (isPinned(kingSquare, friendlySquare, tempSquare))
                        continue;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p> Test if a Piece is pinned </p>
     * <p> Try to move from currentSquare to destSquare, then see if the king is in check.
     * Moves Pieces back to their original positions before returning. </p>
     * <p> Can also be used to test if the King can move out of check to destSquare.
     * In this case the kingSquare and currentSquare parameters should reference the
     * same Square object </p>
     * @param kingSquare the Square the King is on
     * @param currentSquare the Square the Piece is currently on
     * @param destSquare the destination Square the Piece tries to move to
     * @return true if the Piece is pinned, false if it's not
     */
    protected boolean isPinned(Square kingSquare, Square currentSquare, Square destSquare) {
        Piece currentPiece = currentSquare.getPiece();
        Piece destPiece = destSquare.getPiece();
        currentSquare.setPiece(null);
        destSquare.setPiece(currentPiece);
        Square isCheckFromSquare = (kingSquare.equals(currentSquare)) ?
            calculateChecks(destSquare) : calculateChecks(kingSquare);
        currentSquare.setPiece(currentPiece);
        destSquare.setPiece(destPiece);
        return isCheckFromSquare != null;
    }

    /**
     * <p> Calculates how many Pieces are giving check </p>
     * Calculation starts at the Kings Square, then loops outwards in all
     * 8 directions. After this, look for possible Knights around the King.
     * @param kingSquare the Square the King is on
     * @return the Square from which check is given. If there are two Pieces giving
     * check, return the kingSquare instead. Returns null if there is no check
     */
    private Square calculateChecks(Square kingSquare) {
        // Test for checks in all 8 directions, starting with diagonal up right
        // and moving clockwise
        Square attackerSquare = null;
        Square currentSquare;
        for (byte[] offsets : Board.SQUARE_OFFSETS) {
            currentSquare = checkLoop(kingSquare, offsets[0], offsets[1]);
            if (attackerSquare == null && currentSquare != null) {
                attackerSquare = currentSquare;
            }
            // If attackerSquare != null and currentSquare != null, return kingSquare
            else if (currentSquare != null) {
                return kingSquare;
            }
        }
        // check for any Knights giving check
        int file = kingSquare.getFile();
        int rank = kingSquare.getRank();
        for(byte[] offset : Board.KNIGHT_OFFSETS) {
            if (file + offset[0] < 0 || file + offset[0] > 7
                || rank + offset[1] < 0 || rank + offset[1] > 7) {
                continue;
            }
            currentSquare = board.getSquare(file+offset[0], rank+offset[1]);
            Piece p = currentSquare.getPiece();
            // There can only be one Knight at a time giving check. Because we
            // already checked for other Pieces, we can return immediately
            if (p != null && p.getUpperCaseName() == 'N' && p.getColor() != kingSquare.getPieceColor())
                return (attackerSquare == null) ? currentSquare : kingSquare;
        }
        return attackerSquare;
    }

    /**
     * <p> Loop from the Kings Square outwards in a given direction until a Piece
     * is found, or until the edge of the Board is hit. </p>
     * Below is a grid showing the file/rank offsets as viewed from the kingSquare
     * <pre>
     *+---+---+---+
     *|-/+|0/+|+/+|
     *+---+---+---+
     *|-/0| K |+/0|
     *+---+---+---+
     *|-/-|0/-|+/-|
     *+---+---+---+
     * </pre>
     * @param kingSquare the Square the King is on
     * @param fileOffset the amount to offset the file by every iteration
     * @param rankOffset the amount to offset the rank by every iteration
     * @return the Square of the checking Piece if there is one, null otherwise
     */
    private Square checkLoop(Square kingSquare, int fileOffset, int rankOffset) {
        // Add offsets to file and rank so we dont test starting Square
        for(int f = kingSquare.getFile() + fileOffset, r = kingSquare.getRank() + rankOffset;
         f > -1 && f < Board.BOARDSIZE && r > -1 && r < Board.BOARDSIZE;
         f += fileOffset, r += rankOffset) {
            Square currentSquare = board.getSquare(f, r);
            Piece piece = currentSquare.getPiece();
            // Continue loop if we find no Piece
            if(piece == null)
                continue;
            // Return null if we find a Piece of the same color as the King, as
            // any Pieces behind it will be blocked and unable to give check
            if(piece.getColor() == kingSquare.getPieceColor())
                return null;
            // Return currentSquare if there is a Piece with a legalMove to the kingSquare
            if (piece.legalMove(board, currentSquare, kingSquare)) {
                return currentSquare;
            }
            // This part is only reached when there is an opponents Piece in the
            // way with no valid move to the kingSquare. This Piece blocks any
            // Pieces behind it, so no need to test further in this direction
            return null;
        }
        return null;
    }

    /**
     * <p> Calculates if a given King can castle from startSquare towards endSquare
     * and makes the move if it is allowed. </p>
     * Note that the endSquare is not necessarily the Square the King ends up on,
     * but rather the Square the King has been told to move towards. The file of
     * endSquare indicates if it's a queenside or kingside castling move.
     * @param startSquare the starting Square with the King on it
     * @param endSquare the Square to move towards
     * @param king the King
     * @return true if castling is successful, false otherwise
     */
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
        int rookStartFile = 7;
        int rookEndFile = 5;
        int offset = 1;
        // Values when castling queenside
        if (file > endSquare.getFile()) {
            kingEndFile = 2;
            rookStartFile = 0;
            rookEndFile = 3;
            offset = -1;
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
        // Check b-file when castling queenside. Same end result as legalMove() but less expensive
        if (rookEndFile == 3 && board.getPiece(1, rank) != null)
            return false;
        board.getSquare(rookStartFile, rank).setPiece(null);
        board.getSquare(rookEndFile, rank).setPiece(piece);
        ((Rook) piece).setFirstMove(false);
        startSquare.setPiece(null);
        board.getSquare(kingEndFile, rank).setPiece(king);
        king.setFirstMove(false);
        if (currentTurn.equals(whitePlayer))
            whiteKingSquare = board.getSquare(kingEndFile, rank);
        if (currentTurn.equals(blackPlayer))
            blackKingSquare = board.getSquare(kingEndFile, rank);
        return true;
    }

    /**
     * <p> Reverses the last move made in the Game </p>
     * 
     * <p> We'll call this a beta version of the function. It places any Pieces
     * that were on the endSquare back, and moves the startPiece back to its
     * startSquare. What this method doesn't do is set properties such as firstMove
     * and enPassantSquare back to their previous values. Expect weird behaviour when
     * reversing castling Moves. This method needs more work </p>
     */
    public void reverseLastMove() {
        int index = moveList.size() - 1;
        Move move = moveList.get(index);
        moveList.remove(index);
        Square startSquare = move.getStartSquare();
        Piece startPiece = move.getStartPiece();
        Square endSquare = move.getEndSquare();
        Piece endPiece = move.getEndPiece();
        // move the Pieces back
        startSquare.setPiece(startPiece);
        endSquare.setPiece(endPiece);
        if (this.halfMoveCounter > 0) {
            this.halfMoveCounter--;
        }
        if (currentTurn.equals(whitePlayer)) {
            currentTurn = blackPlayer;
            this.fullMoveCounter--;
        } else {
            currentTurn = whitePlayer;
        }
        // update King Square, need to check currentTurn again after changing it
        if(startPiece instanceof King) {
            if (currentTurn.equals(whitePlayer)) {
                whiteKingSquare = startSquare;
            } else {
                blackKingSquare = startSquare;
            }
        }
        updatePieceLists();
        updateCheck();
    }

    /**
     * Switch whose turn it is and increment fullMoveCounter after black move
     */
    private void switchTurn() {
        if (currentTurn.equals(whitePlayer))
            currentTurn = blackPlayer;
        else {
            currentTurn = whitePlayer;
            this.fullMoveCounter++;
        }
    }

    /**
     * Converts the current state of the Game to FEN notation.
     * @return the FEN notation of the current gamestate
     */
    @Override
    public String toString() {
        char turn = (currentTurn.equals(whitePlayer)) ? 'w' : 'b';
        String enPassant = (enPassantSquare != null) ? enPassantSquare.toString() : "-";
        return (board.toString() + " " + turn + " " + getCastlingAvailability() + " "
            + enPassant + " " + halfMoveCounter + " " + fullMoveCounter);
    }

    /**
     * Calculate the castling availability and return it as a String for use in
     * FEN notation. At the start of a Game this would be "KQkq".
     * @return a String containing the castling availability in FEN notation
     */
    public String getCastlingAvailability() {
        String res = "";
        if ( ((King) whiteKingSquare.getPiece() ).isFirstMove()) {
            if (board.getPiece(7, 0) instanceof Rook && ((Rook) board.getPiece(7, 0)).isFirstMove())
                res += "K";
            if (board.getPiece(0, 0) instanceof Rook && ((Rook) board.getPiece(0, 0)).isFirstMove())
                res += "Q";
        }
        if ( ((King) blackKingSquare.getPiece() ).isFirstMove()) {
            if (board.getPiece(7, 7) instanceof Rook && ((Rook) board.getPiece(7, 7)).isFirstMove())
                res += "k";
            if (board.getPiece(0, 7) instanceof Rook && ((Rook) board.getPiece(0, 7)).isFirstMove())
                res += "q";
        }
        if (res.length() == 0)
            return "-";
        return res;
    }

    /**
     * <p> Sets the firstMove property of Kings and Rooks according to the given String </p>
     * 
     * <p> If there is no Rook on an expected location, that part of the String is
     * ignored </p>
     * @param castlingAvailability String containing FEN representation of castling availability
     */
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
        Piece piece = board.getPiece(7, 0);
        if (piece instanceof Rook && piece.getColor() == 'W') {
            ((Rook) piece).setFirstMove(castlingAvailability.contains("K"));
        }
        piece = board.getPiece(0, 0);
        if (piece instanceof Rook && piece.getColor() == 'W') {
            ((Rook) piece).setFirstMove(castlingAvailability.contains("Q"));
        }
        piece = board.getPiece(7, 7);
        if (piece instanceof Rook && piece.getColor() == 'B') {
            ((Rook) piece).setFirstMove(castlingAvailability.contains("k"));
        }
        piece = board.getPiece(0, 7);
        if (piece instanceof Rook && piece.getColor() == 'B') {
            ((Rook) piece).setFirstMove(castlingAvailability.contains("q"));
        }
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    public Square getWhiteKingSquare() {
        return whiteKingSquare;
    }

    public Square getBlackKingSquare() {
        return blackKingSquare;
    }

    public boolean isMate() {
        return isMate;
    }

}
