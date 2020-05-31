package edu.cis.pokemon.Screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import edu.cis.pokemon.Scenes.Hud;

public interface AbstractScreen {
    public Hud getHud();

    public TiledMap getMap();

    public World getWorld();

    public TextureAtlas getAtlas();
}
