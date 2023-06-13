package it.polimi.ingsw.distributed.integrated.messages;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;

import java.io.Serializable;

public class UpdateMessage extends Message implements Serializable {
    //TODO: va a sostituire ClientUpdateMessage
    private EventView eventView;
    private GameModelView gameModelView;

    public UpdateMessage(GameModelView gameModelView, EventView eventView){
        this.gameModelView = gameModelView;
        this.eventView = eventView;
    }

    public EventView getEventView() {
        return eventView;
    }

    public GameModelView getGameModelView() {
        return gameModelView;
    }

    @Override
    public void processMessage() {

    }
}
