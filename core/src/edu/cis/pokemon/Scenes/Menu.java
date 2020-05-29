package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.cis.pokemon.Utils.PKMConstants;

public class Menu extends Actor
{
    private TextureRegion region;

    private VerticalGroup verticalGroup;
    private TextButton temp;

    BitmapFont font;
    private float mid;

    public Menu()
    {
        super();
        verticalGroup = new VerticalGroup();
        verticalGroup.setFillParent(true);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        temp = new TextButton("TEMPORARY", textButtonStyle);

//        InputEvent event = new InputEvent();
//        event.setType(InputEvent.Type.enter);
//        event.setPointer(-1);
//        temp.fire(event);

        verticalGroup.addActor(temp);
        verticalGroup.addActor(new Label("work pls", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
        verticalGroup.draw(batch, parentAlpha);
        //temp.draw(batch, parentAlpha);
       // new Label("work pls", new Label.LabelStyle(new BitmapFont(), Color.WHITE)).draw(batch, parentAlpha);
    }

//    public Menu()
//    {
//        super();
//        region = new TextureRegion();
//
//        region.setRegion(new Texture(Gdx.files
//                .internal("menuBox.png")));
//        font = new BitmapFont(Gdx.files.internal("pokemon.fnt"),
//                Gdx.files.internal("pokemon.png"), false);
//
//        mid = this.getWidth() / 2;
//    }
//
//    public void save() {
//        Gdx.app.log("menu", "Saved");
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha)
//    {
//        super.draw(batch, parentAlpha);
//        batch.draw(region, 0, 0, region.getRegionWidth(),
//                region.getRegionHeight());
//        font.setColor(Color.BLACK);
//        font.draw(batch, "SAVE", mid + 150, 150);
//
//    }
}
