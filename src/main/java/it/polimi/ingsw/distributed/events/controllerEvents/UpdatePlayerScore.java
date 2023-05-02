package it.polimi.ingsw.distributed.events.controllerEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class UpdatePlayerScore extends GameEvent {
    public UpdatePlayerScore() {
        super("UPDATE_PLAYER_SCORE");
    }
}
