package edu.cis.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import edu.cis.pokemon.Screens.WorldMapScreen;
import edu.cis.pokemon.Sprites.Player;
import edu.cis.pokemon.Utils.PKMConstants;

public class Pokemon extends Game {
	public SpriteBatch batch;
	//Texture img;

	private WorldMapScreen screen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		World world = new World(new Vector2(0, 0), true);
		Player player = new Player(world, new TextureAtlas(PKMConstants.ATLAS_FILENAME).findRegion(PKMConstants.PLAYER_SPRITE));

		screen = new WorldMapScreen(this, player);
		this.setScreen(screen);
	}

	@Override
	public void render () {
        super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		//img.dispose();
	}
}
