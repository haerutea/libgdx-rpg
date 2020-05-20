package Screens;

import com.badlogic.gdx.Screen;

import edu.cis.pokemon.Pokemon;

public abstract class AbstractScreen implements Screen {
    private Pokemon game;

    public AbstractScreen(Pokemon game) {
        this.game = game;
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float delta);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void hide();

    @Override
    public abstract void dispose();
}
