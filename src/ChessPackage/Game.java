// Inspiration: https://www.geeksforgeeks.org/design-a-chess-game/

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
    Board board;
    List<Move> moveList;
    int halfMoveCounter; // halfMoveCounter used for fifty-move rule
    int fullMoveCounter; // fullMoveCounter to track amount of full Moves

    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        board = new Board();
        moveList = new ArrayList<Move>(); // check this
        this.halfMoveCounter = 0;
        this.fullMoveCounter = 1;
        this.currentTurn = white;
    }

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
        
        Move move = new Move(startSquare, endSquare);
        this.moveList.add(move);

        if (startPiece instanceof Pawn || endSquare.getPiece() != null)
            this.halfMoveCounter = 0;
        else
            this.halfMoveCounter++;
        
        endSquare.setPiece(startPiece);
        startSquare.setPiece(null);

        if (currentTurn.equals(whitePlayer))
            currentTurn = blackPlayer;
        else { // increment fullMovecounter after black move
            currentTurn = whitePlayer;
            this.fullMoveCounter++;
        }

        return true;
    }

    /**
     * Converts the current state of the Game to FEN notation.
     * Needs more work, currently disables castling and en passant by default.
     * @return the FEN notation of the current gamestate
     */
    @Override
    public String toString() {
        char turn = (currentTurn.equals(whitePlayer)) ? 'w' : 'b';
        // castling availability for both sides
        String castling = "-";
        // en passant square
        String enPassant = "-";
        return (this.board.toString() + " " + turn + " " + castling + " "
            + enPassant + " " + this.halfMoveCounter + " " + this.fullMoveCounter);
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

    
}
