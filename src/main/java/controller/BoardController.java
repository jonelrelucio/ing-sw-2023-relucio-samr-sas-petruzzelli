package controller;

import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardController {
    private ItemTile[][] boardMatrix;
    private ArrayList<int[]> selectedTile;
    private ArrayList<int[]> canBeSelectedTiles;
    private ArrayList<ItemTile> selectedItemTiles = new ArrayList<>();

    public BoardController(ItemTile[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
        this.selectedTile = new ArrayList<>();
        this.canBeSelectedTiles = new ArrayList<>();
        updateCanBeSelectedTiles();
    }
    public ArrayList<int[]> getSelectedTile() {
        return selectedTile;
    }
    public ArrayList<int[]> getCanBeSelectedTiles() {
        return canBeSelectedTiles;
    }
    public ItemTile[][] getBoardMatrix() { return boardMatrix; }

    public boolean isAdjacentEmpty(int x, int y)  {
        if (boardMatrix[x+1][y].isEmpty()) return true;
        else if (boardMatrix[x-1][y].isEmpty()) return true;
        else if (boardMatrix[x][y+1].isEmpty()) return true;
        else return boardMatrix[x][y - 1].isEmpty();
    }

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


    public void updateCanBeSelectedTiles(){
        if (selectedTile.isEmpty()) {
            for (int i = 1; i < boardMatrix.length-1; i++) {
                for (int j = 1; j < boardMatrix[0].length- 1; j++) {
                    if (!boardMatrix[i][j].isEmpty() && isAdjacentEmpty(i, j))
                        canBeSelectedTiles.add(new int[]{i, j});
                }
            }
        } else if (selectedTile.size() == 1 ) {
            canBeSelectedTiles = getAdjacentCoordinates(selectedTile.get(0));
        } else if (selectedTile.size() == 2) {
            canBeSelectedTiles = getTilesIntersection(selectedTile.get(0), selectedTile.get(1));
        }
    }

    public ArrayList<int[]> getTilesIntersection(int[] coordinate1, int[] coordinate2) {
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
        for (int[] tile : canBeSelectedTiles) {
            if (Arrays.equals(tile, new int[] {x, y})) {
                selectedTile.add(new int[]{x, y});
                updateCanBeSelectedTiles();
                return;
            }
        }
        System.out.println("item in given coordinates can't be selected");
    }

    public void popSelectedTile(int[] coordinates ){
        for (int i = 0; i < selectedTile.size(); i++){
            if (coordinates[0] == selectedTile.get(i)[0] && coordinates[1] == selectedTile.get(i)[1]) {
                selectedTile.remove(i);
                updateCanBeSelectedTiles();
                return;
            }
        }
        System.out.println("No coordinates in selectedTiles.");

    }

    public ArrayList<ItemTile> getSelectedItemTiles(ArrayList<int[]> coordinates) {
        for (int[] indices : coordinates) {
            selectedItemTiles.add(boardMatrix[indices[0]][indices[1]]);
            boardMatrix[indices[0]][indices[1]] = new ItemTile(ItemTileType.EMPTY);
        }
        return selectedItemTiles;
    }




}
