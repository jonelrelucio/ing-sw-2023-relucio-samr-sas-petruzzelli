package model.personalGoalCard;

import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileType;

import java.util.*;

import static model.ItemTile.ItemTileType.*;

public class PersonalGoalCard {

    private static int ROW = 6, COL = 5;
    private ItemTile[][] personalGoalMatrix;

    // CONSTRUCTOR
    public PersonalGoalCard() {
        initCard();
        buildPersonalGoalCard(PersonalGoalCardBag.getRandomPersonalCardNum());
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

    // fills card matrix with tiles according to number of players
    public void buildPersonalGoalCard(int key) {
        int[][] coordinates = PersonalGoalCardCoordinates.getCoordinates(key);
        ItemTileType[] itemCoordinates = PersonalGoalCardCoordinates.getItemCoordinates(key);

        for (int i = 0; i < coordinates.length; i++) {
            int[] index = coordinates[i];
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemCoordinates[i]);
            }
        }
    }

    // puts given items in given coordinates
    public void setItemsInCoordinates(int[][] indices, ItemTileType itemTileType) {
        for (int[] index : indices) {
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemTileType);
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
