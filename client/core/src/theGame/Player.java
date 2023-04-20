package theGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Objects;

public class Player extends Sprite {
    private int id;
    private String name;
    private float xPosition;
    private float yPosition;
    private String direction;
    private String previousDirection;


    public Player(float xPosition, float yPosition, String name, int id) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.name = name;
    }

    /**
     * Empty constructor is needed here to receive Player objects over the network.
     */
    public Player() { }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    /**
     * Move character to a new position.
     * And update the character's moving direction based of the move.
     *
     * @param xPos the x coordinate change
     * @param yPos the y coordinate change
     */
    private float prevXPosition;
    private float prevYPosition;
    public void moveToNewPos(float xPos, float yPos) {
        if (xPos > xPosition) {
            direction = "right";
        } else if (xPos < xPosition) {
            direction = "left";
        } else if (yPos > yPosition) {
            direction = "up";
        } else {
            direction = "down";
        }
        prevXPosition = xPosition;
        prevYPosition = yPosition;
        xPosition = xPos;
        yPosition = yPos;

        if (xPos > prevXPosition) {
            previousDirection = "right";
        } else if (xPos < prevXPosition) {
            previousDirection = "left";
        } else {
            previousDirection = direction;
        }
    }

    /**
     * Create a new player.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param name the name of the player
     * @param id the id of the player
     * @return new Player instance
     */
    public static Player createPlayer(float x, float y, String name, int id) {
        return new Player(x, y, name, id);
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Return the Texture to use for a player at the current moment.
     * @return Texture
     */
    public Texture getTexture() {
        if (Objects.equals(direction, "up")) {
            if (Objects.equals(previousDirection, "right")) {
                return new Texture(Gdx.files.internal("rsz_player_back.png"));
            } else if (Objects.equals(previousDirection, "left")) {
                return new Texture(Gdx.files.internal("rsz_player_back_left.png"));
            } else {
                return new Texture(Gdx.files.internal("rsz_player_back.png"));
            }
        } else if (Objects.equals(direction, "down")) {
            return new Texture(Gdx.files.internal("rsz_player_idle.png"));
        } else if (Objects.equals(direction, "left")) {
            return new Texture(Gdx.files.internal("rsz_5player_idle.png"));
        } else if (Objects.equals(direction, "right")) {
//            if (prevXPosition == xPosition && prevYPosition == yPosition) {
//                // Player is standing still
//                return new Texture(Gdx.files.internal("rsz_player_idle.png"));
//            } else {
//                Texture[] textures = new Texture[3];
//                for (int i = 1; i <= 3; i++) {
//                    textures[i-1] = new Texture(Gdx.files.internal("rsz_" + i + "player_idle.png"));
//                }
//                int frameIndex = (int) ((System.currentTimeMillis() / 100) % 3);
//                return textures[frameIndex];
//            }
            return new Texture(Gdx.files.internal("rsz_player_idle.png"));
        } else {
            return new Texture(Gdx.files.internal("rsz_player_idle.png"));
        }
    }
}
