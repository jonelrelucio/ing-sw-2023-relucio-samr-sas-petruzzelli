package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SetNumOfRounds extends GameEvent {
    public SetNumOfRounds() {
        super("setNumOfRounds");
    }
}
