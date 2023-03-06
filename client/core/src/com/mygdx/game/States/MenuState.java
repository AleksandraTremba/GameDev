package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.MyGdxGame;

public class MenuState extends State{
    private Texture background;
    private Texture playButton;

    private BitmapFont titleFont;
    private String title = "Around the world";


    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("mainmenufrog.jpg");
        playButton = new Texture("playbutton.jpg");
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Oswald-Bold.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter paramsTitle = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramsTitle.size = 56;
        paramsTitle.color = Color.BLACK;
        titleFont = gen.generateFont(paramsTitle);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        sb.draw(playButton, (MyGdxGame.V_WIDTH / 2) - (playButton.getWidth() / 2), MyGdxGame.V_HEIGHT / 2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
