package it.polimi.ingsw.server.model;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bookshelf {

    /**
     * The row size of the bookshelf
     */
    private static final int ROW = 6;

    /**
     * The column size of the bookshelf
     */
    private static final int COL = 5;

    /**
     * The bookshelf matrix which is a matrix of Item Tile
     */
    private ItemTile[][] bookshelfMatrix;

    /**
     * the selected column
     */
    private int selectedColumn;

    /**
     * Constructor
     */
    public Bookshelf() {
        initBookshelfMatrix();
    }

    /**
     * Sets all ItemTiles in bookshelf matrix to empty
     */
    private void initBookshelfMatrix() {
        this.bookshelfMatrix = new ItemTile[ROW][COL];
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                setMatrixTile(i, j, new ItemTile(ItemTileType.EMPTY));
            }
        }
    }

    /**
     * Counts how much available space is in selectedColumn
     * @return  number of empty tiles in selected Column
     */
    public int remainingEmptyTilesInSelectedCol(int col){
        int emptyTiles = 0;
        for (ItemTile[] matrix : bookshelfMatrix) {
            if (matrix[col].isEmpty()) emptyTiles++;
        }
        return emptyTiles;
    }

    /**
     * Selects the column
     * @param selectedColumn    selected column int
     */
    public void selectColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    /**
     * Checks if the shelf matrix is full
     * @return  true if the shelf matrix is full, false otherwise
     */
    public boolean isFull(){
        return getMaxAvailableSpace() == 0;
    }

    /**
     * Fills the selected column from the bottom up with the stack of selectedTiles
     * @param selectedTiles stack of selectedTiles
     */
    public EventView updateTiles(ArrayList<ItemTile> selectedTiles){
        if (selectedColumn < 0 || selectedColumn >= bookshelfMatrix.length || selectedTiles.size() > remainingEmptyTilesInSelectedCol(getSelectedColumn()))
            return EventView.SELECT_COLUMN_FAIL;
        for (int i = bookshelfMatrix.length-1; selectedTiles.size() > 0 && i >= 0; i--) {
            if (getMatrixTile(i, selectedColumn).isEmpty() ){
                setMatrixTile(i, selectedColumn, selectedTiles.remove(0));
            }
        }
        return EventView.NEW_TURN;
    }

    /**
     * After calculating the available space of each column returns the max
     * @return  the maximum available space of all column
     */
    public int getMaxAvailableSpace(){
        int max = 0;
        for (int i = 0; i < bookshelfMatrix[0].length; i++ ){
            int temp = remainingEmptyTilesInSelectedCol(i);
            if (temp > max) max = temp;
        }
        return Math.min(max, 3);
    }

    /**
     * Builds the adjacency map
     * @param adjacencyMap  the adjacency map
     */
    private void buildAdjacencyMap( HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap){
        ItemTile[][] itemTileMatrix = bookshelfMatrix;
        int numOfRows = itemTileMatrix.length;
        int numOfCols = itemTileMatrix[0].length;
        for(int r=0;r<numOfRows;r++) {
            for(int c=0;c<numOfCols;c++) {
                ItemTileType cellValue = bookshelfMatrix[r][c].getItemTileType();
                if(!cellValue.equals(ItemTileType.EMPTY)) {
                    if(!adjacencyMap.containsKey(cellValue)) {
                        ArrayList<ArrayList<int[]>> adjacencyLists = new ArrayList<>();
                        ArrayList<int[]> adjacencyGroup = new ArrayList<>();
                        adjacencyGroup.add(new int[] {r,c});
                        adjacencyLists.add(adjacencyGroup);
                        adjacencyMap.put(cellValue, adjacencyLists);
                    }else {
                        addToAdjacencyGroup(cellValue, r, c, adjacencyMap);
                    }

                }
            }
        }
    }

    /**
     * Adds to the adjacency group
     * @param cellValue the cell value
     * @param r         the first value of the coordinates
     * @param c         the second value of the coordinates
     * @param adjacencyMap  the adjacency map
     */
    private void addToAdjacencyGroup(ItemTileType cellValue, int r, int c,
                                            HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap) {

        int lastCol = bookshelfMatrix[0].length-1;
        ArrayList<ArrayList<int[]>> adjacencyLists = adjacencyMap.get(cellValue);
        ArrayList<int[]> cellAboveRightAdjacencyGroup = new ArrayList<>();
        boolean backwardsLShapeFound = false;
        boolean adjacentToNothing = true;
        for(ArrayList<int[]> adjacencyGroup : adjacencyLists) {
            for(int[] adjacentCell : adjacencyGroup) {
                int adjacentCellRow = adjacentCell[0];
                int adjacentCellCol = adjacentCell[1];
                if(r!=0 && c!= lastCol) {
                    int cellAboveRightRow = r-1;
                    int cellAboveRightCol = c+1;
                    ItemTileType cellAboveRightValue = bookshelfMatrix[cellAboveRightRow][cellAboveRightCol].getItemTileType();
                    ItemTileType cellRightValue = bookshelfMatrix[r][c+1].getItemTileType();
                    if(cellValue.equals(cellAboveRightValue) && cellValue.equals(cellRightValue)) {
                        backwardsLShapeFound = true;
                        adjacentToNothing = false;
                        cellAboveRightAdjacencyGroup =
                                getCellAdjacencyGroup(cellAboveRightRow, cellAboveRightCol, cellAboveRightValue, adjacencyMap);
                    }
                }
                if((r-adjacentCellRow==1 && c==adjacentCellCol) ^
                        (c-adjacentCellCol==1 && r==adjacentCellRow)) {
                    adjacentToNothing = false;
                    adjacencyGroup.add(new int[] {r,c});
                    return;
                }
            }

        }


        if(backwardsLShapeFound) {
            cellAboveRightAdjacencyGroup.add(new int[] {r,c});
        }
        if(adjacentToNothing) {
            ArrayList<int[]> adjacencyGroup = new ArrayList<>();
            adjacencyGroup.add(new int[] {r,c});
            adjacencyMap.get(cellValue).add(adjacencyGroup);
        }

    }

    /**
     * Gets the cell adjacency group
     * @param r first value of the coordinates
     * @param c second value of the coordinates
     * @param cellValue the value of the cell
     * @param adjacencyMap  the adjacency map
     * @return  the cell adjacency group
     */
    private ArrayList<int[]> getCellAdjacencyGroup(int r, int c, ItemTileType cellValue,
                                                          HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap){

        ArrayList<int[]> toReturn = new ArrayList<int[]>();

        for(ArrayList<int[]> adjacencyGroup : adjacencyMap.get(cellValue)) {
            for(int[] adjacentCell : adjacencyGroup) {
                int adjacentCellRow = adjacentCell[0];
                int adjacentCellCol = adjacentCell[1];
                if(r==adjacentCellRow && c==adjacentCellCol) {
                    toReturn = adjacencyGroup;
                    break;
                }
            }
        }
        return toReturn;

    }

    /**
     * Gets the score
     * @return  the score
     */
    public int getScore(){
        HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        buildAdjacencyMap( adjacencyMap);
        int points = 0;
        for(Map.Entry<ItemTileType, ArrayList<ArrayList<int[]>>> entry : adjacencyMap.entrySet()) {
            for(ArrayList<int[]> adjacencyGroup : entry.getValue()) {
                int adjacencyGroupSize = adjacencyGroup.size();
                if(adjacencyGroupSize==3) {
                    points+=2;
                }else if(adjacencyGroupSize==4) {
                    points+=3;
                }else if(adjacencyGroupSize==5) {
                    points+=5;
                }else if(adjacencyGroupSize>=6) {
                    points+=8;
                }
            }
        }
        return points;
    }


    /**
     * Gets the matrix tile
     * @param x the first coordinate of the tile
     * @param y the second coordinate of the tile
     * @return  the matrix tile
     */
    public ItemTile getMatrixTile(int x, int y) { return bookshelfMatrix[x][y]; }

    /**
     * Gets the bookshelf matrix
     * @return  the bookshelf matrix
     */
    public ItemTile[][] getBookshelfMatrix() { return bookshelfMatrix;}

    /**
     * Gets the selected column
     * @return  the selected column
     */
    public int getSelectedColumn() { return selectedColumn; }

    /**
     * Sets the matrix tile
     * @param x the first coordinate of the tile
     * @param y the second coordinate of the tile
     * @param itemTile the Item Tile to be set
     */
    public void setMatrixTile(int x, int y, ItemTile itemTile) { bookshelfMatrix[x][y] = itemTile; }

    /**
     * Sets the bookshelf matrix of the bookshelf
     * @param bookshelfMatrix   a matrix of ItemTile
     */
    public void setBookshelfMatrix(ItemTile[][] bookshelfMatrix) { this.bookshelfMatrix = bookshelfMatrix;}

    /**
     * sets the selected colum
     * @param selectedColumn    the selected column
     */
    public void setSelectedColumn(int selectedColumn) {this.selectedColumn = selectedColumn; }
}
