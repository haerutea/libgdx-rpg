package edu.cis.pokemon.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import edu.cis.pokemon.Enums.Direction;
import edu.cis.pokemon.Enums.State;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class Player extends Sprite
{
    private State currentState;
    private Direction currentDirection;

    //TODO: IS A DIFF VAR FOR EACH DIRECTION/STATE NEEDED?
    private TextureAtlas.AtlasRegion playerRegion;
    private TextureRegion frontStand;
    private TextureRegion backStand;
    private TextureRegion sideStand;
    private Animation<TextureRegion> frontWalk;
    private Animation<TextureRegion> backWalk;
    private Animation<TextureRegion> leftWalk;
    private Animation<TextureRegion> rightWalk;

    public World world;
    public Body box2Body;

    public Player(World world, GameScreen screen)
    {
        this.world = world;
        //starts off standing, facing front
        currentState = State.STANDING;
        currentDirection = Direction.FRONT;

        playerRegion = screen.getAtlas().findRegion(PKMConstants.PLAYER_SPRITE);
        makeAnimations();
        defineBox2d();

        setBounds(0, 0, 16, 16);
        setRegion(frontStand);
    }

    public void defineBox2d()
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(150, 150);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        box2Body = world.createBody(bodyDef);

        //TODO: CHANGE THIS SO IT CAN WALK THROUGH THE GRASS
        short collidesWith =
                PKMConstants.BIT_IMPASSIBLE
                | PKMConstants.BIT_SIGN
                | PKMConstants.BIT_LEDGE
                | PKMConstants.BIT_ITEM
                | PKMConstants.BIT_GRASS
                | PKMConstants.BIT_DOOR;

        FixtureDef fixtureDef = PKMUtils.createGameFixture(this, box2Body, PKMConstants.BIT_PLAYER, collidesWith);
        setRegion(frontStand);
    }

    private void makeAnimations()
    {
        frontStand = new TextureRegion(playerRegion, 2, 2, 16, 16);
        backStand = new TextureRegion(playerRegion, 16, 0, 16, 16);
        sideStand = new TextureRegion(playerRegion, 32, 0, 16, 16);

//        Array<TextureRegion> frames = new Array<TextureRegion>(); //make sure to use the badlogic's Array
//        frames.add(new TextureRegion(playerRegion, i * 16, 0, 16, 16));
//        frontWalk = new Animation<>(0.1f, frames);
//        frames.clear();
    }

    public void changeSprite(Direction direction, State state)
    {
        Gdx.app.log("changeSprite", "works");
        currentDirection = direction;
        currentState = state;
        switch(direction)
        {
            case FRONT:
                Gdx.app.log("front", "works");
                setRegion(frontStand);
                break;
            case BACK:
                setRegion(backStand);
                break;
        }
    }

    public void move()
    {
        if(currentState != State.STANDING)
        {
            if(currentDirection.getAxis().equals("x"))
            {
                box2Body.applyLinearImpulse(new Vector2(currentDirection.getVelocity(), 0), box2Body.getWorldCenter(), true);
            }
            else if(currentDirection.getAxis().equals("y"))
            {
                box2Body.applyLinearImpulse(new Vector2(0, currentDirection.getVelocity()), box2Body.getWorldCenter(), true);
            }
        }
    }

    public void stop()
    {
        currentState = State.STANDING;
        box2Body.setLinearVelocity(new Vector2(0, 0));
    }
}
