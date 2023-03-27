package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ItemTile.ItemTile;

public class PersonalGoalCardController {

    private ItemTile[][] card;
    private ItemTile[][] bookshelf;
    private static final int[] pointsMapping = {0, 1, 2, 3, 4, 6, 9, 12};

    // CONSTRUCTOR: card and bookshelf must have same dimensions
    public PersonalGoalCardController(ItemTile[][] card, ItemTile[][] bookshelf){
        this.card = card;
        this.bookshelf = bookshelf;
    }

    // returns number of matching tiles
    public int getMatchingTiles(){
        int matchingTiles = 0;
        for (int i=0; i<card.length; i++){
            for (int j = 0; j< card[i].length; j++){
                if (card[i][j].equals(bookshelf[i][j])) matchingTiles++;
            }
        }
        return matchingTiles;
    }

    // returns score
    public int getScore(){
        return pointsMapping[getMatchingTiles()];
    }




}
