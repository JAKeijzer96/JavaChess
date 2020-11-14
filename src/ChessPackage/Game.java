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
     * Current state (ongoing, checkmate, stalemate, (check?))
     * 
     */

    Player whitePlayer;
    Player blackPlayer;
    Player currentTurn;
    Board board;
    List<Move> movesList;

    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        board = new Board();
        movesList = new ArrayList<Move>(); // check this
        this.currentTurn = white;
    }

    public boolean makeMove (Move move, Player player) {
        // check if legalMove
        // if it is, make the move by changing the board
        // change the player

        // check if its the players turn
        if ( ! player.equals(currentTurn)) {
            return false;
        }
        Piece startPiece = move.getStartPiece();
        // check if theres a piece to move
        if (startPiece == null) {
            return false;
        }
        // check if the color of the piece is the color of the player
        if (startPiece.getColor() != player.getPlayerColor()) {
            return false;
        }
        // check if the move is legal
        if (! startPiece.legalMove(board, move.getStartSquare(), move.getEndSquare())) {
            return false;
        }

        this.movesList.add(move);

        
        // board.getSquare(something); instead of move?
        // move.getEndSquare().setPiece(startPiece);
        // move.getStartSquare().setPiece(null);
        int file1 = move.getStartSquare().getFile();
        int rank1 = move.getStartSquare().getRank();
        int file2 = move.getEndSquare().getFile();
        int rank2 = move.getEndSquare().getRank();

        board.getSquare(file2, rank2).setPiece(startPiece);
        board.getSquare(file1, rank1).setPiece(null);
        

        if (currentTurn.equals(whitePlayer)) {
            currentTurn = blackPlayer;
        }
        else {
            currentTurn = whitePlayer;
        }

        return true;
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

    public List<Move> getMovesList() {
        return movesList;
    }

    public void setMovesList(List<Move> moves) {
        this.movesList = moves;
    }

    
}
