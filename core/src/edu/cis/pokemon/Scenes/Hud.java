package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    private VerticalGroup verticalGroup;
    private TextButton temp;

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
        Gdx.input.setInputProcessor(menuStage);

//        menu = new Menu();
//        menu.setVisible(false);
//
//        menuStage.addActor(menu);
        //Texture bgTexture=new Texture("OptionBox.png");
//        Image bgImage= new Image(new TextureRegionDrawable(new TextureRegion(bgTexture)));
//        bgImage.setSize(bgTexture.getWidth()/2,bgTexture.getHeight()/2);
//        bgImage.setPosition(Gdx.graphics.getWidth()/3-bgImage.getWidth()/2,Gdx.graphics.getHeight()/3-bgImage.getHeight());
//        bgImage.setHeight(10);
//        bgImage.setBounds(0,0,30,30);
//        menuStage.addActor(bgImage);

        verticalGroup = new VerticalGroup();
        verticalGroup.setVisible(false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle.up = skin.getDrawable("menubutton");
        textButtonStyle.down = skin.getDrawable("menubuttonselected");
        temp = new TextButton("TEMPORARY", textButtonStyle);

//        temp.addListener(new InputListener()
//        {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
//            {
//                Gdx.app.log("button", "Pressed");
//                return super.touchDown(event, x, y, pointer, button);
//            }
//        });

        temp.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("WORRKKKK", "Pressed");
            }
        } );


        //verticalGroup.addActor(temp);
        verticalGroup.addActor(new Label("work pls", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));

        verticalGroup.setFillParent(true);
        verticalGroup.center();

        menuStage.addActor(verticalGroup);
        menuStage.addActor(temp);
    }

    public void setMenuVisible(boolean visible)
    {
        Gdx.app.log("menu", "display");
        //menu.setVisible(visible);
        verticalGroup.setVisible(visible);

    }

    public boolean isMenuVisible()
    {
        return verticalGroup.isVisible();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
        menuStage.dispose();
    }
}
