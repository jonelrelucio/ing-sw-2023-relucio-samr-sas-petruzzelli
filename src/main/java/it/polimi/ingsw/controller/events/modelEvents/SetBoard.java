package it.polimi.ingsw.controller.events.modelEvents;

import it.polimi.ingsw.controller.events.GameEvent;

public class SetBoard extends GameEvent {
    public SetBoard() {
        super("setBoard");
    }
}
