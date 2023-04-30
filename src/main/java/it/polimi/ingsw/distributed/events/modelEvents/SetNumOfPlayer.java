package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SetNumOfPlayer extends GameEvent {
    public SetNumOfPlayer() {
        super("setNumOfPlayer");
    }
}
