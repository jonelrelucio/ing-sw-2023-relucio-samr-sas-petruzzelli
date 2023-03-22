package model;

import model.ItemTile.ItemTile;

import java.util.Stack;

public class Bookshelf {
    private final int ROW = 6;
    private final int COL = 5;
    private ItemTile[][] bookshelfMatrix;
    private int selectedColumn;

    public Bookshelf() {
        setBookshelfMatrix();
        initBookshelfMatrix();
    }
    private void initBookshelfMatrix() {
        for (int i = 0; i < ROW; i++ ){
            for (int j = 0; j < COL; j++){
                setMatrixTile(i, j, new ItemTile());
            }
        }
    }

    // TODO: TESTARE SE FUNZIONA
    public void updateTiles(Stack<ItemTile> selectedTiles){
        for (int i = ROW-1; i>=0; i--) {
            if (!selectedTiles.isEmpty() && !getMatrixTile(i,selectedColumn).isEmpty() ){
                setMatrixTile(i, selectedColumn, selectedTiles.pop());
            }
        }
    }
    public ItemTile[][] getBookshelfMatrix() { return bookshelfMatrix;}
    public ItemTile getMatrixTile(int x, int y) { return bookshelfMatrix[x][y]; }
    public void setMatrixTile(int x, int y, ItemTile itemTile) { bookshelfMatrix[x][y] = itemTile; }
    public void setBookshelfMatrix() { this.bookshelfMatrix = new ItemTile[ROW][COL]; }
    public void setBookshelfMatrix(ItemTile[][] bookshelfMatrix) { this.bookshelfMatrix = bookshelfMatrix;}
    public void setSelectedColumn(int selectedColumn) {this.selectedColumn = selectedColumn; }
    public int getSelectedColumn() { return selectedColumn; }
}
