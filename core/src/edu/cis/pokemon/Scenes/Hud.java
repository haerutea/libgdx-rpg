package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.time.LocalDateTime;

import edu.cis.pokemon.Utils.PKMConstants;

public class Hud implements Disposable
{
    public Stage stage;
    public Stage menuStage;
    private Viewport viewport;
    private Viewport menuViewport;

    private LocalDateTime localDateTime;
    private Actor menu;

    Label dateTimeLabel;

    public Hud(SpriteBatch spriteBatch) {
        localDateTime = LocalDateTime.now();

        viewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //dateTimeLabel = new Label(String.format( , ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dateTimeLabel = new Label("test weekday/time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //TODO: change size
        table.top().right().padTop(10).padRight(10); //go to top right
        table.add(dateTimeLabel);
        table.row();

        stage.addActor(table);

        menuViewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        menuStage = new Stage(menuViewport, spriteBatch);

        menu = new Menu();
        menu.setVisible(false);

        menuStage.addActor(menu);
    }

    public void setMenuVisible(boolean visible)
    {
        Gdx.app.log("menu", "display");
        menu.setVisible(visible);
    }

    public boolean isMenuVisible()
    {
        return menu.isVisible();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
