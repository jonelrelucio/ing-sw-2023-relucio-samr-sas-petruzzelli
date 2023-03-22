package model;

import model.ItemTile.ItemTile;
import model.ItemTile.ItemTileBag;
import model.ItemTile.ItemTileType;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Board {

    private final int ROW = 11, COL = 11;
    private ItemTile[][] boardMatrix = new ItemTile[ROW][COL];
    private static ItemTileBag bag;
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
        fillBoard();
    }
    public ItemTile[][] getBoardMatrix(){ return boardMatrix; }
    public void setBoardMatrix(ItemTile[][] boardMatrix ) {
        this.boardMatrix = boardMatrix;
    }
    public void setBag() { bag = new ItemTileBag(); }
    public ItemTileBag getBag() { return bag; }
    public void setNumOfPlayers(int numOfPlayers) {
        if (numOfPlayers >= 2 && numOfPlayers <= 4) {
            this.numOfPlayers = numOfPlayers;
        } else throw new InvalidParameterException();
    }
    public int getNumOfPlayers() {return numOfPlayers; }
    public void setMatrixTile(int x, int y, ItemTile itemTile) { boardMatrix[x][y] = itemTile; }
    public ItemTile getMatrixTile(int x, int y) { return boardMatrix[x][y]; }

    public void initItemTileMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                setMatrixTile(i, j, new ItemTile(ItemTileType.EMPTY));
            }
        }
    }
    public void setItemsInCoordinates(int[][] indices) {
        for (int[] index : indices) {
            if (boardMatrix[index[0]][index[1]].isEmpty()) boardMatrix[index[0]][index[1]] = bag.drawItemTile();
        }
    }
    public void fillBoard(){
        setItemsInCoordinates(COORDINATES);
        if (numOfPlayers == 3) setItemsInCoordinates(COORDINATES3PLAYERS);
        else if (numOfPlayers == 4) setItemsInCoordinates(COORDINATES4PLAYERS);
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
    public ArrayList<ItemTile> getSelectedTile(ArrayList<int[]> coordinates) {
        ArrayList<ItemTile> selectedItemTiles = new ArrayList<>();
        for (int[] indices : coordinates) {
            selectedItemTiles.add(boardMatrix[indices[0]][indices[1]]);
            boardMatrix[indices[0]][indices[1]] = new ItemTile(ItemTileType.EMPTY);
        }
        return selectedItemTiles;
    }

}
