package theGame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Frog;
import theGame.GameInfo.ClientWorld;
import theGame.GameInfo.GameClient;
import theGame.ClientConnection;
import theGame.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ApplicationAdapter implements Screen, InputProcessor{
    SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Rectangle character;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer collisionLayer;

    private ClientConnection clientConnection;
    private final ClientWorld clientWorld;
    private GameClient gameClient;
    private Integer myPlayerId;

    public GameScreen(ClientWorld clientWorld) {
        this.clientWorld = clientWorld;
        create();
        render();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        // create a FitViewport to maintain virtual aspects ratio despite screen size
        //you can zoom and visa virsa, we need it!!!
        gamePort = new FitViewport(2000, 1012, camera);

        //camera.setToOrtho(false, 400, 200);
        batch = new SpriteBatch();

        //Draw all the player that are in the game, onto the map
        drawPlayerGameCharacters();

        // create the map
        tiledMap = new TmxMapLoader().load("lobby.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 5);
        Gdx.input.setInputProcessor(this);

        camera.position.set(gamePort.getWorldWidth() / 4, gamePort.getWorldWidth() / 4, 0);
    }

    public void update(float dt) {
        // set the gamecam so it will not move with the character
        //gamecam.position.x = player.b2body.getPosition().x;

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(85f/255.0f, 110f/255f, 47f/255f, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // getGameCharacter(myPlayerId) is equal to "null" only the first time that this render is called
        // All the later times it won't be null
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            camera.position.x = clientWorld.getGameCharacter(myPlayerId).getXPosition();
            //camera.position.y = clientWorld.getGameCharacter(myPlayerId).getYPosition();
        }
        camera.update();

        //texture stays with the player
        batch.setProjectionMatrix(camera.combined);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        detectInput();

        //Draw all the players in the game onto the map
        drawPlayerGameCharacters();
        batch.end();

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
    public void drawPlayerGameCharacters() {
        List<Player> characterValues = new ArrayList<>(clientWorld.getWorldGameCharactersMap().values());
        for (Player player : characterValues) {
            character = new Rectangle();
            character.x = player.getXPosition();
            character.y = player.getYPosition();
            character.width = 100;
            character.height = 149;
            batch.draw(player.getTexture(), character.x - player.getTexture().getWidth() / 2f, character.y);
            //batch.draw(player.getTexture(), character.x - player.getTexture().getWidth() / 2f, character.y - player.getTexture().getHeight() / 2f);
        }
    }

    /**
     * Resize does not stretch out the game.
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        batch.setProjectionMatrix(camera.combined);
    }


    /**
     * Get input from buttons, react to the player pressing a button on their computer's keyboard.
     * The first part of the method is about collision, but doesn't temporarily work,
     * because the logic about the player's movements has been moved to the server.
     */
    private void detectInput() {
        boolean upAndDownPressed = Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S) ||
                Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean leftAndRightPressed = Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.D) ||
                Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        // input from buttons:
        int speed = 3;
        if (upPressed && rightPressed && !leftPressed) {  // up right
            clientConnection.sendPlayerInfo(speed, speed);
        }
        else if (upPressed && leftPressed && !rightPressed) {  // up left
            clientConnection.sendPlayerInfo(-speed, speed);
        }
        else if (downPressed && leftPressed && !rightPressed) { // down left
            clientConnection.sendPlayerInfo(-speed, -speed);
        }
        else if (downPressed && rightPressed && !leftPressed) { // down right
            clientConnection.sendPlayerInfo(speed, -speed);
        }
        else if (upPressed && !upAndDownPressed) {  // up
            clientConnection.sendPlayerInfo(0, speed);
        }
        else if (leftPressed && !leftAndRightPressed) {  // left
            clientConnection.sendPlayerInfo(-speed, 0);
        }
        else if (downPressed && !upAndDownPressed) {  // down
            clientConnection.sendPlayerInfo(0, -speed);
        }
        else if (rightPressed && !leftAndRightPressed) {  // right
            clientConnection.sendPlayerInfo(speed, 0);
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        render();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        // dispose of all the native resources
        batch.dispose();
    }
}