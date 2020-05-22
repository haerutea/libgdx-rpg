package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.time.LocalDateTime;

import Utils.PKMConstants;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private LocalDateTime localDateTime;

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

        table.top().right().padTop(10).padRight(10); //go to top right
        table.add(dateTimeLabel);
        table.row();
        //TODO: add menu underneath to show up only when button is pressed

        stage.addActor(table);
    }

}
