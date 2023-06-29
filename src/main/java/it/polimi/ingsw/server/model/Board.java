package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.server.model.bag.ItemTileBag;
import it.polimi.ingsw.server.model.util.Utility;

/**
 * This class represents one single board
 */
public class Board {

    /**
     * These attributes represent the size of the board
     */
    private final int ROW = 11, COL = 11;
    /**
     * This attribute represent the matrix of the board in ItemTile
     */
    private ItemTile[][] boardMatrix = new ItemTile[ROW][COL];
    /**
     * This attribute represent the number of player using the board
     */
    private int numOfPlayers;
    /**
     * This attribute represent an array of coordinates used to initialize the board
     */
    private int[][] boardCoordinates;
    /**
     * This attribute represent a bag of ItemTiles used to initialize the board
     */
    private final ItemTileBag bag;
    /**
     * This attribute represent an arraylist of selected coordinates by the player
     */
    private ArrayList<int[]> selectedCoordinates;
    /**
     * This attribute represent an arraylist of coordinates that can be selected on the board by the player
     */
    private ArrayList<int[]> canBeSelectedCoordinates;

    /**
     * Initializes the board taking into consideration the number of players
     * @param numOfPlayers  number of players that use the board
     */
    public Board(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        this.bag = new ItemTileBag();
        this.selectedCoordinates = new ArrayList<>();
        this.canBeSelectedCoordinates = new ArrayList<>();
        initItemTileMatrix();
        initBoardCoordinates(numOfPlayers);
        setItemsInCoordinates(boardCoordinates);
        updateCanBeSelectedCoordinates();
    }

    /**
     * gets the matrix of the board
     * @return  the board matrix in ItemTile[][]
     */
    public ItemTile[][] getBoardMatrix(){ return boardMatrix; }

    /**
     * gets the item tile given the coordinates x an y as integers
     * @param x the row
     * @param y the column
     * @return  the item tile given row and column coordinate
     */
    public ItemTile getMatrixTile(int x, int y) { return boardMatrix[x][y]; }

    /**
     * gets the number of players that use the board
     * @return  number of players that use the board
     */
    public int getNumOfPlayers() {return numOfPlayers; }

    /**
     * gets the arraylist of selected coordinates by the player
     * @return  arraylist of selected coordinates by the player
     */
    public ArrayList<int[]> getSelectedCoordinates() { return selectedCoordinates; }

    /**
     * gets the arraylist of coordinates that can be selected by the player
     * @return  arraylist of coordinates that can be selected by the player
     */
    public ArrayList<int[]> getCanBeSelectedCoordinates() {return canBeSelectedCoordinates;}

    /**
     * gets the matrix of the board of ItemTiles
     * @param boardMatrix   the matrix of the board made of Item Tiles to be used to set the board
     */
    public void setBoardMatrix(ItemTile[][] boardMatrix ) { this.boardMatrix = boardMatrix;}

    /**
     * sets the number of players that use the board
     * @param numOfPlayers  the number of players
     */
    public void setNumOfPlayers(int numOfPlayers) { this.numOfPlayers = numOfPlayers; }

    /**
     * Sets the Item Tile in the given coordinate x and y
     * @param x     Row of the matrix
     * @param y     Column of the matrix
     * @param itemTile  Item tile to be set
     */
    public void setMatrixTile(int x, int y, ItemTile itemTile) { boardMatrix[x][y] = itemTile; }

    /**
     * sets the arraylist of selected coordinates by the player
     * @param selectedCoordinates   arraylist of selected coordinates by the player
     */
    public void setSelectedCoordinates(ArrayList<int[]> selectedCoordinates) {this.selectedCoordinates = selectedCoordinates; }

    /**
     * sets the arraylist of coordinates that can be selected by the player
     * @param canBeSelectedCoordinates  arraylist of can be selected coordinates
     */
    public void setCanBeSelectedCoordinates(ArrayList<int[]> canBeSelectedCoordinates) {this.canBeSelectedCoordinates = canBeSelectedCoordinates; }

    /**
     * Initializes the matrix of itemTile to empty type
     */
    public void initItemTileMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                setMatrixTile(i, j, new ItemTile(ItemTileType.EMPTY));
            }
        }
    }

    /**
     * Initializes the board given the number of players
     * @param key   the number of players
     */
    public void initBoardCoordinates(int key) {
        String PATH = "/json/BoardCoordinates.json";
        BoardCoordinates boardCoordinates = (BoardCoordinates) Utility.deserializeJsonToObject(PATH, BoardCoordinates.class );
        List<List<Integer>> list = boardCoordinates.getBoardCoordinatesMap().get(Integer.toString(key));
        this.boardCoordinates = Utility.convertListListToArrayArray(list);
    }

    /**
     * sets the items in given coordinates
     * @param indices   array of coordinates which empty tiles will be changed to an item tile drawn from the bag
     */
    private void setItemsInCoordinates(int[][] indices) {
        for (int[] index : indices) {
            if (boardMatrix[index[0]][index[1]].isEmpty()) boardMatrix[index[0]][index[1]] = bag.drawItemTile();
        }
    }

    /**
     * given a coordinate, returns true of it has an adjacent empty tile
     * @param coordinates  coordinates in int[]
     * @return              true if the given position is adjacent to an empty tile
     */
    private boolean isAdjacentEmpty(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (boardMatrix[x+1][y].isEmpty()) return true;
        else if (boardMatrix[x-1][y].isEmpty()) return true;
        else if (boardMatrix[x][y+1].isEmpty()) return true;
        else return boardMatrix[x][y - 1].isEmpty();
    }

    private boolean isAdjacent(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (!boardMatrix[x+1][y].isEmpty()) return true;
        else if (!boardMatrix[x-1][y].isEmpty()) return true;
        else if (!boardMatrix[x][y+1].isEmpty()) return true;
        else return !boardMatrix[x][y - 1].isEmpty();
    }

    /**
     * Returns an ArrayList of all non-empty adjacent coordinates of given coordinates
     * @param coordinates   coordinates in int[]
     * @return              ArrayList of Int[] of all the non-empty coordinates of given coordinates
     */
    private ArrayList<int[]> getAdjacentCoordinates(int[] coordinates) {
        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] directions = new int[][] {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
        for ( int[] direction : directions ){
            int x2 = x + direction[0];
            int y2 = y + direction[1];

            if (x2 >= 0 && x2 < boardMatrix.length && y2 >= 0 && y2 < boardMatrix[0].length) {
                if (!boardMatrix[x2][y2].isEmpty() && isAdjacentEmpty(new int[]{x2, y2})) {
                    adjacentCoordinates.add(new int[] {x2, y2});
                }
            }
        }

        return adjacentCoordinates;
    }
    /**
     * Returns an ArrayList of all non-empty adjacent coordinates of given coordinates
     * @param coordinates   coordinates in int[]
     * @return              ArrayList of Int[] of all the non-empty coordinates of given coordinates
     */
    private ArrayList<int[]> getAdjacentCoordinates2(int[] coordinates) {
        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] directions = new int[][] {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
        for ( int[] direction : directions ){
            int x2 = x + direction[0];
            int y2 = y + direction[1];

            if (x2 >= 0 && x2 < boardMatrix.length && y2 >= 0 && y2 < boardMatrix[0].length) {
                if (!boardMatrix[x2][y2].isEmpty() && isAdjacentEmpty(new int[]{x2, y2})) {
                    adjacentCoordinates.add(new int[] {x2, y2});
                }
            }
        }

        return adjacentCoordinates;
    }

    /**
     * Updates the Arraylist of the can be selected Tiles
     */
    public void updateCanBeSelectedCoordinates(){
        if (selectedCoordinates.isEmpty()) {
            canBeSelectedCoordinates = new ArrayList<>();
            for (int i = 1; i < boardMatrix.length-1; i++) {
                for (int j = 1; j < boardMatrix[0].length- 1; j++) {
                    if (!boardMatrix[i][j].isEmpty() && isAdjacentEmpty(new int[]{i, j}))
                        canBeSelectedCoordinates.add(new int[]{i, j});
                }
            }
        } else if (selectedCoordinates.size() == 1 ) {
            canBeSelectedCoordinates = getAdjacentCoordinates2(selectedCoordinates.get(0));
        } else if (selectedCoordinates.size() == 2) {
            canBeSelectedCoordinates = getCommonCoordinates(selectedCoordinates.get(0), selectedCoordinates.get(1));
        }
    }

    /**
     * returns the common adjacent non-empty coordinates of two arrays of coordinates
     * @param coordinate1   first coordinates int[]
     * @param coordinate2   second coordinates int[]
     * @return              ArrayList of the common adjacent non-empty coordinates of the first and second coordinates
     */
    public ArrayList<int[]> getCommonCoordinates(int[] coordinate1, int[] coordinate2) {
        ArrayList<int[]> canBeSelected1 = getAdjacentCoordinates(coordinate1);
        ArrayList<int[]> canBeSelected2 = getAdjacentCoordinates(coordinate2);
        ArrayList<int[]> intersection = new ArrayList<>();
        for (int[] tile1 : canBeSelected1) {
            for (int[] tile2 : canBeSelected2) {
                if (Arrays.equals(tile1, tile2)) {
                    intersection.add(new int[]{tile1[0], tile1[1]});
                }
            }
        }
        return intersection;
    }

    public boolean checkRefill() {
        for (int i = 1; i < boardMatrix.length-1; i++) {
            for (int j = 1; j < boardMatrix[0].length- 1; j++) {
                if (!boardMatrix[i][j].isEmpty() && isAdjacent(new int[]{i, j}))
                    return false;
            }
        }
        return true;
    }

    public void refill() {
        setItemsInCoordinates(boardCoordinates);
        updateCanBeSelectedCoordinates();
    }

}


/**
 * This class is used to deserialize board coordinates from json file
 */
class BoardCoordinates {
    /**
     * A map with a key string associated to a list of list of integer value
     */
    private Map<String, List<List<Integer>>> BoardCoordinatesMap;

    /**
     * @return  the boardCoordinates as a map
     */
    public Map<String, List<List<Integer>>> getBoardCoordinatesMap() {
        return BoardCoordinatesMap;
    }

}


// TODO fix the column when full