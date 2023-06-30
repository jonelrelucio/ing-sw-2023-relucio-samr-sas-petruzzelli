package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;

/**
 * The class that represents the personal goal card
 */
public class PersonalGoalCard {

    /**
     * Personal goal card matrix
     */
    private ItemTile[][] personalGoalCardMatrix;

    /**
     * personal goal card id
     */
    private int personalGoalCardId;

    /**
     * Constructor
     * @param personalGoalMatrix    personal goal matrix
     */
    public PersonalGoalCard(ItemTile[][] personalGoalMatrix){
        this.personalGoalCardMatrix = personalGoalMatrix;
    }

    /**
     * Gets the personal goal card matrix
     * @return the personal goal card matrix
     */
    public ItemTile[][] getPersonalGoalCardMatrix() { return personalGoalCardMatrix; }

    /**
     * Gets the personal goal card id
     * @return the personal goal card id
     */
    public int getPersonalGoalCardId() { return personalGoalCardId; }

    /**
     * Sets the personal goal card matrix
     * @param personalGoalCardMatrix    the personal goal card matrix
     */
    public void setPersonalGoalCardMatrix(ItemTile[][] personalGoalCardMatrix) { this.personalGoalCardMatrix = personalGoalCardMatrix; }

    /**
     * Sets the personal goal card id
     * @param id    the id used to set the personal goal card
     */
    public void setPersonalGoalCardId(int id) { this.personalGoalCardId = id; };

    /**
     * List of points
     */
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

