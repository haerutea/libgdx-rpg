package edu.cis.pokemon.Tools;

public class InteractionProcessor
{
    private static InteractionProcessor singleInstance;
    private Object collidedObject;

    private InteractionProcessor()
    {
        collidedObject = null;
    }

    public static InteractionProcessor getInstance() {
        if (singleInstance == null) {
            singleInstance = new InteractionProcessor();
        }
        return singleInstance;
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
