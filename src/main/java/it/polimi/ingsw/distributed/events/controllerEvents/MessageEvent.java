package it.polimi.ingsw.distributed.events.controllerEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class MessageEvent extends GameEvent {
    private final Event eventType;
    private final String message;

    public MessageEvent(Event event, String message ){
        this.eventType = event;
        this.message = message;
    }

    public Event getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }


}
