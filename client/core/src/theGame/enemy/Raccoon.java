package theGame.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import theGame.ClientConnection;

import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.*;

public class Raccoon extends Sprite {

    private int id;
    private float xPosition;
    private float yPosition;
    private String direction;
    private float speed = 4f;
    private float spawnXPosition;
    private float spawnYPosition;
    private Texture[] textures; // Array to store textures for left-facing raccoon
    private Texture[] texturesRight; // Array to store textures for right-facing raccoon
    private Texture[] texturesLeft; // Array to store textures for right-facing raccoon

    public Raccoon(float xPosition, float yPosition, int id) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.spawnXPosition = xPosition;
        this.spawnYPosition = yPosition;

        // Load textures for idle raccoon
        textures = new Texture[6];
        for (int i = 1; i <= 6; i++) {
            textures[i-1] = new Texture(Gdx.files.internal("raccoon/rsz_raccoon_" + i + ".png"));
        }

        // Load textures for running right raccoon
        texturesRight = new Texture[8];
        for (int i = 1; i <= 8; i++) {
            texturesRight[i-1] = new Texture(Gdx.files.internal("raccoon/rsz_raccoon_run_" + i + ".png"));
        }

        // Load textures for running left raccoon
        texturesLeft = new Texture[8];
        for (int i = 1; i <= 8; i++) {
            texturesLeft[i-1] = new Texture(Gdx.files.internal("raccoon/rsz_raccoon_runn_" + i + ".png"));
        }
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public int getId() {
        return id;
    }

    public void moveToNewPos(float xPos, float yPos) {
        if (xPos > xPosition) {
            direction = "right";
        } else if (xPos < xPosition) {
            direction = "left";
        } else if (yPos > yPosition) {
            direction = "up";
        } else if (yPos < yPosition){
            direction = "down";
        }

        xPosition = xPos;
        yPosition = yPos;
    }

    public static Raccoon createPlayer(float x, float y, int id) {
        return new Raccoon(x, y, id);
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Texture getTexture() {
        if (Objects.equals(direction, "right")) {
            int frameIndex = (int) ((System.currentTimeMillis() / 80) % 8);
            return texturesRight[frameIndex];
        }
        else if (Objects.equals(direction, "left")) {
            int frameIndex = (int) ((System.currentTimeMillis() / 80) % 8);
            return texturesLeft[frameIndex];
        } else {
            int frameIndex = (int) ((System.currentTimeMillis() / 300) % 6);
            return textures[frameIndex];
        }
    }

    public void moveTowardsPlayer(float playerX, float playerY) {
         //calculating distance between a player and the raccoon
        float distanceToPlayer = (float) sqrt(pow(playerX - xPosition, 2) + pow(playerY - yPosition, 2));
        if (distanceToPlayer >= 1000) {
            returnToSpawn();
            // if the distance is greater than or equal to 600 pixels, the raccoon doesn't chase the player
            return;
        }

        // if the distance is less than 600 pixels, the raccoon starts chasing a player
        float angleToPlayer = (float) atan2(playerY - yPosition, playerX - xPosition);
        xPosition += speed * cos(angleToPlayer);
        yPosition += speed * sin(angleToPlayer);

        direction = playerX > xPosition ? "right" : "left";
    }

    public void returnToSpawn() {
        float distanceToSpawn = (float) sqrt(pow(spawnXPosition - xPosition, 2) + pow(spawnYPosition - yPosition, 2));
        if (distanceToSpawn <= 5) {
            // If the distance to the spawn point is less than or equal to 5 pixels,
            // the raccoon has reached its spawn point and can stop moving.
            direction = "";
            return;
        }

        // If the distance to the spawn point is greater than 5 pixels,
        // move towards the spawn point.
        float angleToSpawn = (float) atan2(spawnYPosition - yPosition, spawnXPosition - xPosition);
        xPosition += speed * cos(angleToSpawn);
        yPosition += speed * sin(angleToSpawn);

        // Set direction based on the movement towards the spawn point
        direction = spawnXPosition > xPosition ? "right" : "left";
    }

}

