package theGame.GameInfo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import theGame.ClientConnection;
import theGame.Screens.GameOverScreen;
import theGame.Screens.GameScreen;
import theGame.Screens.StartScreen;

import java.io.IOException;

public class GameClient extends Game {
    //    public static final int V_WIDTH = 400;
//    public static final int V_HEIGHT = 208;
    public SpriteBatch batch;
    private GameScreen gameScreen;
    private StartScreen startScreen;
    private GameOverScreen gameOverScreen;
    private Skin gameSkin;
    /**
     * Method creates a new Client who connects to the Server with its ClientWorld and GameScreen.
     */
    public void createClient(ClientWorld clientWorld, GameScreen gameScreen) throws IOException {
        ClientConnection clientConnection = new ClientConnection();
        clientConnection.setGameScreen(gameScreen);
        clientConnection.setClientWorld(clientWorld);
        clientConnection.setGameClient(this);
        clientConnection.sendPacketConnect("player");

        gameScreen.registerClientConnection(clientConnection);
        clientWorld.registerClient(clientConnection);
    }

    @Override
    public void create() {
        this.startScreen = new StartScreen(this);
        setScreen(startScreen);

    }

    /**
     * Starts a game and tries to create a new client.
     * This method is called from StartScreen class, when the player presses "PLAY" button.
     * Screen is changed from StartScreen to GameScreen.
     */
    public void startGame() {
        ClientWorld clientWorld = new ClientWorld();
        gameScreen = new GameScreen(clientWorld, this);
        setScreen(gameScreen);

        try {
            createClient(clientWorld, gameScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Gdx.input.setInputProcessor(gameScreen);
    }

    public void endGame() {
        gameOverScreen = new GameOverScreen(this);
        setScreen(gameOverScreen);
    }

    /**
     * Disposes the screen.
     */
    @Override
    public void dispose() {
        gameScreen.dispose();
        startScreen.dispose();
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
