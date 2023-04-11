package com.mygdx.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Scenes.LobbyHud;
import com.mygdx.game.Sprites.Frog;

public class Lobby implements Screen{
    //Reference to our Game, used to set Screens
    private MyGdxGame game;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private LobbyHud hud;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    //sprites
    private Frog player;
    private Frog player2;

    //private Texture frogPng;
    //private SpriteBatch bat

    public Lobby(MyGdxGame game) {
        this.game = game;

        // create cam used to follow frog through cam world
        gamecam = new OrthographicCamera();

        // create a FitViewport to maintain virtual aspects ratio despite screen size
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam);

        // create our HUD for world/level info
        hud = new LobbyHud(game.batch);

        // load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lobby.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);

        // initially set our gamecam to be centered correctly at the start of the map
        gamecam.position.set(gamePort.getWorldWidth() / 4, gamePort.getWorldWidth() / 4, 0);

        //create our Box2D world, setting no gravity in X, -50 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -150), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create frog in our game world
        player = new Frog(world, 200, 32, "player1");
        //player2 = new Frog(world, 250, 32, "player2");
        //player = new Frog(world, 150, 32, "frog3");

        //frogPng = new Texture(Gdx.files.internal("frog1.png"));
        //batch = new SpriteBatch();

        //create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        Frog.handleInput(dt);

        world.step(1/60f, 6, 2);

        // set the gamecam so it will not move with the character
        //gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        renderer.render();
        //see the lines of the objects
        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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
