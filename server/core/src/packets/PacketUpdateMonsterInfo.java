package packets;


/**
 * Packet for updating monster's coordinates.
 */
public class PacketUpdateMonsterInfo {

    private int xPosition;
    private int yPosition;

    private int id;

    public void setX(int x) {
        this.xPosition = x;
    }

    public void setY(int y) {
        this.yPosition = y;
    }

    public void setId(int id) {this.id = id;}

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getId() {
        return id;
    }
}

