package it.polimi.ingsw.server.model.bag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTileBagTest {

    @Test
    public void testItemTileBagSize() {
        ItemTileBag itemTileBag = new ItemTileBag();
        assertEquals(132, itemTileBag.getItemTilesBag().size());
    }

}