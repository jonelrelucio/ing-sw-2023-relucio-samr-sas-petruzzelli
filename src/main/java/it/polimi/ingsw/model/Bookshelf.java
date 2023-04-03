package it.polimi.ingsw.model;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;

import java.util.Stack;

public class Bookshelf {

    // Attributes
    private static final int ROW = 6;
    private static final int COL = 5;
    private ItemTile[][] bookshelfMatrix;
    private int selectedColumn;
    private boolean isFull;

    // Constructor
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
     * Counts how much available space in selectedColumn
     * @return  number of empty tiles in selected Column
     */
    private int remainingEmptyTilesInCol(){
        int emptyTiles = 0;
        for (ItemTile[] matrix : bookshelfMatrix) {
            if (matrix[selectedColumn].isEmpty()) emptyTiles++;
        }
        return emptyTiles;
    }

    /**
     * Selects the column
     * after checking if selected column is not out of bound
     * @param selectedColumn    selected column int
     */
    public void selectColumn(int selectedColumn) {
        if (selectedColumn < 0 || selectedColumn >= bookshelfMatrix.length ) throw new IllegalArgumentException("Selected Column out of Bound");
        this.selectedColumn = selectedColumn;
    }

    /**
     * Checks if the shelf matrix is full
     * @return  true if the shelf matrix is full, false otherwise
     */
    public boolean checkFull(){
        for (int i = 0; i < bookshelfMatrix.length; i++) {
            if(selectedColumn > 0) return false;
        }
        return true;
    }


    // TODO: TESTARE SE FUNZIONA

    /**
     * Fills the selected column from the bottom up with the stack of selectedTiles
     * @param selectedTiles stack of selectedTiles
     */
    public void updateTiles(Stack<ItemTile> selectedTiles){
        if (selectedTiles.size() > remainingEmptyTilesInCol()) throw new IllegalArgumentException("Selected Tiles are in larger number than available space in selected Column.");
        for (int i = bookshelfMatrix.length-1; i>=0; i--) {
            if (!selectedTiles.isEmpty() && !getMatrixTile(i,selectedColumn).isEmpty() ){
                setMatrixTile(i, selectedColumn, selectedTiles.pop());
            }
        }
        setFull(checkFull());
    }

    // Getters
    public ItemTile getMatrixTile(int x, int y) { return bookshelfMatrix[x][y]; }
    public ItemTile[][] getBookshelfMatrix() { return bookshelfMatrix;}
    public int getSelectedColumn() { return selectedColumn; }
    public boolean isFull() { return isFull; }

    // Setters
    public void setMatrixTile(int x, int y, ItemTile itemTile) { bookshelfMatrix[x][y] = itemTile; }
    public void setBookshelfMatrix(ItemTile[][] bookshelfMatrix) { this.bookshelfMatrix = bookshelfMatrix;}
    public void setSelectedColumn(int selectedColumn) {this.selectedColumn = selectedColumn; }
    public void setFull(boolean isFull ) {this.isFull = isFull; }
}
