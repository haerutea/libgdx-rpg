package edu.cis.pokemon.Tools;

import com.badlogic.gdx.Game;

import java.util.Stack;

import edu.cis.pokemon.Enums.GameContext;
import edu.cis.pokemon.Enums.GameState;
import edu.cis.pokemon.Scenes.Hud;

public class GameStateManager
{
    private Stack<GameState> gsManager;
    private Stack<GameContext> screenManager;
    private Game game;
    private Hud hud;

    public GameStateManager(Game game, Hud hud, GameState initialState, GameContext initalContext)
    {
        this.game = game;
        this.hud =hud;
        this.gsManager = new Stack<GameState>();
        this.screenManager = new Stack<GameContext>();
        pushCurrentState(initialState);
        pushCurrentContext(initalContext);
    }

    public void pushCurrentState(GameState newState)
    {
        this.gsManager.push(newState);
    }

    public void setCurrentState(GameState newState)
    {
        this.gsManager.pop();
        this.gsManager.push(newState);
    }

    public void popState()
    {
        this.gsManager.pop();
    }

    public void pushCurrentContext(GameContext newContext)
    {
        this.screenManager.push(newContext);
    }

    public GameState getCurrentState()
    {
        return this.gsManager.peek();
    }

    public void setCurrentContext(GameContext newContext)
    {
        this.screenManager.pop();
        this.screenManager.push(newContext);
    }

    public GameContext getCurrentContext()
    {
        return this.screenManager.peek();
    }

    public void renderCurrent()
    {
        switch (getCurrentState())
        {
            case CONTINUE:
                hud.setMenuVisible(false);
                break;
            case PAUSED:
                hud.setMenuVisible(true);
                break;
            default:
                break;
        }

        switch (getCurrentContext())
        {
            case LAB:
                break;
            case HOUSE:
                break;
            case WORLDMAP:
                break;
            case PLAYERHOUSE:
                break;
            default:
                break;
        }

    }
}
