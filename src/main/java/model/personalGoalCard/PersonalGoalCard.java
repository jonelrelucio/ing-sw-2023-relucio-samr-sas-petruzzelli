package model.personalGoalCard;

import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileType;

import java.util.*;

import static model.ItemTile.ItemTileType.*;

public class PersonalGoalCard {

    private static int ROW = 6, COL = 5;
    private static HashMap<Integer, Integer> pointsMapping;
    private ItemTile[][] personalGoalMatrix;


    public PersonalGoalCard() {
        buildPointsMapping();
        initBoard();
        buildPersonalGoalCard(PersonalGoalCardBag.getRandomPersonalCardNum());
    }
    public void initBoard() {
        personalGoalMatrix = new ItemTile[ROW][COL];
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++ ){
                personalGoalMatrix[i][j] = new ItemTile(EMPTY);
            }
        }
    }


    public void buildPersonalGoalCard(int key) {
        int[][] coordinates = PersonalGoalCardCoordinates.getCoordinates()[key-1];
        ItemTileType[] itemCoordinates = PersonalGoalCardCoordinates.getItemcoordinates()[key-1];

        for (int i = 0; i < coordinates.length; i++) {
            int[] index = coordinates[i];
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemCoordinates[i]);
            }
        }
    }


    public void setItemsInCoordinates(int[][] indices, ItemTileType itemTileType) {
        for (int[] index : indices) {
            if (personalGoalMatrix[index[0]][index[1]].isEmpty()) {
                personalGoalMatrix[index[0]][index[1]] = new ItemTile(itemTileType);
            }
        }
    }


    public void buildPointsMapping() {
        pointsMapping = new HashMap<>();
        pointsMapping.put(1, 1);
        pointsMapping.put(2, 2);
        pointsMapping.put(3, 4);
        pointsMapping.put(4, 6);
        pointsMapping.put(5, 9);
        pointsMapping.put(6, 12);
    }

    public ItemTile[][] getPersonalGoalCardMatrix() { return personalGoalMatrix; }

    public void printPersonalGoal() {
        for (ItemTile[] itemTiles : personalGoalMatrix) {
            for (int k = 0; k < personalGoalMatrix[0].length; k++) {
                System.out.printf("%10s", itemTiles[k].getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }

    public static void main(String[] args){
        PersonalGoalCard personalGoalCard = new PersonalGoalCard();
        System.out.print("\nPersonal Goal Card: \n");
        personalGoalCard.printPersonalGoal();

    }




}
