package model.ItemTile;

import static model.ItemTile.ItemTileType.EMPTY;
import java.util.Random;

public class ItemTile {
    private ItemTileType itemTileType;
    private int id;

    // CONSTRUCTOR
    public ItemTile(ItemTileType itemTileType){
        Random r = new Random();
        this.itemTileType = itemTileType;
        if (itemTileType != EMPTY) this.id = r.nextInt(3);
        else this.id = 0;
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
