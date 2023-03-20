package model;

import java.util.Collections;
import java.util.Stack;

import static model.ItemTileType.*;

public class ItemTileBag {
    private Stack<ItemTile> availableItemTiles;


    public ItemTileBag(){
        availableItemTiles = new Stack<>();
        bagCreate();
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