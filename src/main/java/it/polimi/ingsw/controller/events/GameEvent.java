package it.polimi.ingsw.controller.events;

public abstract class GameEvent {
    protected final String eventName;

    public GameEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
