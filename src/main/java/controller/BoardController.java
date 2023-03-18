package controller;

import model.Board;
import model.ItemTile;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class BoardController {
    private final int ROW = 11, COL = 11;
    private Board board;
    private Stack<int[]> selectedTile;
    private Stack<int[]> canBeSelectedTiles;

    public BoardController(Board board) {
        setBoard(board);
        setSelectedTile();
        setCanBeSelectedTiles();
    }

    public void setBoard(Board board) { this.board = board; }
    public Board getBoard() { return board; }
    public void setSelectedTile() { selectedTile = new Stack<>(); }
    public Stack<int[]> getSelectedTile() { return selectedTile; }
    public void setCanBeSelectedTiles() { canBeSelectedTiles = new Stack<>(); }
    public Stack<int[]> getCanBeSelectedTiles() { return canBeSelectedTiles; }

    public boolean isAdjacentEmpty(int x, int y){
        if (board.getItemTileMatrix()[x+1][y].isEmpty()) return true;
        else if (board.getItemTileMatrix()[x-1][y].isEmpty()) return true;
        else if (board.getItemTileMatrix()[x][y+1].isEmpty()) return true;
        else if (board.getItemTileMatrix()[x][y-1].isEmpty()) return true;
        else return false;
    }


    //TODO: implement updateCanbeSelectedTiles case when selectedTile.size() == 1 || 2 || 3
    public void updateCanBeSelectedTiles(){
        if (selectedTile.isEmpty()) {
            for (int i = 1; i < ROW - 1; i++) {
                for (int j = 1; j < COL - 1; j++) {
                    if (!board.getItemTileMatrix()[i][j].isEmpty() && isAdjacentEmpty(i, j))
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


        System.out.println("Updated board: ");
        board.printBoard();



        Stack<ItemTile> selectedItemTile = bc.board.getSelectedTile(bc.selectedTile); //parte importante gestita poi dal player
        sc.close();
    }



}
