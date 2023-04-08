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

    //moved from the controller
    private final ArrayList<int[]> selectedCoordinates;
    //moved from the controller
    private ArrayList<int[]> canBeSelectedCoordinates;

    private final ArrayList<ItemTile> selectedItemTiles = new ArrayList<>();

    // CONSTRUCTOR
    public Board(int numOfPlayers) {
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
    public ItemTile[][] getBoardMatrix() {
        return boardMatrix;
    }

    public ItemTile getMatrixTile(int x, int y) {
        return boardMatrix[x][y];
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public ArrayList<int[]> getSelectedCoordinates() {
        return selectedCoordinates;
    }

    public ArrayList<int[]> getCanBeSelectedCoordinates() {
        return canBeSelectedCoordinates;
    }

    // Setters
    public void setBoardMatrix(ItemTile[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public void setMatrixTile(int x, int y, ItemTile itemTile) {
        boardMatrix[x][y] = itemTile;
    }

    /**
     * Initializes the matrix of itemTile to empty type
     */
    public void initItemTileMatrix() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                setMatrixTile(i, j, new ItemTile(ItemTileType.EMPTY));
            }
        }
    }

    /**
     * Initializes the board given the number of players
     *
     * @param key the number of players
     */
    public void initBoardCoordinates(int key) {
        String PATH = "/json/BoardCoordinates.json";
        BoardCoordinates boardCoordinates = (BoardCoordinates) Utility.deserializeJsonToObject(PATH, BoardCoordinates.class);
        List<List<Integer>> list = boardCoordinates.getBoardCoordinatesMap().get(Integer.toString(key));
        this.boardCoordinates = Utility.convertListListToArrayArray(list);
    }

    /**
     * sets the items in given coordinates
     *
     * @param indices array of coordinates which empty tiles will be changed to an item tile drawn from the bag
     */
    private void setItemsInCoordinates(int[][] indices) {
        for (int[] index : indices) {
            if (boardMatrix[index[0]][index[1]].isEmpty()) boardMatrix[index[0]][index[1]] = bag.drawItemTile();
        }
    }

    // TODO: Remove this method
    public void printBoard() {
        for (int j = 0; j < getBoardMatrix().length; j++) {
            for (int k = 0; k < getBoardMatrix()[0].length; k++) {
                System.out.printf("%10s", getMatrixTile(j, k).getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }

    public boolean isAdjacentEmpty(int x, int y) {
        if (boardMatrix[x + 1][y].isEmpty()) return true;
        else if (boardMatrix[x - 1][y].isEmpty()) return true;
        else if (boardMatrix[x][y + 1].isEmpty()) return true;
        else return boardMatrix[x][y - 1].isEmpty();
    }

    public ArrayList<int[]> getAdjacentCoordinates(int[] coordinates) {
        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            int x2 = x + direction[0];
            int y2 = y + direction[1];
            if (x2 >= 0 && x2 < boardMatrix.length && y2 >= 0 && y2 < boardMatrix[0].length) {
                if (!boardMatrix[x2][y2].isEmpty() && isAdjacentEmpty(x2, y2)) {
                    adjacentCoordinates.add(new int[]{x2, y2});
                }
            }
        }
        return adjacentCoordinates;
    }

    public void updateCanBeSelectedCoordinates() {
        if (selectedCoordinates.isEmpty()) {
            for (int i = 1; i < boardMatrix.length - 1; i++) {
                for (int j = 1; j < boardMatrix[0].length - 1; j++) {
                    if (!boardMatrix[i][j].isEmpty() && isAdjacentEmpty(i, j))
                        canBeSelectedCoordinates.add(new int[]{i, j});
                }
            }
        } else if (selectedCoordinates.size() == 1) {
            canBeSelectedCoordinates = getAdjacentCoordinates(selectedCoordinates.get(0));
        } else if (selectedCoordinates.size() == 2) {
            canBeSelectedCoordinates = getCommonCoordinates(selectedCoordinates.get(0), selectedCoordinates.get(1));
        }

    }

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

    public void selectTile(int x, int y) {
        for (int[] tile : canBeSelectedCoordinates) {
            if (Arrays.equals(tile, new int[]{x, y})) {
                selectedCoordinates.add(new int[]{x, y});
                updateCanBeSelectedCoordinates();
                return;
            }
        }
        System.out.println("item in given coordinates can't be selected");
    }

    //maybe change the name method to removeSelectedTile
    public void popSelectedTile(int[] coordinates) {
        for (int i = 0; i < selectedCoordinates.size(); i++) {
            if (coordinates[0] == selectedCoordinates.get(i)[0] && coordinates[1] == selectedCoordinates.get(i)[1]) {
                selectedCoordinates.remove(i);
                updateCanBeSelectedCoordinates();
                return;
            }
        }
        System.out.println("No coordinates in selectedTiles.");

    }

    public ArrayList<ItemTile> getSelectedItemTiles() {
        for (int[] indices : selectedCoordinates) {
            selectedItemTiles.add(boardMatrix[indices[0]][indices[1]]);
            boardMatrix[indices[0]][indices[1]] = new ItemTile(ItemTileType.EMPTY);
        }
        selectedCoordinates.clear();
        return selectedItemTiles;
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

