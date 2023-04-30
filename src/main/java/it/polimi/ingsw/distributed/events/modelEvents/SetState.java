package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SetState extends GameEvent {
    public SetState() {
        super("setState");
    }
}
