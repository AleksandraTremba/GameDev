package theGame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.Screens.Lobby;
import theGame.GameInfo.GameClient;

public class StartScreen1 implements Screen {

    private GameClient gameClient;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture backGround;
    Vector2 windowSize;

    public StartScreen1(final GameClient gameClient) {
        playButtonActive = new Texture("playButtonInactive.png");
        playButtonInactive = new Texture("playButtonActive.png");
        exitButtonActive = new Texture("exitButtonInactive.png");
        exitButtonInactive = new Texture("exitButtonActive.png");
        backGround = new Texture("background.jpg");
        windowSize = new Vector2(400, 208);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float exitButtonWidth = 400 - exitButtonActive.getWidth() / 2 + 120;
        float playButtonWidth = 400 / 2 - playButtonActive.getWidth() / 2 + 120;
        Vector2 scalingSize = windowSize.cpy().scl((float) 1 / 640, (float) 1 / 480);
        Vector2 mouseInput = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        mouseInput.scl(1 / scalingSize.x, 1 / scalingSize.y);

        gameClient.batch.begin();
        gameClient.batch.draw(backGround, 0, 0, 650, 500);

        if (mouseInput.x < exitButtonWidth + exitButtonActive.getWidth()
                && mouseInput.x > exitButtonWidth
                && mouseInput.y - 208 < 100 + exitButtonActive.getHeight()
                && mouseInput.y - 208 > 100) {
            gameClient.batch.draw(exitButtonActive, exitButtonWidth, 100);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            gameClient.batch.draw(exitButtonInactive, exitButtonWidth, 100);
        }
        if (mouseInput.x < playButtonWidth + playButtonActive.getWidth() && mouseInput.x > playButtonWidth
                && mouseInput.y - 208 < 5 + playButtonActive.getHeight() && mouseInput.y - 208 > 5) {
            gameClient.batch.draw(playButtonActive, playButtonWidth, 200);
            if (Gdx.input.isTouched()) {
                this.dispose();
                gameClient.startGame();
            }
        } else {
            gameClient.batch.draw(playButtonInactive, playButtonWidth, 200);
        }
        gameClient.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        windowSize.x = width;
        windowSize.y = height;
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
