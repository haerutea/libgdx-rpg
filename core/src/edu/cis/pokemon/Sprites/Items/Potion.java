package edu.cis.pokemon.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class Potion extends Item {

    public Potion(GameScreen screen, float x, float y) {
        super(screen, x, y);
    }

    @Override
    protected void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        box2Body = world.createBody(bodyDef);

        short canCollideWith = PKMConstants.BIT_PLAYER; //is this even necessary with a staticBody?

        FixtureDef fixtureDef = PKMUtils.createGameFixture(this, box2Body, PKMConstants.BIT_ITEM, canCollideWith);
        box2Body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        if(setToDestroy && !destroyed) {
            world.destroyBody(box2Body);
            destroyed = true;
        }
        else if(!destroyed) {
            setPosition(box2Body.getPosition().x - getWidth() / 2, box2Body.getPosition().y - getHeight() / 2);
        }
    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }

    @Override
    public String interactionDialog() {
        return super.interactionDialog();
    }

}
