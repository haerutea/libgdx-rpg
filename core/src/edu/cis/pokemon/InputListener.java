package edu.cis.pokemon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Screens.GameScreen;

public class InputListener implements InputProcessor
{
    public GameScreen screen;
    public Hud hud;
    public OrthographicCamera gameCam;

    public InputListener(GameScreen screen, Hud hud, OrthographicCamera gameCam)
    {
        this.screen = screen;
        this.hud = hud;
        this.gameCam = gameCam;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch(keycode)
        {
            case Input.Keys.X:
                hud.setMenuVisible(!hud.isMenuVisible());
                Gdx.app.log("x", "works");

        }
        return true;
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
