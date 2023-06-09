package it.polimi.ingsw.distributed.events.controllerEvents;


import java.io.Serializable;

public class MessageEvent implements Serializable {

    static final long serialVersionUID = 1L;
    private final EventController eventType;
    private final String message;

    public MessageEvent(EventController event, String message ){
        this.eventType = event;
        this.message = message;
    }

    public EventController getEventType() {
        return eventType;
    }

    public String getMessage() {
        return message;
    }

}
