package it.polimi.ingsw.distributed.events.controllerEvents;


import java.io.Serializable;

public class MessageEvent implements Serializable {

    static final long serialVersionUID = 1L;
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
