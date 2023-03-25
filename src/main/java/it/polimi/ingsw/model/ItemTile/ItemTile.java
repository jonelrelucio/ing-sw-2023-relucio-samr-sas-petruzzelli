package it.polimi.ingsw.model.ItemTile;

import static it.polimi.ingsw.model.ItemTile.ItemTileType.EMPTY;

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

    // overrides equals method
    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj.getClass() != this.getClass() ) return false;

        final ItemTile item = (ItemTile) obj;
        return this.itemTileType == item.getItemTileType();
    }

}
