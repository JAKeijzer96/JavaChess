// Inspiration: https://www.geeksforgeeks.org/design-a-chess-game/

package ChessPackage;

import java.util.ArrayList;
import java.util.List;

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
    Board board;
    List<Move> moves;

    public Game(Player white, Player black) {
        this.whitePlayer = white;
        this.blackPlayer = black;
        board = new Board();
        moves = new ArrayList<Move>(); // check this
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

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    
}
