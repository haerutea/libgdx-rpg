package edu.cis.pokemon.Tools.Creators;

import edu.cis.pokemon.Pokemon;
import edu.cis.pokemon.Screens.GameScreen;

public interface Creator {

    public void update(float deltaTime);

    public void draw(Pokemon game, GameScreen gameScreen);

    public void dispose();
}
