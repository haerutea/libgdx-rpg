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
import edu.cis.pokemon.Sprites.Environment.Door;
import edu.cis.pokemon.Sprites.Items.Item;
import edu.cis.pokemon.Utils.PKMConstants;

public class WorldMapCreator implements Creator {
    private Array<Item> items;
    private Array<Door> doors;
    private Body body;
    private TiledMap map;
    private Array<Body> allBodies;

    public WorldMapCreator(GameScreen screen)
    {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polyShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef(); //gotta define the fixture first and then add body

        World world = screen.getWorld();
        map = screen.getMap();
        Hud hud = screen.getHud();

        body = world.createBody(bodyDef);
        allBodies = new Array<>();
        allBodies.add(body);

        //impassible stuff like trees, buildings, etc
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_ENVIRONMENT).getObjects().getByType(RectangleMapObject.class))
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

        //grass
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_GRASS).getObjects().getByType(RectangleMapObject.class))
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

        //ledges
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_LEDGES).getObjects().getByType(RectangleMapObject.class))
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

        //door
        doors = new Array<>();
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_DOORS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Door door = new Door(world, object);
            doors.add(door);
            allBodies.add(door.getBox2Body());
        }

        //sign
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_SIGNS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_SIGN;

            body.createFixture(fixtureDef);
            allBodies.add(body);
        }

        //items
        items = new Array<>();
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_ITEMS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Item item = new Item(screen, object);
            items.add(item);
            allBodies.add(item.getBox2Body());
        }

        //trainers
        //TODO: CHANGE TO MAKE new Trainer() INSTEAD
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_TRAINERS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_TRAINER;

            body.createFixture(fixtureDef);
            allBodies.add(body);
        }
    }

    public Array<Item> getItems() {
        return items;
    }
    public Array<Door> getDoors() {
        return doors;
    }

    @Override
    public void update(float deltaTime) {
        for(Item item : items) {
            item.update(deltaTime);
        }

        for(Door door : doors) {
            door.update(deltaTime);
        }
    }

    @Override
    public void draw(Pokemon game, GameScreen gameScreen) {
        for(Item item : items) {
            item.draw(game.batch);
        }

        for(Door door : doors) {
            door.draw(game.batch);

            String mapName = "";
            if (door.isInteracted()) {
                if (door.getProperties(PKMConstants.PROPERTY_PLAYER_HOUSE)) {
                    mapName = PKMConstants.PLAYER_HOUSE_MAP_FILENAME;
                } else if (door.getProperties(PKMConstants.PROPERTY_LAB)) {
                    mapName = PKMConstants.LAB_MAP_FILENAME;
                } else if (door.getProperties(PKMConstants.PROPERTY_HOUSE)) {
                    mapName = PKMConstants.HOUSE_MAP_FILENAME;
                } else if (door.getProperties(PKMConstants.PROPERTY_ROUTE)) {
                    mapName = "";
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
        //highest index layer is trainers
//        for(int i = 0; i <= PKMConstants.WORLD_TRAINERS; i++) {
//            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(i);
//            TiledMapTileLayer.Cell cell = layer.getCell((int) (body.getPosition().x) , (int) (body.getPosition().y));
//            cell.setTile(null);
//        }
        for (Body body : allBodies) {
            Array<Fixture> fixtures = body.getFixtureList();
            for (Fixture fixture : fixtures) {
                body.destroyFixture(fixture); //TODO: items and doors don't delete
            }
        }
    }
}
