package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ItemTile.ItemTile;

public class PersonalGoalCardController {

    private static final int[] pointsMapping = {0, 1, 2, 3, 4, 6, 9, 12};

    /**
     * returns the number of matching tiles given the personal goal card
     * @param card          matrix of ItemTile
     * @param bookshelf     matrix of ItemTile
     * @return number of matching tiles
     */
    public int getScore(ItemTile[][] card, ItemTile[][] bookshelf){
        if (card.length != bookshelf.length || card[0].length != bookshelf[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");
        }
        int matchingTiles = 0;
        for (int i=0; i<card.length; i++){
            for (int j = 0; j< card[i].length; j++){
                if (card[i][j].equals(bookshelf[i][j])) matchingTiles++;
            }
        }
        return pointsMapping[matchingTiles];
    }
}
