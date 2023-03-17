package model;

public class Board {

    private final int ROW = 11, COL = 11;
    private ItemTile[][] itemTileMatrix = new ItemTile[ROW][COL];
    private Bag bag = new Bag();
    private int numOfPlayers;
    private GameModel gameModel;

    private final int[][] coordinates = {
            {2, 4}, {2, 5}, {3, 5}, {3, 6}, {4, 3}, {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 8},
            {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8},
            {6, 2}, {6, 3}, {6, 4}, {6, 5}, {6, 6}, {6, 7},
            {7, 4}, {7, 5}, {7, 6},
            {8, 5}, {8, 6}
    };
    private final int[][] coordinates3Players = {
            {1, 4}, {3, 3}, {3, 7}, {4, 9}, {6, 1}, {7, 3}, {7, 7}, {9, 6}
    };
    private final int[][] coordinates4Players = {
            {1, 5}, {2, 6}, {4, 2}, {4, 2}, {5, 1}, {5, 9}, {6, 8}, {8, 4}, {9, 4}
    };

    /***********************************************************************/

    public Board(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        createMatrix();
        fill(coordinates);
        if (numOfPlayers == 3) fill(coordinates3Players);
        else if (numOfPlayers == 4) fill(coordinates4Players);
    }

    public void createMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                itemTileMatrix[i][j] = new ItemTile(ItemTileType.EMPTY);
            }
        }
    }
    public void fill(int[][] indices) {
        for (int[] index : indices) {
            itemTileMatrix[index[0]][index[1]] = bag.drawItemTile();
        }
    }
    public void refill(){
        fill(coordinates);
        if (numOfPlayers == 3) fill(coordinates3Players);
        else if (numOfPlayers == 4) fill(coordinates4Players);
    }

    public ItemTile[][] getBoard(){ return itemTileMatrix; }
//    public ItemTile[] getTiles(ItemTile[] tiles){
//        int[][] chosenCoordinates;
//    }
    public ItemTile[][] getItemTileMatrix() { return itemTileMatrix; }




    public static void main(String[] args ){
        Board board = new Board(2);

        for (int j = 0; j < board.getItemTileMatrix().length; j++) {
            for (int k = 0; k < board.getItemTileMatrix()[0].length; k++) {
                System.out.print(String.format("%10s", board.getItemTileMatrix()[j][k].getItemTileType().toString()));
            }
            System.out.println(" ");
        }
    }

}
