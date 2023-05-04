package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class BookshelfTest {

    private final ItemTile[][] bookshelf1 =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

    private final ItemTile[][] bookshelf2 =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT)}};

    private final ItemTile[][] bookshelf3 =
            {       {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME)}};

    @Test
    public void testGetBookshelfMatrix() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf1);
        assertEquals(bookshelf1, bookshelf.getBookshelfMatrix());
    }

    @Test
    public void testRemainingEmptyTilesInCol() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf1);
        bookshelf.setSelectedColumn(0);
        assertEquals(5, bookshelf.remainingEmptyTilesInSelectedCol(bookshelf.getSelectedColumn()));

        bookshelf.setBookshelfMatrix(bookshelf2);
        assertEquals(1, bookshelf.remainingEmptyTilesInSelectedCol(0));
    }

    @Test
    public void testCheckFull() {

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf1);
        assertFalse(bookshelf.isFull());

        bookshelf.setBookshelfMatrix(bookshelf3);
        assertTrue(bookshelf.isFull());
    }

    @Test
    public void testSelectColumnThrowsExceptionForOutOfBounds() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf2);
        assertThrows(IllegalArgumentException.class, () -> bookshelf.selectColumn(100));
    }

    @Test
    public void testGetMaxAvailableSpace() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf2);
        assertEquals(3, bookshelf.getMaxAvailableSpace());


        bookshelf.setBookshelfMatrix(bookshelf3);
        assertEquals(0, bookshelf.getMaxAvailableSpace());
    }

    @Test
    public void testUpdateTilesThrowsExceptionForSelectedTilesLargerThanAvailableSpace() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf2);
        bookshelf.selectColumn(1);
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        selectedTiles.add(new ItemTile(ItemTileType.GAME));
        selectedTiles.add(new ItemTile(ItemTileType.BOOK));
        selectedTiles.add(new ItemTile(ItemTileType.BOOK));
        selectedTiles.add(new ItemTile(ItemTileType.TROPHY));
        assertThrows(IllegalArgumentException.class, () -> bookshelf.updateTiles(selectedTiles));
    }

    @Test
    public void testUpdateTiles() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookshelf2);
        bookshelf.selectColumn(1);
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        selectedTiles.add(new ItemTile(ItemTileType.GAME));
        selectedTiles.add(new ItemTile(ItemTileType.BOOK));
        bookshelf.updateTiles(selectedTiles);
        assertEquals(ItemTileType.GAME, bookshelf.getMatrixTile(1, 1).getItemTileType());
        assertEquals(ItemTileType.BOOK, bookshelf.getMatrixTile(0, 1).getItemTileType());
    }




    @Test
    void backwardsLShapeTest() {
        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.FRAME)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
        int points = bookshelf.getScore();
        assertEquals(6, points);
    }

    @Test
    void adjacentToNothingTest() {

        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
        int points = bookshelf.getScore();
        assertEquals(4, points);
    }

    @Test
    void groupOfFiveTest() {
        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
        int points = bookshelf.getScore();
        assertEquals(7, points);
    }

    @Test
    void groupOfSixTest() {
        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.EMPTY)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
        int points = bookshelf.getScore();
        assertEquals(10, points);
    }
}