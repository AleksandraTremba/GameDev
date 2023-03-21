package com.mygdx.game.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Sprites.Frog;
import com.mygdx.game.Sprites.PlayerHandler;

import java.util.Objects;

public class JoinListener extends Listener {

    public void received(Connection connection, Object object) {
        //join request
        if (object instanceof JoinRequestEvent) {
            final JoinRequestEvent joinRequestEvent = (JoinRequestEvent) object;
        }
        super.received(connection, object);
    }
}
