package theGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Objects;

public class Player extends Sprite {
    public static World world;
    public static Body b2body;
    private int id;
    private String name;
    private float xPosition;
    private float yPosition;
    private String direction;


    public Player(World world, float xPosition, float yPosition, String name, int id) {
        this.world = world;
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.name = name;
        definePlayer();
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(xPosition, yPosition);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    /**
     * Empty constructor is needed here to receive Player objects over the network.
     */
    public Player(float x, float y, String name, int id) { }

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
        } else {
            direction = "down";
        }
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
    public static Player createPlayer( float x, float y, String name, int id) {
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
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        } else if (Objects.equals(direction, "down")) {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        } else if (Objects.equals(direction, "left")) {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        } else {
            return new Texture(Gdx.files.internal("rsz_1player_idle.png"));
        }
    }
}
