package server;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ServerFoundation {
    private static ServerFoundation instance;
    private final Server ;
    private Server server;

    public static void main(String[] args) {
        // Port
        ServerFoundation.instance = new ServerFoundation();
    }

    public ServerFoundation() {
        this.server = new Server(1_000_000, 1_000_000);
        this.server.getKryo().register(PlayerAddEvent.class);
        this.server.getKryo().register(PlayerUpdateEvent.class);
        this.server.getKryo().register(PlayerRemoveEvent.class);
        this.server.getKryo().register(String.class);
        //this.server.getKryo().register(Color.class);
        this.bindServer(8080, 8080);
    }

    public void bindServer(final int tcpPort, final int udpPort) {
        this.server.start();
        try {
            this.server.bind(tcpPort, udpPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
