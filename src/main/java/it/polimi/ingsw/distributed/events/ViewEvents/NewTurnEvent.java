package it.polimi.ingsw.distributed.events.ViewEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class NewTurnEvent extends GameEvent {

    private String currentPlayer;

    public NewTurnEvent(String currentPlayer){
        super("NEW_TURN_EVENT");
        this.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
