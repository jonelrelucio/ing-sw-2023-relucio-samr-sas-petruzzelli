package model;

import java.util.HashMap;

import static model.ItemTileType.*;

public class PersonalGoalCard {

    private static int ROW = 6, COL = 5, NUMOFCARDS = 12;
    private static HashMap<Integer, Integer> pointsMapping;
    private ItemTile[][] personalGoal;
    private HashMap<Integer, int[][]> coordinatesMapping;
    private HashMap<Integer, ItemTileType[]> itemCoordinatesMapping;

    private final int[][] COORDINATES1 = { {0,0}, {0,2}, {1,4}, {2,3}, {1,3}, {5,2} };
    private final int[][] COORDINATES2 = { {1,1}, {2,0}, {2,2}, {3,4}, {4,3}, {5,4} };
    private final int[][] COORDINATES3 = { {1,0}, {1,3}, {2,2}, {3,1}, {3,4}, {5,0} };
    private final int[][] COORDINATES4 = { {0,4}, {2,0}, {2,2}, {3,3}, {4,1}, {4,2} };
    private final int[][] COORDINATES5 = { {1,1}, {3,1}, {3,2}, {4,4}, {5,0}, {5,3} };
    private final int[][] COORDINATES6 = { {0,2}, {0,4}, {2,3}, {4,1}, {4,3}, {5,0} };
    private final int[][] COORDINATES7 = { {0,0}, {1,3}, {2,1}, {3,0}, {4,4}, {5,2} };
    private final int[][] COORDINATES8 = { {0,4}, {1,1}, {2,2}, {3,0}, {4,3}, {5,3} };
    private final int[][] COORDINATES9 = { {0,2}, {2,2}, {3,4}, {4,1}, {4,4}, {5,0} };
    private final int[][] COORDINATES10 = { {0,4}, {1,1}, {2,0}, {3,3}, {4,3}, {5,3} };
    private final int[][] COORDINATES11 = { {0,2}, {1,1}, {0,2}, {3,2}, {4,4}, {5,3} };
    private final int[][] COORDINATES12 = { {0,2}, {1,1}, {2,2}, {3,3}, {4,4}, {5,0} };

    private final ItemTileType[] ITEMCOORDINATES1 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES2 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES3 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES4 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES5 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES6 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES7 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES8 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES9 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES10 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES11 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};
    private final ItemTileType[] ITEMCOORDINATES12 = {PLANT, FRAME, CAT, BOOK, GAME, TROPHY};


    public void initCoordinatesMapping() {
        for (int i = 1; i <= NUMOFCARDS; i++) {
            coordinatesMapping.put(i, COORDINATES1);
        }
    }



    public PersonalGoalCard(int key) {
        createPointsMapping();
        initBoard();
        //TODO: buildPersonaGoalCard();
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


}
