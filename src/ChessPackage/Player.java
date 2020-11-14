package ChessPackage;

public class Player {
    
    char playerColor;
    String playerName;

    public Player(char playerColor, String playerName) {
        this.playerColor = playerColor;
        this.playerName = playerName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Player)) {
            return false;
        }
        Player p = (Player) other;
        return ( (this.getPlayerColor() == p.getPlayerColor())
            && (this.getPlayerName() == p.getPlayerName()) );
    }

    public char getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(char playerColor) {
        this.playerColor = playerColor;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    
}
