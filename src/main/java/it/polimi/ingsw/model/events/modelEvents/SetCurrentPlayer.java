package it.polimi.ingsw.model.events.modelEvents;

import it.polimi.ingsw.model.events.GameEvent;

public class SetCurrentPlayer extends GameEvent {
    public SetCurrentPlayer() {
        super("setCurrentPlayer");
    }
}
