package edu.cis.pokemon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.cis.pokemon.InputListener;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Pokemon;

public class GameScreen implements Screen {
    private Pokemon game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private InputListener inputListener;

    public GameScreen(Pokemon game) {
        this.game = game;
        atlas = new TextureAtlas(PKMConstants.ATLAS_FILENAME);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, gameCam);
        hud = new Hud(game.batch);

        Gdx.input.setInputProcessor(new InputListener(this, hud,
                gameCam));

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(PKMConstants.MAP_FILENAME);
        renderer = new OrthogonalTiledMapRenderer(map);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    public void handleInput(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameCam.position.x += 100 * deltaTime; //TODO: temp check
        }
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
        gameCam.update();

        renderer.setView(gameCam);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.end();

		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		if(hud.isMenuVisible())
        {
            hud.menuStage.draw();
        }
		hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
         gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        map.dispose();
        renderer.dispose();
        hud.dispose();
    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }
}
