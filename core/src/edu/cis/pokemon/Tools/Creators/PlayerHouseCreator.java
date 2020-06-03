package edu.cis.pokemon.Tools.Creators;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Pokemon;
import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Screens.PlayerHouseScreen;
import edu.cis.pokemon.Sprites.Environment.Door;
import edu.cis.pokemon.Utils.PKMConstants;

public class PlayerHouseCreator implements Creator{
    private Array<Door> exits;
    private Body body;
    private TiledMap map;
    private Array<Body> allBodies;

    public PlayerHouseCreator(GameScreen screen)
    {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polyShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef(); //gotta define the fixture first and then add body
        Body body;

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        Hud hud = screen.getHud();

        body = world.createBody(bodyDef);
        allBodies = new Array<>();
        allBodies.add(body);

        //impassible stuff like trees, buildings, etc
        for(MapObject object : map.getLayers().get(PKMConstants.PLAYER_HOUSE_ENVIRONMENT).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_IMPASSIBLE;
            body.createFixture(fixtureDef);
            allBodies.add(body);
        }

        //tv
        for(MapObject object : map.getLayers().get(PKMConstants.PLAYER_HOUSE_TV).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_GRASS;

            body.createFixture(fixtureDef);
            allBodies.add(body);
        }

        //bed
        for(MapObject object : map.getLayers().get(PKMConstants.PLAYER_HOUSE_BED).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_LEDGE;

            body.createFixture(fixtureDef);
            allBodies.add(body);
        }

        //exit
        exits = new Array<>();
        for(MapObject object : map.getLayers().get(PKMConstants.PLAYER_HOUSE_EXIT).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Door door = new Door(world, object);
            exits.add(door);
            allBodies.add(door.getBox2Body());
            allBodies.add(body);
        }

        //npc
    }

    @Override
    public void update(float deltaTime) {
        for(Door door : exits) {
            door.update(deltaTime);
        }
    }

    @Override
    public void draw(Pokemon game, GameScreen gameScreen) {
        for(Door door : exits) {
            door.draw(game.batch);

            String mapName = "";
            if (door.isInteracted()) {
                if (door.getProperties(PKMConstants.PROPERTY_HOUSE)) {
                    mapName = PKMConstants.MAIN_MAP_FILENAME;
                }
                door.setInteracted(false);

                if(!mapName.equals("")) {
                    gameScreen.setMapName(mapName);
                    gameScreen.restart(mapName);
                }
            }
        }
    }

    @Override
    public void dispose() {
        for (Body body : allBodies) {
            Array<Fixture> fixtures = body.getFixtureList();
            for (Fixture fixture : fixtures) {
                body.destroyFixture(fixture); //TODO: items and doors don't delete
            }
        }
    }
}
