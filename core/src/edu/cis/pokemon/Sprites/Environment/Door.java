package edu.cis.pokemon.Sprites.Environment;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import edu.cis.pokemon.Screens.AbstractScreen;
import edu.cis.pokemon.Screens.PlayerHouseScreen;
import edu.cis.pokemon.Sprites.Interactable;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class Door extends Sprite implements Interactable {
    public World world;
    public Body box2Body;
    protected boolean open;
    private MapObject mapObject;
    private boolean interacted;

    public Door(World world, MapObject mapObject) {
        this.world = world;
        this.mapObject = mapObject;
        Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
        setPosition(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
        interacted = false;

        defineDoor();

        setBounds(getX(), getY(), 16, 16);
        //box2Body.setActive(false);
        open = false;
    }

    protected void defineDoor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        box2Body = world.createBody(bodyDef);

        short canCollideWith = PKMConstants.BIT_PLAYER; //is this even necessary with a staticBody?

        FixtureDef fixtureDef = PKMUtils.createGameFixture(this, box2Body, PKMConstants.BIT_DOOR, canCollideWith);
        box2Body.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float dt) {
//        if(open) {
//            world.destroyBody(box2Body);
//            destroyed = true;
//        }
//        else if(!destroyed) {
//            setPosition(box2Body.getPosition().x - getWidth() / 2, box2Body.getPosition().y - getHeight() / 2);
//        }
    }

    public void draw(Batch batch) {
//        super.draw(batch);
    }

    @Override
    public void interact() {
        interacted = true;
        switch (mapObject.getProperties().toString()) {
            case PKMConstants.PROPERTY_LAB:

                break;
            case PKMConstants.PROPERTY_PLAYER_HOUSE:
//                PlayerHouseScreen screen = new PlayerHouseScreen();
                break;
            case PKMConstants.PROPERTY_HOUSE:

                break;
            case PKMConstants.PROPERTY_ROUTE:

                break;

        }
    }

    public boolean isInteracted() {
        return interacted;
    }

    public void setInteracted(boolean interacted) {
        this.interacted = interacted;
    }

    public boolean getProperties(String key) {
        return mapObject.getProperties().containsKey(key);
    }
}
