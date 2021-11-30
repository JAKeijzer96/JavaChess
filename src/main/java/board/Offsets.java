package board;

public class Offsets {

    /**
     * 2D array with all 8 possible offsets to find the surrounding Squares
     * that a Knight can be on to attack the current Square. The first offset is
     * up two and one to the right, subsequent offsets move clockwise.
     */
    public static final byte[][] forKnight = {{1, 2}, {2, 1}, {2, -1},
            {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
    /**
     * 2D array with all 8 possible offsets of the surrounding Squares
     * compared to the current Square. The first offset is up and to the right,
     * subsequent offsets move clockwise.
     */
    public static final byte[][] forKing = {{1, 1}, {1, 0}, {1, -1},
        {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}};

}
