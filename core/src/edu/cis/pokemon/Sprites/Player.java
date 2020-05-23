package edu.cis.pokemon.Sprites;

import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Utils.PKMConstants;

public class Player extends Sprite
{
    public enum State {
        STANDING, WALKING
    }
    public enum Direction {
        FRONT, BACK, LEFT, RIGHT
    }
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


    }

    private void makeAnimations()
    {
        frontStand = new TextureRegion(playerRegion, 0, 0, 16, 16);
        backStand = new TextureRegion(playerRegion, 16, 0, 16, 16);
        sideStand = new TextureRegion(playerRegion, 32, 0, 16, 16);

//        Array<TextureRegion> frames = new Array<TextureRegion>(); //make sure to use the badlogic's Array
//        frames.add(new TextureRegion(playerRegion, i * 16, 0, 16, 16));
//        frontWalk = new Animation<>(0.1f, frames);
//        frames.clear();
    }
}
