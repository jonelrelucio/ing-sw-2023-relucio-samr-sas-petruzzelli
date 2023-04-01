package it.polimi.ingsw.model.personalGoalCard;

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

    // fills card matrix with empty tiles
    public void initCard() {
        personalGoalMatrix = new ItemTile[ROW][COL];
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++ ){
                personalGoalMatrix[i][j] = new ItemTile(EMPTY);
            }
        }
    }

    // Builds a Personal card, given a random number from the bag
    public void buildPersonalGoalCard() {
        int key = bag.drawPersonalCardNum();
        String path = "json/PersonalGoalCoordinates";
        PersonalGoalCardCoordinates pgc = (PersonalGoalCardCoordinates) Utility.deserializeJsonToObject(path, PersonalGoalCardCoordinates.class);
        int[][] matrixCoordinates = pgc.getMatrixCoordinates(key);
        ItemTileType[] itemCoordinates = pgc.getItemCoordinates(key);
        updateItemsInCoordinates(matrixCoordinates, itemCoordinates);
    }

    // puts given items in given coordinates
    public void updateItemsInCoordinates(int[][] matrixCoordinates, ItemTileType[] itemTileType) {
        for (int i = 0; i < matrixCoordinates.length; i++) {
            int[] index = matrixCoordinates[i];
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemTileType[i]);
            }
        }
    }


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

