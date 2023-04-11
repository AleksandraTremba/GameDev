package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import packets.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyServer {
    /**
     * We want to keep track of all players that are in the game.
     * We use a hashmap (python dictionary) that has their IP as key and Player object as value
     * Each player object has coordinates (x and y)
     */
    private final HashMap<Integer, server.Player> players = new HashMap<>();  //java.net.InetSocketAddress
    private final Server server;
    private int playerCount = 1;
    private World serverWorld;
    public MyServer() throws IOException {

        server = new Server();  // initialize server
        Network.register(server);  // register all the classes that are sent over the network

        this.serverWorld = new World();

        // Add listener to tell the server, what to do after something is sent over the network
        server.addListener(new Listener() {

            /**
             * We received some data from one of the players.
             */
            public void received(Connection connection, Object object) {
                System.out.println("GOT SOME DATA FROM PLAYERS:");
                System.out.println(object);
                System.out.println(object instanceof PacketConnect);

                if (object instanceof PacketConnect) {
                    PacketConnect packetConnect = (PacketConnect) object;
                    System.out.println("In server, received block, PacketConnect");

                    playerCount += 1;
                    System.out.println("Received message from the client: " + packetConnect.getPlayerName());
                    Player player = new Player(connection.getID(), "player", 200, 32);
                    players.put(connection.getID(), player);   // connection.getRemoteAddressUDP()
                    serverWorld.addGameCharacter(connection.getID(), player);
                    addPlayerToClientsGame(connection, player);

                    System.out.println(connection.getRemoteAddressUDP().toString() + " connected");
                }
                // We check if we received a character (they want to tell the server, where they want to go).
                else if (object instanceof PacketUpdateCharacterInfo) {
                    PacketUpdateCharacterInfo packet = (PacketUpdateCharacterInfo) object;
                    System.out.println("In server, received block, PacketUpdateCharacterInfo");

                    System.out.println("packet x and y");
                    System.out.println(packet.getXPosition());
                    System.out.println(packet.getYPosition());

                    Player character = serverWorld.getGameCharacter(connection.getID());
                    character.move(packet.getXPosition(), packet.getYPosition());

                    // Those x and y positions are not in coordinates,
                    // but -1.0 means "go back", 1.0 means "go forward" and 0 means "stand where you are".
                    System.out.println(character.getXPosition());
                    System.out.println(character.getYPosition());

                    sendUpdatedGameCharacter(connection.getID(), packet.getXPosition(), packet.getYPosition());
                }
            }

            /**
             * Someone disconnected from the game.
             * Removes that player from the game.
             */
            public void disconnected(Connection c) {
                players.remove(c.getID());
                playerCount -= 1;
                serverWorld.removePlayer(c.getID());
                PacketClientDisconnect packet = PacketCreator.createPacketClientDisconnect(c.getID());
                server.sendToAllUDP(packet);
            }
        });

        server.bind(8080, 8090);  // set ports for TCP, UDP. They must be equal with clients ports.
        server.start();  // start the server
    }

    public void sendUpdatedGameCharacter(int Id, float xPos, float yPos) {
        System.out.println("SENDING STH BACK TO CLIENT");

        serverWorld.movePlayerGameCharacter(Id, xPos, yPos);  // Update given PlayerGameCharacter.
        Player character = serverWorld.getGameCharacter(Id);

        // Send updated PlayerGameCharacter's info to all connections.
        PacketUpdateCharacterInfo packet = PacketCreator.createPacketUpdateCharacterInfo(
                Id, character.getXPosition(), character.getYPosition());
        server.sendToAllUDP(packet);
    }

    /**
     * Method for sending new PlayerGameCharacter instance info to all connections and sending existing characters
     * to the new connection.
     *
     * @param newPlayerConnection new connection (Connection)
     * @param newPlayer new PlayerGameCharacter instance that was created for new connection (PlayerGameCharacter)
     */
    public void addPlayerToClientsGame(Connection newPlayerConnection, Player newPlayer) {
        // Add existing PlayerGameCharacter instances to new connection.

        List<Player> clientsValues = new ArrayList<>(serverWorld.getClients().values());
        for (Player character : clientsValues) {
            // Create a new packet for sending PlayerGameCharacter instance info.
            PacketAddPlayer addCharacter = PacketCreator.createPacketAddPlayer(character.getName(),
                    character.getId(), character.getXPosition(), character.getYPosition());
            // Send packet only to new connection.
            server.sendToTCP(newPlayerConnection.getID(), addCharacter);
        }

        // Add new PlayerGameCharacter instance to Server's world.
        serverWorld.addPlayer(newPlayerConnection.getID(), newPlayer);

        // Add new PlayerGameCharacter instance to all connections.
        // Create a packet to send new PlayerGameCharacter's info.
        PacketAddPlayer addCharacter = PacketCreator.createPacketAddPlayer(newPlayer.getName(),
                newPlayerConnection.getID(), newPlayer.getXPosition(), newPlayer.getYPosition());
        server.sendToAllTCP(addCharacter);  // Send packet to all connections.
    }
}
