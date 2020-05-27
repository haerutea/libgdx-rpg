package edu.cis.pokemon.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import edu.cis.pokemon.Utils.PKMConstants;

public class WorldContactListener implements ContactListener
{

    private InteractionProcessor interactionProcessor;

    public WorldContactListener(InteractionProcessor interProcessor)
    {
        super();
        this.interactionProcessor = interProcessor;
    }


    @Override
    public void beginContact(Contact contact)
    {
        //figure out which fixture is which
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int collisionDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(collisionDef)
        {
            case PKMConstants.BIT_PLAYER | PKMConstants.BIT_ITEM:
                Gdx.app.log("item", "collided");
                if(fixA.getFilterData().categoryBits == PKMConstants.BIT_PLAYER) //if fixA == Player
                {
                    Gdx.app.log("item is", "fixB");
                    interactionProcessor.setCollidedObject(fixB.getUserData());
                }
                else
                {
                    Gdx.app.log("item is", "fixA");
                    interactionProcessor.setCollidedObject(fixA.getUserData());
                }
            case PKMConstants.BIT_PLAYER | PKMConstants.BIT_DOOR:
                Gdx.app.log("door", "collided");
                if(fixA.getFilterData().categoryBits == PKMConstants.BIT_PLAYER) //if fixA == Player
                {
                    Gdx.app.log("door is", "fixB");
                    interactionProcessor.setCollidedObject(fixB.getUserData());
                }
                else
                {
                    Gdx.app.log("door is", "fixA");
                    interactionProcessor.setCollidedObject(fixA.getUserData());
                }

        }
    }

    @Override
    public void endContact(Contact contact)
    {
        interactionProcessor.setCollidedObject(null);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
