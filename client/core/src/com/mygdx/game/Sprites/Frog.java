package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class Frog extends Sprite {
    public World world;
    public Body b2body;
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
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
