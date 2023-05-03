package theGame.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Objects;

import static com.badlogic.gdx.math.MathUtils.random;

public class Raccoon extends Sprite {

    private int id;
    private float xPosition;
    private float yPosition;
    private String direction = "idle";
    private float speed = 1f;
    private float idleTime = 0;
    private boolean movingRight = true;

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
//    public void moveRightAndLeft() {
//        // move right
//        for (int i = 0; i < 400; i++) {
//            float newX = getXPosition() + 1;
//            float newY = getYPosition();
//            moveToNewPos(newX, newY);
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // idle
//        try {
//            Thread.sleep(random.nextInt(8000) + 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // move left
//        for (int i = 0; i < 400; i++) {
//            float newX = getXPosition() - 1;
//            float newY = getYPosition();
//            moveToNewPos(newX, newY);
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // idle
//        try {
//            Thread.sleep(random.nextInt(8000) + 5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void move() {
        if (direction.equals("idle")) {
            idleTime += Gdx.graphics.getDeltaTime();
            if (idleTime >= 5f + Math.random() * 7f) {
                direction = "right";
                idleTime = 0;
            }
        }
        else if (direction.equals("right")) {
            xPosition += speed;
            if (xPosition >= 4000) {
                direction = "left";
            }
        }
        else if (direction.equals("left")) {
            xPosition -= speed;
            if (xPosition <= 3600) {
                direction = "idle";
            }
        }
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

