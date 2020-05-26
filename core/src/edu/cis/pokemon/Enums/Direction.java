package edu.cis.pokemon.Enums;

public enum Direction
{
    FRONT(-100, "y"),
    BACK(100, "y"),
    LEFT(-100, "x"),
    RIGHT(100, "x");

    int velocity;
    String axis;

    Direction(int v, String axis)
    {
        this.velocity = v;
        this.axis = axis;
    }

    public int getVelocity()
    {
        return velocity;
    }

    public String getAxis()
    {
        return axis;
    }
}
