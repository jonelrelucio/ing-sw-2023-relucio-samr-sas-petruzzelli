package it.polimi.ingsw.events;

public abstract class GameEvent{
    protected String eventName;

    public GameEvent(String eventName){
        this.eventName = eventName;
    }

    public String getEventName(){
        return eventName;
    }




}
