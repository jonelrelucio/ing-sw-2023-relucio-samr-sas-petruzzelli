package model.ItemTile;

import java.util.Stack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTileBagTest {

    @Test
    public void testNumOfItemsInBag() {
        assertEquals(132, ItemTileBag.getInstance().getAvailableItemTiles().size());
    }

    @Test
    public void testBag() {
        Stack<ItemTile> temp = new Stack<>();
        for( int i = 0; i < 132; i++) {
            temp.push(ItemTileBag.getInstance().drawItemTile()) ;
        }
        assertEquals(0, ItemTileBag.getInstance().getAvailableItemTiles().size());

    }

    @Test
    public void testRefillBag() {
        ItemTileBag.getInstance().fillBag();
        assertEquals(132, ItemTileBag.getInstance().getAvailableItemTiles().size());
    }

}