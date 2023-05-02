package it.polimi.ingsw.distributed.events.controllerEvents;

import it.polimi.ingsw.distributed.events.GameEvent;

public class SelectCoordinatesEvent extends GameEvent {

    private int x, y;

    public SelectCoordinatesEvent(int x, int y) {
        super("SELECT_COORDINATES");
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
