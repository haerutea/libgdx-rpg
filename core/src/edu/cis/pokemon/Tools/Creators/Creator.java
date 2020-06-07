package edu.cis.pokemon.Tools.Creators;

import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Pokemon;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Environment.Door;

public interface Creator {

    public void update(float deltaTime);

    public void draw(Pokemon game, GameScreen gameScreen);

    public void dispose();

    public Array<Door> getExits();
}
