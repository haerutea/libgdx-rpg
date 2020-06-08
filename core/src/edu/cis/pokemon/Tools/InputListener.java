package edu.cis.pokemon.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import edu.cis.pokemon.Enums.Direction;
import edu.cis.pokemon.Enums.GameState;
import edu.cis.pokemon.Enums.State;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Screens.AbstractScreen;
import edu.cis.pokemon.Sprites.Player;
import edu.cis.pokemon.Utils.PKMUtils;

public class InputListener implements InputProcessor
{
    private AbstractScreen screen;
    private Hud hud;
    private Player player;
    private InteractionProcessor interactionProcessor;

    private GameStateManager gsManager;

    public InputListener(AbstractScreen screen, Hud hud, Player player, InteractionProcessor interProcessor, GameStateManager gsManager)
    {
        this.screen = screen;
        this.hud = hud;
        this.player = player;
        this.interactionProcessor = interProcessor;
        this.gsManager = gsManager;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(gsManager.getCurrentState() == GameState.CONTINUE)
        {
            switch(keycode)
            {
                case Input.Keys.X:
                    hud.setMenuVisible(true);
                    gsManager.setCurrentState(GameState.PAUSED);
                    Gdx.app.log("x", "works");
                    break;
                case Input.Keys.UP:
                    //forces can't be implemented here bcs it'll only apply for when key is first pressed down, apply force in main loop instead
                    player.setDirection(Direction.BACK);
                    player.setState(State.RUNNING);
                    break;
                case Input.Keys.DOWN:
                    player.setDirection(Direction.FRONT);
                    player.setState(State.RUNNING);
                    break;
                case Input.Keys.LEFT:
                    if(player.isFacingRight())
                    {
                        player.setTurnDirection(true);
                    }
                    player.setDirection(Direction.LEFT);
                    player.setState(State.RUNNING);
                    break;
                case Input.Keys.RIGHT:
                    if(!player.isFacingRight())
                    {
                        player.setTurnDirection(true);
                    }
                    player.setDirection(Direction.RIGHT);
                    player.setState(State.RUNNING);
                    break;
                case Input.Keys.ENTER:
                    //TODO: CHANGE THIS TO CALL A PROCESS METHOD IN INTERACTION PROCESSOR?
                    Gdx.app.log("object: " , "" + interactionProcessor.getCollidedObject());

            }
        }
        else //if gamestate = paused
        {
            switch(keycode)
            {
                case Input.Keys.TAB:
                    hud.changeButtonDisplay();
                    break;
                case Input.Keys.X:
                    hud.setMenuVisible(false);
                    gsManager.setCurrentState(GameState.CONTINUE);
                    break;
                case Input.Keys.ENTER:
                    hud.triggerButton();
                    break;
            }
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        player.stop();
        return true;
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
