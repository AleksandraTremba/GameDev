package theGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import theGame.GameInfo.ClientWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Objects;

public class Frog extends Sprite {
    public static World world;
    public static Body b2body;
    private int id;
    private String name;
    private float xPosition;
    private float yPosition;
    private String direction;

    public Frog(float xPosition, float yPosition, String name, int id) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.name = name;
    }

    public void defineFrog() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    /**
     * Empty constructor is needed here to receive Player objects over the network.
     */
    public Frog() { }

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
    public void moveToNewPos(float xPos, float yPos) {
        if (xPos > xPosition) {
            direction = "right";
        } else if (xPos < xPosition) {
            direction = "left";
        } else if (yPos > yPosition) {
            direction = "up";
        }
        xPosition = xPos;
        yPosition = yPos;
    }

    public static Frog createPlayer(float x, float y, String name, int id) {
        return new Frog(x, y, name, id);
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public static boolean isOnGround() {
        Array<Contact> contacts = world.getContactList();
        for (Contact contact : contacts) {
            if (contact.isTouching() && (contact.getFixtureA().getBody() == b2body || contact.getFixtureB().getBody() == b2body)) {
                Vector2 contactNormal = contact.getWorldManifold().getNormal();
                if (contactNormal.y > 0.5f) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void handleInput(float dt) {
        float speed = 70f; // adjust this to change the speed of the character
        Vector2 velocity = b2body.getLinearVelocity();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && isOnGround()) {
            velocity.y = 70f; // jump speed is higher than normal speed
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -70;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -speed;
        } else {
            velocity.x = 0;
        }

        b2body.setLinearVelocity(velocity);
    }

    public Texture getTexture() {
        if (Objects.equals(direction, "up")) {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        } else if (Objects.equals(direction, "left")) {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        } else if (Objects.equals(direction, "right")) {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        } else {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        }
    }
}
