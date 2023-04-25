package theGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    public int getId() {
        return id;
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

    public void setTexture(int xPos, int yPos) {
        if (xPos > xPosition) {
            direction = "right";
        } else if (xPos < xPosition) {
            direction = "left";
        } else if (yPos > yPosition) {
            direction = "up";
        } else {
            direction = "down";
        }
    }

    public String getDirection() {
        return direction;
    }

    /**
     * Return the Texture to use for a player at the current moment.
     * @return Texture
     */
    public Texture getTexture() {
        boolean upAndDownPressed = Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S) ||
                Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean leftAndRightPressed = Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.D) ||
                Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        if (Objects.equals(direction, "up")) {
//            if (upPressed) {
//                Texture[] textures = new Texture[2];
//                textures[0] = new Texture(Gdx.files.internal("rsz_1player_back.png"));
//                textures[1] = new Texture(Gdx.files.internal("rsz_2player_back.png"));
//                int frameIndex = (int) ((System.currentTimeMillis() / 400) % 2);
//                return textures[frameIndex];
//            }
            return new Texture(Gdx.files.internal("rsz_player_back.png"));

        } else if (Objects.equals(direction, "down")) {
//            if (downPressed) {
//                Texture[] textures = new Texture[3];
//                for (int i = 1; i <= 3; i++) {
//                    textures[i-1] = new Texture(Gdx.files.internal("rsz_" + i + "player_idle.png"));
//                }
//                int frameIndex = (int) ((System.currentTimeMillis() / 100) % 3);
//                return textures[frameIndex];
//            }
            return new Texture(Gdx.files.internal("rsz_player_idle.png"));
        } else if (Objects.equals(direction, "left")) {
//            if (leftPressed) {
//                Texture[] textures = new Texture[3];
//                for (int i = 1; i <= 3; i++) {
//                    textures[i-1] = new Texture(Gdx.files.internal("rsz_" + i + "player_idle_left.png"));
//                }
//                int frameIndex = (int) ((System.currentTimeMillis() / 100) % 3);
//                return textures[frameIndex];
//            }
            return new Texture(Gdx.files.internal("rsz_5player_idle.png"));
        } else if (Objects.equals(direction, "right")) {
//            if (rightPressed) {
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
