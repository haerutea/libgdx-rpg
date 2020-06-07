package edu.cis.pokemon.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import edu.cis.pokemon.Screens.AbstractScreen;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class Trainer extends Sprite
{
    public World world;
    private Body box2Body;
    private float x;
    private float y;
    private MapObject mapObject;
    private TextureAtlas.AtlasRegion npcRegion;
    private String identity;
    private TextureRegion frontStand;

    public Trainer(AbstractScreen screen, MapObject mapObject, String npcIdentity) {
        this.world = screen.getWorld();
        this.mapObject = mapObject;
        Rectangle rect = ((RectangleMapObject) mapObject).getRectangle();
        x = rect.getX() + rect.getWidth() / 2;
        y = rect.getY() + rect.getHeight() / 2;
        setPosition(x, y);

        defineBox2d();

        this.identity = npcIdentity;
        npcRegion = screen.getAtlas().findRegion(npcIdentity);
        frontStand = new TextureRegion(npcRegion, 0, 0, 16, 16);
        this.setRegion(frontStand);
        this.setBounds(x - 8, y - 8, 16, 16); //minus 8 so the sprite's bottom left corner is rect's bottom left corner
    }

    private void defineBox2d()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        box2Body = world.createBody(bodyDef);

        short canCollideWith = PKMConstants.BIT_PLAYER;

        FixtureDef fixtureDef = PKMUtils.createGameFixture(this, box2Body, PKMConstants.BIT_TRAINER, canCollideWith);
        box2Body.createFixture(fixtureDef).setUserData(this);
    }

    public void draw(Batch batch) {
            super.draw(batch);
    }
    //@Override
    public void interact()
    {

    }

    public Body getBox2Body()
    {
        return box2Body;
    }

    public String getIdentity()
    {
        return identity;
    }
}
