package edu.cis.pokemon.Screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.cis.pokemon.Enums.GameContext;
import edu.cis.pokemon.Enums.GameState;
import edu.cis.pokemon.Enums.State;
import edu.cis.pokemon.Sprites.Environment.Door;
import edu.cis.pokemon.Sprites.Items.Item;
import edu.cis.pokemon.Tools.Creators.Creator;
import edu.cis.pokemon.Tools.Creators.GateCreator;
import edu.cis.pokemon.Tools.Creators.HouseCreator;
import edu.cis.pokemon.Tools.Creators.LabCreator;
import edu.cis.pokemon.Tools.Creators.PlayerHouseCreator;
import edu.cis.pokemon.Tools.Creators.WorldMapCreator;
import edu.cis.pokemon.Tools.GameStateManager;
import edu.cis.pokemon.Tools.InputListener;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Sprites.Player;
import edu.cis.pokemon.Tools.InteractionProcessor;
import edu.cis.pokemon.Tools.WorldContactListener;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Pokemon;
import edu.cis.pokemon.Utils.PKMUtils;

public class GameScreen implements Screen, AbstractScreen {
    private Pokemon game;
    private TextureAtlas atlas;
    private String mapName;
    private Vector2 previousPosition;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr; //gives graphical representation of bodies in box2d world
    private Creator box2dCreator;

    //sprites
    private Player player;

    //interaction
    private InteractionProcessor interactionProcessor;
    private GameStateManager gsManager;

    public GameScreen(Pokemon game, Player player, String mapName, Vector2 position) {
        this.game = game;
        atlas = new TextureAtlas(PKMConstants.SPRITES_ATLAS_FILENAME);
        this.mapName = mapName;
        Gdx.app.log("new screen position", player.box2Body.getPosition().toString());
        previousPosition = position;
        Gdx.app.log("new screen previousposition", previousPosition.toString());

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(PKMConstants.V_WIDTH, PKMConstants.V_HEIGHT, gameCam);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);

        renderer = new OrthogonalTiledMapRenderer(map);

        this.world = player.getWorld();
//        world = new World(new Vector2(0, 0), true); //sleep objects that are at rest, therefore not calculating
        b2dr = new Box2DDebugRenderer();
        switch (mapName) {
//            case PKMConstants.MAIN_MAP_FILENAME:
//                box2dCreator = new WorldMapCreator(this);
//                break;
            case PKMConstants.PLAYER_HOUSE_MAP_FILENAME:
                box2dCreator = new PlayerHouseCreator(this);
                player.box2Body.setTransform(PKMConstants.TILE_SIZE * 4, PKMConstants.TILE_SIZE * 2, 0);
                break;
            case PKMConstants.HOUSE_MAP_FILENAME:
                box2dCreator = new HouseCreator(this);
                player.box2Body.setTransform(PKMConstants.TILE_SIZE * 4, PKMConstants.TILE_SIZE * 2, 0);
                break;
            case PKMConstants.LAB_MAP_FILENAME:
                box2dCreator = new LabCreator(this);
                player.box2Body.setTransform(PKMConstants.TILE_SIZE * 6, PKMConstants.TILE_SIZE * 2, 0);
                break;
            case PKMConstants.GATE_MAP_FILENAME:
                box2dCreator = new GateCreator(this);
                player.box2Body.setTransform(PKMConstants.TILE_SIZE * 5, PKMConstants.TILE_SIZE * 2, 0);
                break;
            default:
                box2dCreator = new WorldMapCreator(this); //catch all
                player.box2Body.setTransform(previousPosition, 0);
                break;
        }

        this.player = player;
//        player = new Player(world, this);


        this.gsManager = new GameStateManager(this.game, this.hud, GameState.CONTINUE, GameContext.WORLDMAP);
        interactionProcessor = InteractionProcessor.getInstance();
        InputProcessor processor = new InputListener(this, hud, player, interactionProcessor, this.gsManager);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud.menuStage);
        inputMultiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        world.setContactListener(new WorldContactListener(interactionProcessor));
    }

    public void update(float deltaTime) {
        world.step(PKMConstants.FPS, PKMConstants.V_ITER, PKMConstants.P_ITER);
        gameCam.position.x = player.box2Body.getPosition().x;
        gameCam.position.y = player.box2Body.getPosition().y;
        gameCam.update();

        player.update(deltaTime);

//        Gdx.app.log("creator ", "here");
        box2dCreator.update(deltaTime);

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

        gsManager.renderCurrent();

        if(gsManager.getCurrentState() != GameState.PAUSED)
        {
            player.move();
        }

        player.draw(game.batch);

        box2dCreator.draw(game, this);

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

    public void restart(String name){
        if(!mapName.equals(PKMConstants.MAIN_MAP_FILENAME)){
            Gdx.app.log("position", mapName);
            Gdx.app.log("position position", player.box2Body.getPosition().toString());
            Gdx.app.log("position previousposition", previousPosition.toString());
            game.setScreen(new GameScreen(game, player, name, previousPosition));
        }
        else {
            Gdx.app.log("position main map", mapName);
            Gdx.app.log("position main map position", player.box2Body.getPosition().toString());
            Gdx.app.log("position main map previousposition", previousPosition.toString());
            game.setScreen(new GameScreen(game, player, name, player.box2Body.getPosition()));
        }
        this.dispose();
    }

    @Override
    public void dispose()
    {
        map.dispose();
        box2dCreator.dispose();
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

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
