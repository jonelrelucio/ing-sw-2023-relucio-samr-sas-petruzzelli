package model;

import static model.ItemTileType.EMPTY;

public class ItemTile {
    private ItemTileType itemTileType;
    private boolean isEmpty;

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
    public boolean isEmpty() { return itemTileType == EMPTY; }

}
