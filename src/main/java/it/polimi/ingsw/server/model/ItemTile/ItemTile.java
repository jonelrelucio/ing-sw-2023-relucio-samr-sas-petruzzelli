package it.polimi.ingsw.server.model.ItemTile;

import static it.polimi.ingsw.server.model.ItemTile.ItemTileType.EMPTY;

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

    /**
     *
     * @return  item tile type of the item instance
     */
    public ItemTileType getItemTileType() {
        return itemTileType;
    }

    /**
     *
     * @return  image id of the item instance
     */
    public int getId() {return id;}

    /**
     *
     * @return  true if item tile instante is empty
     */
    public boolean isEmpty() { return itemTileType == EMPTY; }

    /**
     *
     * @param obj   ItemTile Object
     * @return      true if obj and instance caller are equal
     */
    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj.getClass() != this.getClass() ) return false;

        final ItemTile item = (ItemTile) obj;
        return this.itemTileType == item.getItemTileType();
    }

}
