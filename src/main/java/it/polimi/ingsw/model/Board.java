package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.model.bag.ItemTileBag;
import it.polimi.ingsw.model.util.Utility;

public class Board {

    private final int ROW = 11, COL = 11;
    private ItemTile[][] boardMatrix = new ItemTile[ROW][COL];
    private int numOfPlayers;
    private int[][] boardCoordinates;
    private final ItemTileBag bag;
    private ArrayList<int[]> selectedCoordinates;
    private ArrayList<int[]> canBeSelectedCoordinates;

    // CONSTRUCTOR
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

    // Getters
    public ItemTile[][] getBoardMatrix(){ return boardMatrix; }
    public ItemTile getMatrixTile(int x, int y) { return boardMatrix[x][y]; }
    public int getNumOfPlayers() {return numOfPlayers; }
    public ArrayList<int[]> getSelectedCoordinates() { return selectedCoordinates; }
    public ArrayList<int[]> getCanBeSelectedCoordinates() {return canBeSelectedCoordinates;}

    // Setters
    public void setBoardMatrix(ItemTile[][] boardMatrix ) {
        this.boardMatrix = boardMatrix;
    }
    public void setNumOfPlayers(int numOfPlayers) { this.numOfPlayers = numOfPlayers; }
    public void setMatrixTile(int x, int y, ItemTile itemTile) { boardMatrix[x][y] = itemTile; }
    public void setSelectedCoordinates(ArrayList<int[]> selectedCoordinates) {this.selectedCoordinates = selectedCoordinates; }
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
     * given a coordinate, returns true of it is adjacent to an empty tile
     * @param coordinates  coordinates in int[]
     * @return          true if the given position is adjacent to an empty tile
     */
    private boolean isAdjacentEmpty(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (boardMatrix[x+1][y].isEmpty()) return true;
        else if (boardMatrix[x-1][y].isEmpty()) return true;
        else if (boardMatrix[x][y+1].isEmpty()) return true;
        else return boardMatrix[x][y - 1].isEmpty();
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
        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
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
            for (int i = 1; i < boardMatrix.length-1; i++) {
                for (int j = 1; j < boardMatrix[0].length- 1; j++) {
                    if (!boardMatrix[i][j].isEmpty() && isAdjacentEmpty(new int[]{i, j}))
                        canBeSelectedCoordinates.add(new int[]{i, j});
                }
            }
        } else if (selectedCoordinates.size() == 1 ) {
            canBeSelectedCoordinates = getAdjacentCoordinates(selectedCoordinates.get(0));
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




    // TODO: Remove this method
    public void printBoard() {
        for (int j = 0; j < getBoardMatrix().length; j++) {
            for (int k = 0; k < getBoardMatrix()[0].length; k++) {
                System.out.printf("%10s", getMatrixTile(j,k).getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }


}

class BoardCoordinates {
    private Map<String, List<List<Integer>>> BoardCoordinatesMap;

    public Map<String, List<List<Integer>>> getBoardCoordinatesMap() {
        return BoardCoordinatesMap;
    }

    public void setBoardCoordinatesMap(Map<String, List<List<Integer>>> boardCoordinatesMap) {
        BoardCoordinatesMap = boardCoordinatesMap;
    }
}