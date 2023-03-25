package packets;

/**
 * Packet for sending disconnected client's id to other connections.
 */
public class PacketClientDisconnect extends Packet {

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
