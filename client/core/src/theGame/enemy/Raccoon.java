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
    private String direction = "idle";
    private float speed = 4f;
    private float idleTime = 0;
    private boolean movingRight = true;
    private float spawnXPosition;
    private float spawnYPosition;

    public Raccoon(float xPosition, float yPosition, int id) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.spawnXPosition = xPosition;
        this.spawnYPosition = yPosition;
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
        } else {
            direction = "left";
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
            return new Texture(Gdx.files.internal("raccoon.png"));
        } else {
            return new Texture(Gdx.files.internal("raccoon.png"));
        }
    }

    public void moveTowardsPlayer(float playerX, float playerY) {
        // calculating distance between a player and the raccoon
        float distanceToPlayer = (float) sqrt(pow(playerX - xPosition, 2) + pow(playerY - yPosition, 2));
        if (distanceToPlayer <= 600) {
            // if the distance is less than 600 pixels, the raccoon starts chasing a player
            float angleToPlayer = (float) atan2(playerY - yPosition, playerX - xPosition);
            xPosition += speed * cos(angleToPlayer);
            yPosition += speed * sin(angleToPlayer);

            direction = playerX > xPosition ? "right" : "left";
        } else if (distanceToPlayer > 600) {
            // if player is too far away, the raccoon moves back to spawn location
            float angleToSpawn = (float) atan2(spawnYPosition - yPosition, spawnXPosition - xPosition);
            xPosition += speed * cos(angleToSpawn);
            yPosition += speed * sin(angleToSpawn);
            direction = spawnXPosition > xPosition ? "right" : "left";
        }
    }

}

