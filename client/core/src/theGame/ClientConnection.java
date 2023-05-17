package theGame;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import packets.*;
import theGame.GameInfo.ClientWorld;
import theGame.GameInfo.GameClient;
import theGame.Screens.GameScreen;

import javax.swing.*;
import java.io.IOException;

/**
 Class for connecting client to the server and exchanging information with the server.
 */
public class ClientConnection {
    String ip = "localhost";
    Integer tcpPort = 8085;
    Integer udpPort = 8085;
    private final Client client;
    private ClientWorld clientWorld;
    private Integer clientId;
    public ClientConnection() throws IOException {
        client = new Client();  // initialize client
        client.start();
        Network.register(client);  // register all the classes that are sent over the network

        // Add listener to tell the client, what to do after something is sent over the network
        client.addListener(new Listener() {

            /**
             * We recieved something from the server.
             * In this case, it is probably a list of all players.
             */
            public void received(Connection connection, Object object) {
                if (object instanceof PacketUpdateCharacterInfo) {
                    PacketUpdateCharacterInfo packetUpdateCharacterInfo = (PacketUpdateCharacterInfo) object;
                    System.out.println("GOT CHARACTER UPDATE");
                    System.out.println(((PacketUpdateCharacterInfo) object).getX());
                    System.out.println(((PacketUpdateCharacterInfo) object).getY());
                    if (clientWorld.getWorldGameCharactersMap().containsKey(packetUpdateCharacterInfo.getId())) {
                        // Update PlayerGameCharacter's coordinates and direction.
                        clientWorld.movePlayerGameCharacter(packetUpdateCharacterInfo.getId(),
                                packetUpdateCharacterInfo.getX(), packetUpdateCharacterInfo.getY());
                    }
                } else if (object instanceof PacketAddPlayer) {
                    PacketAddPlayer packetAddCharacter = (PacketAddPlayer) object;
                    // Create a new PlayerGameCharacter instance from received info.
                    Player newPlayer = Player.createPlayer(packetAddCharacter.getX(), packetAddCharacter.getY(), packetAddCharacter.getPlayerName(), packetAddCharacter.getId());
                    // Add new PlayerGameCharacter to client's game.
                    clientWorld.addGameCharacter(packetAddCharacter.getId(), newPlayer);
                } else if (object instanceof PacketCoins) {
                    PacketCoins packetCoins = (PacketCoins) object;
                    clientWorld.addCoin(packetCoins.getXPos(), packetCoins.getYPos());
                }else if (object instanceof PacketClientDisconnect) {
                    PacketClientDisconnect packetClientDisconnect = (PacketClientDisconnect) object;
                    clientWorld.removeGameCharacter(packetClientDisconnect.getId());
                }

            }
        });

        /**
         * Connect the client to the server.
         * If server is on a local machine, "localhost" should be used as host.
         * Ports should be the same as in the server.
         * If server can not be reached, message dialog will be shown.
         */
        try {
            // Connected to the server - wait 5000ms before failing.
            client.start();
            // client.connect(5000, ip, tcpPort, udpPort);  // <- use this when server is up on school pc
            client.connect(5000, ip, tcpPort, udpPort);
            clientId = client.getID();  // put the id of this player into variable
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Can not connect to the Server.");
            return;
        }
    }

    public int getClientId() {
        return clientId;
    }

    /**
     * Send client's character's new coordinates to the server.
     * (actually send the direction to move towards, not actual new coordinates).
     * @param xChange new x coordinate
     * @param yChange new y coordinate
     */
    public void sendPlayerInfo(float xChange, float yChange) {
        PacketUpdateCharacterInfo packet = PacketCreator.createPacketUpdateCharacterInfo(client.getID(), xChange, yChange);
        client.sendUDP(packet);
    }

    public void setGameScreen(GameScreen gameScreen) {
    }

    public void setClientWorld(ClientWorld clientWorld){
        this.clientWorld = clientWorld;
    }

    public void setGameClient(GameClient gameClient) {
        //GameScreen gameScreen = new GameScreen(clientWorld);
    }

    public void sendPacketConnect(String playerName) {
        PacketConnect packetConnect = PacketCreator.createPacketConnect(playerName);
        client.sendTCP(packetConnect);
    }

    public void sendPacketCoin(PacketCoins packet) {
        client.sendTCP(packet);
    }

    public void setPlayerName(String playerName) {
    }
}
