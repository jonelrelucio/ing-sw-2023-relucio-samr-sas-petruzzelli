package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class WaitingForPlayersEvent extends GameEvent {

    private final boolean waiting;

    public WaitingForPlayersEvent(boolean waiting) {
        super("WAITING_PLAYERS");
        this.waiting = waiting;
    }

    public boolean isWaiting() { return waiting; }

}
