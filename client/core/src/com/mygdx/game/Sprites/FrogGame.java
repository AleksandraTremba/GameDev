package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

public class FrogGame {
    private HashMap<String, Frog> frogs;

    public FrogGame() {
        frogs = new HashMap<String, Frog>();
        createFrogs();
    }

    private void createFrogs() {
        World world = new World(new Vector2(0, 0), true);
        // Create four frogs with unique IDs
        for (int i = 1; i <= 4; i++) {
            Frog frog = new Frog(world, 100 + i * 50, 32, "player" + i);
            // Add the frog to the HashMap using its ID as the key
            frogs.put(frog.getFrogId(), frog);
        }
    }

    public Frog getFrog(String frogId) {
        return frogs.get(frogId);
    }
}
