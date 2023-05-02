package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class FullGameLobbyEvent extends GameEvent {

    private final boolean waitingToJoin;

    public FullGameLobbyEvent(boolean waitingToJoin){
        super("WAITING_TO_JOIN");
        this.waitingToJoin = waitingToJoin;
    }

    public boolean isWaitingToJoin() { return waitingToJoin; }
}
