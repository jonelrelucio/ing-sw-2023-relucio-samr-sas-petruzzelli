package model;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class Board {

    private final int ROW = 11, COL = 11;
    private final ItemTile[][] boardMatrix = new ItemTile[ROW][COL];
    private Bag bag;
    private int numOfPlayers;

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

    public Board(int numOfPlayers){
        setNumOfPlayers(numOfPlayers);
        setBag();
        initItemTileMatrix();
        fillBoard(COORDINATES);
        if (numOfPlayers == 3) fillBoard(COORDINATES3PLAYERS);
        else if (numOfPlayers == 4) fillBoard(COORDINATES4PLAYERS);
    }

    public ItemTile[][] getBoardMatrix(){ return boardMatrix; }
    public void setBag() { bag = new Bag(); }
    public Bag getBag() { return bag; }
    public void setNumOfPlayers(int numOfPlayers) { this.numOfPlayers = numOfPlayers; }
    public int getNumOfPlayers() {return numOfPlayers; }
    public void setMatrixTile(int x, int y, ItemTile itemTile) { boardMatrix[x][y] = itemTile; }
    public ItemTile getMatrixTile(int x, int y) { return boardMatrix[x][y]; }

    public void initItemTileMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                setMatrixTile(i, j, new ItemTile());
            }
        }
    }
    public void fillBoard(int[][] indices) {
        for (int[] index : indices) {
            setMatrixTile(index[0], index[1], bag.drawItemTile());
        }
    }
    public void refillBoard(){
        fillBoard(COORDINATES);
        if (numOfPlayers == 3) fillBoard(COORDINATES3PLAYERS);
        else if (numOfPlayers == 4) fillBoard(COORDINATES4PLAYERS);
    }
    public void printBoard() {
        for (int j = 0; j < getBoardMatrix().length; j++) {
            for (int k = 0; k < getBoardMatrix()[0].length; k++) {
                System.out.printf("%10s", getMatrixTile(j,k).getItemTileType().toString());
            }
            System.out.println(" ");
        }
    }

    // Il controller garantisce che le itemTile con coordinate selectedTile non siano empty e cha siano adiacenti a una cella empty
    // TODO: Codice for loop potrebbe essere sbagliato. da testare
    public Stack<ItemTile> getSelectedTile(@NotNull Stack<int[]> coordinates) {
        Stack<ItemTile> selectedItemTiles = new Stack<>();
        for (int[] indices : coordinates) {
            selectedItemTiles.push(getBoardMatrix()[indices[0]][indices[1]]);
            getBoardMatrix()[indices[0]][indices[1]] = new ItemTile();
        }
        return selectedItemTiles;
    }

}
