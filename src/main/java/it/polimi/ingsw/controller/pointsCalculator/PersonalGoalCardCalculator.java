package it.polimi.ingsw.controller.pointsCalculator;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.personalGoalCard.PersonalGoalCard;

public class PersonalGoalCardCalculator {

    private static final int[] pointsMapping = {0, 1, 2, 3, 4, 6, 9, 12};

    /**
     * Returns the score which depends on the number of matching tiles
     * @param personalGoalCard  personalGoalCard
     * @param bookshelf         bookshelf
     * @return                  score depending on number of matching tiles and pointsMapping
     */
    public static int getScore(PersonalGoalCard personalGoalCard, Bookshelf bookshelf){
        ItemTile[][] cardMatrix = personalGoalCard.getPersonalGoalCardMatrix();
        ItemTile[][] bookshelfMatrix = bookshelf.getBookshelfMatrix();

        if (cardMatrix.length != bookshelfMatrix.length || cardMatrix[0].length != bookshelfMatrix[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");
        }
        int matchingTiles = getNumMatchingTiles(cardMatrix, bookshelfMatrix);
        return pointsMapping[matchingTiles];
    }


    /**
     * compares the two parameters and returns the number of matching tiles
     * @param cardMatrix        cardMatrix which is an ItemTile[][]
     * @param bookshelfMatrix   bookShelfMatrix which is an ItemTile[][]
     * @return                  number of matching tiles between cardMatrix and bookShelfMatrix
     */
    public static int getNumMatchingTiles(ItemTile[][] cardMatrix, ItemTile[][] bookshelfMatrix) {
        if (cardMatrix.length != bookshelfMatrix.length || cardMatrix[0].length != bookshelfMatrix[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");
        }
        int matchingTiles = 0;
        for (int i=0; i<cardMatrix.length; i++){
            for (int j = 0; j< cardMatrix[i].length; j++){
                if (cardMatrix[i][j].equals(bookshelfMatrix[i][j])) matchingTiles++;
            }
        }
        return matchingTiles;
    }


}
