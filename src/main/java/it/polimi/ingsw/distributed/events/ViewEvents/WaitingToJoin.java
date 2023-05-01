package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class WaitingToJoin extends GameEvent {

    private final boolean waitingToJoin;

    public WaitingToJoin(boolean waitingToJoin){
        super("WAITING_TO_JOIN");
        this.waitingToJoin = waitingToJoin;
    }

    public boolean isWaitingToJoin() { return waitingToJoin; }
}
