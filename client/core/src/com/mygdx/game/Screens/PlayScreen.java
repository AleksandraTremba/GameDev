package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    // Texture texture;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(MyGdxGame game) {
        this.game = game;
        // texture = new Texture("badlogic.jpg");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 4, gamePort.getWorldWidth() / 4, 0);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        /**
         * This part should create ground for world, but "object" in second line throws error :/
         *
         * for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class) {
         *             Rectangle rect = ((RectangleMapObject) object).getRectangle();
         *
         *             bdef.type = BodyDef.BodyType.StaticBody;
         *             bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
         *
         *             body = world.createBody(bdef);
         *
         *             shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
         *             fdef.shape = shape;
         *             body.createFixture(fdef);
         *         }
         */


    }
    @Override
    public void show() {

    }

    public void handleInput(float dt) {
         if (Gdx.input.isTouched()) {
                     gamecam.position.x += 100 * dt;
                 }
    }
    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        // game.batch.setProjectionMatrix(gamecam.combined);
        // game.batch.begin();
        // game.batch.draw(texture, 0, 0);
        // game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
