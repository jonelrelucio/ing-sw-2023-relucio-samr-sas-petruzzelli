package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;

/**
 * This interface represents a common goal card
 * and declare the method used to check if the goal is reached by a player
 */
public interface CommonGoalCard {
    /**
     * Declaration of the method that check if a player has reached the goal
     * @param bookshelf
     * @return true if the goal is reached
     */
    boolean checkPattern(ItemTile[][] bookshelf) ;

    /**
     * Getter for the card's id
     * @return the id as int
     */
    int getId();
}
