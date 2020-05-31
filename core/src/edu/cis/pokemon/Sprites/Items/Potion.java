package edu.cis.pokemon.Sprites.Items;

public class Potion implements Consumable {
    private int health;

    public Potion() {
        health = 20;
    }

    @Override
    public void use() {

    }

    public int getHealth() {
        return health;
    }
}
