package theGame.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
}
