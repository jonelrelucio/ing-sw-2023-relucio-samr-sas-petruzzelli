package it.polimi.ingsw.model.events;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import java.util.ArrayList;

public class SelectTiles extends GameEvent {
    private ArrayList<ItemTile> tilesList;

    public SelectTiles(ArrayList<ItemTile> tilesList) {
        super("selectTiles");
        this.tilesList = tilesList;
    }
}