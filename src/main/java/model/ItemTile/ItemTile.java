package model.ItemTile;

import static model.ItemTile.ItemTileType.EMPTY;

public class ItemTile {
    private ItemTileType itemTileType;
    private int id;

    // CONSTRUCTOR
    public ItemTile(ItemTileType itemTileType){
        this.itemTileType = itemTileType;
    }

    public ItemTile(ItemTileType itemTileType, int id){
        this.itemTileType = itemTileType;
        this.id = id;
    }

    // Setter
    public void setItemTileType(ItemTileType itemTileType) { this.itemTileType = itemTileType; }
    public void setId(int id) { this.id = id; }

    // Getter
    public ItemTileType getItemTileType() {
        return itemTileType;
    }
    public int getId() {return id;}

    // returns if itemTileType is empty
    public boolean isEmpty() { return itemTileType == EMPTY; }

}
