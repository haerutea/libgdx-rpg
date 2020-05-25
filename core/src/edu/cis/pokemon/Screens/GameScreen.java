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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.cis.pokemon.Tools.Box2dWorldCreator;
import edu.cis.pokemon.Tools.InputListener;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Sprites.Player;
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

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr; //gives graphical representation of bodies in box2d world
    private Box2dWorldCreator box2dCreator;

    //sprites
    private Player player;

    public GameScreen(Pokemon game) {
        this.game = game;
        atlas = new TextureAtlas(PKMConstants.ATLAS_FILENAME);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(PKMConstants.MAP_FILENAME);

        renderer = new OrthogonalTiledMapRenderer(map);

        world = new World(new Vector2(0, 0), true); //sleep objects that are at rest, therefore not calculating
        b2dr = new Box2DDebugRenderer();
        box2dCreator = new Box2dWorldCreator(this);

        player = new Player(world, this);
        Gdx.input.setInputProcessor(new InputListener(this, hud, player));
    }

    public void handleInput(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameCam.position.x += 100 * deltaTime; //TODO: temp check
        }
    }


    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(PKMConstants.FPS, PKMConstants.V_ITER, PKMConstants.P_ITER);
        gameCam.position.x = player.box2Body.getPosition().x;
        gameCam.position.y = player.box2Body.getPosition().y;
        gameCam.update();

        //player.update(dt);

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

        b2dr.render(world, gameCam.combined);

        game.batch.begin();

        player.move();
        player.draw(game.batch);

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

    public Hud getHud()
    {
        return hud;
    }

    public TiledMap getMap()
    {
        return map;
    }

    public World getWorld()
    {
        return world;
    }
}
