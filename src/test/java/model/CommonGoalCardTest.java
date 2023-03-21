package model;

import model.commonGoalCard.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonGoalCardTest {
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
            {       {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.FRAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT)}};

    private final ItemTile[][] bookshelf4 =
            {       {new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.EMPTY),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.BOOK),  new ItemTile(ItemTileType.FRAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.CAT),      new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.BOOK),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),   new ItemTile(ItemTileType.BOOK),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)}};

    private final ItemTile[][] bookshelf5 =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)}};

    private final ItemTile[][] bookshelf6 =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.TROPHY), new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.FRAME)}};


    private final ItemTile[][] bookshelf7 =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.PLANT),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.TROPHY)}};

    @Test
    public void testCard1Absent() {
        CommonGoalCard card = new CommonGoalCard1();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard1Present() {
        CommonGoalCard card = new CommonGoalCard1();
        assertTrue(card.checkPattern(bookshelf4));
    }

    @Test
    public void testCard2Absent() {
        CommonGoalCard card = new CommonGoalCard2();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard2Present() {
        CommonGoalCard card = new CommonGoalCard2();
        assertTrue(card.checkPattern(bookshelf5));
    }

    @Test
    public void testCard3Absent() {
        CommonGoalCard card = new CommonGoalCard3();
        assertFalse(card.checkPattern(bookshelf2));
    }

    @Test
    public void testCard3Present() {
        CommonGoalCard card = new CommonGoalCard3();
        assertTrue(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard4Absent() {
        CommonGoalCard card = new CommonGoalCard4();
        assertFalse(card.checkPattern(bookshelf6));
    }

    @Test
    public void testCard4Present() {
        CommonGoalCard card = new CommonGoalCard4();
        assertTrue(card.checkPattern(bookshelf2));
    }

    @Test
    public void testCard5Present() {
        CommonGoalCard card = new CommonGoalCard5();
        assertTrue(card.checkPattern(bookshelf4));
    }

    @Test
    public void testCard5Absent() {
        CommonGoalCard card = new CommonGoalCard5();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard6Absent() {
        CommonGoalCard card = new CommonGoalCard6();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard6APresentDiagonal1() {
        CommonGoalCard card = new CommonGoalCard6();
        assertTrue(card.checkPattern(bookshelf3));
    }

    @Test
    public void testCard6PresentDiagonal2() {
        CommonGoalCard card = new CommonGoalCard6();
        assertTrue(card.checkPattern(bookshelf2));
    }

    @Test
    public void testCard6AbsentDiagonal3() {
        CommonGoalCard card = new CommonGoalCard6();
        assertTrue(card.checkPattern(bookshelf4));
    }

    @Test
    public void testCard6AbsentDiagonal4() {
        CommonGoalCard card = new CommonGoalCard6();
        assertTrue(card.checkPattern(bookshelf5));
    }

    @Test
    public void testCard7Absent() {
        CommonGoalCard card = new CommonGoalCard7();
        assertFalse(card.checkPattern(bookshelf3));
    }

    @Test
    public void testCard7PresentIncreasing() {
        CommonGoalCard card = new CommonGoalCard7();
        assertTrue(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard7PresentDecreasing() {
        CommonGoalCard card = new CommonGoalCard7();
        assertTrue(card.checkPattern(bookshelf2));
    }

    @Test
    public void testCard8Absent() {
        CommonGoalCard card = new CommonGoalCard8();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard8Present() {
        CommonGoalCard card = new CommonGoalCard8();
        assertTrue(card.checkPattern(bookshelf2));
    }

    @Test
    public void testCard9Absent() {
        CommonGoalCard card = new CommonGoalCard9();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard9Present() {
        CommonGoalCard card = new CommonGoalCard9();
        assertTrue(card.checkPattern(bookshelf4));
    }

    @Test
    public void testCard10Absent() {
        CommonGoalCard card = new CommonGoalCard10();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard10Present() {
        CommonGoalCard card = new CommonGoalCard10();
        assertTrue(card.checkPattern(bookshelf4));
    }

    @Test
    public void testCard11Absent() {
        CommonGoalCard card = new CommonGoalCard11();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard11Present() {
        CommonGoalCard card = new CommonGoalCard11();
        assertTrue(card.checkPattern(bookshelf6));
    }

    @Test
    public void testCard12Absent() {
        CommonGoalCard card = new CommonGoalCard12();
        assertFalse(card.checkPattern(bookshelf1));
    }

    @Test
    public void testCard12Present() {
        CommonGoalCard card = new CommonGoalCard12();
        assertTrue(card.checkPattern(bookshelf6));
    }
}