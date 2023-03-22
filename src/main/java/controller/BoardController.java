package controller;

import model.Board;
import model.ItemTile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class BoardController {
    private Board board;
    private ArrayList<int[]> selectedTile;
    private ArrayList<int[]> canBeSelectedTiles;

    public BoardController(Board board) {
        setBoard(board);
        setSelectedTile();
        setCanBeSelectedTiles();
    }

    public void setBoard(Board board) { this.board = board; }
    public Board getBoard() { return board; }
    public void setSelectedTile() { selectedTile = new ArrayList<>(); }
    public ArrayList<int[]> getSelectedTile() { return selectedTile; }
    public void setCanBeSelectedTiles() { canBeSelectedTiles = new ArrayList<>(); }
    public ArrayList<int[]> getCanBeSelectedTiles() { return canBeSelectedTiles; }

    public boolean isAdjacentEmpty(int x, int y)  {
        if (board.getBoardMatrix()[x+1][y].isEmpty()) return true;
        else if (board.getBoardMatrix()[x-1][y].isEmpty()) return true;
        else if (board.getBoardMatrix()[x][y+1].isEmpty()) return true;
        else if (board.getBoardMatrix()[x][y-1].isEmpty()) return true;
        else return false;
    }

    public ArrayList<int[]> getAdjacentCoordinates(int[] coordinates) {
        ArrayList<int[]> adjacentCoordinates = new ArrayList<>();
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            int x2 = x + direction[0];
            int y2 = y + direction[1];
            if (x2 >= 0 && x2 < board.getBoardMatrix().length && y2 >= 0 && y2 < board.getBoardMatrix()[0].length) {
                if (!board.getBoardMatrix()[x2][y2].isEmpty() && isAdjacentEmpty(x2, y2)) {
                    adjacentCoordinates.add(new int[] {x2, y2});
                }
            }
        }
        return adjacentCoordinates;
    }


    public void updateCanBeSelectedTiles(){
        if (selectedTile.isEmpty()) {
            for (int i = 1; i < board.getBoardMatrix().length-1; i++) {
                for (int j = 1; j < board.getBoardMatrix()[0].length- 1; j++) {
                    if (!board.getBoardMatrix()[i][j].isEmpty() && isAdjacentEmpty(i, j))
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




//    public void printCanBeSelectedTiles() {
//        updateCanBeSelectedTiles();
//        if (canBeSelectedTiles.isEmpty()) System.out.print("No more tiles can be selected.");
//        for (int[] element : canBeSelectedTiles) {
//            System.out.printf(Arrays.toString(element) +"  ");
//        }
//        System.out.println(" ");
//    }
//    public void printSelectedTile() {
//        for (int[] element : selectedTile ){
//            System.out.print(Arrays.toString(element) +"  ");
//        }
//    }


}
