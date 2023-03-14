package model;

enum ItemTileType {
    CAT, BOOK, GAME, FRAME, TROPHY, PLANT, EMPTY
}

public class ItemTile {
    public ItemTileType getItemTileType() {
        return itemTileType;
    }

    public void setItemTileType(ItemTileType itemTileType) {
        this.itemTileType = itemTileType;
    }

    private ItemTileType itemTileType;

}
