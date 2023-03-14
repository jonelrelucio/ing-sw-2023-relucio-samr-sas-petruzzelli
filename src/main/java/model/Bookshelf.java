package model;

public class Bookshelf {
    //TODO cambiare questi valori
    private final int ROW = 10;
    private final int COL = 10;
    private ItemTile[][] bookshelf;
    private int selectedColumn;

    public void updateTiles(){

    }
    public ItemTile[][] getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(ItemTile[][] bookshelf) {
        this.bookshelf = bookshelf;
    }

    public void setSelectedColumn(int selectedColumn) {
        this.selectedColumn = selectedColumn;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }
}
