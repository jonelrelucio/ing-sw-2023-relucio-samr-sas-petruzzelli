package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SetCurrentPlayer extends GameEvent {
    public SetCurrentPlayer() {
        super("setCurrentPlayer");
    }
}
