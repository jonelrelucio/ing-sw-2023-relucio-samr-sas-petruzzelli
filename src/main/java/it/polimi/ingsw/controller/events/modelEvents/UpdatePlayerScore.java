package it.polimi.ingsw.controller.events.modelEvents;

import it.polimi.ingsw.controller.events.GameEvent;

public class UpdatePlayerScore extends GameEvent {
    public UpdatePlayerScore() {
        super("UPDATE_PLAYER_SCORE");
    }
}
