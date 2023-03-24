package model.ItemTile;

import java.util.Collections;
import java.util.Stack;

import static model.ItemTile.ItemTileType.*;

// Singleton Pattern: May break if a new game is created (maybe)
public class ItemTileBag {
    private static ItemTileBag instance;
    private final Stack<ItemTile> availableItemTiles;

    // SINGLETON PATTERN
    private ItemTileBag(){
        availableItemTiles = new Stack<>();
        fillBag();
    }

    public Stack<ItemTile> getAvailableItemTiles() { return availableItemTiles; }

    public static ItemTileBag getInstance() {
        if (instance == null) { instance = new ItemTileBag(); }
        return instance;
    }

    public ItemTile drawItemTile() {
        return availableItemTiles.pop();
    }


    public void fillBag(){
        for (int i = 0; i < 7; i++){
            for (int j = 1; j <= 3; j++ ){
                pushBag(j);
            }
        }
        pushBag(3);
        Collections.shuffle(availableItemTiles);
    }

    private void pushBag(int id){
        availableItemTiles.push(new ItemTile(CAT, id));
        availableItemTiles.push(new ItemTile(BOOK, id));
        availableItemTiles.push(new ItemTile(GAME, id));
        availableItemTiles.push(new ItemTile(FRAME, id));
        availableItemTiles.push(new ItemTile(TROPHY, id));
        availableItemTiles.push(new ItemTile(PLANT, id));
    }


    // TODO: REMOVE
    public int printBag(){
        int counter = 0;
        for (ItemTile availableItemTile : availableItemTiles) {
            System.out.printf(availableItemTile.getItemTileType().toString());
            System.out.printf("%3d\n", availableItemTile.getId());
            counter++;
        }
        return counter;
    }
}