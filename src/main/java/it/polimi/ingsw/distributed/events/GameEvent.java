package it.polimi.ingsw.distributed.events;

import java.io.Serializable;

public abstract class GameEvent implements Serializable {
    static final long serialVersionUID = 1L;
    protected final String eventName;

    public GameEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
