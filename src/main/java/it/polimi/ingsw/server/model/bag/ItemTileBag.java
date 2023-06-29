package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.Collections;
import java.util.Stack;

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
        itemTilesBag.push(new ItemTile(ItemTileType.CAT, id));
        itemTilesBag.push(new ItemTile(ItemTileType.BOOK, id));
        itemTilesBag.push(new ItemTile(ItemTileType.GAME, id));
        itemTilesBag.push(new ItemTile(ItemTileType.FRAME, id));
        itemTilesBag.push(new ItemTile(ItemTileType.TROPHY, id));
        itemTilesBag.push(new ItemTile(ItemTileType.PLANT, id));
    }

    public ItemTile drawItemTile() {return itemTilesBag.pop();}

    public Stack<ItemTile> getItemTilesBag() { return itemTilesBag; }
}