package model;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static model.ItemTileType.*;

public class PersonalGoalCard {

    private static int ROW = 6, COL = 5;
    private static HashMap<Integer, Integer> pointsMapping;
    private ItemTile[][] personalGoal;
    private HashMap<Integer, int[][]> coordinatesMapping;
    private HashMap<Integer, ItemTileType[]> itemCoordinatesMapping;


    private static final int[][][] COORDINATES = {
            { {0,0}, {0,2}, {1,4}, {2,3}, {1,3}, {5,2} },
            { {1,1}, {2,0}, {2,2}, {3,4}, {4,3}, {5,4} },
            { {1,0}, {1,3}, {2,2}, {3,1}, {3,4}, {5,0} },
            { {0,4}, {2,0}, {2,2}, {3,3}, {4,1}, {4,2} },
            { {1,1}, {3,1}, {3,2}, {4,4}, {5,0}, {5,3} },
            { {0,2}, {0,4}, {2,3}, {4,1}, {4,3}, {5,0} },
            { {0,0}, {1,3}, {2,1}, {3,0}, {4,4}, {5,2} },
            { {0,4}, {1,1}, {2,2}, {3,0}, {4,3}, {5,3} },
            { {0,2}, {2,2}, {3,4}, {4,1}, {4,4}, {5,0} },
            { {0,4}, {1,1}, {2,0}, {3,3}, {4,3}, {5,3} },
            { {0,2}, {1,1}, {0,2}, {3,2}, {4,4}, {5,3} },
            { {0,2}, {1,1}, {2,2}, {3,3}, {4,4}, {5,0} }
    };

    private final ItemTileType[][] ITEMCOORDINATES = {
            {PLANT, FRAME, CAT, BOOK, GAME, TROPHY},
            {PLANT, CAT, GAME, BOOK, TROPHY, FRAME},
            {FRAME, GAME, PLANT, CAT, TROPHY, BOOK},
            {GAME, TROPHY, FRAME, PLANT, BOOK, CAT},
            {TROPHY, FRAME, BOOK, PLANT, GAME, CAT},
            {TROPHY, CAT, BOOK, GAME, FRAME, PLANT},
            {CAT, FRAME, PLANT, TROPHY, GAME, BOOK},
            {FRAME, CAT, TROPHY, PLANT, BOOK, GAME},
            {GAME, CAT, BOOK, TROPHY, PLANT, FRAME},
            {TROPHY, GAME, BOOK, CAT, FRAME, PLANT},
            {PLANT, BOOK, GAME, FRAME, CAT, TROPHY},
            {BOOK, PLANT, FRAME, TROPHY, GAME, CAT}
    };

    public void initCoordinatesMapping() {
        coordinatesMapping = new HashMap<>();
        for (int i = 0; i < COORDINATES.length; i++) {
            coordinatesMapping.put(i+1, COORDINATES[i]);
        }
    }
    public void initItemCoordinatesMapping() {
        itemCoordinatesMapping = new HashMap<>();
        for (int i = 0; i < ITEMCOORDINATES.length; i++) {
            itemCoordinatesMapping.put(i+1, ITEMCOORDINATES[i]);
        }
    }
    public void buildPersonalGoalCard(int key) {
        int[][] indices = coordinatesMapping.get(key);
        ItemTileType[] itemTileType = itemCoordinatesMapping.get(key);

        for (int i = 0; i < indices.length; i++) {
            int[] index = indices[i];
            if (personalGoal[index[0]][index[1]].isEmpty()) {
                personalGoal[index[0]][index[1]] = new ItemTile(itemTileType[i]);
            }
        }
    }
    public void setItemsInCoordinates(int[][] indices, ItemTileType itemTileType) {
        for (int[] index : indices) {
            if (personalGoal[index[0]][index[1]].isEmpty()) {
                personalGoal[index[0]][index[1]] = new ItemTile(itemTileType);
            }
        }
    }

    public PersonalGoalCard(int key) {
        createPointsMapping();
        initBoard();
        initCoordinatesMapping();
        initItemCoordinatesMapping();
        buildPersonalGoalCard(key);
    }

    public void createPointsMapping() {
        pointsMapping = new HashMap<>();
        pointsMapping.put(1, 1);
        pointsMapping.put(2, 2);
        pointsMapping.put(3, 4);
        pointsMapping.put(4, 6);
        pointsMapping.put(5, 9);
        pointsMapping.put(6, 12);
    }

    public void initBoard() {
        personalGoal = new ItemTile[ROW][COL];
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++ ){
                personalGoal[i][j] = new ItemTile(EMPTY);
            }
        }
    }

    public void printPersonalGoal() {
        for (ItemTile[] itemTiles : personalGoal) {
            for (int k = 0; k < personalGoal[0].length; k++) {
                System.out.printf("%10s", itemTiles[k].getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }



    public static void main(String[] args){
        Random r = new Random();
        int randomNumber = r.nextInt(12) + 1;
        System.out.println("\n\nChosen random number: "+randomNumber);
        System.out.println("Building card: PersonalGoalCard"+randomNumber);
        PersonalGoalCard personalGoalCard = new PersonalGoalCard(randomNumber);
        System.out.print("\nPersonal Goal Card: \n");
        personalGoalCard.printPersonalGoal();

    }


}
