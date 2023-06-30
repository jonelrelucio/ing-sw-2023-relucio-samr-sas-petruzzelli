package it.polimi.ingsw.server.model.bag;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.PersonalGoalCard;
import it.polimi.ingsw.server.model.util.Utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * Class the represents the personal goal card bag
 */
public abstract class PersonalGoalCardBag {

    /**
     * The sizes of Row and Columns of personal goal card
     */
    private static final int ROW = 6, COL = 5;

    /**
     * A stack of integers that represent the bag containing the ids of the personal goal cards
     */
    private static final Stack<Integer> personalGoalCardBag = new Stack<>();

    /**
     * the number of draws that has been done
     */
    private static int numOfDraws = 0;

    /**
     * resets the number of draws to 0
     * Initializes the personal goal card bag
     */
    public static void initPersonalGoalCardBag(int numOfPlayers){
        reset();
        Integer[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        personalGoalCardBag.addAll(Arrays.asList(cards));
        Collections.shuffle(personalGoalCardBag);
    }

    /**
     * If the number of draws == 0 or if the number of draws == number of players, initializes the bag
     * Then draws a random personalGoalCard from 1 to 12
     * @param numOfPlayers      number of players
     * @return                  random PersonGoalCard
     */
    public static PersonalGoalCard drawPersonalGoalCard(int numOfPlayers) {
        if (numOfDraws >= numOfPlayers ) throw new IllegalCallerException("Cant draw anymore cards");
        if (numOfDraws == 0) initPersonalGoalCardBag(numOfPlayers);
        numOfDraws++;
        return drawPersonalGoalCard(numOfPlayers, personalGoalCardBag.pop());
    }

    /**
     * Utility method
     * Builds a specific personal goal card given a key
     * @param key   represents the specific personal goal card
     * @return      returns the specific personal goal card
     */
    public static PersonalGoalCard drawPersonalGoalCard(int numOfPlayers, int key) {
        ItemTile[][] personalGoalMatrix = new ItemTile[ROW][COL];
        initCard(personalGoalMatrix);
        buildPersonalGoalCard(personalGoalMatrix, key);
        PersonalGoalCard psg = new PersonalGoalCard(personalGoalMatrix);
        psg.setPersonalGoalCardId(key);
        return psg;
    }

    /**
     * Builds a personalGoalCard
     * the path string contains the json file path from the resources folder
     * deserializes given path to an PersonalGoalCardCoordinates object
     * @param personalGoalMatrix ItemTile[][] personalGoalMatrix to be built
     */
    private static void buildPersonalGoalCard(ItemTile[][] personalGoalMatrix, int key) {
        String path = "/json/PersonalGoalCoordinates.json";
        PersonalGoalCardCoordinates pgc = (PersonalGoalCardCoordinates) Utility.deserializeJsonToObject(path, PersonalGoalCardCoordinates.class);
        int[][] matrixCoordinates = pgc.getMatrixCoordinates(key);
        ItemTileType[] itemCoordinates = pgc.getItemCoordinates(key);
        updateItemsInCoordinates(personalGoalMatrix, matrixCoordinates, itemCoordinates);

    }

    /**
     * Sets the personalGoalCardMatrix to EMPTY
     * @param personalGoalMatrix    ItemTile[][] personGoalMatrix to be initialized
     */
    private static void initCard(ItemTile[][] personalGoalMatrix) {
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++ ){
                personalGoalMatrix[i][j] = new ItemTile(ItemTileType.EMPTY);
            }
        }
    }

    /**
     * Updates the empty personalGoalMatrix with the given set of ItemTileType of the given set of coordinates
     * @param personalGoalMatrix    Empty ItemTile[][]
     * @param matrixCoordinates     coordinates which will be filled with itemTiles
     * @param itemTileType          the types of the itemTiles which will be used to fill the matrixCoordinates
     */
    private static void updateItemsInCoordinates(ItemTile[][] personalGoalMatrix, int[][] matrixCoordinates, ItemTileType[] itemTileType) {
        for (int i = 0; i < matrixCoordinates.length; i++) {
            int[] index = matrixCoordinates[i];
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemTileType[i]);
            }
        }
    }

    /**
     * Utility method resets the numOfDraws to 0
     */
    public static void reset() {
        numOfDraws = 0;
    }

    /**
     * Getter for the number of draws
     * @return  the number of draws that has been done
     */
    public static int getNumOfDraws() { return numOfDraws; }

}

/**
 * The coordinates of the personal goal cards used for deserialization from the json file from resources
 */
class PersonalGoalCardCoordinates {
    /**
     * a set of matrix coordinates with serialized name coordinates
     */
    @SerializedName("coordinates")
    private int[][][] matrixCoordinates;

    /**
     * a set of item coordinates with serialized name item coordinates
     */
    @SerializedName("itemCoordinates")
    private ItemTileType[][] itemCoordinates;

    /**
     * Getter for a specific matrix coordinates given a key
     * @param key   the key of a specific set of coordinates
     * @return      the matrix coordinates
     */
    public int[][] getMatrixCoordinates(int key) {
        return matrixCoordinates[key-1];
    }

    /**
     * Getter for a specific item coordinates, given a key
     * @param key   the key
     * @return      the item coordinates
     */
    public ItemTileType[] getItemCoordinates(int key) {
        return itemCoordinates[key-1];
    }
}

