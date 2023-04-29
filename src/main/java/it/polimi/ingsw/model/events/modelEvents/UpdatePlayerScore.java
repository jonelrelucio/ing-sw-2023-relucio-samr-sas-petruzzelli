package it.polimi.ingsw.model.events.modelEvents;

import it.polimi.ingsw.model.events.GameEvent;

public class UpdatePlayerScore extends GameEvent {
    public UpdatePlayerScore() {
        super("updatePlayerScore");
    }
}
