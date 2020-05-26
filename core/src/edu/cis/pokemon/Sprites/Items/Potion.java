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
    private int health = 20;

    public Potion(GameScreen screen, float x, float y) {
        super(screen, x, y);
    }
}
