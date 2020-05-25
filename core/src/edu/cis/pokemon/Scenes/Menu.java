package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import edu.cis.pokemon.Utils.PKMConstants;

public class Menu extends Actor
{
    private TextureRegion region;

    public Menu()
    {
        super();
        region = new TextureRegion();
        region.setRegion(new Texture(PKMConstants.MENU_FILENAME));
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
        batch.draw(region, 0, 0, region.getRegionWidth(),
                region.getRegionHeight()); //TODO: change size
    }
}
