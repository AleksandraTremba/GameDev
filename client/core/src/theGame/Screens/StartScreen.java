package theGame.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
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

public class StartScreen extends ApplicationAdapter implements Screen {
    private GameClient gameClient;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Texture playButtonActive;
    private final Texture playButtonInactive;
    private final Texture exitButtonActive;
    private final Texture exitButtonInactive;
    private ImageButton playButton;
    private ImageButton exitButton;


    public StartScreen(final GameClient gameClient) {

        stage = new Stage(new ScreenViewport());
        // Load textures
        backgroundTexture = new Texture("background.jpg");

        playButtonActive = new Texture("playButtonActive.png");
        playButtonInactive = new Texture("playButtonInactive.png");

        playButton = new ImageButton(new TextureRegionDrawable(playButtonInactive),
                new TextureRegionDrawable(playButtonActive));
        playButton.setWidth(Gdx.graphics.getWidth() / 3f);
        playButton.setPosition(Gdx.graphics.getWidth() / 2f - playButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 3f - playButton.getHeight() / 2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClient.startGame();
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playButton.getImage().setDrawable(new TextureRegionDrawable(playButtonActive));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playButton.getImage().setDrawable(new TextureRegionDrawable(playButtonInactive));
            }
        });
        stage.addActor(playButton);

        exitButtonActive = new Texture("exitButtonActive.png");
        exitButtonInactive = new Texture("exitButtonInactive.png");

        exitButton = new ImageButton(new TextureRegionDrawable(exitButtonInactive),
                new TextureRegionDrawable(exitButtonActive));
        exitButton.setWidth(Gdx.graphics.getWidth() / 3f);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 6f - exitButton.getHeight() / 2);
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

        // Update play button size and position
        ImageButton playButton = (ImageButton) stage.getActors().get(0);
        playButton.setWidth(stage.getWidth() / 3f);
        playButton.setPosition(stage.getWidth() / 2f - playButton.getWidth() / 2,
                stage.getHeight() / 3f - playButton.getHeight() / 2);

        // Update exit button size and position
        ImageButton exitButton = (ImageButton) stage.getActors().get(1);
        exitButton.setWidth(stage.getWidth() / 3f);
        exitButton.setPosition(stage.getWidth() / 2f - exitButton.getWidth() / 2,
                stage.getHeight() / 6f - exitButton.getHeight() / 2);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
