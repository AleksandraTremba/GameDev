package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screens.PlayScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	// Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
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
