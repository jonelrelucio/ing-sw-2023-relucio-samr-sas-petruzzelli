package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class WaitingForPlayersEvent extends GameEvent {

    private final boolean isFirstPlayer;
    private final int remainingPlayers;
    private final String connectedPlayer;


    public WaitingForPlayersEvent( boolean isFirstPlayer, int remainingPlayers, String connectedPlayer) {
        super("WAITING_PLAYERS");
        this.isFirstPlayer = isFirstPlayer;
        this.remainingPlayers = remainingPlayers;
        this.connectedPlayer = connectedPlayer;
    }

    public boolean isFirstPlayer() {return isFirstPlayer;}

    public int remainingPlayers() { return remainingPlayers; }

    public String getConnectedPlayer() { return connectedPlayer; }

}
