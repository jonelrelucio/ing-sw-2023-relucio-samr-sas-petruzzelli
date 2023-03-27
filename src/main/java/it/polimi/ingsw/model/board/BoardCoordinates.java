package it.polimi.ingsw.model.board;

public class BoardCoordinates {

    private static final int[][][] COORDINATES = {
            {    // Coordinates for 2 players
                    {2, 4}, {2, 5},
                    {3, 4}, {3, 5}, {3, 6},
                    {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
                    {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8},
                    {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7},
                    {7, 4}, {7, 5}, {7, 6},
                    {8, 5}, {8, 6}
            },
            {   //coordinates for 3 Players
                    {1, 4},
                    {2, 4}, {2, 5},
                    {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7},
                    {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8}, {4, 9},
                    {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8},
                    {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7},
                    {7, 3}, {7, 4}, {7, 5}, {7, 6}, {7, 7},
                    {8, 5}, {8, 6},
                    {9, 6}
            },
            {   // Coordinates for 4 players
                    {1, 5},
                    {2, 4}, {2, 5}, {2, 6},
                    {3, 4}, {3, 5}, {3, 6},
                    {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
                    {5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8}, {5, 9},
                    {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 8},
                    {7, 4}, {7, 5}, {7, 6},
                    {8, 4}, {8, 5}, {8, 6},
                    {9, 4}
            }
    };

    public static int[][] getCoordinates(int key ){
        return COORDINATES[key-2];
    }


}
