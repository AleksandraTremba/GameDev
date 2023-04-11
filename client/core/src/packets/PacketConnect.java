package packets;

/**
 * Packet that is used when a client wants to connect to the server.
 */
public class PacketConnect extends Packet {

    private String playerName;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }
}
