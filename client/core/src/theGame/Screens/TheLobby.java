package theGame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.LobbyHud;
import com.mygdx.game.Sprites.Frog;
import theGame.GameInfo.ClientWorld;
import theGame.GameInfo.GameClient;
import theGame.ClientConnection;
import theGame.Player;

import java.util.ArrayList;
import java.util.List;

public class TheLobby implements Screen {
    SpriteBatch batch;
    private ClientWorld clientWorld;
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


    private ClientConnection clientConnection;
    private GameClient gameClient;
    private Integer myPlayerId;

    public TheLobby(ClientWorld clientWorld) {
        this.clientWorld = clientWorld;

        // create cam used to follow frog through cam world
        gamecam = new OrthographicCamera();

        // create a FitViewport to maintain virtual aspects ratio despite screen size
        gamePort = new FitViewport(400, 208, gamecam);

        // load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("lobby.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1);

        // initially set our gamecam to be centered correctly at the start of the map
        gamecam.position.set(gamePort.getWorldWidth() / 4, gamePort.getWorldWidth() / 4, 0);

        //create our Box2D world, setting no gravity in X, -50 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, 0), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Draw all the player that are in the game, onto the map
        //drawPlayerGameCharacters();

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
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        // getGameCharacter(myPlayerId) is equal to "null" only the first time that this render is called
        // All the later times it won't be null
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            gamecam.position.x = clientWorld.getGameCharacter(myPlayerId).getXPosition();
        }
        gamecam.update();
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        renderer.render();
        //see the lines of the objects
        b2dr.render(world, gamecam.combined);

    }

    /**
     * Register the clientConnection instance that connects this client to the server.
     * Save the id of this particular player (this client) into a variable.
     * @param clientConnection the clientConnection
     */
    public void registerClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        this.myPlayerId = clientConnection.getClientId();
    }

    /**
     * Method for drawing PlayerGameCharacters.
     * Takes a list of all the players in the game, and draws their Textures to the right places on the map.
     */
//    public void drawPlayerGameCharacters() {
//        List<theGame.Frog> characterValues = new ArrayList<>(clientWorld.getWorldGameCharactersMap().values());
//        for (theGame.Frog player : characterValues) {
//            character = new Rectangle();
//            character.x = player.getXPosition();
//            character.y = player.getYPosition();
//            character.width = 22;
//            character.height = 22;
//            batch.draw(player.getTexture(), character.x, character.y);
//        }
//    }

    @Override
    public void resize(int width, int height) {

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
        // dispose of all the native resources
        batch.dispose();
    }
}
