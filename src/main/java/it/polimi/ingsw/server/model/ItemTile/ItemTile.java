package it.polimi.ingsw.server.model.ItemTile;

import static it.polimi.ingsw.server.model.ItemTile.ItemTileType.EMPTY;

/**
 * The Item Tile class
 */
public class ItemTile {
    /**
     * the type of item tile
     */
    private ItemTileType itemTileType;

    /**
     * The id of the item tile that is used to indicate what image it is
     */
    private int id;

    /**
     * Constructor
     * @param itemTileType  the type of item tile
     */
    public ItemTile(ItemTileType itemTileType){
        this.itemTileType = itemTileType;
    }

    /**
     * Constructor with a specific id
     * @param itemTileType  the type of item tile
     * @param id            the id of the item tile
     */
    public ItemTile(ItemTileType itemTileType, int id){
        this.itemTileType = itemTileType;
        this.id = id;
    }

    /**
     * Setter for the item tile type
     * @param itemTileType  the item tile type
     */
    public void setItemTileType(ItemTileType itemTileType) { this.itemTileType = itemTileType; }
    public void setId(int id) { this.id = id; }

    /**
     * Getter for the item tile type
     * @return  item tile type of the item instance
     */
    public ItemTileType getItemTileType() {
        return itemTileType;
    }

    /**
     * Getter for the id of the item
     * @return  image id of the item instance
     */
    public int getId() {return id;}

    /**
     * Checks if the type of the item tile is EMPTY
     * @return  true if item tile instante is empty
     */
    public boolean isEmpty() { return itemTileType == EMPTY; }

    /**
     * checks if the two item tiles have the same type
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
