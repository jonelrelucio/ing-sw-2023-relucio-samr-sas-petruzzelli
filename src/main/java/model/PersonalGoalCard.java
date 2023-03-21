package model;

import model.commonGoalCard.CommonGoalCard;

import java.util.*;

import static model.ItemTileType.*;

public class PersonalGoalCard {

    private static int ROW = 6, COL = 5;
    private static HashMap<Integer, Integer> pointsMapping;
    private ItemTile[][] personalGoalMatrix;


    public PersonalGoalCard() {
        createPointsMapping();
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
        int[][][] COORDINATES = {
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
        ItemTileType[][] ITEMCOORDINATES = {
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

        int[][] coordinates = COORDINATES[key-1];
        ItemTileType[] itemCoordinates = ITEMCOORDINATES[key-1];

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
    public void createPointsMapping() {
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
