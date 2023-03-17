package model;

import static model.ItemTileType.EMPTY;

enum ItemTileType {
    CAT, BOOK, GAME, FRAME, TROPHY, PLANT, EMPTY;

    @Override
    public String toString() {
        return super.toString();
    }
}

public class ItemTile {
    private ItemTileType itemTileType;

    public ItemTile(){
        setItemTileType(EMPTY);
    }
    public ItemTile(ItemTileType itemTileType){
        setItemTileType(itemTileType);
    }
    public ItemTileType getItemTileType() {
        return itemTileType;
    }
    public void setItemTileType(ItemTileType itemTileType) {
        this.itemTileType = itemTileType;
    }

}
