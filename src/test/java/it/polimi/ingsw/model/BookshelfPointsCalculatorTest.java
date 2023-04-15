package it.polimi.ingsw.model;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.ItemTile.ItemTileType;
import org.junit.jupiter.api.Test;

class BookshelfPointsCalculatorTest {

	@Test
	void backwardsLShapeTest() {
		//HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        /*
        String[][] board = {
                {"R","R","B"},
                {"R","B","B"},
                {"R","","B"}
        };
         */
        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.FRAME)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
//        int points = BookshelfPointsCalculator.getScore(bookshelf);
//        assertEquals(6, points);
	}
	
	@Test
	void adjacentToNothingTest() {
		//HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        /*
        String[][] board = {
                {"R","R","B"},
                {"R","G","B"},
                {"","R","B"}
        };
        */

        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
//        int points = BookshelfPointsCalculator.getScore(bookshelf);
//        assertEquals(4, points);
	}
	
	@Test
	void groupOfFiveTest() {
        /*
        String[][] board = {
                {"R","R","B"},
                {"R","G","B"},
                {"R","R","B"}
        };
        */
        //HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
//        int points = BookshelfPointsCalculator.getScore(bookshelf);
//        assertEquals(7, points);
	}
	
	@Test
	void groupOfSixTest() {
        /*
        String[][] board = {
                {"R","R","B"},
                {"R","G","B"},
                {"R","R","B"},
                {"","R",""}
        };
        */

        //HashMap<ItemTileType, ArrayList<ArrayList<int[]>>> adjacencyMap = new HashMap<>();
        ItemTile[][] bookShelfItemTileMatrix = {
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.CAT), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.FRAME)},
                {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.BOOK), new ItemTile(ItemTileType.EMPTY)}
        };
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfMatrix(bookShelfItemTileMatrix);
//        int points = BookshelfPointsCalculator.getScore(bookshelf);
//        assertEquals(10, points);
	}

}
