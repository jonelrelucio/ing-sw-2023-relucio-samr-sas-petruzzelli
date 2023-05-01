package it.polimi.ingsw.distributed.events.modelEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class AddPlayer extends GameEvent {
    private final String username;

    public AddPlayer(String username) {
        super("ADD_PLAYER");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
