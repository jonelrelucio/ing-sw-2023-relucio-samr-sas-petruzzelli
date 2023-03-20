package controller;

import model.Board;
import model.ItemTile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class BoardController {
    private final int ROW = 11, COL = 11;
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

    public boolean isAdjacentEmpty(int x, int y){
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
        if (!board.getBoardMatrix()[x-1][y].isEmpty()  && isAdjacentEmpty(x-1, y)){
            adjacentCoordinates.add(new int[] {x-1, y});
            if (!board.getBoardMatrix()[x-2][y].isEmpty()  && isAdjacentEmpty(x-2, y)) {
                adjacentCoordinates.add(new int[] {x-2, y});
            }
        }
        if (!board.getBoardMatrix()[x+1][y].isEmpty()  && isAdjacentEmpty(x+1, y)) {
            adjacentCoordinates.add(new int[] {x+1, y });
            if (!board.getBoardMatrix()[x-2][y].isEmpty()  && isAdjacentEmpty(x+2, y)) {
                adjacentCoordinates.add(new int[] {x+2, y});
            }
        }
        if (!board.getBoardMatrix()[x][y-1].isEmpty()  && isAdjacentEmpty(x, y-1)) {
            adjacentCoordinates.add(new int[] {x, y-1 });
            if (!board.getBoardMatrix()[x][y-2].isEmpty()  && isAdjacentEmpty(x, y-2) ) {
                adjacentCoordinates.add(new int[] {x, y-2});
            }
        }
        if (!board.getBoardMatrix()[x][y+1].isEmpty()  && isAdjacentEmpty(x, y+1)) {
            adjacentCoordinates.add(new int[] {x, y+1});
            if (!board.getBoardMatrix()[x][y+2].isEmpty()  && isAdjacentEmpty(x, y+2)) {
                adjacentCoordinates.add(new int[] {x, y+2});
            }
        }
            return adjacentCoordinates;
    }

    public void updateCanBeSelectedTiles(){
        if (selectedTile.isEmpty()) {
            for (int i = 1; i < ROW - 1; i++) {
                for (int j = 1; j < COL - 1; j++) {
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

    public void printCanBeSelectedTiles() {
        if (canBeSelectedTiles.isEmpty()) System.out.println("No more tiles can be selected.");
        for (int[] element : canBeSelectedTiles) {
            System.out.printf(Arrays.toString(element) +"  ");
        }
    }

    public void selectTile(int x, int y) {
        for (int[] tile : canBeSelectedTiles) {
            if (Arrays.equals(tile, new int[] {x, y})) {
                selectedTile.add(new int[]{x, y});
                return;
            }
        }
        System.out.println("Error to be handled.!");
    }

    public void popSelectedTile(int[] coordinates ){
        if (!selectedTile.isEmpty()) {
            selectedTile.remove(coordinates);
        }
        else System.out.println("Error to be handled");
    }
    public void printSelectedTile() {
        for (int[] element : selectedTile ){
            System.out.print(Arrays.toString(element) +"  ");
        }
    }




    /********************************************************************************************
     * Board creation testing
     **************************************************************/

    public static void main(String[] args ){
        Board board = new Board(2);
        BoardController bc = new BoardController(board);
        Scanner sc = new Scanner(System.in);

        System.out.println(" ");
        board.printBoard();
        for (int i = 0; i < 3; i++) {
            bc.updateCanBeSelectedTiles();
            System.out.print("\nCan be selected tiles at coordinates: ");
            bc.printCanBeSelectedTiles();
            System.out.print("\nSelect tile: ");
            String input = sc.nextLine();
            String[] parts = input.split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            bc.selectTile(a,b);
            System.out.print("SelectedTiles: ");
            bc.printSelectedTile();
        }
        System.out.print("\n\n");

        ArrayList<ItemTile> selectedItemTile = bc.board.getSelectedTile(bc.selectedTile); //parte importante gestita poi dal player
        System.out.println("Updated board: ");
        board.printBoard();


        sc.close();
    }



}
