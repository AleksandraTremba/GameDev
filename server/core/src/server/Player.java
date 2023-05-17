package server;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private float xPosition;
    private float yPosition;
    private final String name;
    private final int id;
    private List<Coin> coins = new ArrayList<>();

    public Player(int id, String name, float x, float y) {
        this.xPosition = x;
        this.yPosition = y;
        this.name = name;
        this.id = id;
    }

    /**
     * Move the player by 1 unit.
     * Players can not move out of some set bounds.
     * @param xPos the information which way the player should move along the x axis.
     * @param yPos the information which way the player should move along the y axis.
     */
    public void move(float xPos, float yPos) {
        xPosition += xPos;
        yPosition += yPos;

        //The height and width of the map is 1900
        if (xPosition < 0) xPosition= 0;
        //if (xPosition > 1900) xPosition = 1900;
        if (yPosition < 0) yPosition = 0;
        //if (yPosition > 1900) yPosition = 1900;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void addCoins(List<Coin> coins) {
        this.coins = coins;
    }

    public static Player createNewPlayer(float xPos, float yPos, String name, int id) {
        return new Player(id, name, xPos, yPos);
    }
}
