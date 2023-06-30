package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoalCardTest {
    /**
     * Bookshelf used for commonGoalCard 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 and 12
     */
    private final ItemTile[][] stair =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),     new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),     new ItemTile(ItemTileType.FRAME),    new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

    /**
     * Bookshelf used for commonGoalCard 1 and 5
     */
    private final ItemTile[][] stair2 =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT)}};

    /**
     * Bookshelf used for commonGoalCard 2
     */
    private final ItemTile[][] eightPieces =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK)}};

    /**
     * Bookshelf used for commonGoalCard 1, 3, 4
     */
    private final ItemTile[][] square =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)}};

    /**
     * Bookshelf used for commonGoalCard 5 and 6
     */
    private final ItemTile[][] diagonal =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)}};

    /**
     * Bookshelf used for commonGoalCard 7
     */
    private final ItemTile[][] groupOfTwo =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME)}};

    /**
     * Bookshelf used for commonGoalCard 8
     */
    private final ItemTile[][] getGroupOfFour =
            {       {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME)}};

    /**
     * Bookshelf used for commonGoalCard 9 and 11
     */
    private final ItemTile[][] colMax3Types =
            {       {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME)}};

    /**
     * Bookshelf used for commonGoalCard 10 and 12
     */
    private final ItemTile[][] rowMax3Types =
            {       {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.FRAME)}};

    /**
     * Bookshelf used for commonGoalCard 6
     */
    private final ItemTile[][] x =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)}};

    /**
     * Bookshelf used for commonGoalCard 10 and 11
     */
    private final ItemTile[][] none =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.EMPTY),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.PLANT),    new ItemTile(ItemTileType.BOOK),  new ItemTile(ItemTileType.EMPTY),     new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)}};

    /**
     * Bookshelf used for commonGoalCard 7
     */
    private final ItemTile[][] test13 =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY)}};

    /**
     * Bookshelf used for commonGoalCard 7
     */
    private final ItemTile[][] test14 =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY)}};

    /**
     * Bookshelf used for commonGoalCard 7
     */
    private final ItemTile[][] test15 =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.FRAME)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME)}};

    /**
     * Bookshelf used for commonGoalCard 3
     */
    private final ItemTile[][] test16 =
            {       {new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY), new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.PLANT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.FRAME)},
                    {new ItemTile(ItemTileType.CAT),  new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.TROPHY),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testStair() {
        int[][] coords = {  {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1}};
        CommonGoalCard card = new CommonGoalExactShape(1, coords, true);
        assertTrue(card.checkPattern(stair));
        assertTrue(card.checkPattern(stair2));
        assertFalse(card.checkPattern(square));
        int id = card.getId();
        assertEquals(1, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testEightPieces() {
        int[][] coords = {{0, 0}};
        CommonGoalCard card = new CommonGoalShape(2, 1, 1, coords, 8, false, false, false, true, false);
        assertTrue(card.checkPattern(eightPieces));
        assertFalse(card.checkPattern(stair));
        int id = card.getId();
        assertEquals(2, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testSquare() {
        int[][] coords = {{0,0}, {0,1}, {1,0}, {1,1}};
        CommonGoalCard card = new CommonGoalShape(3, 2, 2, coords, 2, false, false, false, false, true);
        assertTrue(card.checkPattern(square));
        assertFalse(card.checkPattern(stair));
        int id = card.getId();
        assertEquals(3, id);
    }

    /**
     * Tests the branches verticalSymmetric, horizontalSymmetric, diagonalSymmetric, separated and groupOfSameType for CommonGoalShape
     */
    @Test
    public void testSquare2() {
        int[][] coords = {{0,0}, {0,1}, {1,0}, {1,1}};
        CommonGoalCard card = new CommonGoalShape(3, 2, 2, coords, 2, true, true, true, true, true);
        assertTrue(card.checkPattern(test16));
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testCorners() {
        int[][] coords = {{0,0}, {0,4}, {5,0}, {5,4}};
        CommonGoalCard card = new CommonGoalShape(4, 5, 6, coords, 1, false, false, false, false, false);
        assertTrue(card.checkPattern(square));
        assertFalse(card.checkPattern(stair));
        int id = card.getId();
        assertEquals(4, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testDiagonal() {
        int[][] coords = {{0,0}, {1,1}, {2,2}, {3,3}, {4,4}};
        CommonGoalCard card = new CommonGoalShape(5, 5, 5, coords, 1, false, true, false, false, false);
        assertTrue(card.checkPattern(diagonal));
        assertTrue(card.checkPattern(stair));
        assertFalse(card.checkPattern(stair2));
        int id = card.getId();
        assertEquals(5, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testXShape() {
        int[][] coords = {{0,0}, {1,1}, {2,2}, {0,2}, {2,0}};
        CommonGoalCard card = new CommonGoalShape(6, 3, 3, coords, 1, false, false, false, false, false);
        assertTrue(card.checkPattern(diagonal));
        assertFalse(card.checkPattern(stair));
        assertTrue(card.checkPattern(x));
        int id = card.getId();
        assertEquals(6, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testGroupOfTwo() {
        CommonGoalCard card = new CommonGoalSameTypeGroup(7, 2, 6, true, false);
        assertFalse(card.checkPattern(groupOfTwo));
        assertFalse(card.checkPattern(stair));
        assertFalse(card.checkPattern(test13));
        assertFalse(card.checkPattern(test14));
        assertTrue(card.checkPattern(test15));
        int id = card.getId();
        assertEquals(7, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testGroupOfFour() {
        CommonGoalCard card = new CommonGoalSameTypeGroup(8, 4, 4, true, false);
        assertTrue(card.checkPattern(getGroupOfFour));
        assertFalse(card.checkPattern(stair));
        int id = card.getId();
        assertEquals(8, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testColumnMax3Types() {
        CommonGoalCard card = new CommonGoalDifferentType(9, 6, 1, 3, 1, 3);
        assertTrue(card.checkPattern(colMax3Types));
        assertFalse(card.checkPattern(stair));
        int id = card.getId();
        assertEquals(9, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testRowMax3Types() {
        CommonGoalCard card = new CommonGoalDifferentType(10, 1, 5, 4, 1, 3);
        assertTrue(card.checkPattern(rowMax3Types));
        assertFalse(card.checkPattern(stair));
        assertFalse(card.checkPattern(none));
        int id = card.getId();
        assertEquals(10, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testColumnAllDifferent() {
        CommonGoalCard card = new CommonGoalDifferentType(11, 6, 1, 2, 6, 6);
        assertTrue(card.checkPattern(colMax3Types));
        assertFalse(card.checkPattern(stair));
        assertFalse(card.checkPattern(none));
        int id = card.getId();
        assertEquals(11, id);
    }

    /**
     * Tests if the checkPattern method of the card find the described shape,
     * and tests if the getter of the card id return the correct value
     */
    @Test
    public void testRowAllDifferent() {
        CommonGoalCard card = new CommonGoalDifferentType(12, 1, 5, 2, 5, 5);
        assertTrue(card.checkPattern(rowMax3Types));
        assertFalse(card.checkPattern(stair));
        int id = card.getId();
        assertEquals(12, id);
    }

}