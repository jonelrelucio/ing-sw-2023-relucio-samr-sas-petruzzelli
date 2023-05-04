package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;

public class PersonalGoalCard {

    private ItemTile[][] personalGoalCardMatrix;

    public PersonalGoalCard(ItemTile[][] personalGoalMatrix){
        this.personalGoalCardMatrix = personalGoalMatrix;
    }

    public ItemTile[][] getPersonalGoalCardMatrix() { return personalGoalCardMatrix; }
    public void setPersonalGoalCardMatrix(ItemTile[][] personalGoalCardMatrix) { this.personalGoalCardMatrix = personalGoalCardMatrix; }
    private static final int[] pointsMapping = {0, 1, 2, 3, 4, 6, 9, 12};

    /**
     * Returns the score which depends on the number of matching tiles
     * @return                  score depending on number of matching tiles and pointsMapping
     */
    public int getScore(ItemTile[][] bookshelfMatrix){
        if(personalGoalCardMatrix == null) throw new NullPointerException("Player has no personalGoalCardMatrix or no bookshelfMatrix ");
        if (personalGoalCardMatrix.length != bookshelfMatrix.length || personalGoalCardMatrix[0].length != bookshelfMatrix[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");}
        int matchingTiles = getNumMatchingTiles( bookshelfMatrix);
        return pointsMapping[matchingTiles];
    }

    /**
     * compares the two parameters and returns the number of matching tiles
     * @param bookshelfMatrix   bookShelfMatrix which is an ItemTile[][]
     * @return                  number of matching tiles between personalGoalCardMatrix and bookShelfMatrix
     */
    public int getNumMatchingTiles(ItemTile[][] bookshelfMatrix) {
        if (personalGoalCardMatrix.length != bookshelfMatrix.length || personalGoalCardMatrix[0].length != bookshelfMatrix[0].length) {
            throw new IllegalArgumentException("Card and bookshelf matrices must have the same dimensions.");}
        int matchingTiles = 0;
        for (int i=0; i<personalGoalCardMatrix.length; i++){
            for (int j = 0; j< personalGoalCardMatrix[i].length; j++){
                if (personalGoalCardMatrix[i][j].equals(bookshelfMatrix[i][j]) && !personalGoalCardMatrix[i][j].isEmpty()) matchingTiles++;
            }
        }
        return matchingTiles;
    }
}

