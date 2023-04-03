package it.polimi.ingsw.model.bag;

import it.polimi.ingsw.model.ItemTile.ItemTile;

import java.util.Collections;
import java.util.Stack;

import static it.polimi.ingsw.model.ItemTile.ItemTileType.*;

public class ItemTileBag {

    private final Stack<ItemTile> itemTilesBag = new Stack<>();

    public ItemTileBag() {
        initItemTileBag();
    }

    private void initItemTileBag() {
        for (int i = 0; i < 7; i++){
            for (int j = 1; j <= 3; j++ ){
                pushBag(j);
            }
        }
        pushBag(3);
        Collections.shuffle(itemTilesBag);
    }

    /**
     * @param id    indicates the value of the image
     */
    private void pushBag(int id){
        itemTilesBag.push(new ItemTile(CAT, id));
        itemTilesBag.push(new ItemTile(BOOK, id));
        itemTilesBag.push(new ItemTile(GAME, id));
        itemTilesBag.push(new ItemTile(FRAME, id));
        itemTilesBag.push(new ItemTile(TROPHY, id));
        itemTilesBag.push(new ItemTile(PLANT, id));
    }

    public ItemTile drawItemTile() {return itemTilesBag.pop();}
}
