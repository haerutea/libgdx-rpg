package edu.cis.pokemon.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
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
    private boolean turnDirection;
    private boolean facingRight;

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
        turnDirection = false;

        playerRegion = screen.getAtlas().findRegion(PKMConstants.PLAYER_SPRITE);
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
                | PKMConstants.BIT_ITEM
                | PKMConstants.BIT_GRASS
                | PKMConstants.BIT_DOOR;
//        short collidesWith = PKMConstants.BIT_ITEM;

        FixtureDef fixtureDef = PKMUtils.createGameFixture(this, box2Body, PKMConstants.BIT_PLAYER, collidesWith);

        this.setBounds(150 - getWidth() / 2, 150 - getHeight() / 2, 16, 16);
        this.setRegion(frontStand);
    }

    private void makeAnimations()
    {
        frontStand = new TextureRegion(playerRegion, PKMConstants.X_OFFSET, PKMConstants.Y_OFFSET, 16, 16);
        backStand = new TextureRegion(playerRegion, 16 + PKMConstants.X_OFFSET, PKMConstants.Y_OFFSET, 16, 16);
        sideStand = new TextureRegion(playerRegion, 32 + PKMConstants.X_OFFSET, PKMConstants.Y_OFFSET, 16, 16);

//        Array<TextureRegion> frames = new Array<TextureRegion>(); //make sure to use the badlogic's Array
//        frames.add(new TextureRegion(playerRegion, i * 16, 0, 16, 16));
//        frontWalk = new Animation<>(0.1f, frames);
//        frames.clear();
    }

    public void changeSprite(Direction direction, State state)
    {
        currentDirection = direction;
        currentState = state;
        switch(direction)
        {
            case FRONT:
                this.setRegion(frontStand);
                break;
            case BACK:
               this.setRegion(backStand);
                break;
            case LEFT:
                if(turnDirection)
                {
                    sideStand.flip(true, false);
                    facingRight = false;
                    turnDirection = false;
                }
                this.setRegion(sideStand);
                break;
            case RIGHT:
                if(turnDirection)
                {
                    sideStand.flip(true, false);
                    turnDirection = false;
                    facingRight = true;
                }
                this.setRegion(sideStand);
                break;
        }
    }

    public void update(float dt)
    {
        setPosition(box2Body.getPosition().x - getWidth() / 2, box2Body.getPosition().y - getHeight() / 2);
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
}