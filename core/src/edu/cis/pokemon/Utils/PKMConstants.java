package edu.cis.pokemon.Utils;

public class PKMConstants {
    //View ratios
    public static final int V_HEIGHT = 9 * 16; //arbitrary numbers
    public static final int V_WIDTH = 11 * 16;

    //atlas file name
    public static final String ATLAS_FILENAME = "pokemon_sprites.atlas";
    //map file name
    public static final String MAP_FILENAME = "new_town.tmx";

    //map layer indexes
    public static final int ENVIRONMENT = 1;
    public static final int GRASS = 2;
    public static final int LEDGES = 3;
    public static final int DOORS = 4;
    public static final int SIGNS = 5;
    public static final int ITEMS = 6;
    public static final int TRAINERS = 7;

    //menu image file name
    public static final String MENU_FILENAME = "badlogic.jpg";
    //animation sprites
    public static final String PLAYER_SPRITE = "player";
    public static final String ITEM_SPRITE = "item";
    //x and y offset for sprites
    public static final int X_OFFSET = 0;
    public static final int Y_OFFSET = 0;

    //graphics
    public static final float FPS = 1/60f;
    public static final int V_ITER = 6;
    public static final int P_ITER = 2;

    //collision bits
    public static final short BIT_PLAYER = 1;
    public static final short BIT_IMPASSIBLE = 2;
    public static final short BIT_SIGN = 4;
    public static final short BIT_LEDGE = 8;
    public static final short BIT_GRASS = 16;
    public static final short BIT_ITEM = 32;
    public static final short BIT_DOOR = 64;
    public static final short BIT_TRAINER = 128;
    public static final short BIT_PLAYER_FACE = 256;
}
