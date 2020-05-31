package edu.cis.pokemon.Tools.Creators;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Screens.WorldMapScreen;
import edu.cis.pokemon.Sprites.Environment.Door;
import edu.cis.pokemon.Sprites.Items.Item;
import edu.cis.pokemon.Utils.PKMConstants;

public class WorldMapCreator
{
    private Array<Item> items;
    private Array<Door> doors;

    public WorldMapCreator(WorldMapScreen screen)
    {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polyShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef(); //gotta define the fixture first and then add body
        Body body;

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        Hud hud = screen.getHud();

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
        }

        //door
        doors = new Array<>();
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_DOORS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            doors.add(new Door(world, object));
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
        }

        //items
        items = new Array<>();
        for(MapObject object : map.getLayers().get(PKMConstants.WORLD_ITEMS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            items.add(new Item(screen, object));
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
        }
    }

    public Array<Item> getItems() {
        return items;
    }
    public Array<Door> getDoors() {
        return doors;
    }
}
