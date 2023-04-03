package it.polimi.ingsw.model;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import it.polimi.ingsw.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.model.util.Utility;

import static it.polimi.ingsw.model.ItemTile.ItemTileType.*;

public class PersonalGoalCard {

    private static int ROW = 6, COL = 5;
    private ItemTile[][] personalGoalMatrix;
    private PersonalGoalCardBag bag;

    // CONSTRUCTOR
    public PersonalGoalCard(PersonalGoalCardBag bag) {
        this.bag = bag;
        initCard();
        buildPersonalGoalCard();
    }

    /**
     * Fills Personal goal card with empty item tiles
     */
    public void initCard() {
        personalGoalMatrix = new ItemTile[ROW][COL];
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++ ){
                personalGoalMatrix[i][j] = new ItemTile(EMPTY);
            }
        }
    }

    /**
     * Builds a personalGoalCard
     * the path string contains the json file path from the resources folder
     * deserializes given path to an PersonalGoalCardCoordinates object
     */
    public void buildPersonalGoalCard() {
        int key = bag.drawPersonalCardNum();
        String path = "json/PersonalGoalCoordinates";
        PersonalGoalCardCoordinates pgc = (PersonalGoalCardCoordinates) Utility.deserializeJsonToObject(path, PersonalGoalCardCoordinates.class);
        int[][] matrixCoordinates = pgc.getMatrixCoordinates(key);
        ItemTileType[] itemCoordinates = pgc.getItemCoordinates(key);
        updateItemsInCoordinates(matrixCoordinates, itemCoordinates);
    }

    /**
     *
     * @param matrixCoordinates coordinates which will be filled with itemTiles
     * @param itemTileType      the types of the itemTiles which will be used to fill the matrixCoordinates
     */
    private void updateItemsInCoordinates(int[][] matrixCoordinates, ItemTileType[] itemTileType) {
        for (int i = 0; i < matrixCoordinates.length; i++) {
            int[] index = matrixCoordinates[i];
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemTileType[i]);
            }
        }
    }

    /**
     *
     * @return  personalGoalMatrix which is a matrix of ItemTile
     */
    public ItemTile[][] getPersonalGoalCardMatrix() { return personalGoalMatrix; }

    // TODO: remove print personal goal
    public void printPersonalGoal() {
        for (ItemTile[] itemTiles : personalGoalMatrix) {
            for (int k = 0; k < personalGoalMatrix[0].length; k++) {
                System.out.printf("%10s", itemTiles[k].getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }


}

class PersonalGoalCardCoordinates {

    @SerializedName("coordinates")
    private int[][][] matrixCoordinates;

    @SerializedName("itemCoordinates")
    private ItemTileType[][] itemCoordinates;


    public int[][] getMatrixCoordinates(int key) {
        return matrixCoordinates[key-1];
    }
    public ItemTileType[] getItemCoordinates(int key) {
        return itemCoordinates[key-1];
    }
}

