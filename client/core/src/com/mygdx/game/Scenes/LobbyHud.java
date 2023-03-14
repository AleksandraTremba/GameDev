package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.awt.*;

public class LobbyHud {
    public Stage stage;
    private Viewport viewport;
    private Integer level;
    Label levelLabel;
    Label worldLabel;
    public LobbyHud(SpriteBatch sb) {
        level = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        levelLabel = new Label("Wait for other players", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Lobby", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(worldLabel).expandX().padTop(5);
        table.row();
        table.add(levelLabel).expandX();
        stage.addActor(table);
    }
}
