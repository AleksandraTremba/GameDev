package theGame.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Objects;

public class Raccoon extends Sprite {

    private int id;
    private float xPosition;
    private float yPosition;
    private String direction;

    public Raccoon(float xPosition, float yPosition, int id) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
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

    public static Raccoon createPlayer(float x, float y, String name, int id) {
        return new Raccoon(x, y, id);
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Texture getTexture() {
        if (Objects.equals(direction, "up")) {
            return new Texture(Gdx.files.internal("raccoon.png"));
        } else {
            return new Texture(Gdx.files.internal("raccoon.png"));
        }
    }

}

