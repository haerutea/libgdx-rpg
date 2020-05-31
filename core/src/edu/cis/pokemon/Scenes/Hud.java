package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.time.LocalDateTime;

import edu.cis.pokemon.Utils.PKMConstants;

public class Hud implements Disposable
{
    public Stage infoStage;
    public Stage menuStage;
    private Viewport viewport;
    private Viewport menuViewport;

    private Table infoTable;
    private Table menuTable;
    private TextButton temp;

    Label dateTimeLabel;

    public Hud(SpriteBatch spriteBatch) {

        viewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        infoStage = new Stage(viewport, spriteBatch);

        menuViewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        menuStage = new Stage(menuViewport, spriteBatch);

        //dateTimeLabel = new Label(String.format( , ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dateTimeLabel = new Label("random time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dateTimeLabel.setAlignment(Align.center);
        dateTimeLabel.setFontScale(PKMConstants.LABEL_FONT_SCALE);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle.up = skin.getDrawable("menubutton");
        textButtonStyle.down = skin.getDrawable("menubuttonselected");

        temp = new TextButton("TEMPORARY", textButtonStyle);
        temp.debug();
        temp.getLabel().setFontScale(PKMConstants.BUTTON_FONT_SCALE);

        temp.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                Gdx.app.log("button", "Pressed");
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        infoTable = new Table();
        //infoTable.debug();
        infoTable.setPosition(PKMConstants.V_WIDTH * PKMConstants.INFO_X, PKMConstants.V_HEIGHT * PKMConstants.INFO_Y);
        infoTable.add(dateTimeLabel).fill();

        infoStage.addActor(infoTable);

        menuTable = new Table();
        //menuTable.debug();
//        Texture bgTexture=new Texture("OptionBox.png");
//        menuTable.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));
        menuTable.setVisible(true);
        menuTable.setPosition(PKMConstants.V_WIDTH * PKMConstants.INFO_X, PKMConstants.V_HEIGHT * PKMConstants.MENU_Y);
        menuTable.add(temp).fill();
        menuTable.setTransform(true);
        menuTable.setScale(PKMConstants.MENU_SCALE);

        menuStage.addActor(menuTable);
    }

    public void setMenuVisible(boolean visible)
    {
        Gdx.app.log("menu", "display");
        //menu.setVisible(visible);
        menuTable.setVisible(visible);

    }

    public boolean isMenuVisible()
    {
        return menuTable.isVisible();
    }

    @Override
    public void dispose()
    {
        infoStage.dispose();
        menuStage.dispose();
    }
}
