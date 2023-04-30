package it.polimi.ingsw.controller.events.modelEvents;

import it.polimi.ingsw.controller.events.GameEvent;

public class SetCurrentPlayer extends GameEvent {
    public SetCurrentPlayer() {
        super("setCurrentPlayer");
    }
}
