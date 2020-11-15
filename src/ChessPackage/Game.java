// Inspiration: https://www.geeksforgeeks.org/design-a-chess-game/

package ChessPackage;

import java.util.ArrayList;
import java.util.List;

import ChessPackage.Pieces.Piece;

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

    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        board = new Board();
        moveList = new ArrayList<Move>(); // check this
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
        
        endSquare.setPiece(startPiece);
        startSquare.setPiece(null);

        if (currentTurn.equals(whitePlayer))
            currentTurn = blackPlayer;
        else
            currentTurn = whitePlayer;

        return true;
    }

    @Override
    public String toString() {
        return this.board.toString();
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
