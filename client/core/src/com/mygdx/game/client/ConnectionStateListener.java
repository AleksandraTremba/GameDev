package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ConnectionStateListener extends Listener {

    public void connected(Connection connection) {

        super.connected(connection);
    }

    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }
}
