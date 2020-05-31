package edu.cis.pokemon.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.cis.pokemon.Sprites.Environment.Door;
import edu.cis.pokemon.Sprites.Items.Item;
import edu.cis.pokemon.Tools.Creators.WorldMapCreator;
import edu.cis.pokemon.Tools.InputListener;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Sprites.Player;
import edu.cis.pokemon.Tools.InteractionProcessor;
import edu.cis.pokemon.Tools.WorldContactListener;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Pokemon;

public class WorldMapScreen implements Screen, AbstractScreen {
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
    private WorldMapCreator box2dCreator;

    //sprites
    private Player player;

    //interaction
    private InteractionProcessor interactionProcessor;

    public WorldMapScreen(Pokemon game, Player player) {
        this.game = game;
        atlas = new TextureAtlas(PKMConstants.ATLAS_FILENAME);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(PKMConstants.MAIN_MAP_FILENAME);

        renderer = new OrthogonalTiledMapRenderer(map);

        this.world = player.getWorld();
//        world = new World(new Vector2(0, 0), true); //sleep objects that are at rest, therefore not calculating
        b2dr = new Box2DDebugRenderer();
        box2dCreator = new WorldMapCreator(this);

        this.player = player;
//        player = new Player(world, this);

        interactionProcessor = InteractionProcessor.getInstance();
        InputProcessor processor = new InputListener(this, hud, player, interactionProcessor);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud.menuStage);
        inputMultiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //Gdx.input.setInputProcessor(new InputListener(this, hud, player, interactionProcessor));
        world.setContactListener(new WorldContactListener(interactionProcessor));
    }

    public void handleInput(float deltaTime) {
//        if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
//            gameCam.position.x += 2000 * deltaTime; //TODO: temp
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
//            gameCam.position.x -= 2000 * deltaTime; //TODO: temp
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
//            gameCam.position.y += 2000 * deltaTime; //TODO: temp
//        }
//        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
//            gameCam.position.y -= 2000 * deltaTime; //TODO: temp
//        }
    }


    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(PKMConstants.FPS, PKMConstants.V_ITER, PKMConstants.P_ITER);
        gameCam.position.x = player.box2Body.getPosition().x;
        gameCam.position.y = player.box2Body.getPosition().y;
        gameCam.update();

        player.update(deltaTime);

        for(Item item : box2dCreator.getItems()) {
            item.update(deltaTime);
        }

        for(Door door : box2dCreator.getDoors()) {
            door.update(deltaTime);
        }

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

        for(Item item : box2dCreator.getItems()) {
            item.draw(game.batch);
        }

        for(Door door : box2dCreator.getDoors()) {
            door.draw(game.batch);
        }

        game.batch.end();

		game.batch.setProjectionMatrix(hud.infoStage.getCamera().combined);

		if(hud.isMenuVisible())
        {
            hud.menuStage.act(deltaTime);
            hud.menuStage.draw();
        }

		hud.infoStage.draw();
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
