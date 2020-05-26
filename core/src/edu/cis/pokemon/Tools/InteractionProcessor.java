package edu.cis.pokemon.Tools;

public class InteractionProcessor
{
    private Object collidedObject;

    public InteractionProcessor()
    {
        collidedObject = null;
    }

    //TODO: ADD PROCESS METHODS?


    public Object getCollidedObject()
    {
        return collidedObject;
    }

    public void setCollidedObject(Object collidedObject)
    {
        this.collidedObject = collidedObject;
    }
}
