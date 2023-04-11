package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

public class Frog extends Sprite {
    public static World world;
    public static Body b2body;
    private String frogId;
    private float x;
    private float y;

    public Frog(World world, float x, float y, String frogId) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.frogId = frogId;
        defineFrog();
    }

    public void defineFrog() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        /**
         FixtureDef fdef2 = new FixtureDef();
         CircleShape shape2 = new CircleShape();
         shape2.setRadius(12 / MyGdxGame.PPM);
         fdef2.shape = shape2;
         fdef2.isSensor = true; // set the fixture to be a sensor
         b2body.createFixture(fdef2);
         **/
    }
    public String getFrogId() {
        return frogId;
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

}
