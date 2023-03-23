package model.ItemTile;

import java.util.Collections;
import java.util.Stack;

import static model.ItemTile.ItemTileType.*;

// May break
public class ItemTileBag {
    private static ItemTileBag instance;
    private final Stack<ItemTile> availableItemTiles;

    // SINGLETON PATTERN
    private ItemTileBag(){
        availableItemTiles = new Stack<>();
        bagCreate();
    }

    public static ItemTileBag getInstance() {
        if (instance == null) { instance = new ItemTileBag(); }
        return instance;
    }

    public ItemTile drawItemTile() {
        return availableItemTiles.pop();
    }

    public void bagCreate(){
        for (int i = 0; i < 22; i++){
            availableItemTiles.push(new ItemTile(CAT));
            availableItemTiles.push(new ItemTile(BOOK));
            availableItemTiles.push(new ItemTile(GAME));
            availableItemTiles.push(new ItemTile(FRAME));
            availableItemTiles.push(new ItemTile(TROPHY));
            availableItemTiles.push(new ItemTile(PLANT));
        }
        Collections.shuffle(availableItemTiles);
    }
}