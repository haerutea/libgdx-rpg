package edu.cis.pokemon.Utils;

public class PKMConstants {
    //View ratios
    public static final int V_HEIGHT = 9 * 16; //arbitrary numbers
    public static final int V_WIDTH = 11 * 16;

    //HUD sizes and positions
    public static final float LABEL_FONT_SCALE = 0.5f;
    public static final float BUTTON_FONT_SCALE = 2f;
    public static final float INFO_X = 0.85f;
    public static final float INFO_Y = 0.92f;

    public static final float MENU_Y = 0.80f;
    public static final float MENU_SCALE = 0.15f;


    //atlas file name
    public static final String SPRITES_ATLAS_FILENAME = "pokemon_sprites.atlas";
    public static final String BUTTON_ATLAS_FILENAME = "buttons.atlas";
    //map file name
    public static final String MAIN_MAP_FILENAME = "new_town.tmx";
    public static final String PLAYER_HOUSE_MAP_FILENAME = "player_house.tmx";
    public static final String HOUSE_MAP_FILENAME = "npc_house.tmx";
    public static final String LAB_MAP_FILENAME = "lab.tmx";


    //dialog
    public static final String STRING_FOUND_POTION = "you found a potion!";
    public static final String STRING_FOUND_POKEBALL = "you found a pokeball!";

    //map object properties
    //items
    public static final String PROPERTY_POTION = "potion";
    public static final String PROPERTY_POKEBALL = "pokeball";
    //doors
    public static final String PROPERTY_PLAYER_HOUSE = "player_house";
    public static final String PROPERTY_LAB = "lab";
    public static final String PROPERTY_HOUSE = "house";
    public static final String PROPERTY_ROUTE = "route";


    //world map layer indexes
    public static final int WORLD_ENVIRONMENT = 1;
    public static final int WORLD_GRASS = 2;
    public static final int WORLD_LEDGES = 3;
    public static final int WORLD_DOORS = 4;
    public static final int WORLD_SIGNS = 5;
    public static final int WORLD_ITEMS = 6;
    public static final int WORLD_TRAINERS = 7;

    //player_house map layer indexes
    public static final int PLAYER_HOUSE_ENVIRONMENT = 1;
    public static final int PLAYER_HOUSE_TV = 2;
    public static final int PLAYER_HOUSE_BED = 3;

    //house map layer indexes
    public static final int HOUSE_ENVIRONMENT = 1;
    public static final int HOUSE_TV = 2;
    public static final int HOUSE_BED = 3;

    //lab map layer indexes
    public static final int LAB_ENVIRONMENT = 1;

    //menu image file name
    public static final String MENU_FILENAME = "badlogic.jpg";

    //animation sprites
    public static final String PLAYER_SPRITE = "player";
    public static final String ITEM_SPRITE = "item";

    //walk animation frame duration
    public static final float PLAYER_WALK_SPEED = 0.23f;

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
