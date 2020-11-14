package ChessPackage;

import ChessPackage.Pieces.Piece;

public class Move {
    /**
     * This class is mainly used to keep track of the moves done in the game
     * Logic for checking if a move is allowed is located in classes that
     * extend the abstract Piece class
     * 
     * Properties:
     * Player making the move (keep track in game?)
     * Start position
     * End position
     * Piece that's moving?
     * Piece that's captured?
     */

    // Do we need to explicitly define the player in the Move class,
    // or can we keep track of it well enough in the Game class
    // Moving back and forth through moves, showing in the proper
    // place in the GUI, etc.
    // Keeping track of Player here could add a relatively high amount
    // of memory usage, referencing the same two Player objects many times
    // But as long as we don't create new Player objects each time it
    // should be fine?
    // Need to decide on pros vs cons later.
    Player player;
    Square startSquare;
    Square endSquare;
    Piece startPiece;
    Piece endPiece;

    public Move(Player player, Square startSquare, Square endSquare) {
        this.player = player;
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.startPiece = this.getStartPiece();
        this.endPiece = this.getEndPiece();
    }

    public Piece getStartPiece() {
        return this.startSquare.getPiece();
    }

    public Piece getEndPiece() {
        return this.endSquare.getPiece();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Square getStartSquare() {
        return startSquare;
    }

    public void setStartSquare(Square startSquare) {
        this.startSquare = startSquare;
    }

    public Square getEndSquare() {
        return endSquare;
    }

    public void setEndSquare(Square endSquare) {
        this.endSquare = endSquare;
    }

    
}
