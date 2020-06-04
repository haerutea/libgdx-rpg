package edu.cis.pokemon.Scenes;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.cis.pokemon.Utils.PKMConstants;

public class Hud implements Disposable
{
    public Stage infoStage;
    public Stage menuStage;
    private Viewport viewport;
    private Viewport menuViewport;

    private Table infoTable;
    private Table menuTable;
    private ArrayList<TextButton> buttons;
    private TextButton pokemonButton;
    private TextButton bagButton;
    private TextButton saveButton;

    private TextButton.TextButtonStyle upButtonStyle;
    private TextButton.TextButtonStyle highlightedButtonStyle;
    private Label dateTimeLabel;
    private int currentButtonIndex; //0 is the very top

    public Hud(SpriteBatch spriteBatch) {

        viewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        infoStage = new Stage(viewport, spriteBatch);

        menuViewport = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, new OrthographicCamera());
        menuStage = new Stage(menuViewport, spriteBatch);

        currentButtonIndex = 0;
        //dateTimeLabel = new Label(String.format( , ), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dateTimeLabel = new Label("random time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        dateTimeLabel.setAlignment(Align.center);
        dateTimeLabel.setFontScale(PKMConstants.LABEL_FONT_SCALE);

        Skin buttonSkin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(PKMConstants.BUTTON_ATLAS_FILENAME));
        buttonSkin.addRegions(buttonAtlas);

        upButtonStyle = new TextButton.TextButtonStyle();
        highlightedButtonStyle = new TextButton.TextButtonStyle();
        upButtonStyle.font = new BitmapFont();
        highlightedButtonStyle.font = new BitmapFont();
        upButtonStyle.up = buttonSkin.getDrawable("menubutton");
        highlightedButtonStyle.up = buttonSkin.getDrawable("menubuttonselected");

        bagButton = new TextButton("BAG", highlightedButtonStyle);
        bagButton.getLabel().setFontScale(PKMConstants.BUTTON_FONT_SCALE);

        pokemonButton = new TextButton("POKEMON", upButtonStyle);
        pokemonButton.getLabel().setFontScale(PKMConstants.BUTTON_FONT_SCALE);

        saveButton = new TextButton("SAVE", upButtonStyle);
        saveButton.getLabel().setFontScale(PKMConstants.BUTTON_FONT_SCALE);

        bagButton.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                Gdx.app.log("triggered", "bagButton");
                return false;
            }
        });

        pokemonButton.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                Gdx.app.log("triggered", "pokemonButton");
                return false;
            }
        });

        saveButton.addListener(new InputListener()
        {
            @Override
            public boolean keyDown(InputEvent event, int keycode)
            {
                Gdx.app.log("triggered", "saveButton");
                return false;
            }
        });

        buttons = new ArrayList<>();
        buttons.add(bagButton);
        buttons.add(pokemonButton);
        buttons.add(saveButton);

        infoTable = new Table();
        //infoTable.debug();
        infoTable.setPosition(PKMConstants.V_WIDTH * PKMConstants.INFO_X, PKMConstants.V_HEIGHT * PKMConstants.INFO_Y);
        infoTable.add(dateTimeLabel).fill();

        infoStage.addActor(infoTable);

        menuTable = new Table();
        //menuTable.debug();
//        Texture bgTexture=new Texture("OptionBox.png");
//        menuTable.setBackground(new TextureRegionDrawable(new TextureRegion(bgTexture)));
        menuTable.setVisible(false);
        menuTable.setPosition(PKMConstants.V_WIDTH * PKMConstants.INFO_X, PKMConstants.V_HEIGHT * PKMConstants.MENU_Y);
        menuTable.add(bagButton).fill();
        menuTable.row();
        menuTable.add(pokemonButton).fill();
        menuTable.row();
        menuTable.add(saveButton).fill();
        menuTable.setTransform(true);
        menuTable.setScale(PKMConstants.MENU_SCALE);

        menuStage.addActor(menuTable);

    }

    public void changeButtonDisplay()
    {
        Gdx.app.log("currentButtonIndex", "" + currentButtonIndex);
        Gdx.app.log("change button:", "" + buttons.get(currentButtonIndex).getText());
        currentButtonIndex = (currentButtonIndex + 1) % 3;

        if(currentButtonIndex == 0)
        {
            bagButton.setStyle(highlightedButtonStyle);
            saveButton.setStyle(upButtonStyle);
        }
        else if(currentButtonIndex == 1)
        {
            pokemonButton.setStyle(highlightedButtonStyle);
            bagButton.setStyle(upButtonStyle);
        }
        else if(currentButtonIndex == 2)
        {
            saveButton.setStyle(highlightedButtonStyle);
            pokemonButton.setStyle(upButtonStyle);
        }

        //TODO: I HAVE NO IDEA WHY THE ARRAYLIST METHOD DOESN'T WORK???
        //buttons.get(nextButtonIndex).setBackground(highlightedButtonStyle.up);
//        if(nextButtonIndex == 0)
//        {
//            buttons.get(buttons.size() - 1).setStyle(upButtonStyle);
//        }
//        else
//        {
//            buttons.get(nextButtonIndex - 1).setStyle(upButtonStyle);
//        }


    }

    public void triggerButton()
    {
        InputEvent event = new InputEvent();
        event.setType(InputEvent.Type.keyDown);
        event.setKeyCode(Input.Keys.ENTER);
        buttons.get(currentButtonIndex).fire(event);
    }

    public void setMenuVisible(boolean visible)
    {
        Gdx.app.log("menu", "display");
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
