package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class AddPlayer extends GameEvent {
    public AddPlayer() {
        super("addPlayer");
    }
}
