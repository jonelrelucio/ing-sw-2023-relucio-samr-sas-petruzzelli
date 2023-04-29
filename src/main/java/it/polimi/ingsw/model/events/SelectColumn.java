package it.polimi.ingsw.model.events;

public class SelectColumn extends GameEvent {
    private int col;

    public SelectColumn(int col) {
        super("selectColumn");
        this.col = col;
    }
}
