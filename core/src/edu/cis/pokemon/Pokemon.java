package edu.cis.pokemon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.cis.pokemon.Screens.GameScreen;

public class Pokemon extends Game {
	public SpriteBatch batch;
	//Texture img;

	private GameScreen screen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		screen = new GameScreen(this);

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
