package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class Frog extends Sprite {
    public World world;
    public Body b2body;

    public Frog(World world) {
        this.world = world;
        defineFrog();
    }

    public void defineFrog() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / MyGdxGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
