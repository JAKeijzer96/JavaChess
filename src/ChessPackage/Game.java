// Inspiration: https://www.geeksforgeeks.org/design-a-chess-game/

package ChessPackage;

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

    Board board;
    List<Move> moves;
}
