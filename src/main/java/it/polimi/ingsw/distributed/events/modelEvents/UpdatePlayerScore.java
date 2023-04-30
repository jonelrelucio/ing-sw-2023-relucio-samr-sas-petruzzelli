package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class UpdatePlayerScore extends GameEvent {
    public UpdatePlayerScore() {
        super("UPDATE_PLAYER_SCORE");
    }
}
