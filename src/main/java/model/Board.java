package model;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Board {

    private final int ROW = 11, COL = 11;
    private ItemTile[][] itemTileMatrix = new ItemTile[ROW][COL];
    private Bag bag;
    private int numOfPlayers;
    private Stack<int[]> selectedTile;
    private Stack<int[]> canBeSelectedTiles;

    private final int[][] COORDINATES = {
            {2, 4}, {2, 5},
            {3, 4}, {3, 5}, {3, 6},
            {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
            {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8},
            {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7},
            {7, 4}, {7, 5}, {7, 6},
            {8, 5}, {8, 6}
    };
    private final int[][] COORDINATES3PLAYERS = {
            {1, 4}, {3, 3}, {3, 7}, {4, 9}, {6, 1}, {7, 3}, {7, 7}, {9, 6}
    };
    private final int[][] COORDINATES4PLAYERS = {
            {1, 5}, {2, 6}, {4, 2}, {4, 2}, {5, 1}, {5, 9}, {6, 8}, {8, 4}, {9, 4}
    };

    public void setItemTileMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                itemTileMatrix[i][j] = new ItemTile();
            }
        }
    }
    public ItemTile[][] getItemTileMatrix(){ return itemTileMatrix; }
    public void setBag() { bag = new Bag(); }
    public Bag getBag() { return bag; }
    public void setNumOfPlayers(int numOfPlayers) { this.numOfPlayers = numOfPlayers; }
    public int getNumOfPlayers() {return numOfPlayers; }
    public void setCanBeSelectedTile() { canBeSelectedTiles = new Stack<>(); }
    public void setSelectedTile() { selectedTile = new Stack<>(); }

    public Board(int numOfPlayers){
        setNumOfPlayers(numOfPlayers);
        setBag();
        setItemTileMatrix();
        setSelectedTile();
        setCanBeSelectedTile();
        fillBoard(COORDINATES);
        if (numOfPlayers == 3) fillBoard(COORDINATES3PLAYERS);
        else if (numOfPlayers == 4) fillBoard(COORDINATES4PLAYERS);
    }

    public void fillBoard(int[][] indices) {
        for (int[] index : indices) {
            itemTileMatrix[index[0]][index[1]] = bag.drawItemTile();
        }
    }
    public void refillBoard(){
        fillBoard(COORDINATES);
        if (numOfPlayers == 3) fillBoard(COORDINATES3PLAYERS);
        else if (numOfPlayers == 4) fillBoard(COORDINATES4PLAYERS);
    }
    public void printBoard() {
        for (int j = 0; j < getItemTileMatrix().length; j++) {
            for (int k = 0; k < getItemTileMatrix()[0].length; k++) {
                System.out.printf("%10s", getItemTileMatrix()[j][k].getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }

//    public void setSelectedTile(int x, int y) {
//        if (!itemTileMatrix[x][y].isEmpty() && isAdjacentEmpty(x,y)) {
//            selectedTile.push(itemTileMatrix[x][y]);
//            itemTileMatrix[x][y] = new ItemTile();
//        }
//        else System.out.println("Adjacent tile is not empty!");
//    }

    public boolean isAdjacentEmpty(int x, int y){
        if (itemTileMatrix[x+1][y].isEmpty()) return true;
        else if (itemTileMatrix[x-1][y].isEmpty()) return true;
        else if (itemTileMatrix[x][y+1].isEmpty()) return true;
        else if (itemTileMatrix[x][y-1].isEmpty()) return true;
        else return false;
    }

    public void setCanBeSelected(){
        if (selectedTile.isEmpty()) {
            for (int i = 1; i < ROW - 1; i++) {
                for (int j = 1; j < COL - 1; j++) {
                    if (!itemTileMatrix[i][j].isEmpty() && isAdjacentEmpty(i, j))
                        canBeSelectedTiles.push(new int[]{i, j});
                }
            }
        }
        else if (selectedTile.size() == 1){

        }
    }


    public void printCanBeSelectedTiles() {
        for (int[] element : canBeSelectedTiles) {
            System.out.printf(Arrays.toString(element) +"  ");
        }
    }
    public void selectTile(int x, int y) {
        for (int[] tile : canBeSelectedTiles) {
            if (Arrays.equals(tile, new int[] {x, y})) {
                selectedTile.push(new int[]{x, y});
                return;
            }
        }
        System.out.println("Error to be handled.!");
    }
    public void popSelectedTile(){
        if (!selectedTile.isEmpty()) selectedTile.pop();
        else System.out.println("Error to be handled");
    }
    public void printSelectedTile() {
        for (int[] element : selectedTile ){
            System.out.println(Arrays.toString(element) +"  ");
        }
    }


    /********************************************************************************************
     * Board creation testing
     **************************************************************/

    public static void main(String[] args ){
        Board board = new Board(2);
        Scanner sc = new Scanner(System.in);

        System.out.println(" ");
        board.printBoard();
        board.setCanBeSelected();
        System.out.print("\nCan be selected tiles at coordinates: ");
        board.printCanBeSelectedTiles();
        System.out.print("\nSelect tile: ");
        String input = sc.nextLine();
        String[] parts = input.split(" ");
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);
        board.selectTile(a,b);
        System.out.print("SelectedTiles: ");
        board.printSelectedTile();
        board.setCanBeSelected();
        System.out.print("\nCan be selected tiles at coordinates: ");
        board.printCanBeSelectedTiles();
        System.out.print("\n\n");


        System.out.println("Updated board: ");
        board.printBoard();
        sc.close();
    }

}
