package edu.cis.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
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
		Player player = new Player(world, new TextureAtlas(PKMConstants.SPRITES_ATLAS_FILENAME).findRegion(PKMConstants.PLAYER_SPRITE));


		screen = new GameScreen(this, player, PKMConstants.MAIN_MAP_FILENAME, player.box2Body.getPosition());
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
		this.screenManager.pop();
		this.screenManager.push(newContext);
	}

	public GameContext getCurrentContext()
	{
		return this.screenManager.peek();
	}

	@Override
	public void render () {
        super.render();
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
			case LAB:
				break;
			case HOUSE:
				break;
			case WORLDMAP:
				if(getCurrentContext() != GameContext.WORLDMAP)
				{
					ArrayList<Integer> fixtures = new ArrayList<>();
					fixtures.add(PKMConstants.WORLD_ENVIRONMENT);
					fixtures.add(PKMConstants.WORLD_GRASS);
					fixtures.add(PKMConstants.WORLD_LEDGES);
					fixtures.add(PKMConstants.WORLD_SIGNS);
					Creator b2dCreator = new GenericCreator(screen, fixtures,
							PKMConstants.WORLD_DOORS,
							PKMConstants.WORLD_ITEMS,
							PKMConstants.WORLD_TRAINERS);
//                    GameScreen worldScreen =
//                    this.setScreen();
				}
				break;
			case PLAYERHOUSE:
				break;
			default:
				break;
		}
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();

	}
}
