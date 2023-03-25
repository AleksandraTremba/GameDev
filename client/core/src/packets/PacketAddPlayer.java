package packets;

/**
 * Packet that is used to add existing Players to the new player's world and to add the new PlayerGameCharacter to existing players' worlds.
 */
public class PacketAddPlayer extends Packet {

    private String playerName;  // Player's username
    private int id;  // Connection id.
    private float x;
    private float y;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
