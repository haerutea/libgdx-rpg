package edu.cis.pokemon.Tools.Creators;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import edu.cis.pokemon.Pokemon;
import edu.cis.pokemon.Screens.GameScreen;
import edu.cis.pokemon.Sprites.Environment.Door;
import edu.cis.pokemon.Utils.PKMConstants;
import edu.cis.pokemon.Utils.PKMUtils;

public class GateCreator implements Creator {

    private Array<Door> exits;
    private Array<Body> allBodies;

    public GateCreator(GameScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bodyDef = new BodyDef();
        Body body = world.createBody(bodyDef);

        allBodies = new Array<>();
        allBodies.add(body);

        //impassible stuff like trees, buildings, etc
        allBodies.addAll(PKMUtils.createBody(screen, PKMConstants.GATE_ENVIRONMENT));

        //exits
        exits = new Array<>();
        exits.addAll(PKMUtils.createDoors(screen, PKMConstants.GATE_EXIT, exits));
        for(Door exit : exits) {
            allBodies.add(exit.getBox2Body());
        }

        //npc
    }

    @Override
    public void update(float deltaTime) {
        for(Door door : exits) {
            door.update(deltaTime);
        }
    }

    @Override
    public void draw(Pokemon game, GameScreen gameScreen) {
        for(Door door : exits) {
            door.draw(game.batch);

            String mapName = "";
            if (door.isInteracted()) {
                if (door.getProperties(PKMConstants.PROPERTY_TOWN)) {
                    mapName = PKMConstants.MAIN_MAP_FILENAME; //TODO: set area
                }
                else if(door.getProperties(PKMConstants.PROPERTY_ROUTE)) {
                    mapName = PKMConstants.MAIN_MAP_FILENAME;
                }
                door.setInteracted(false);

                if(!mapName.equals("")) {
                    gameScreen.setMapName(mapName);
                    gameScreen.restart(mapName);
                }
            }
        }
    }

    public Array<Door> getExits() {
        return exits;
    }

    @Override
    public void dispose() {
        for (Body body : allBodies) {
            Array<Fixture> fixtures = body.getFixtureList();
            for (Fixture fixture : fixtures) {
                body.destroyFixture(fixture);
            }
        }
    }
}
