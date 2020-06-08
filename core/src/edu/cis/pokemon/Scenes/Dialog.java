package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.cis.pokemon.Screens.AbstractScreen;
import edu.cis.pokemon.Utils.PKMConstants;

public class Dialog implements Disposable
{
    public Stage dialogStage;
    private Viewport viewport;

    private Table dialogTable;
    private Label dialogText;

    public Dialog(SpriteBatch spriteBatch)
    {
        viewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        dialogStage = new Stage(viewport, spriteBatch);

        dialogTable = new Table();
        dialogTable.debug();
//        TextureAtlas dialogAtlas = new TextureAtlas(PKMConstants.DIALOG_BOX_ATLAS_FILENAME);
//        TextureAtlas.AtlasRegion dialogBoxRegion = dialogAtlas.findRegion(PKMConstants.DIALOG_BOXES_SPRITE);
//        TextureRegion boxBackground = new TextureRegion(dialogBoxRegion, 0, 0, 521, 515);
//        dialogTable.setBackground(new TextureRegionDrawable(new TextureRegion(boxBackground)));

        TextButton.TextButtonStyle upButtonStyle = new TextButton.TextButtonStyle();
        upButtonStyle.font = new BitmapFont();
        upButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("textures/OptionBox.png")));

        TextButton bagButton = new TextButton("BAG", upButtonStyle);

//        Texture bgTexture=new Texture("textures/OptionBox.png");
//        dialogTable.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.RED);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        dialogTable.setBackground(textureRegionDrawableBg);
        dialogTable.setPosition(50, PKMConstants.V_HEIGHT * PKMConstants.INFO_Y);

        Label dateTimeLabel = new Label("random time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dialogTable.add(dateTimeLabel);
        dialogTable.row();
        dialogTable.add(bagButton).fill();
        dialogStage.addActor(dialogTable);
    }
    @Override
    public void dispose()
    {
        dialogStage.dispose();
    }
}
