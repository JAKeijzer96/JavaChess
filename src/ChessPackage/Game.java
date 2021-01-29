package ChessPackage;

import java.util.ArrayList;
import java.util.List;

import ChessPackage.Pieces.*;

public class Game {
    Board board;
    List<Move> moveList;
    Player whitePlayer;
    Player blackPlayer;
    Player currentTurn;
    List<Square> whitePiecesSquares; // list of all Squares with white Pieces
    List<Square> blackPiecesSquares; // list of all Squares with black Pieces
    Square whiteKingSquare;
    Square blackKingSquare;
    boolean isCheck;
    boolean isMate;
    int halfMoveCounter; // halfMoveCounter used for fifty-move rule
    int fullMoveCounter; // fullMoveCounter to track amount of full Moves
    String castlingAvailability; // String for FEN notation
    Square enPassantSquare; // For FEN notation

    /**
     * Constructs a Chess Game with default Players and starting position
     */
    public Game() {
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<Move>();
        this.board = new Board();
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = null;
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
    }

    /**
     * Constructs a new Chess Game with the standard starting position
     * @param white Player with the white pieces
     * @param black Player with the black pieces
     */
    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        this.moveList = new ArrayList<Move>();
        this.board = new Board();
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = null;
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
    }

    /**
     * Constructor to make the game represented by the given FEN notation. <p>
     * This uses default Players for white and black.
     * @param fenNotation String containing valid FEN notation
     * @throws IllegalArgumentException if the FEN notation is not valid
     */
    public Game(String fenNotation) {
        String[] arrOfStr = fenNotation.split(" ");
        if (arrOfStr.length != 6)
            throw new IllegalArgumentException("Invalid FEN notation: " +
                "too many or too few spaces");
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<Move>();
        this.board = new Board(arrOfStr[0]);
        String turn = arrOfStr[1].toLowerCase();
        if (turn.equals("w"))
            this.currentTurn = this.whitePlayer;
        else if (turn.equals("b"))
            this.currentTurn = this.blackPlayer;
        else
            throw new IllegalArgumentException("Invalid turn indicator in FEN");
        this.castlingAvailability = arrOfStr[2];
        String enPassant = arrOfStr[3];
        this.enPassantSquare = (enPassant.equals("-")) ? null : board.getSquare(enPassant);
        this.halfMoveCounter = Integer.parseInt(arrOfStr[4]);
        this.fullMoveCounter = Integer.parseInt(arrOfStr[5]);
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        for(Square square : whitePiecesSquares) {
            if(square.getPiece().getName() == 'K') {
                this.whiteKingSquare = square;
                break;
            }
        }
        for(Square square : blackPiecesSquares) {
            if(square.getPiece().getName() == 'k') {
                this.blackKingSquare = square;
                break;
            }
        }
        if (this.whiteKingSquare == null)
            throw new IllegalArgumentException("No white king found in FEN notation");
        if (this.blackKingSquare == null)
            throw new IllegalArgumentException("No black king found in FEN notation");
    }

    /**
     * Makes a move on the Board <p>
     * This method checks if the move is valid, moves the Piece, adds the Move
     * to the moveList, increments halfMoveCounter if needed and switches turns
     * @param startString String representation of the start Square
     * @param endString String representation of the end Square
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(String startString, String endString) {
        if (isMate)
            return false;
        Square startSquare = board.getSquare(startString);
        Square endSquare = board.getSquare(endString);
        Piece startPiece = startSquare.getPiece();
        Piece endPiece = endSquare.getPiece();
        Square kingSquare = (currentTurn.equals(whitePlayer)) ? whiteKingSquare : blackKingSquare;
        if (startPiece == null || startPiece.getColor() != currentTurn.getPlayerColor())
            return false;
        
        // need to think of how to reverse castling move
        // --> make new Move constructor?
        // also figure out how to translate castling availability into string
        // --> if king and rook firstmove, add that possibility. 
        //      if king is normally able to castle but is in check/would move through check,
        //      it still shows in FEN as being able to castle. this is so that
        //      once check is removed without the king and/or rook moving, castling is still possible
        // --> FEN castling notation for chess960 is different, shows files of rooks starting
        //      squares instead. e.g. AHah instead of KQkq
        boolean castled = false;
        // Do easy checks before calling function
        if (! isCheck && startPiece instanceof King &&
          (Math.abs(startSquare.getFile() - endSquare.getFile()) >= 2
            || endSquare.getPiece() instanceof Rook)) {
            castled = calculateCastling(startSquare, endSquare, (King) startPiece);
            if (! castled)
                return false;
            
        } else if (!startPiece.legalMove(board, startSquare, endSquare)
          || isPinned(kingSquare, startSquare, endSquare))
            return false;
        if (startPiece instanceof Pawn || endPiece != null)
            this.halfMoveCounter = 0;
        else
            this.halfMoveCounter++;
        // move the Piece
        if (! castled) {
            endSquare.setPiece(startPiece);
            startSquare.setPiece(null);
            // update King Square
            if(startPiece instanceof King) {
                if (currentTurn.equals(whitePlayer))
                    whiteKingSquare = endSquare;
                if(currentTurn.equals(blackPlayer))
                    blackKingSquare = endSquare;
            }
        }
        updateGameVariables();

        Move move = new Move(startSquare, startPiece, endSquare, endPiece, isCheck, isMate, castled);
        this.moveList.add(move);

        switchTurn();
        return true;
    }

    public void updateGameVariables() {
        // update piecesSquares lists. always update both in case of capture
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        // change kingSquare to the other Players kingSquare to see if it's in check
        // move to seperate function?
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
     * Tests if there are any legalMoves that can remove check <p>
     * Check can be removed in three different ways:
     * <ul>
     * <li> By moving the King away </li>
     * <li> By capturing the Piece giving check - except if there are two attackers </li>
     * <li> By blocking the Piece giving check - except if there are two attackers
     *      or if the attacker is a Knight </li>
     * </ul>
     * If none of these are possible, it is checkmate
     * @param kingSquare the Square the king is on
     * @param checkingSquare the Square from which check is given. This can be:
     * <ul>
     * <li> null - no check given
     * <li> a Square on the Board from which check is given
     * <li> kingSquare - meaning there are two Pieces giving check at the same time
     * </ul>
     * @return true if there is a move which removes check, false otherwise
     */
    public boolean canRemoveCheck(Square kingSquare, Square checkingSquare) {
        if (checkingSquare == null)
            return true;
        Piece king = kingSquare.getPiece();
        int kingFile = kingSquare.getFile();
        int kingRank = kingSquare.getRank();
        // calculate if the King can move to a different Square
        for (byte[] offsets : Board.squareOffsets) {
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
        // if we reach this when there are two attackers, return false
        if (checkingSquare.equals(kingSquare))
            return false;
        // get PiecesSquares of the Player in check
        List<Square> friendlyPiecesSquares =
            (currentTurn.equals(whitePlayer)) ? blackPiecesSquares: whitePiecesSquares;
        // calculate if the checking Piece can be captured
        for(Square friendlySquare : friendlyPiecesSquares) {
            if (friendlySquare.getPiece().legalMove(board, friendlySquare, checkingSquare)) {
                if (isPinned(kingSquare, friendlySquare, checkingSquare))
                    continue;
                return true;
            }
        }
        // if the attacker is a Knight and it can't be captured or moved away from
        if (checkingSquare.getPiece() instanceof Knight)
            return false;
        // calculate offsets used in for loop
        int attackerFile = checkingSquare.getFile();
        int attackerRank = checkingSquare.getRank();
        int fileOffset = 0;
        int rankOffset = 0;
        if (kingFile > attackerFile)
            fileOffset = 1;
        else if (kingFile < attackerFile)
            fileOffset = -1;
        if (kingRank > attackerRank)
            rankOffset = 1;
        else if (kingRank < attackerRank)
            rankOffset = -1;
        // for each friendly Piece, test if it can block check by moving to any
        // Square between attacker and King
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
     * Test if a Piece is pinned <p>
     * Try to move from currentSquare to tempSquare, then see if the king is in check.
     * Moves Pieces back to their original positions before returning.
     * @param kingSquare the Square the King is on
     * @param currentSquare the Square the Piece is currently on
     * @param tempSquare the Square the Piece tries to move to
     * @return true if the Piece is pinned, false if it's not
     */
    public boolean isPinned(Square kingSquare, Square currentSquare, Square tempSquare) {
        Piece currentPiece = currentSquare.getPiece();
        Piece tempPiece = tempSquare.getPiece();
        currentSquare.setPiece(null);
        tempSquare.setPiece(currentPiece);
        Square isCheckFromSquare = (kingSquare.equals(currentSquare)) ?
            calculateChecks(tempSquare) : calculateChecks(kingSquare);
        currentSquare.setPiece(currentPiece);
        tempSquare.setPiece(tempPiece);
        return isCheckFromSquare != null;
    }

    /**
     * Calculates how many Pieces are giving check <p>
     * Calculation starts at the Kings Square, then loops outwards in all
     * 8 directions. After this, look for possible Knights around the King.
     * @param kingSquare the Square the King is on
     * @return the Square from which check is given. If there are two Pieces giving
     * check, return the kingSquare instead. Returns null if there is no check
     */
    public Square calculateChecks(Square kingSquare) {
        int file = kingSquare.getFile();
        int rank = kingSquare.getRank();
        // check for checks in all 8 directions, starting with diagonal up right
        // and moving clockwise
        Square attackerSquare = null;
        Square currentSquare;
        for (byte[] offsets : Board.squareOffsets) {
            currentSquare = checkLoop(kingSquare, file, rank, offsets[0], offsets[1]);
            // update the attackerSquare
            if (attackerSquare == null)
                attackerSquare = currentSquare;
            // if attackerSquare != null and currentSquare != null, return kingSquare
            else if (currentSquare != null)
                return kingSquare;
        }
        // check for any Knights giving check
        for(byte[] offset : Board.knightOffsets) {
            if (file + offset[0] < 0 || file + offset[0] > 7
                || rank + offset[1] < 0 || rank + offset[1] > 7)
                continue;
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
     * Loop from the Kings Square outwards in a given direction until a Piece
     * is found, or until the edge of the Board is hit. <p>
     * Below is a grid showing the file/rank offsets viewed from the kingSquare
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
     * @param file the file of the kingSquare
     * @param rank the rank of the kingSquare
     * @param fileOffset the amount to offset the file by every iteration
     * @param rankOffset the amount to offset the rank by every iteration
     * @return the Square of the checking Piece if there is one, null otherwise
     */
    private Square checkLoop(Square kingSquare, int file, int rank, int fileOffset, int rankOffset) {
        // add offsets to file and rank so we dont test starting Square
        for(int f = file + fileOffset, r = rank + rankOffset;
         f > -1 && f < Board.boardSize && r > -1 && r < Board.boardSize;
         f += fileOffset, r += rankOffset) {
            Square currentSquare = board.getSquare(f, r);
            Piece piece = currentSquare.getPiece();
            // continue loop if we find no piece
            if(piece == null)
                continue;
            // return null if we find a Piece of the same color as the King,
            // any Pieces behind it will be blocked and unable to give check
            if(piece.getColor() == kingSquare.getPieceColor())
                return null;
            // return 1 if there is a Piece with a legalMove to the kingSquare
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
     * Calculate if a given King can castle from startSquare towards endSquare.
     * Note that the endSquare is not necessarily the Square the King ends up on,
     * but rather the Square the King has been told to move towards. The file of
     * endSquare indicates if it's a queenside or kingside castling move.
     * <p>
     * This method assumes that the King is not currently in check.
     * @param startSquare the starting Square with the King on it
     * @param endSquare the end Square
     * @param king the King
     * @return true if castling is allowed, false otherwise
     */
    public boolean calculateCastling(Square startSquare, Square endSquare, King king) {
        // Method is called from makeMove() after verifying that the startPiece
        // is a King, the King is not in check and that
        // (difference in file >= 2 OR endSquare has a rook on it)
        int rank = startSquare.getRank();
        if (rank != endSquare.getRank() || ! (rank == 0 || rank == 7) )
            return false;
        if (! king.isFirstMove())
            return false;
        int startFile = startSquare.getFile();
        int endFile = endSquare.getFile();
        int file = startFile;
        Piece piece = null;
        Square rookSquare = null;
        Square rookDestinationSquare = null;
        Rook rook = null;
        Square newKingSquare = null;
        Square tempSquare = null;

        // Castling kingside
        // Both in regular chess and chess960, the king ends up on the g-file
        // when castling kingside while the rook ends up on the f-file.
        // This means that index 5 (f-file) and index 6 (g-file) can be hardcoded
        if (startFile < endFile) {
            while(piece == null) {
                file++;
                piece = board.getPiece(file, rank);
                tempSquare = board.getSquare(file, rank);
                // Test if King would be in check on the current Square
                if (file <= 6 && isPinned(startSquare, startSquare, tempSquare))
                    return false;
            }
            if (piece instanceof Rook && piece.isSameColorAs(king)) {
                rook = (Rook) piece;
                rookSquare = board.getSquare(file, rank);
                if (! rook.isFirstMove())
                    return false;
                // Test if the rook can move to its destination Square
                rookDestinationSquare = board.getSquare(5, rank);
                startSquare.setPiece(null);
                if (! rook.legalMove(board, rookSquare, rookDestinationSquare)) {
                    startSquare.setPiece(king);
                    return false;
                }
                // Get the new kingSquare and place the Rook on its destination Square
                newKingSquare = board.getSquare(6, rank);
            } else
                return false;
        }
        // Castling queenside
        // Both in regular chess and chess960, the king ends up on the c-file
        // when castling queenside while the rook ends up on the d-file.
        // This means that index 2 (c-file) and index 3 (d-file) can be hardcoded
        else {
            // Edgecase in chess960 where the rook is on the a-file and the king on the b-file.
            // Castling queenside is still possible by dragging the king left to the a-file,
            // but the King will end up moving right to the c-file if castling is permitted.
            int fileOffset = (startFile < 2) ? 1 : -1;
            while (piece == null) {
                file += fileOffset;
                piece = board.getPiece(file, rank);
                tempSquare = board.getSquare(file, rank);
                // Test if King would be in check on the current Square
                if (file != 2 && isPinned(startSquare, startSquare, tempSquare))
                    return false;
            }
            if (fileOffset == -1 && piece instanceof Rook && piece.isSameColorAs(king)) {
                rook = (Rook) piece;
                rookSquare = board.getSquare(file, rank);
                if (! rook.isFirstMove())
                    return false;
                // Test if the Rook can move to its destination Square
                rookDestinationSquare = board.getSquare(3, rank);
                startSquare.setPiece(null);
                if (! rook.legalMove(board, rookSquare, rookDestinationSquare)) {
                    startSquare.setPiece(king);
                    return false;
                }
                newKingSquare = board.getSquare(2, rank);
            
            // In the edgecase as described above, if we find a rook on the c-file
            // that's the wrong rook.
            } else if (fileOffset == 1) {
                rook = (Rook) endSquare.getPiece();
                rookSquare = endSquare;
                if (! (rook.isFirstMove() && rook.isSameColorAs(king)))
                    return false;
                // Test if the Rook can move to its destination Square
                rookDestinationSquare = board.getSquare(3, rank);
                startSquare.setPiece(null);
                if (! rook.legalMove(board, rookSquare, rookDestinationSquare)) {
                    startSquare.setPiece(king);
                    return false;
                }
                newKingSquare = board.getSquare(2, rank);
            } else
                return false;
        }
        startSquare.setPiece(null);
        newKingSquare.setPiece(king);
        if (currentTurn.equals(whitePlayer))
            whiteKingSquare = newKingSquare;
        if (currentTurn.equals(blackPlayer))
            blackKingSquare = newKingSquare;
        rookSquare.setPiece(null);
        rookDestinationSquare.setPiece(rook);
        king.setFirstMove(false);
        rook.setFirstMove(false);
        return true;
    }

    /**
     * Reverses the last move made in the Game
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
        if (this.halfMoveCounter > 0)
            this.halfMoveCounter--;
        if (currentTurn.equals(whitePlayer)) {
            currentTurn = blackPlayer;
            this.fullMoveCounter--;
        } else
            currentTurn = whitePlayer;
        // update King Square, need to check currentTurn again after changing it
        if(startPiece instanceof King) {
            if (currentTurn.equals(whitePlayer))
                whiteKingSquare = startSquare;
            else
                blackKingSquare = startSquare;
        }
        updateGameVariables();
    }

    /**
     * Switch whose turn it is and increment fullMoveCounter after black move
     */
    public void switchTurn() {
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
        return (board.toString() + " " + turn + " " + castlingAvailability + " "
            + enPassant + " " + halfMoveCounter + " " + fullMoveCounter);
    }

    // Remove unused getters and setters
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Move> moves) {
        this.moveList = moves;
    }

    public Player getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Player currentTurn) {
        this.currentTurn = currentTurn;
    }

    public int getHalfMoveCounter() {
        return halfMoveCounter;
    }

    public void setHalfMoveCounter(int halfMoveCounter) {
        this.halfMoveCounter = halfMoveCounter;
    }

    public int getFullMoveCounter() {
        return fullMoveCounter;
    }

    public void setFullMoveCounter(int fullMoveCounter) {
        this.fullMoveCounter = fullMoveCounter;
    }

    public String getCastlingAvailability() {
        return castlingAvailability;
    }

    public void setCastlingAvailability(String castlingAvailability) {
        this.castlingAvailability = castlingAvailability;
    }

    public Square getEnPassantSquare() {
        return enPassantSquare;
    }

    public void setEnPassantSquare(Square enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }

    public Square getWhiteKingSquare() {
        return whiteKingSquare;
    }

    public void setWhiteKingSquare(Square whiteKingSquare) {
        this.whiteKingSquare = whiteKingSquare;
    }

    public Square getBlackKingSquare() {
        return blackKingSquare;
    }

    public void setBlackKingSquare(Square blackKingSquare) {
        this.blackKingSquare = blackKingSquare;
    }
    
}
