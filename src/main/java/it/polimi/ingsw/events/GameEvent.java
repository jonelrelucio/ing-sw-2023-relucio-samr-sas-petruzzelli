package it.polimi.ingsw.events;

public abstract class GameEvent{
    protected String name;

    public GameEvent(String name){
        this.name = name;
    }
}
