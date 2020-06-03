package edu.cis.pokemon.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Scenes.Hud;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Environment.Door;

public class PKMUtils
{
    public static FixtureDef createGameFixture(Object userData, Body box2Body, short fixtureBit, short canCollideWith)
    {
        PolygonShape polyShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = fixtureBit;
        fixtureDef.filter.maskBits = canCollideWith;

        polyShape.setAsBox(8, 8); //change values so it fills up whole space?
        fixtureDef.shape = polyShape;
        box2Body.createFixture(fixtureDef).setUserData(userData);

        return fixtureDef;
    }

    public static FixtureDef createPlayerFixture(Object userData, Body box2Body, short fixtureBit, short canCollideWith)
    {
        PolygonShape polyShape = new PolygonShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = fixtureBit;
        fixtureDef.filter.maskBits = canCollideWith;

        polyShape.setAsBox(7, 7); //change values so it fills up whole space?
        fixtureDef.shape = polyShape;
        box2Body.createFixture(fixtureDef).setUserData(userData);

        return fixtureDef;
    }

    public static Array<Body> createBody(GameScreen screen, int index) {

        Array<Body> returnBodies = new Array<>();

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bodyDef = new BodyDef();
        Body body;

        PolygonShape polyShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef(); //gotta define the fixture first and then add body

        for(MapObject object : map.getLayers().get(index).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

            body = world.createBody(bodyDef);

            polyShape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fixtureDef.shape = polyShape;
            fixtureDef.filter.categoryBits = PKMConstants.BIT_IMPASSIBLE;
            body.createFixture(fixtureDef);
            returnBodies.add(body);
        }

        return returnBodies;
    }

    public static Array<Door> createDoors(GameScreen screen, int index, Array<Door> doors) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        for(MapObject object : map.getLayers().get(index).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Door door = new Door(world, object);
            doors.add(door);
//            returnBodies.add(door.getBox2Body());
        }

        return doors;
    }
}
