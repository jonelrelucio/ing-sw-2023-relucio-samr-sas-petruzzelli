package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SetPlayerList extends GameEvent {
    public SetPlayerList() {
        super("setPlayerList");
    }
}
