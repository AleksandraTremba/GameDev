package com.mygdx.game.Sprites;

import java.util.LinkedList;

public class PlayerHandler {
    public static final PlayerHandler INSTANCE = new PlayerHandler();
    public final LinkedList<Frog> players;

    public PlayerHandler() {
        this.players = new LinkedList<>();
    }

    public void update() {
        for (int i = 0; i < this.players.size(); i++) {
            this.players.get(i).update();
        }
    }

    public LinkedList<Frog> getPlayers() {
        return players;
    }
}
