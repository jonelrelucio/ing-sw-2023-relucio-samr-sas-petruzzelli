package it.polimi.ingsw.controller.events.modelEvents;

import it.polimi.ingsw.controller.events.GameEvent;

public class AddPlayer extends GameEvent {
    public AddPlayer() {
        super("addPlayer");
    }
}
