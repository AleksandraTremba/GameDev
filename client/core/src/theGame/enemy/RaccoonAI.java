package theGame.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.*;
import static java.lang.Math.sin;

public class RaccoonAI {
    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;
    private Rectangle boundingRectangle;
    private int zombieSpeed;
    private float xPosition;
    private float yPosition;
    private String direction;

    public RaccoonAI(float xPosition, float yPosition, float width, float height, int speed, String textureFile) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.velocity = new Vector2(0, 0);
        this.zombieSpeed = speed;
        this.texture = new Texture(textureFile);


    }

    public void update(float deltaTime, Rectangle player) {
        // calculate distance between zombie and player
        Vector2 playerPosition = new Vector2(player.x, player.y);
        Vector2 distanceVector = playerPosition.sub(position);
        float distance = distanceVector.len();

        // calculate velocity to move towards player
        Vector2 velocity = distanceVector.nor().scl(zombieSpeed);

        // move zombie
        position.add(velocity.scl(deltaTime));

        // update bounding rectangle
        boundingRectangle.setPosition(position.x, position.y);
    }


    public Texture getTexture() {
        return texture;
    }

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setPosition(Vector2 direction) {
        position.set(direction);
        boundingRectangle.setPosition(position);
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setSpeed(int speed) {
        zombieSpeed = speed;
    }

    public float getZombieSpeed() {
        return zombieSpeed;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setBoundingRectangle(Rectangle boundingRectangle) {
        this.boundingRectangle = boundingRectangle;
    }

    public void dispose() {
        texture.dispose();
    }

    public void draw(SpriteBatch batch) {
        // draw the zombie texture at the current position
        batch.draw(texture, position.x, position.y);
    }

    public Rectangle getRectangle() {
        return boundingRectangle;
    }


    public Rectangle getBounds() {
        return boundingRectangle;
    }

    public void setPosition(float x, float y) {
    }

    //    public void drawRaccoons() {
//        for (Raccoon raccoon : raccoons) {
//            float raccoonX = raccoon.getXPosition();
//            float raccoonY = raccoon.getYPosition();
//            if (raccoonX > character.x + 200 || raccoonX < character.x - 200) {
//                continue;
//            } else if (raccoonY > character.y + 200 || raccoonY < character.y - 200) {
//                continue;
//            }
//            raccoon.draw(batch);
//
//            //batch.draw(raccoon.getTexture(), raccoon.getXPosition(), raccoon.getYPosition());
//        }
//
//    }
    //render method in GameScreen
//    Raccoon raccoon = raccoons.get(0);
//if (raccoon.getXPosition() < 4400) {
//        raccoon.moveToNewPos(raccoon.getXPosition() + 1, raccoon.getYPosition());
//    } else {
//        // move raccoon to the left
//        if (raccoon.getXPosition() > 3600) {
//            raccoon.moveToNewPos(raccoon.getXPosition() - 1, raccoon.getYPosition());
//        } else {
//            // make raccoon idle for 5-12 seconds
//            try {
//                Thread.sleep(random.nextInt(7000) + 5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    public void createRaccoons() {
////        for (int i = 0; i < 2; i++) {
////            float x, y;
////            do {
////                x = 4000;
////                y = 2900;
////            } while ((x >= 3000 && x <= 5000) && (y >= 3000 && y <= 5000));
////            raccoons.add(new Raccoon(x + i * 200, y - i * 200, i));
////        }
//        raccoons.add(new Raccoon(3900, 2600, 1));
//    }

//    public void move() {
//        if (direction.equals("idle")) {
//            idleTime += Gdx.graphics.getDeltaTime();
//            if (idleTime >= 5f + Math.random() * 7f) {
//                direction = "right";
//                idleTime = 0;
//            }
//        }
//        else if (direction.equals("right")) {
//            xPosition += speed;
//            if (xPosition >= 4000) {
//                direction = "left";
//            }
//        }
//        else if (direction.equals("left")) {
//            xPosition -= speed;
//            if (xPosition <= 3600) {
//                direction = "idle";
//            }
//        }
//    }

//    for (Raccoon raccoon : raccoons) {
////            raccoon.move();
////        }
    //        ClientConnection clientConnection = new ClientConnection();
//        clientConnection.sendRaccoonInfo(xPosition, yPosition);

//    Texture[] textures = new Texture[6];
//            for (int i = 1; i <= 6; i++) {
//        textures[i-1] = new Texture(Gdx.files.internal("raccoon/rsz_raccoon_" + i + ".png"));
//    }
//    int frameIndex = (int) ((System.currentTimeMillis() / 100) % 6);
//            return textures[frameIndex];
    //draw the raccoon's movement towards a player
//        if (clientWorld.getGameCharacter(myPlayerId) != null) {
//            Player player = clientWorld.getGameCharacter(myPlayerId);
//            for (Raccoon raccoon : raccoons) {
//                raccoon.moveTowardsPlayer(player.getXPosition(), player.getYPosition());
//            }
//        }
// calculating distance between a player and the raccoon
//    float distanceToPlayer = (float) sqrt(pow(playerX - xPosition, 2) + pow(playerY - yPosition, 2));
//    if (distanceToPlayer >= 600) {
//        // if the distance is greater than or equal to 600 pixels, the raccoon doesn't chase the player
//        return;
//    }
//
//    // if the distance is less than 600 pixels, the raccoon starts chasing a player
//    float angleToPlayer = (float) atan2(playerY - yPosition, playerX - xPosition);
//    xPosition += speed * cos(angleToPlayer);
//    yPosition += speed * sin(angleToPlayer);
//
//    direction = playerX > xPosition ? "right" : "left";

    // calculating distance between a player and the raccoon
//    float distanceToPlayer = (float) sqrt(pow(playerX - xPosition, 2) + pow(playerY - yPosition, 2));
//        if (distanceToPlayer <= 600) {
//        // if the distance is less than 600 pixels, the raccoon starts chasing a player
//        float angleToPlayer = (float) atan2(playerY - yPosition, playerX - xPosition);
//        xPosition += speed * cos(angleToPlayer);
//        yPosition += speed * sin(angleToPlayer);
//
//        direction = playerX > xPosition ? "right" : "left";
//    } else if (distanceToPlayer > 600) {
//        // if player is too far away, the raccoon moves back to spawn location
//        float angleToSpawn = (float) atan2(spawnYPosition - yPosition, spawnXPosition - xPosition);
//        xPosition += speed * cos(angleToSpawn);
//        yPosition += speed * sin(angleToSpawn);
//        direction = spawnXPosition > xPosition ? "right" : "left";
//    }

    //if (upPressed) {
//                Texture[] textures = new Texture[2];
//                textures[0] = new Texture(Gdx.files.internal("rsz_1player_back.png"));
//                textures[1] = new Texture(Gdx.files.internal("rsz_2player_back.png"));
//                int frameIndex = (int) ((System.currentTimeMillis() / 400) % 2);
//                return textures[frameIndex];
//            }
//            if (downPressed) {
//                Texture[] textures = new Texture[3];
//                for (int i = 1; i <= 3; i++) {
//                    textures[i-1] = new Texture(Gdx.files.internal("rsz_" + i + "player_idle.png"));
//                }
//                int frameIndex = (int) ((System.currentTimeMillis() / 100) % 3);
//                return textures[frameIndex];
//            }
//            if (leftPressed) {
//                Texture[] textures = new Texture[3];
//                for (int i = 1; i <= 3; i++) {
//                    textures[i-1] = new Texture(Gdx.files.internal("rsz_" + i + "player_idle_left.png"));
//                }
//                int frameIndex = (int) ((System.currentTimeMillis() / 100) % 3);
//                return textures[frameIndex];
//            }
//            if (rightPressed) {
//                Texture[] textures = new Texture[3];
//                for (int i = 1; i <= 3; i++) {
//                    textures[i-1] = new Texture(Gdx.files.internal("rsz_" + i + "player_idle.png"));
//                }
//                int frameIndex = (int) ((System.currentTimeMillis() / 100) % 3);
//                return textures[frameIndex];
//            }
}
