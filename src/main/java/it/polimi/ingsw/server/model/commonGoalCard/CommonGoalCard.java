package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;

public interface CommonGoalCard {
    boolean checkPattern(ItemTile[][] bookshelf) ;
    int getId();
}
