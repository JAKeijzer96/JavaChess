package ChessPackage;

import java.util.ArrayList;
import java.util.List;

import ChessPackage.Pieces.*;

public class Game {
    /**
     * Player white
     * Player black
     * Board (which is a 2D array of Squares, which in thurn have Pieces on them)
     * List of Moves so we can go back
     * Current state? (ongoing, checkmate, stalemate, (check?))
     * 
     */

    Player whitePlayer;
    Player blackPlayer;
    Player currentTurn;
    List<Move> moveList;
    Board board;
    Square[] whitePiecesSquares; // list of all Squares with white Pieces
    Square[] blackPiecesSquares; // list of all Squares with black Pieces
    Square whiteKingSquare;
    Square blackKingSquare;
    int halfMoveCounter; // halfMoveCounter used for fifty-move rule
    int fullMoveCounter; // fullMoveCounter to track amount of full Moves
    // Might want to change way to save castling and en passant later
    String castlingAvailability; // String for FEN notation
    String enPassantSquare; // String for FEN notation


    /**
     * Constructor to make a new Game with default Players
     */
    public Game() {
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<Move>();
        this.board = new Board();
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = "-";
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
    }

    /**
     * Constructor to make a new Game with custom Players
     * @param white Player with the white pieces
     * @param black Player with the black pieces
     */
    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        this.moveList = new ArrayList<Move>();
        this.board = new Board();
        this.castlingAvailability = "KQkq";
        this.enPassantSquare = "-";
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = this.whitePlayer;
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        this.whiteKingSquare = board.getSquare("e1");
        this.blackKingSquare = board.getSquare("e8");
    }

    /**
     * Constructor to make the game represented by the given FEN notation.
     * This uses default Players for white and black.
     * @param fenNotation String containing valid FEN notation
     */
    public Game(String fenNotation) {
        String[] arrOfStr = fenNotation.split(" ");
        if (arrOfStr.length != 6)
            throw new IllegalArgumentException("Invalid FEN notation");
        this.whitePlayer = new Player('W', "White");
        this.blackPlayer = new Player('B', "Black");
        this.moveList = new ArrayList<Move>();
        this.board = new Board(arrOfStr[0]);
        this.currentTurn = (arrOfStr[1].toLowerCase().equals("w")) ? this.whitePlayer : this.blackPlayer;
        this.castlingAvailability = arrOfStr[2];
        this.enPassantSquare = arrOfStr[3];
        this.halfMoveCounter = Integer.parseInt(arrOfStr[4]);
        this.fullMoveCounter = Integer.parseInt(arrOfStr[5]);
        this.whitePiecesSquares = this.board.getSquaresOfPlayerColor('W');
        this.blackPiecesSquares = this.board.getSquaresOfPlayerColor('B');
        for(Square s : whitePiecesSquares) {
            if(s.getPiece().getName() == 'K') {
                this.whiteKingSquare = s;
                break;
            }
        }
        for(Square s : blackPiecesSquares) {
            if(s.getPiece().getName() == 'k') {
                this.blackKingSquare = s;
                break;
            }
        }
    }

    /**
     * Method to make a move on the Board
     * This method checks if the move is valid, moves the Piece, adds the Move
     * to the moveList, increments halfMoveCounter if needed and switches turns
     * @param startString String representation of the start Square
     * @param endString String representation of the end Square
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(String startString, String endString) {
        Square startSquare = board.getSquare(startString);
        Square endSquare = board.getSquare(endString);
        Piece startPiece = startSquare.getPiece();
        if (startPiece == null)
            return false;
        if (startPiece.getColor() != currentTurn.getPlayerColor())
            return false;
        if (!startPiece.legalMove(board, startSquare, endSquare))
            return false;
        // might need to move (haha) this down to allow adding check notation
        Move move = new Move(startSquare, endSquare);
        this.moveList.add(move);

        if (startPiece instanceof Pawn || endSquare.getPiece() != null)
            this.halfMoveCounter = 0;
        else
            this.halfMoveCounter++;
        endSquare.setPiece(startPiece);
        startSquare.setPiece(null);
        int amountOfCheckingPieces = checkCheck();
        if(amountOfCheckingPieces > 0) {
            System.out.println(amountOfCheckingPieces + " check(s)! " + startString + "->" + endString);
        }

        switchTurn();
        return true;
    }

    public int checkCheck() {
        // lets say white has moved. then we need to test if black king is in check
        Square kingSquare = (currentTurn.equals(whitePlayer)) ? blackKingSquare : whiteKingSquare;
        int file = kingSquare.getFile();
        int rank = kingSquare.getRank();
        // return amount of Pieces giving check, either 0, 1 or 2
        // if it's 2, no need to check if Piece capture is possible on the next move
        int returnValue = 0;
        

        // check if were white to move again, they could capture the black king
        // do this by looping in all 8 directions + closest squares that could be
        // occupied by opponents knights. If we find an >>unobstructed<< Piece there
        // with a legalMove to the current kings square, it is check
        // in 8 directions: if there is a friendly piece in the way, break
        // in 8 directions: if there is an opponents piece, check if it has a legalMove
        // if not, break: any pieces behind it are blocked
        // knight squares: no need to check for obstructions

        // how do we make sure we dont go out of the board?
        // boardSize-1

        // check from rank right
        // add 1 to file so we dont check starting square
        for(int f = file + 1; f < Board.boardSize; f++) {
            Square s = board.getSquare(f, rank);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            // if there is a Piece with a legalMove to the kingSquare, add it
            // to the returnValue and break out of the current loop
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            // This part is only reached when there is an opponents Piece in the
            // way with no valid move to the kingSquare. This Piece blocks any
            // Pieces behind it, so no need to check further in this direction
            break;
        }
        // check from rank left
        // subtract 1 from file so we dont check starting square
        for(int f = file - 1; f > -1; f--) {
            Square s = board.getSquare(f, rank);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }
        // check from file up
        // add 1 to rank so we dont check starting square
        for(int r = rank + 1; r < Board.boardSize; r++) {
            Square s = board.getSquare(file, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }
        // check from file down
        // subtract 1 from rank so we dont check starting square
        for(int r = rank - 1; r > -1; r--) {
            Square s = board.getSquare(file, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }
        // check from diag up right
        // Add 1 to file and rank so we don't collide with ourselves.
        for(int f = file + 1, r = rank + 1; f < Board.boardSize && r < Board.boardSize; f++, r++) {
            Square s = board.getSquare(f, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }
        // check from diag down left
        // subtract 1 from file and rank so we don't collide with ourselves.
        for(int f = file - 1, r = rank - 1; f > -1 && r > -1; f--, r--) {
            Square s = board.getSquare(f, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }
        // check from diag up left
        // subtract 1 from file and add 1 to rank so we don't collide with ourselves.
        for(int f = file - 1, r = rank + 1; f > -1 && r < Board.boardSize; f--, r++) {
            Square s = board.getSquare(f, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }
        // check from diag down right
        // add 1 to file and subtract 1 from rank so we don't collide with ourselves.
        for(int f = file + 1, r = rank - 1; f < Board.boardSize && r > -1; f++, r--) {
            Square s = board.getSquare(f, r);
            Piece p = s.getPiece();
            // continue loop if we find no piece
            if(p == null)
                continue;
            // break loop if we find a piece of the same color as the king we are checking for
            if(p.getColor() == kingSquare.getPiece().getColor())
                break;
            if (p.legalMove(board, s, kingSquare)) {
                returnValue++;
                break;
            }
            break;
        }


        // 2B5/2N1N2B/1N3N2/3k4/BN3N2/2N1N3/8/K4B2 b - - 0 1
        // and now for testing if there are any knights giving check..
        // make an array with all possible offsets
        // file+1, rank+2 >^^
        // file+2, rank+1 >>^
        // file+2, rank-1 >>v
        // file+1, rank-2 >vv
        // file-1, rank-2 <vv
        // file-2, rank-1 <<v
        // file-2, rank+1 <<^
        // file-1, rank+2 <^^
        byte[][] knightOffsets = {{1, 2}, {2, 1}, {2, -1},
            {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
        
        for(byte[] offset : knightOffsets) {
            // avoid IndexErrors
            if (file + offset[0] < 0 || file + offset[0] > 7
                || rank + offset[1] < 0 || rank + offset[1] > 7)
                continue;
            Piece p = board.getPiece(file+offset[0], rank+offset[1]);
            // there can only be one knight at a time giving check, so can break when we find one
            if (p != null && p.getUpperCaseName() == 'N' && p.getColor() != kingSquare.getPiece().getColor()) {
                returnValue++;
                break;
            }
        }
        return returnValue;
    }

    /**
     * Switch whose turn it is
     */
    public void switchTurn() {
        if (currentTurn.equals(whitePlayer))
            currentTurn = blackPlayer;
        else { // increment fullMovecounter after black move
            currentTurn = whitePlayer;
            this.fullMoveCounter++;
        }
    }

    /**
     * Converts the current state of the Game to FEN notation.
     * Needs more work
     * @return the FEN notation of the current gamestate
     */
    @Override
    public String toString() {
        char turn = (currentTurn.equals(whitePlayer)) ? 'w' : 'b';
        return (board.toString() + " " + turn + " " + castlingAvailability + " "
            + enPassantSquare + " " + halfMoveCounter + " " + fullMoveCounter);
    }

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

    public String getEnPassantSquare() {
        return enPassantSquare;
    }

    public void setEnPassantSquare(String enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }
}
