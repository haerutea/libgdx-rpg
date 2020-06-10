package edu.cis.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import edu.cis.pokemon.Enums.GameContext;
import edu.cis.pokemon.Enums.GameState;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Player;
import edu.cis.pokemon.Tools.Creators.Creator;
import edu.cis.pokemon.Tools.Creators.GenericCreator;
import edu.cis.pokemon.Utils.PKMConstants;

public class Pokemon extends Game {
	public SpriteBatch batch;
	//Texture img;

	private GameScreen screen;
	private Player player;

	private Stack<GameState> gsManager;
	private Stack<GameContext> screenManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		this.gsManager = new Stack<GameState>();
		this.screenManager = new Stack<GameContext>();
		pushCurrentState(GameState.CONTINUE);
		pushCurrentContext(GameContext.WORLDMAP);

		World world = new World(new Vector2(0, 0), true);
		player = new Player(world, new TextureAtlas(PKMConstants.SPRITES_ATLAS_FILENAME).findRegion(PKMConstants.PLAYER_SPRITE));

		screen = new GameScreen(this, player, PKMConstants.MAIN_MAP_FILENAME);
		this.setScreen(screen);
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
//		this.screenManager.pop();
		this.screenManager.push(newContext);
	}

	public GameContext getPreviousContext()
	{
		GameContext currentContext = this.screenManager.pop();
		GameContext oldContext;
		try{
			oldContext = this.screenManager.peek();
		} catch (EmptyStackException e) {
			oldContext = currentContext;
		}
		this.screenManager.push(currentContext);
		return oldContext;
	}

	public GameContext getCurrentContext()
	{
		return this.screenManager.peek();
	}

	@Override
	public void render () {
        super.render();
//		Gdx.app.log("context", "" + getCurrentContext());
		switch (getCurrentState())
		{
			case CONTINUE:
				if(screen.getHud().isMenuVisible()) screen.getHud().setMenuVisible(false);
				break;
			case PAUSED:
				if(!screen.getHud().isMenuVisible())  screen.getHud().setMenuVisible(true);
				break;
			default:
				break;
		}

		switch (getCurrentContext())
		{
			case GATE:
				if(getPreviousContext() != GameContext.GATE)
				{
					screen = new GameScreen(this, player, PKMConstants.GATE_MAP_FILENAME);
//					screen.dispose();
					this.setScreen(screen);
					setCurrentContext(GameContext.GATE);
				}
				break;
			case LAB:
				if(getPreviousContext() != GameContext.LAB)
				{
					screen = new GameScreen(this, player, PKMConstants.LAB_MAP_FILENAME);
//					screen.dispose();
					this.setScreen(screen);
					setCurrentContext(GameContext.LAB);
				}
				break;
			case HOUSE:
				if(getPreviousContext() != GameContext.HOUSE)
				{
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.HOUSE_MAP_FILENAME);
					this.setScreen(screen);
					setCurrentContext(GameContext.HOUSE);
				}
				break;
			case WORLDMAP:
				if(getPreviousContext() != GameContext.WORLDMAP)
				{
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.MAIN_MAP_FILENAME);
					this.setScreen(screen);
					setCurrentContext(GameContext.WORLDMAP);
				}
				break;
			case PLAYERHOUSE:
				if(getPreviousContext() != GameContext.PLAYERHOUSE)
				{
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.PLAYER_HOUSE_MAP_FILENAME);
					this.setScreen(screen);
					setCurrentContext(GameContext.PLAYERHOUSE);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public GameScreen getScreen() {
		return screen;
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();

	}
}
