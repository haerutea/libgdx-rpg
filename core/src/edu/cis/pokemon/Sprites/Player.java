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
import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Enums.Direction;
import edu.cis.pokemon.Enums.State;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class Player extends Sprite
{
    private State currentState;
    private State previousState;
    private Direction currentDirection;
    private boolean turnDirection;
    private boolean facingRight;
    private float stateTimer;

    private TextureAtlas.AtlasRegion playerRegion;
    private TextureRegion frontStand;
    private TextureRegion backStand;
    private TextureRegion sideStand;
    private Animation<TextureRegion> frontWalk;
    private Animation<TextureRegion> backWalk;
    private Animation<TextureRegion> leftWalk;
    private Animation<TextureRegion> rightWalk;

    private World world;
    public Body box2Body;

    public Player(World world, TextureAtlas.AtlasRegion atlasRegion)
    {
        this.world = world;
        //starts off standing, facing front
        currentState = State.STANDING;
        previousState = State.STANDING;
        currentDirection = Direction.FRONT;
        stateTimer = 0;
        turnDirection = false;

        playerRegion = atlasRegion;
        makeAnimations();
        defineBox2d();
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
                | PKMConstants.BIT_DOOR
                | PKMConstants.BIT_ITEM
                | PKMConstants.BIT_GRASS
                | PKMConstants.BIT_TRAINER;
//        short collidesWith = PKMConstants.BIT_ITEM;

        FixtureDef fixtureDef = PKMUtils.createPlayerFixture(this, box2Body, PKMConstants.BIT_PLAYER, collidesWith);

        this.setBounds(150 - getWidth() / 2, 150 - getHeight() / 2, 16, 16);
        this.setRegion(frontStand);
    }

    private void makeAnimations()
    {
        frontStand = new TextureRegion(playerRegion,0, 0, 16, 16);
        backStand = new TextureRegion(playerRegion, 16, 0, 16, 16);
        sideStand = new TextureRegion(playerRegion, 32, 0, 16, 16);

        //TODO: MAYBE FIX THE AMOUNT OF REPEATED CODE HERE?
        //front walk
        Array<TextureRegion> frames = new Array<TextureRegion>(); //make sure to use the badlogic's Array
        TextureRegion otherSideWalk = new TextureRegion(playerRegion, 48, 0, 16, 16);
        otherSideWalk.flip(true, false);

        frames.add(new TextureRegion(playerRegion, 48, 0, 16, 16)); //for some reason if I don't do this, the flip doesn't work?
        frames.add(frontStand);
        frames.add(otherSideWalk);
        frames.add(frontStand);

        frontWalk = new Animation<>(PKMConstants.PLAYER_WALK_SPEED, frames);
        frames.clear();

        //back walk
        otherSideWalk = new TextureRegion(playerRegion, 64, 0, 16, 16);
        otherSideWalk.flip(true, false);

        frames.add(new TextureRegion(playerRegion, 64, 0, 16, 16));
        frames.add(backStand);
        frames.add(otherSideWalk);
        frames.add(backStand);

        backWalk = new Animation<>(PKMConstants.PLAYER_WALK_SPEED, frames);
        frames.clear();

        //left walk
        otherSideWalk = new TextureRegion(playerRegion, 80, 0, 16, 16);
        otherSideWalk.flip(true, false);
        frames.add(new TextureRegion(playerRegion, 80, 0, 16, 16));
        frames.add(sideStand);
        leftWalk = new Animation<>(PKMConstants.PLAYER_WALK_SPEED, frames);
        frames.clear();

        //right walk
        frames.add(otherSideWalk);
        frames.add(sideStand);
        rightWalk = new Animation<>(PKMConstants.PLAYER_WALK_SPEED, frames);
        frames.clear();
    }

    public void changeSprite(float dt)
    {
        switch(currentDirection)
        {
            case FRONT:
                if(currentState == State.STANDING)
                {
                    this.setRegion(frontStand);
                }
                else if(currentState == State.RUNNING)
                {
                    this.setRegion(frontWalk.getKeyFrame(stateTimer, true));
                }
                break;
            case BACK:
                if(currentState == State.STANDING)
                {
                    this.setRegion(backStand);
                }
                else if(currentState == State.RUNNING)
                {
                    this.setRegion(backWalk.getKeyFrame(stateTimer, true));
                }
                break;
            case LEFT:
                if(turnDirection)
                {
                    sideStand.flip(true, false);
                    facingRight = false;
                    turnDirection = false;
                }
                if(currentState == State.STANDING)
                {
                    this.setRegion(sideStand);
                }
                else if(currentState == State.RUNNING)
                {
                    this.setRegion(leftWalk.getKeyFrame(stateTimer, true));
                }
                break;
            case RIGHT:
                if(turnDirection)
                {
                    sideStand.flip(true, false);
                    turnDirection = false;
                    facingRight = true;
                }
                if(currentState == State.STANDING)
                {
                    this.setRegion(sideStand);
                }
                else if(currentState == State.RUNNING)
                {
                    this.setRegion(rightWalk.getKeyFrame(stateTimer, true));
                }
                break;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
    }

    public void update(float dt)
    {
        setPosition(box2Body.getPosition().x - getWidth() / 2, box2Body.getPosition().y - getHeight() / 2);
        changeSprite(dt);
    }

    public void move()
    {
        if(currentState != State.STANDING)
        {
            if(currentDirection.getAxis().equals("x"))
            {
                // this turns linear impulse into applyForce, there is a gradual change in the movement speed
                // see https://www.iforce2d.net/b2dtut/forces which is in C++ but you get the idea
                // switch statement here should work better
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

    public boolean isFacingRight()
    {
        return facingRight;
    }

    public void setTurnDirection(boolean turnDirection)
    {
        this.turnDirection = turnDirection;
    }

    public State getState()
    {
        return currentState;
    }

    public void setState(State currentState)
    {
        this.currentState = currentState;
    }

    public Direction getDirection()
    {
        return currentDirection;
    }

    public void setDirection(Direction currentDirection)
    {
        this.currentDirection = currentDirection;
    }

    public World getWorld() {
        return world;
    }
}