package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.Screens.PlayScreen;


public class MyGdxGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 1;
	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
		// img = new Texture("frog1.png");

	}

	@Override
	public void render () {
		super.render();

		//ScreenUtils.clear(1, 0, 0, 1);
		//.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}
	
	//@Override
	//public void dispose () {
		//batch.dispose();
		// img.dispose();
	//}

}
