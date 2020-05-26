package edu.cis.pokemon.Tools;

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
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Items.Item;
import edu.cis.pokemon.Utils.PKMConstants;

public class Box2dWorldCreator
{
    private Array<Item> items;

    public Box2dWorldCreator(GameScreen screen)
    {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polyShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef(); //gotta define the fixture first and then add body
        Body body;

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        Hud hud = screen.getHud();

        //impassible stuff like trees, buildings, etc
        for(MapObject object : map.getLayers().get(PKMConstants.ENVIRONMENT).getObjects().getByType(RectangleMapObject.class))
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
        for(MapObject object : map.getLayers().get(PKMConstants.GRASS).getObjects().getByType(RectangleMapObject.class))
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
        for(MapObject object : map.getLayers().get(PKMConstants.LEDGES).getObjects().getByType(RectangleMapObject.class))
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
        for(MapObject object : map.getLayers().get(PKMConstants.DOORS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_DOOR;

            body.createFixture(fixtureDef);
        }

        //sign
        for(MapObject object : map.getLayers().get(PKMConstants.SIGNS).getObjects().getByType(RectangleMapObject.class))
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
        for(MapObject object : map.getLayers().get(PKMConstants.ITEMS).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            items.add(new Item(screen, rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2));
        }

        //trainers
        //TODO: CHANGE TO MAKE new Trainer() INSTEAD
        for(MapObject object : map.getLayers().get(PKMConstants.TRAINERS).getObjects().getByType(RectangleMapObject.class))
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
}
