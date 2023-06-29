package it.polimi.ingsw.server.model.bag;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import java.util.Collections;
import java.util.Stack;

/**
 * This class represents the bag that contains all the available item tiles to fill the board
 */
public class ItemTileBag {
    /**
     * List of item tiles
     */
    private final Stack<ItemTile> itemTilesBag = new Stack<>();

    /**
     * Constructor that initialize the list of item tiles
     */
    public ItemTileBag() {
        initItemTileBag();
    }

    /**
     * This method fill the bag calling pushBag() method and shuffle it
     * @see #pushBag(int)
     */
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
     * This method fills the bag with an equal number of tiles of different types
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

    /**
     * This method extract a tile from the list of available item tiles
     * @return the item tile extracted
     */
    public ItemTile drawItemTile() {return itemTilesBag.pop();}

    public Stack<ItemTile> getItemTilesBag() { return itemTilesBag; }
}