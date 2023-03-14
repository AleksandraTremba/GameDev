package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {

    private MyGdxGame game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture backGround;
    Vector2 windowSize;


    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        playButtonActive = new Texture("playButtonInactive.png");
        playButtonInactive = new Texture("playButtonActive.png");
        exitButtonActive = new Texture("exitButtonInactive.png");
        exitButtonInactive = new Texture("exitButtonActive.png");
        backGround = new Texture("background.jpg");
        windowSize = new Vector2(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float exitButtonWidth = MyGdxGame.V_WIDTH / 2 - exitButtonActive.getWidth() / 2 + 120;
        float playButtonWidth = MyGdxGame.V_WIDTH / 2 - playButtonActive.getWidth() / 2 + 120;
        Vector2 scalingSize = windowSize.cpy().scl((float) 1 / 640, (float) 1 / 480);
        Vector2 mouseInput = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        mouseInput.scl(1 / scalingSize.x, 1 / scalingSize.y);


        game.batch.begin();
        game.batch.draw(backGround, 0, 0, 650, 500);


        if (mouseInput.x < exitButtonWidth + exitButtonActive.getWidth()
                && mouseInput.x > exitButtonWidth
                && mouseInput.y - MyGdxGame.V_HEIGHT < 100 + exitButtonActive.getHeight()
                && mouseInput.y - MyGdxGame.V_HEIGHT > 100) {
            game.batch.draw(exitButtonActive, exitButtonWidth, 100);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, exitButtonWidth, 100);
        }
        if (mouseInput.x < playButtonWidth + playButtonActive.getWidth() && mouseInput.x > playButtonWidth
                && mouseInput.y - MyGdxGame.V_HEIGHT < 5 + playButtonActive.getHeight() && mouseInput.y - MyGdxGame.V_HEIGHT > 5) {
            game.batch.draw(playButtonActive, playButtonWidth, 200);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new Lobby(game));
            }
        } else {
            game.batch.draw(playButtonInactive, playButtonWidth, 200);
        }
        game.batch.end();
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
