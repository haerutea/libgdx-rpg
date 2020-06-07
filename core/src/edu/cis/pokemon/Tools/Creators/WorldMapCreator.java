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
import edu.cis.pokemon.Sprites.Trainer;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class WorldMapCreator implements Creator {
    private Array<Item> items;
    private Array<Door> doors;
    private Array<Trainer> trainers;
    private Array<Body> allBodies;

    public WorldMapCreator(GameScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bodyDef = new BodyDef();
        Body body = world.createBody(bodyDef);

        allBodies = new Array<>();
        allBodies.add(body);

        //impassible stuff like trees, buildings, etc
        allBodies.addAll(PKMUtils.createBody(screen, PKMConstants.WORLD_ENVIRONMENT));

        //grass
        allBodies.addAll(PKMUtils.createBody(screen, PKMConstants.WORLD_GRASS));

        //ledges
        allBodies.addAll(PKMUtils.createBody(screen, PKMConstants.WORLD_LEDGES));

        //door
        doors = new Array<>();
        doors.addAll(PKMUtils.createDoors(screen, PKMConstants.WORLD_DOORS, doors));
        for(Door door : doors) {
            allBodies.add(door.getBox2Body());
        }

        //sign
        allBodies.addAll(PKMUtils.createBody(screen, PKMConstants.WORLD_SIGNS));

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
        trainers = new Array<>();
        for(int i = 0; i < map.getLayers().get(PKMConstants.WORLD_TRAINERS).getObjects().getCount(); i++)
        {
            RectangleMapObject object = map.getLayers().get(PKMConstants.WORLD_TRAINERS).getObjects().getByType(RectangleMapObject.class).get(i);
            Rectangle rect = object.getRectangle();
            Trainer npcTrainer = new Trainer(screen, object, PKMConstants.NPC_SPRITES[i]);
            trainers.add(npcTrainer);
            allBodies.add(npcTrainer.getBox2Body());
        }
//        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_TRAINERS).getObjects().getByType(RectangleMapObject.class))
//        {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            Trainer npcTrainer = new Trainer(screen, object, PKMConstants.ITEM_SPRITE);
//            trainers.add(npcTrainer);
//            allBodies.add(npcTrainer.getBox2Body());
//        }


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
                } else if (door.getProperties(PKMConstants.PROPERTY_GATE)) {
                    mapName = PKMConstants.GATE_MAP_FILENAME;
                }
                door.setInteracted(false);

                if(!mapName.equals("")) {
                    gameScreen.setMapName(mapName);
                    gameScreen.restart(mapName);
                }
            }
        }
        for(Trainer trainer : trainers) {
            trainer.draw(game.batch);
        }
    }

    public Array<Door> getExits() {
        return doors;
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
                body.destroyFixture(fixture); //TODO: items don't delete (doors do delete now)
            }
        }
    }
}
