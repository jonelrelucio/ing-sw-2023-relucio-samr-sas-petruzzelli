package it.polimi.ingsw.distributed.events;

public class SelectColumn extends GameEvent {
    private int col;

    public SelectColumn(int col) {
        super("selectColumn");
        this.col = col;
    }
}
