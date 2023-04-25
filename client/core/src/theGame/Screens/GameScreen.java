package theGame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import theGame.GameInfo.ClientWorld;
import theGame.GameInfo.GameClient;
import theGame.ClientConnection;
import theGame.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private BitmapFont font;
    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private SpriteBatch hudBatch;
    private String hudText = "Collect 15 sticks to save your friend!";


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
        gamePort = new FitViewport(3000, 2012, camera);

        //camera.setToOrtho(false, 400, 200);
        batch = new SpriteBatch();

        //Draw all the player that are in the game, onto the map
        drawPlayerGameCharacters();

        // create the map
        tiledMap = new TmxMapLoader().load("Big_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 5);
        //collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        Gdx.input.setInputProcessor(this);

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        System.out.println(collisionLayer);
        Gdx.input.setInputProcessor(this);

        camera.position.set(gamePort.getWorldWidth() / 4, gamePort.getWorldWidth() / 4, 0);

        font = new BitmapFont();
        font.getData().setScale(5f);
        font.setColor(Color.YELLOW);
        //font.setColor(1f, 0.5f, 0f, 1f); // red: 1, green: 0.5, blue: 0, alpha: 1
        //font.setColor(0, 0.5f, 0, 1); // set color to dark green (R=0, G=0.5, B=0, A=1)
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(3000, 2012, hudCamera);
        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hudCamera.combined);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(85f/255.0f, 110f/255f, 47f/255f, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // getGameCharacter(myPlayerId) is equal to "null" only the first time that this render is called
        // All the later times it won't be null
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            float x = clientWorld.getGameCharacter(myPlayerId).getXPosition();
            float y = clientWorld.getGameCharacter(myPlayerId).getYPosition();
            // calculate the distance from the player to the edge of the map
            float leftDistance = x;
            float rightDistance = 8000 - x;
            float bottomDistance = y;
            float topDistance = 8000 - y;

            // calculate the threshold distance from the edge of the map where the camera should stop following the player
            float threshold = 1000; // adjust this value to change the threshold distance
            float threshold2 = 1500;

            // adjust the camera position based on the player's position and the distance from the edge of the map
            if (leftDistance < threshold) {
                camera.position.x = threshold;
            } else if (rightDistance < threshold) {
                camera.position.x = 8000 - threshold;
            } else {
                camera.position.x = x;
            }

            if (bottomDistance < threshold) {
                camera.position.y = threshold;
            } else if (topDistance < threshold) {
                camera.position.y = 8000 - threshold;
            } else {
                camera.position.y = y;
            }
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

        // Draw scared frog at (4030, 3990)
        Texture scaredFrog = new Texture("assets/scared_frog.png");
        batch.draw(scaredFrog, 3930, 4090, scaredFrog.getWidth(), scaredFrog.getHeight());

        batch.end();

        // draw HUD
        hudCamera.update();
        hudBatch.setProjectionMatrix(hudCamera.combined);
        hudBatch.begin();
        font.draw(hudBatch, hudText, hudCamera.position.x - 1400, hudCamera.position.y + 900);
        hudBatch.end();

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
            character.width = 150;
            character.height = 150;
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
        hudViewport.update(width, height, true);
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
        int speed = 5;
        if (upPressed && rightPressed && !leftPressed && !collidesTop() && !collidesRight()) {  // up right
            clientConnection.sendPlayerInfo(speed, speed);
        }
        else if (upPressed && leftPressed && !rightPressed && !collidesTop() && !collidesLeft()) {  // up left
            clientConnection.sendPlayerInfo(-speed, speed);
        }
        else if (downPressed && leftPressed && !rightPressed && !collidesBottom() && !collidesLeft()) { // down left
            clientConnection.sendPlayerInfo(-speed, -speed);
        }
        else if (downPressed && rightPressed && !leftPressed && !collidesBottom() && !collidesRight()) { // down right
            clientConnection.sendPlayerInfo(speed, -speed);
        }
        else if (upPressed && !upAndDownPressed && !collidesTop()) {  // up
            clientConnection.sendPlayerInfo(0, speed);
        }
        else if (leftPressed && !leftAndRightPressed && !collidesLeft()) {  // left
            clientConnection.sendPlayerInfo(-speed, 0);
        }
        else if (downPressed && !upAndDownPressed && !collidesBottom()) {  // down
            clientConnection.sendPlayerInfo(0, -speed);
        }
        else if (rightPressed && !leftAndRightPressed && !collidesRight()) {  // right
            clientConnection.sendPlayerInfo(speed, 0);
        }
    }

    // Collision checks
    // Check if the block exists in front of them and is signed as "collision"
    public boolean isCellBlocked (TiledMapTileLayer collisionLayer, float x, float y) {
        TiledMapTileLayer.Cell cell = this.collisionLayer.getCell((int) (x / this.collisionLayer.getTileWidth() / 5), (int) (y / this.collisionLayer.getTileHeight() / 5));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    // If collision happens, returns true
    public boolean collidesRight() {
        boolean collides = false;
        for (float step = 0; step < character.getHeight(); step += collisionLayer.getTileHeight() / 2.0) {
            collides = isCellBlocked(collisionLayer, character.getX() + character.getWidth(), character.getY() + step);
            if (collides) {
                System.out.println("I am always - right");
                break;
            }
        }
        return collides;

    }
    public boolean collidesLeft() {
        boolean collides = false;
        for (float step = 1; step < character.getHeight(); step += collisionLayer.getTileHeight() / 2.0) {
            collides = isCellBlocked(collisionLayer, character.getX() - character.getWidth() / 10, character.getY() + step);
            if (collides) {
                System.out.println("On your - left");
                break;
            }
        }
        return collides;
    }
    public boolean collidesTop() {
        boolean collides = false;
        for (float step = 0; step < character.getWidth(); step += collisionLayer.getTileWidth() / 2.0) {
            collides = isCellBlocked(collisionLayer, character.getX() + step, character.getY() + character.getHeight());
            if (collides) {
                System.out.println("Never gonna give you - up");
                break;
            }
        }
        return collides;

    }
    public boolean collidesBottom() {
        boolean collides = false;
        for (float step = 0; step < character.getWidth(); step += collisionLayer.getTileWidth() / 2.0) {
            collides = isCellBlocked(collisionLayer, character.getX() + step, character.getY() - character.getHeight() / 10);
            if (collides) {
                System.out.println("Never gonna let you - down");
                break;
            }
        }
        return collides;

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        if(keycode == Input.Keys.NUM_3)
            tiledMap.getLayers().get(2).setVisible(!tiledMap.getLayers().get(2).isVisible());
        if(keycode == Input.Keys.NUM_4)
            tiledMap.getLayers().get(2).setVisible(!tiledMap.getLayers().get(3).isVisible());
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
