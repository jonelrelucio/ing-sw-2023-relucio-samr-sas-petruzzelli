package it.polimi.ingsw.distributed.integrated.messages;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.io.Serializable;

/**
 * Update Message that extends the abstract class Message used for the socket connection
 * Used for sending messages of game model updates
 */
public class UpdateMessage extends Message implements Serializable {

    /**
     * The type of event for the View
     */
    private EventView eventView;

    /**
     * The game model view which is serializable
     */
    private GameModelView gameModelView;

    /**
     * Constructor
     * @param gameModelView gameModelView
     * @param eventView     eventView
     */
    public UpdateMessage(GameModelView gameModelView, EventView eventView){
        this.gameModelView = gameModelView;
        this.eventView = eventView;
    }

    /**
     * Getter for the EventView
     * @return  the EventView
     */
    public EventView getEventView() {
        return eventView;
    }

    /**
     * Getter for the GameModelView
     * @return  gameModeView
     */
    public GameModelView getGameModelView() {
        return gameModelView;
    }

}
