package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import edu.cis.pokemon.Utils.PKMConstants;

public class Menu extends Actor
{
    private TextureRegion region;
    private Table table;
    private TextButton temp;

    public Menu()
    {
        super();
        region = new TextureRegion();
        region.setRegion(new Texture(PKMConstants.MENU_FILENAME));

        table = new Table();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        temp = new TextButton("temp", textButtonStyle);

        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.enter);
        event.setPointer(-1);
        temp.fire(event);

        table.add(temp);
        table.row();
        table.add(new Label("work pls", new Label.LabelStyle(new BitmapFont(), Color.WHITE)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
        table.draw(batch, parentAlpha);

    }


}
