package it.polimi.ingsw.controller.pointsCalculator;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.Player;

public class PersonalGoalCardCalculator {

    private static final int[] pointsMapping = {0, 1, 2, 3, 4, 6, 9, 12};

    /**
     * Returns the score which depends on the number of matching tiles
     * @param player            given the player, it gets the bookshelf and the PersonalGoalCard
     * @return                  score depending on number of matching tiles and pointsMapping
     */
    public static int getScore(Player player){
        ItemTile[][] personalGoalCardMatrix = player.getPersonalGoalCard().getPersonalGoalCardMatrix();
        ItemTile[][] bookshelfMatrix = player.getBookshelf().getBookshelfMatrix();
        if(personalGoalCardMatrix == null) throw new NullPointerException("Player has no personalGoalCardMatrix or no bookshelfMatrix ");
        if (personalGoalCardMatrix.length != bookshelfMatrix.length || personalGoalCardMatrix[0].length != bookshelfMatrix[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");
        }
        int matchingTiles = getNumMatchingTiles(personalGoalCardMatrix, bookshelfMatrix);
        return pointsMapping[matchingTiles];
    }


    /**
     * compares the two parameters and returns the number of matching tiles
     * @param personalGoalCardMatrix        personalGoalCardMatrix which is an ItemTile[][]
     * @param bookshelfMatrix   bookShelfMatrix which is an ItemTile[][]
     * @return                  number of matching tiles between personalGoalCardMatrix and bookShelfMatrix
     */
    public static int getNumMatchingTiles(ItemTile[][] personalGoalCardMatrix, ItemTile[][] bookshelfMatrix) {
        if (personalGoalCardMatrix.length != bookshelfMatrix.length || personalGoalCardMatrix[0].length != bookshelfMatrix[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");
        }
        int matchingTiles = 0;
        for (int i=0; i<personalGoalCardMatrix.length; i++){
            for (int j = 0; j< personalGoalCardMatrix[i].length; j++){
                if (personalGoalCardMatrix[i][j].equals(bookshelfMatrix[i][j]) && !personalGoalCardMatrix[i][j].isEmpty()) matchingTiles++;
            }
        }
        return matchingTiles;
    }


}
