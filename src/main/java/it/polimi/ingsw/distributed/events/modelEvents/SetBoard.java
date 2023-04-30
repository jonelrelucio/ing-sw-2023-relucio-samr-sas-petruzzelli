package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SetBoard extends GameEvent {
    public SetBoard() {
        super("setBoard");
    }
}
