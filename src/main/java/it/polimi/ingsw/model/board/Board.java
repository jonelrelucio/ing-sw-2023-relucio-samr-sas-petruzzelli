package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileBag;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import java.util.List;
import java.util.Map;
import it.polimi.ingsw.model.util.Utility;

public class Board {

    private final int ROW = 11, COL = 11;
    private ItemTile[][] boardMatrix = new ItemTile[ROW][COL];
    private int numOfPlayers;
    private int[][] boardCoordinates;
    private final String PATH = "/json/BoardCoordinates.json";

    // CONSTRUCTOR
    public Board(int numOfPlayers){
        setNumOfPlayers(numOfPlayers);
        initItemTileMatrix();
        initBoardCoordinates(numOfPlayers);
        setItemsInCoordinates(boardCoordinates);
    }

    // Getters
    public ItemTile[][] getBoardMatrix(){ return boardMatrix; }
    public ItemTile getMatrixTile(int x, int y) { return boardMatrix[x][y]; }
    public int getNumOfPlayers() {return numOfPlayers; }

    // Setters
    public void setBoardMatrix(ItemTile[][] boardMatrix ) {
        this.boardMatrix = boardMatrix;
    }
    public void setNumOfPlayers(int numOfPlayers) { this.numOfPlayers = numOfPlayers; }
    public void setMatrixTile(int x, int y, ItemTile itemTile) { boardMatrix[x][y] = itemTile; }

    // Fills the board with empty tiles
    public void initItemTileMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                setMatrixTile(i, j, new ItemTile(ItemTileType.EMPTY));
            }
        }
    }

    // Initializes the board coordinates given the number of players
    public void initBoardCoordinates(int key) {
        BoardCoordinates boardCoordinates = (BoardCoordinates) Utility.deserializeJsonToObject(PATH, BoardCoordinates.class );
        List<List<Integer>> list = boardCoordinates.getBoardCoordinatesMap().get(Integer.toString(key));
        this.boardCoordinates = Utility.convertListListToArrayArray(list);
    }

    // sets the Items in board
    public void setItemsInCoordinates(int[][] indices) {
        for (int[] index : indices) {
            if (boardMatrix[index[0]][index[1]].isEmpty()) boardMatrix[index[0]][index[1]] = ItemTileBag.getInstance().drawItemTile();
        }
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

