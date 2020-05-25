package edu.cis.pokemon.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

import edu.cis.pokemon.Enums.Direction;
import edu.cis.pokemon.Enums.State;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Player;

public class InputListener implements InputProcessor
{
    private GameScreen screen;
    private Hud hud;
    private Player player;

    public InputListener(GameScreen screen, Hud hud, Player player)
    {
        this.screen = screen;
        this.hud = hud;
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch(keycode)
        {
            case Input.Keys.X:
                hud.setMenuVisible(!hud.isMenuVisible());
                Gdx.app.log("x", "works");
                break;
            case Input.Keys.UP:
                //forces can't be implemented here bcs it'll only apply for when key is first pressed down, apply force in main loop instead
                player.changeSprite(Direction.BACK, State.RUNNING);
                break;
            case Input.Keys.DOWN:
                player.changeSprite(Direction.FRONT, State.RUNNING);
                break;
            case Input.Keys.LEFT:
                if(player.isFacingRight())
                {
                    player.setTurnDirection(true);
                }
                player.changeSprite(Direction.LEFT, State.RUNNING);
                break;
            case Input.Keys.RIGHT:
                Gdx.app.log("right", "");
                if(!player.isFacingRight())
                {
                    Gdx.app.log("not facing right", "");
                    player.setTurnDirection(true);
                }
                player.changeSprite(Direction.RIGHT, State.RUNNING);
                break;
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
