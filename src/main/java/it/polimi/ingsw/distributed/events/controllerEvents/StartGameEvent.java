package it.polimi.ingsw.distributed.events.controllerEvents;
import it.polimi.ingsw.distributed.events.GameEvent;

public class StartGameEvent extends GameEvent {

    public StartGameEvent() {
        super("START_GAME");
    }

}
