package edu.cis.pokemon.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class PKMUtils
{
    public static FixtureDef createGameFixture(Object userData, Body box2Body, short fixtureBit, short canCollideWith)
    {
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6);
        fixtureDef.filter.categoryBits = fixtureBit;
        fixtureDef.filter.maskBits = canCollideWith;

        fixtureDef.shape = circleShape;
        box2Body.createFixture(fixtureDef).setUserData(userData);

        return fixtureDef;
    }
}
