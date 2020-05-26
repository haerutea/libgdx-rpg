package edu.cis.pokemon.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Interactable;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class Item extends Sprite implements Interactable {
    public World world;
    public Body box2Body;
    protected boolean setToDestroy;
    protected boolean destroyed;

    private TextureAtlas.AtlasRegion itemRegion;

    public Item(GameScreen screen, float x, float y) {
        this.world = screen.getWorld();
        setPosition(x, y);
        defineItem();
        itemRegion = screen.getAtlas().findRegion(PKMConstants.ITEM_SPRITE);
        TextureRegion image =  new TextureRegion(itemRegion, PKMConstants.X_OFFSET, PKMConstants.Y_OFFSET, 16, 16);
        setRegion(image);

        setBounds(getX(), getY(), 16, 16);
//        box2Body.setActive(false);
        setToDestroy = false;
        destroyed = false;
    }

    protected void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.StaticBody;
        box2Body = world.createBody(bodyDef);

        short canCollideWith = PKMConstants.BIT_PLAYER; //is this even necessary with a staticBody?

        FixtureDef fixtureDef = PKMUtils.createGameFixture(this, box2Body, PKMConstants.BIT_ITEM, canCollideWith);
        box2Body.createFixture(fixtureDef).setUserData(this);
    }

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
    public void interact() {
        //dialog: you found _____!
        setToDestroy = true; //remove texture
        //add to bag
    }
}
