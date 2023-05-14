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
        updateInfo.setX(xPos);
        updateInfo.setY(yPos);
        updateInfo.setId(id);
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
     * Create a PacketAddPlayer.
     *
     * @param name of the player (String)
     * @param id of the player's connection (int)
     * @param xPos of the player (float)
     * @param yPos of the player (float)
     * @return new PacketAddCharacter
     */
    public static PacketAddPlayer createPacketAddPlayer(String name, int id, float xPos, float yPos) {
        PacketAddPlayer packetAddCharacter = new PacketAddPlayer();
        packetAddCharacter.setPlayerName(name);
        packetAddCharacter.setId(id);
        packetAddCharacter.setX(xPos);
        packetAddCharacter.setY(yPos);
        return packetAddCharacter;
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
