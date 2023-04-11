package packets;

/**
 * Packet for updating existing client's Player's coordinates.
 */
public class PacketUpdateCharacterInfo {

    private float xPosition;
    private float yPosition;

    private int id;

    public void setX(float x) {
        this.xPosition = x;
    }

    public void setY(float y) {
        this.yPosition = y;
    }

    public void setId(int id) {this.id = id;}

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public int getId() {
        return id;
    }
}
