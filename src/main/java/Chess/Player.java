package Chess;

import lombok.Getter;

@Getter
public class Player {
    private final char playerColor; // 'W' or 'B'
    private final String playerName;

    /**
     * Player object to be used in a game of Chess
     * @param playerColor char representing the Players color.
     * @param playerName the Players name
     * @throws IllegalArgumentException if playerColor is not 'W', 'w', 'B' or 'b'
     */
    public Player(char playerColor, String playerName) throws IllegalArgumentException {
        playerColor = Character.toUpperCase(playerColor);
        if (playerColor == 'W' || playerColor == 'B')
            this.playerColor = playerColor;
        else
            throw new IllegalArgumentException("Invalid player color");
        this.playerName = playerName;
    }

    /**
     * Compares two Players, they are equal if they have the same name and color
     * @param other the Object to compare to
     * @return true if this Player is the same as the Object argument, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Player))
            return false;
        Player p = (Player) other;
        return ( (this.getPlayerColor() == p.getPlayerColor())
            && (this.getPlayerName().equals(p.getPlayerName())) );
    }

    @Override
    public String toString() {
        return Character.toString(this.getPlayerColor());
    }
}
