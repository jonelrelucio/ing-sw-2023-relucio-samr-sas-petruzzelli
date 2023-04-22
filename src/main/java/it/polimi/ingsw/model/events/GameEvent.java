package it.polimi.ingsw.model.events;

public abstract class GameEvent {
    protected String eventName;

    public GameEvent(String eventName) {
        this.eventName = eventName;
    }
}
