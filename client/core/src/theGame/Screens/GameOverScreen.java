package theGame.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import theGame.GameInfo.GameClient;

public class GameOverScreen extends ApplicationAdapter implements Screen {
    private GameClient gameClient;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Texture exitButtonActive;
    private final Texture exitButtonInactive;
    private final ImageButton exitButton;

    public GameOverScreen(final GameClient gameClient) {

        stage = new Stage(new ScreenViewport());
        // Load textures
        backgroundTexture = new Texture("gameover.png");

        exitButtonActive = new Texture("exitButtonInactive.png");
        exitButtonInactive = new Texture("exitButtonActive.png");
        // Create the exit button with its textures scaled up by a factor of 2
        Drawable exitButtonActiveDrawable = new TextureRegionDrawable(new TextureRegion(exitButtonInactive));
        Drawable exitButtonInactiveDrawable = new TextureRegionDrawable(new TextureRegion(exitButtonActive));
        exitButtonActiveDrawable.setMinHeight(exitButtonActiveDrawable.getMinHeight() * 2);
        exitButtonActiveDrawable.setMinWidth(exitButtonActiveDrawable.getMinWidth() * 2);
        exitButtonInactiveDrawable.setMinHeight(exitButtonInactiveDrawable.getMinHeight() * 2);
        exitButtonInactiveDrawable.setMinWidth(exitButtonInactiveDrawable.getMinWidth() * 2);

        exitButton = new ImageButton(exitButtonActiveDrawable, exitButtonInactiveDrawable);
        exitButton.setWidth(Gdx.graphics.getWidth() / 3f * 2);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6f - exitButton.getHeight() / 3);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.getImage().setDrawable(new TextureRegionDrawable(exitButtonActive));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitButton.getImage().setDrawable(new TextureRegionDrawable(exitButtonInactive));
            }
        });
        stage.addActor(exitButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // Update the stage viewport
        stage.getViewport().update(width, height, true);

        // Update exit button size and position
        ImageButton exitButton = (ImageButton) stage.getActors().get(0);
        exitButton.setWidth(stage.getWidth() / 3f);
        exitButton.setPosition(stage.getWidth() / 2f - exitButton.getWidth() / 2,
                stage.getHeight() / 6f);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
