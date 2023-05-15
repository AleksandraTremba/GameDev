package theGame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import packets.*;

public class Network {

    /**
     * Register classes that are sent over the network.
     * This class should be equal in server and client.
     */
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Player.class);
        kryo.register(Player[].class);
        kryo.register(Character.class);
        kryo.register(Packet.class);
        kryo.register(PacketConnect.class);
        kryo.register(PacketCreator.class);
        kryo.register(PacketUpdateCharacterInfo.class);
        kryo.register(PacketAddPlayer.class);
        kryo.register(PacketClientDisconnect.class);
        kryo.register(PacketCoins.class);
        kryo.register(PacketRemoveCoin.class);
    }
}
