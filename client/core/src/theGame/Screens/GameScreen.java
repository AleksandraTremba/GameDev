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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Texture;
import packets.PacketCoins;
import theGame.Coin;
import theGame.GameInfo.ClientWorld;
import theGame.GameInfo.GameClient;
import theGame.ClientConnection;
import theGame.Player;
import theGame.enemy.Monkey;
import theGame.enemy.Raccoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

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

    private List<Raccoon> raccoons = new ArrayList<>();
    private List<Monkey> monkeys = new ArrayList<>();
    private Stage stage;
    private Texture exitButtont;
    private ImageButton exitButton;
    private List<Coin> broughtSticks = new ArrayList<>();
    private int broughtSticksCounter = 0;
    private String hudText;
    private Rectangle coin;
    private List<Coin> coins = new ArrayList<>();
    private Texture coinTexture;
    private GameOverScreen gameOverScreen;


    public GameScreen(ClientWorld clientWorld, GameClient gameClient) {
        this.clientWorld = clientWorld;
        this.gameClient = gameClient;
        createRaccoons();
        createMonkey();
        create();
        render();
    }

    /**
     * Creating raccoons to the map and adding them to the list.
     */
    public void createRaccoons() {
//        raccoons.add(new Raccoon(3300, 2900, 11));
//        raccoons.add(new Raccoon(3200, 2900, 24));
        raccoons.add(new Raccoon(4000, 7000, 1));
        raccoons.add(new Raccoon(1900, 5900, 2));
        raccoons.add(new Raccoon(1400, 3150, 3));
        raccoons.add(new Raccoon(2050, 1650, 4));
        raccoons.add(new Raccoon(3100, 220, 5));
        raccoons.add(new Raccoon(5530, 1670, 6));
        raccoons.add(new Raccoon(6900, 5400, 7));
        raccoons.add(new Raccoon(6200, 5400, 8));
        raccoons.add(new Raccoon(5500, 6600, 9));

    }

    public void createMonkey() {
        monkeys.add(new Monkey(7000, 500, 1));
    }


    @Override
    public void create() {
        batch = new SpriteBatch();

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        // create a FitViewport to maintain virtual aspects ratio despite screen size
        //you can zoom and visa virsa, we need it!!!
        gamePort = new FitViewport(3000, 2012, camera);

        batch = new SpriteBatch();

        //Draw all the player that are in the game, onto the map
        drawPlayerGameCharacters();

        // create the map
        tiledMap = new TmxMapLoader().load("Big_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 5);
        Gdx.input.setInputProcessor(this);

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        System.out.println(collisionLayer);
        Gdx.input.setInputProcessor(this);

        camera.position.set(gamePort.getWorldWidth() / 4, gamePort.getWorldWidth() / 4, 0);

        font = new BitmapFont();
        font.getData().setScale(5f);
        hudCamera = new OrthographicCamera();
        font.setColor(Color.YELLOW);
        hudViewport = new FitViewport(3000, 2012, hudCamera);
        hudBatch = new SpriteBatch();
        hudBatch.setProjectionMatrix(hudCamera.combined);

        // create exit button
        stage = new Stage(new ScreenViewport());

        exitButtont = new Texture("rsz_exit_button.png");
        exitButton = new ImageButton(new TextureRegionDrawable(exitButtont));
        exitButton.setWidth(Gdx.graphics.getWidth() / 3f);
        exitButton.setPosition(Gdx.graphics.getWidth()  - 170, Gdx.graphics.getHeight() - 120);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);

        coinTexture = new Texture(Gdx.files.internal("stick.png"));
        coins = clientWorld.getCoins();
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

        //draw raccons to the map
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            float x = clientWorld.getGameCharacter(myPlayerId).getXPosition();
            float y = clientWorld.getGameCharacter(myPlayerId).getYPosition();
            for (Raccoon raccoon : raccoons) {
                //draw the raccoon's movement towards a player
                raccoon.moveTowardsPlayer(x, y);
                float raccoonX = raccoon.getXPosition();
                float raccoonY = raccoon.getYPosition();
                //calculate the distance between the raccoon and a player
                float distanceToPlayer = (float) sqrt(pow(x - raccoonX, 2) + pow(y - raccoonY, 2));
                //draw the raccoons only if the distance between it and a player is less or equal to 1600 pixels
                if (distanceToPlayer <= 1800) {
                    batch.draw(raccoon.getTexture(), raccoon.getXPosition(), raccoon.getYPosition());
                }
            }
        }


        //Draw all the players in the game onto the map
        drawPlayerGameCharacters();
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            float x = clientWorld.getGameCharacter(myPlayerId).getXPosition();
            float y = clientWorld.getGameCharacter(myPlayerId).getYPosition();
            for (Monkey monkey : monkeys) {
                float kongX = monkey.getXPosition();
                float kongY = monkey.getYPosition();
                float distanceToPlayer = (float) sqrt(pow(x - kongX, 2) + pow(y - kongY, 2));
                if (distanceToPlayer <= 1800) {
                    batch.draw(monkey.getTexture(), monkey.getXPosition() - 193, monkey.getYPosition() - 149);
                }
            }
            // update the monkey
            Monkey monkey = monkeys.get(0);
            monkey.update(x, y);
        }

        // Draw scared frog at (4030, 3990)
        Texture scaredFrog = new Texture("assets/scared_frog.png");
        batch.draw(scaredFrog, 3930, 4090, scaredFrog.getWidth(), scaredFrog.getHeight());

        drawCoins();
        detectCoin();
        drawCoinCounter();

        batch.end();

        updateText();


        // draw exit button
        stage.draw();

    }


    public void updateText() {
        hudText = "Collect " + (15 - broughtSticks.size()) + " sticks to save your friend!";
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
        }
    }

    public void drawCoins() {
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            float x = clientWorld.getGameCharacter(myPlayerId).getXPosition();
            float y = clientWorld.getGameCharacter(myPlayerId).getYPosition();
            for (Coin oneCoin : coins) {
                float coinX = oneCoin.getXPos();
                float coinY = oneCoin.getYPos();
                //calculate the distance between the raccoon and a player
                float distanceToPlayer = (float) sqrt(pow(x - coinX, 2) + pow(y - coinY, 2));
                //draw the raccoons only if the distance between it and a player is less or equal to 1600 pixels
                if (distanceToPlayer <= 1000) {
                    oneCoin.draw(batch);
                }
            }
//            int myPlayerX = (int) clientWorld.getGameCharacter(myPlayerId).getXPosition();
//            int myPlayerY = (int) clientWorld.getGameCharacter(myPlayerId).getYPosition();
//
//            for (Coin oneCoin : coins) {
//                if (oneCoin.getXPos() > myPlayerX + 1600 || oneCoin.getXPos() < myPlayerX - 1600) {
//                    continue;
//                } else if (oneCoin.getYPos() > myPlayerY + 1900 || oneCoin.getYPos() < myPlayerY - 1900) {
//                    continue;
//                }
//                oneCoin.draw(batch);
//            }
        }
    }


    /**
     * Draw the coin counter to the screen.
     */
    public void drawCoinCounter() {
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            Integer coins = clientWorld.getGameCharacter(myPlayerId).getCoinCounter();
            font.draw(batch, "Sticks: " + coins, camera.position.x + 800, camera.position.y + 900);
        }
    }


    /**
     * Detect coin.
     */

    public void detectCoin() {
        List<Coin> coin2 = new ArrayList<>(coins);
        for (Coin coin: coin2) {
            if (character.overlaps(coin.getBoundingBox())) {
                if (clientWorld.getGameCharacter(myPlayerId) != null) {
                    if (clientWorld.getGameCharacter(myPlayerId).getCoinCounter() < 1) {
                        clientWorld.getGameCharacter(myPlayerId).addCoin();
                        PacketCoins packet = new PacketCoins();
                        packet.setXPos(coin.getXPos());
                        packet.setYPos(coin.getYPos());
                        clientConnection.sendPacketCoin(packet);
                        coins.remove(coin);
                        break;
                    }
                }
            }
        }
    }

    public void bringCoin() {
        if (broughtSticksCounter == 15) {
            gameClient.endGame();
        }
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            if (clientWorld.getGameCharacter(myPlayerId).getCoinCounter() > 0) {
                int x = currentXandY().get(0);
                int y = currentXandY().get(1);
                if (x > 3000 && x < 4500 && y > 3000 && y < 4000)
                {
                    Coin coin = new Coin(x, y);
                    broughtSticks.add(coin);
                    broughtSticksCounter++;
                    clientWorld.getGameCharacter(myPlayerId).emptyCoins();
                }
            }
        }
    }

    public List<Integer> currentXandY() {
        List<Integer> xandy = new ArrayList<>();
        if (clientWorld.getGameCharacter(myPlayerId) != null) {
            xandy.add((int) clientWorld.getGameCharacter(myPlayerId).getXPosition());
            xandy.add((int) clientWorld.getGameCharacter(myPlayerId).getYPosition());
        }
        return xandy;
    }


    /**
     * Resize does not stretch out the game.
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        // Update the stage viewport
        stage.getViewport().update(width, height, true);
        exitButton.setWidth(stage.getWidth() / 3f);
        exitButton.setPosition(stage.getWidth() - exitButton.getWidth() + 170,
                stage.getHeight() - exitButton.getHeight() - 30);
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
        boolean EPressed = Gdx.input.isKeyPressed(Input.Keys.E);

        // input from buttons:
        int speed = 3;
        if (EPressed) {
            bringCoin();
        }
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
    public boolean isCellBlocked (float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth() / 5), (int) (y / collisionLayer.getTileHeight() / 5));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    // If collision happens, returns true
    public boolean collidesRight() {
        boolean collides = false;
        for (float step = 0; step < character.getHeight(); step += collisionLayer.getTileHeight() / 2.0) {
            collides = isCellBlocked(clientWorld.getGameCharacter(myPlayerId).getXPosition() + character.getWidth(),
                    clientWorld.getGameCharacter(myPlayerId).getYPosition() + step);
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
            collides = isCellBlocked(clientWorld.getGameCharacter(myPlayerId).getXPosition() - character.getWidth() / 10,
                    clientWorld.getGameCharacter(myPlayerId).getYPosition() + step);
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
            collides = isCellBlocked(clientWorld.getGameCharacter(myPlayerId).getXPosition() + step,
                    clientWorld.getGameCharacter(myPlayerId).getYPosition() + character.getHeight());
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
            collides = isCellBlocked(clientWorld.getGameCharacter(myPlayerId).getXPosition() + step,
                    clientWorld.getGameCharacter(myPlayerId).getYPosition() - character.getHeight() / 10);
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
        Gdx.input.setInputProcessor(stage);
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
