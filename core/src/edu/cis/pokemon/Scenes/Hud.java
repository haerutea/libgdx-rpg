package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.time.LocalDateTime;

import edu.cis.pokemon.Utils.PKMConstants;

public class Hud implements Disposable, InputProcessor
{
    public Stage infoStage;
    public Stage menuStage;
    private Viewport viewport;
    private Viewport menuViewport;

    private LocalDateTime localDateTime;
    private Actor menu;

    private VerticalGroup verticalGroup;
    private Table infoTable;
    private Table menuTable;
    private TextButton temp;

    Label dateTimeLabel;

    public Hud(SpriteBatch spriteBatch) {
        localDateTime = LocalDateTime.now();

        viewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        infoStage = new Stage(viewport, spriteBatch);

        Table infoTable = new Table();
        infoTable.setSize(PKMConstants.V_WIDTH / 3, PKMConstants.V_HEIGHT / 3);
        infoTable.top();

        //dateTimeLabel = new Label(String.format( , ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dateTimeLabel = new Label("test weekday/time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //TODO: change size
        infoTable.top().right().padTop(10).padRight(10); //go to top right
        infoTable.add(dateTimeLabel).expandX();
        infoTable.row();
        infoStage.addActor(infoTable);

        menuViewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        menuStage = new Stage(menuViewport, spriteBatch);

//        menu = new Menu();
//        menu.setVisible(false);
//
//        menuStage.addActor(menu);

        //Texture bgTexture=new Texture("OptionBox.png");

        menuTable = new Table();
        //menuTable.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.RED);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        menuTable.setBackground(textureRegionDrawableBg);
        menuTable.setVisible(true);

        //verticalGroup = new VerticalGroup();
        //verticalGroup.setVisible(false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle.up = skin.getDrawable("menubutton");
        textButtonStyle.down = skin.getDrawable("menubuttonselected");
        TextButton temp = new TextButton("TEMPORARY", textButtonStyle);
        temp.setTransform(true);
        //temp.setPosition(PKMConstants.V_WIDTH / 2, PKMConstants.V_HEIGHT / 2);
        temp.setScale(0.2f);

        temp.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                Gdx.app.log("button", "Pressed");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
//        verticalGroup.addActor(temp);
//
//        verticalGroup.setFillParent(true);
//        verticalGroup.center();
        menuTable.add(temp).expandX();
        //menuTable.setTransform(true);
        menuTable.setPosition(PKMConstants.V_WIDTH / 2, PKMConstants.V_HEIGHT / 2);
        menuTable.setSize(5 * 16, 5 * 16);
        //menuTable.setScale(0.5f);
        menuTable.left().center();

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

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
