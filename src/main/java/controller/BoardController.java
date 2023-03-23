package controller;

import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardController {
    private ItemTile[][] boardMatrix;
    private ArrayList<int[]> selectedCoordinates;
    private ArrayList<int[]> canBeSelectedCoordinates;
    private ArrayList<ItemTile> selectedItemTiles = new ArrayList<>();

    public BoardController(ItemTile[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
        this.selectedCoordinates = new ArrayList<>();
        this.canBeSelectedCoordinates = new ArrayList<>();
        updateCanBeSelectedTiles();
    }
    public ArrayList<int[]> getSelectedCoordinates() {
        return selectedCoordinates;
    }
    public ArrayList<int[]> getCanBeSelectedCoordinates() {
        return canBeSelectedCoordinates;
    }
    public ItemTile[][] getBoardMatrix() { return boardMatrix; }

    // Checks if the tile in position (x,y) is adjacent to an empty tile
    public boolean isAdjacentEmpty(int x, int y)  {
        if (boardMatrix[x+1][y].isEmpty()) return true;
        else if (boardMatrix[x-1][y].isEmpty()) return true;
        else if (boardMatrix[x][y+1].isEmpty()) return true;
        else return boardMatrix[x][y - 1].isEmpty();
    }

    // Returns an ArrayList of all non-empty adjacent coordinates of given coordinates
    public ArrayList<int[]> getAdjacentCoordinates(int[] coordinates) {
        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            int x2 = x + direction[0];
            int y2 = y + direction[1];
            if (x2 >= 0 && x2 < boardMatrix.length && y2 >= 0 && y2 < boardMatrix[0].length) {
                if (!boardMatrix[x2][y2].isEmpty() && isAdjacentEmpty(x2, y2)) {
                    adjacentCoordinates.add(new int[] {x2, y2});
                }
            }
        }
        return adjacentCoordinates;
    }

    // Updates the Arraylist of the can be selected Tiles
    public void updateCanBeSelectedTiles(){
        if (selectedCoordinates.isEmpty()) {
            for (int i = 1; i < boardMatrix.length-1; i++) {
                for (int j = 1; j < boardMatrix[0].length- 1; j++) {
                    if (!boardMatrix[i][j].isEmpty() && isAdjacentEmpty(i, j))
                        canBeSelectedCoordinates.add(new int[]{i, j});
                }
            }
        } else if (selectedCoordinates.size() == 1 ) {
            canBeSelectedCoordinates = getAdjacentCoordinates(selectedCoordinates.get(0));
        } else if (selectedCoordinates.size() == 2) {
            canBeSelectedCoordinates = getCommonCoordinates(selectedCoordinates.get(0), selectedCoordinates.get(1));
        }
    }

    // returns the common coordinates given two arrays of coordinates
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

    // Adds the given coordinates in the ArrayList of selectedTiles and updates the canBeSelectedTiles Arraylist
    public void selectTile(int x, int y) {
        for (int[] tile : canBeSelectedCoordinates) {
            if (Arrays.equals(tile, new int[] {x, y})) {
                selectedCoordinates.add(new int[]{x, y});
                updateCanBeSelectedTiles();
                return;
            }
        }
        System.out.println("item in given coordinates can't be selected");
    }

    // Pops the given coordinates from the Arraylist of selectedTiles and updates the canBeSelectedTiles ArrayList
    public void popSelectedTile(int[] coordinates ){
        for (int i = 0; i < selectedCoordinates.size(); i++){
            if (coordinates[0] == selectedCoordinates.get(i)[0] && coordinates[1] == selectedCoordinates.get(i)[1]) {
                selectedCoordinates.remove(i);
                updateCanBeSelectedTiles();
                return;
            }
        }
        System.out.println("No coordinates in selectedTiles.");

    }

    // returns the ItemTiles from the coordinates in the arraylist selectedCoordinates
    public ArrayList<ItemTile> getSelectedItemTiles() {
        for (int[] indices : selectedCoordinates) {
            selectedItemTiles.add(boardMatrix[indices[0]][indices[1]]);
            boardMatrix[indices[0]][indices[1]] = new ItemTile(ItemTileType.EMPTY);
        }
        selectedCoordinates.clear();
        return selectedItemTiles;
    }




}
