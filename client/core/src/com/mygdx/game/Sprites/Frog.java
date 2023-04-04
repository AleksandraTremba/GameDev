package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import theGame.Player;

public class Frog extends Sprite {
    public static World world;
    public static Body b2body;
    private int id;
    private String name;
    private float xPosition;
    private float yPosition;

    public Frog(World world, float xPosition, float yPosition, String name, int id) {
        this.world = world;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.name = name;
        this.id = id;
        defineFrog();
    }

    public void defineFrog() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(xPosition, yPosition);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }


    @Override
    public float getX() {
        return b2body.getPosition().x;
    }

    @Override
    public float getY() {
        return b2body.getPosition().y;
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

    public void update(){

    }
    public static Frog createPlayer(World world, float x, float y, String name, int id) {
        return new Frog(world, x, y, name, id);
    }

}
