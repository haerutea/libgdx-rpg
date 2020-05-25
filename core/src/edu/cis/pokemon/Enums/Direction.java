package edu.cis.pokemon.Enums;

public enum Direction
{
    FRONT(-1, "y"),
    BACK(1, "y"),
    LEFT(-1, "x"),
    RIGHT(1, "x");

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
