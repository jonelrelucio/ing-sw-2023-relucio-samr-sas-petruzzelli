package model.ItemTile;

import static model.ItemTile.ItemTileType.EMPTY;
import java.util.Random;

public class ItemTile {
    private ItemTileType itemTileType;
    private int id;

    // CONSTRUCTOR
    public ItemTile(ItemTileType itemTileType){
        this.itemTileType = itemTileType;
        this.id = newId(itemTileType);
    }

    // Setter
    public void setItemTileType(ItemTileType itemTileType) { this.itemTileType = itemTileType; }
    public void setId(int id) { this.id = id; }

    // Getter
    public ItemTileType getItemTileType() {
        return itemTileType;
    }
    public int getId() {return id;}

    // TODO: CREATE ID FOR ITEMTILE: code below is wrong
    private int newId(ItemTileType itemTileType){
        Random r = new Random();
        if (itemTileType != EMPTY) return r.nextInt(3);
        else return 0;
    }

    // returns if itemTileType is empty
    public boolean isEmpty() { return itemTileType == EMPTY; }

}
