package edu.cis.pokemon.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import edu.cis.pokemon.Enums.GameState;

public class PKMUtils
{
    public static GameState currentGameState;

    public static void setCurrentGameState(GameState currentGameState)
    {
        PKMUtils.currentGameState = currentGameState;
    }

    public static GameState getCurrentGameState()
    {
        return currentGameState;
    }

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

//        FixtureDef fixtureDef = new FixtureDef();
//        CircleShape circleShape = new CircleShape();
//        circleShape.setRadius(6);
//        fixtureDef.filter.categoryBits = fixtureBit;
//        fixtureDef.filter.maskBits = canCollideWith;
//
//        fixtureDef.shape = circleShape;
//        box2Body.createFixture(fixtureDef).setUserData(userData);
//
//        return fixtureDef;
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

//    void changeMap() {
//        Gdx.app.postRunnable(() -> { //Post runnable posts the below task in opengl thread
//            TiledMap testMap = new TmxMapLoader().load(PKMConstants.PLAYER_HOUSE_MAP_FILENAME); //load the new map
//            renderer.getMap().dispose(); //dispose the old map
//            renderer.setMap(testMap); //set the map in your renderer
//        });
//    }
}
