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

		this.gsManager = new Stack<GameState>();
		this.screenManager = new Stack<GameContext>();
		pushCurrentState(GameState.CONTINUE);
		pushCurrentContext(GameContext.WORLDMAP);

		World world = new World(new Vector2(0, 0), true);
		player = new Player(world, new TextureAtlas(PKMConstants.SPRITES_ATLAS_FILENAME).findRegion(PKMConstants.PLAYER_SPRITE));

		screen = new GameScreen(this, player, PKMConstants.MAIN_MAP_FILENAME, player.getX(), player.getY());
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
					float prevX = player.box2Body.getPosition().x;
					float prevY = player.box2Body.getPosition().y;
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.GATE_MAP_FILENAME,
							prevX, prevY - PKMConstants.TILE_SIZE / 4);
					this.setScreen(screen);
					setCurrentContext(GameContext.GATE);
				}
				break;
			case LAB:
				if(getPreviousContext() != GameContext.LAB)
				{
					float prevX = player.box2Body.getPosition().x;
					float prevY = player.box2Body.getPosition().y;
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.LAB_MAP_FILENAME,
							prevX, prevY - PKMConstants.TILE_SIZE / 4);
					this.setScreen(screen);
					setCurrentContext(GameContext.LAB);
				}
				break;
			case HOUSE:
				if(getPreviousContext() != GameContext.HOUSE)
				{
					float prevX = player.box2Body.getPosition().x;
					float prevY = player.box2Body.getPosition().y;
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.HOUSE_MAP_FILENAME,
							prevX, prevY - PKMConstants.TILE_SIZE / 4);
					this.setScreen(screen);
					setCurrentContext(GameContext.HOUSE);
				}
				break;
			case WORLDMAP:
				if(getPreviousContext() != GameContext.WORLDMAP)
				{
					float prevX = getScreen().getPrevX();
					float prevY = getScreen().getPrevY();
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.MAIN_MAP_FILENAME,
							prevX, prevY);
					this.setScreen(screen);
					setCurrentContext(GameContext.WORLDMAP);
				}
				break;
			case PLAYERHOUSE:
				if(getPreviousContext() != GameContext.PLAYERHOUSE)
				{
					float prevX = player.box2Body.getPosition().x;
					float prevY = player.box2Body.getPosition().y;
					screen.dispose();
					screen = new GameScreen(this, player, PKMConstants.PLAYER_HOUSE_MAP_FILENAME,
							prevX, prevY - PKMConstants.TILE_SIZE / 4);
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
