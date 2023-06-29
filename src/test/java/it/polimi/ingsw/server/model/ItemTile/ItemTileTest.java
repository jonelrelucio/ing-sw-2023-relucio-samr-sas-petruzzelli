package it.polimi.ingsw.server.model.ItemTile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTileTest {

    @Test
    void testSettersAndGetters() {
        ItemTileType itemType = ItemTileType.CAT;
        ItemTile item = new ItemTile(itemType);
        assertEquals(itemType, item.getItemTileType());

        itemType = ItemTileType.BOOK;
        item.setItemTileType(itemType);
        assertEquals(itemType, item.getItemTileType());

        int itemId = 2;
        item.setId(itemId);
        assertEquals(itemId, item.getId());

        itemType = ItemTileType.EMPTY;
        item.setItemTileType(itemType);
        assertTrue(item.isEmpty());

        itemType = ItemTileType.BOOK;
        item.setItemTileType(itemType);
        ItemTileType itemType2 = ItemTileType.BOOK;
        ItemTile item2 = new ItemTile(itemType2);
        assertTrue(item.equals(item2));

    }
}