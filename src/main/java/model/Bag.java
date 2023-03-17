package model;

import java.util.Collections;
import java.util.Stack;

import static model.ItemTileType.*;

public class Bag {
    Stack<ItemTile> availableItemTiles = new Stack<ItemTile>();

    public Bag(){
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

    public ItemTile drawItemTile() {
        return availableItemTiles.pop();
    }
}