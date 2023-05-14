package packets;

/**
 * Class for creating new packets to keep the code readable.
 */
public class PacketCreator {

    /**
     * Create packet for updating character information.
     */
    public static PacketUpdateCharacterInfo createPacketUpdateCharacterInfo(int id, float xPos, float yPos) {
        PacketUpdateCharacterInfo updateInfo = new PacketUpdateCharacterInfo();
        updateInfo.setId(id);
        updateInfo.setX(xPos);
        updateInfo.setY(yPos);
        return updateInfo;
    }
//    public static PacketUpdateRaccoonInfo createPacketUpdateRaccoonInfo(int id, float xPos, float yPos) {
//        PacketUpdateRaccoonInfo updateInfo = new PacketUpdateRaccoonInfo();
//        updateInfo.setId(id);
//        updateInfo.setX(xPos);
//        updateInfo.setY(yPos);
//        return updateInfo;
//    }

    /**
     * Create a PacketConnect.
     *
     * @param name of the player that wants to connect (String)
     * @return new PacketConnect
     */
    public static PacketConnect createPacketConnect(String name) {
        PacketConnect packetConnect = new PacketConnect();
        packetConnect.setPlayerName(name);
        return packetConnect;
    }

    /**
     * Create a PacketClientDisconnect.
     *
     * @return new PacketClientDisconnect
     */
    public static PacketClientDisconnect createPacketClientDisconnect(int id) {
        PacketClientDisconnect packetClientDisconnect = new PacketClientDisconnect();
        packetClientDisconnect.setId(id);
        return packetClientDisconnect;
    }
}
