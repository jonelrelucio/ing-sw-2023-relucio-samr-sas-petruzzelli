package it.polimi.ingsw.distributed.events.controllerEvents;


import java.io.Serializable;

/**
 * The class event that is sent from client to server
 */
public class MessageEvent implements Serializable {

    /**
     * An identifier that is used to serialize/deserialize an object of a Serializable class.
     */
    static final long serialVersionUID = 1L;

    /**
     * The type of event
     */
    private final EventController eventType;

    /**
     * the message attached to the event
     */
    private final String message;

    /**
     * Constructor for the Message Event
     * @param event     type of event
     * @param message   message as string
     */
    public MessageEvent(EventController event, String message ){
        this.eventType = event;
        this.message = message;
    }

    /**
     * gets the event type
     * @return  the eventy type
     */
    public EventController getEventType() {
        return eventType;
    }

    /**
     * gets the message
     * @return  the message
     */
    public String getMessage() {
        return message;
    }

}
