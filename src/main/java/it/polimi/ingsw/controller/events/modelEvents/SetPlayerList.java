package it.polimi.ingsw.controller.events.modelEvents;

import it.polimi.ingsw.controller.events.GameEvent;

public class SetPlayerList extends GameEvent {
    public SetPlayerList() {
        super("setPlayerList");
    }
}
