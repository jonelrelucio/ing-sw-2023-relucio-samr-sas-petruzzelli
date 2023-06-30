package it.polimi.ingsw.server.model.commonGoalCard;

import it.polimi.ingsw.server.model.Bookshelf;
import it.polimi.ingsw.server.model.ItemTile.ItemTile;
import it.polimi.ingsw.server.model.ItemTile.ItemTileType;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the CommonGoalCardDeck class
 */
class CommonGoalCardDeckTest {
    /**
     * Bookshelf used for commonGoalCard 1
     */
    private final ItemTile[][] stair =
            {       {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),     new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.EMPTY),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),     new ItemTile(ItemTileType.FRAME),    new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)}};

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
     * Bookshelf used for commonGoalCard 3 and 4
     */
    private final ItemTile[][] square =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.FRAME),  new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.BOOK)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.FRAME),   new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT)}};

    /**
     * Bookshelf used for commonGoalCard 5
     */
    private final ItemTile[][] diagonal =
            {       {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.EMPTY),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.CAT),    new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.BOOK),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.PLANT)},
                    {new ItemTile(ItemTileType.BOOK),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.CAT),     new ItemTile(ItemTileType.TROPHY),  new ItemTile(ItemTileType.CAT)},
                    {new ItemTile(ItemTileType.GAME),   new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.GAME),    new ItemTile(ItemTileType.PLANT),   new ItemTile(ItemTileType.PLANT)}};

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
     * Tests if the CommonGoalCardDeck constructor builds the correct deck based on the number of player (2)
     */
    @Test
    void createDeck2player() {
        CommonGoalCardDeck test;
        test = new CommonGoalCardDeck(2);
        for (CommonGoalCard card : test.getDeck().keySet()) {
            int[] expected = {4, 8};
            int[] actual = new int[2];
            for (int i = 0; i < test.getDeck().get(card).size(); i++) {
                actual[i] = test.getDeck().get(card).get(i);
            }
            assertArrayEquals(expected, actual);
        }

        for (CommonGoalCard card : test.getDeck().keySet()) {
            System.out.println(card.getClass().getSimpleName() + ", scoring tokens: " + test.getDeck().get(card).toString().replaceAll("\\[", "").replaceAll("]", ""));
        }
    }

    /**
     * Tests if the CommonGoalCardDeck constructor builds the correct deck based on the number of player (3)
     */
    @Test
    void createDeck3player() {
        CommonGoalCardDeck test;
        test = new CommonGoalCardDeck(3);
        for (CommonGoalCard card : test.getDeck().keySet()) {
            int[] expected = {4, 6, 8};
            int[] actual = new int[3];
            for (int i = 0; i < test.getDeck().get(card).size(); i++) {
                actual[i] = test.getDeck().get(card).get(i);
            }
            assertArrayEquals(expected, actual);
        }

        for (CommonGoalCard card : test.getDeck().keySet()) {
            System.out.println(card.getClass().getSimpleName() + ", scoring tokens: " + test.getDeck().get(card).toString().replaceAll("\\[", "").replaceAll("]", ""));
        }
    }

    /**
     * Tests if the CommonGoalCardDeck constructor builds the correct deck based on the number of player (4)
     */
    @Test
    void createDeck4player() {
        CommonGoalCardDeck test;
        test = new CommonGoalCardDeck(4);
        for (CommonGoalCard card : test.getDeck().keySet()) {
            int[] expected = {2, 4, 6, 8};
            int[] actual = new int[4];
            for (int i = 0; i < test.getDeck().get(card).size(); i++) {
                actual[i] = test.getDeck().get(card).get(i);
            }
            assertArrayEquals(expected, actual);
        }

        for (CommonGoalCard card : test.getDeck().keySet()) {
            System.out.println(card.getClass().getSimpleName() + ", scoring tokens: " + test.getDeck().get(card).toString().replaceAll("\\[", "").replaceAll("]", ""));
        }
    }

    /**
     * Tests if the taken score from the obtained common goal card is correct
     */
    @Test
    void getScoringToken() {
        CommonGoalCardDeck test;
        test = new CommonGoalCardDeck(3);

        int actualScore;

        for (CommonGoalCard card : test.getDeck().keySet()) {
            actualScore = test.getScoringToken(card);
            assertEquals(8, actualScore);

            int[] expected = {4, 6};
            int[] actual = new int[2];
            for (int i = 0; i < test.getDeck().get(card).size(); i++) {
                actual[i] = test.getDeck().get(card).get(i);
            }
            assertArrayEquals(expected, actual);
        }


        for (CommonGoalCard card : test.getDeck().keySet()) {
            actualScore = test.getScoringToken(card);
            assertEquals(6, actualScore);

            int[] expected = {4};
            int[] actual = new int[1];
            for (int i = 0; i < test.getDeck().get(card).size(); i++) {
                actual[i] = test.getDeck().get(card).get(i);
            }
            assertArrayEquals(expected, actual);
        }

        for (CommonGoalCard card : test.getDeck().keySet()) {
            actualScore = test.getScoringToken(card);
            assertEquals(4, actualScore);

            boolean isEmpty = test.getDeck().get(card).isEmpty();
            assertTrue(isEmpty);
        }

        for (CommonGoalCard card : test.getDeck().keySet()) {
            actualScore = test.getScoringToken(card);
            assertEquals(0, actualScore);
        }
    }

    /**
     * Tests if the player gets the correct score after obtained a common goal card
     */
    @Test
    void getScoreTest() {
        CommonGoalCardDeck deck = new CommonGoalCardDeck(4);
        Player player = new Player("nickname", null, null);
        Bookshelf bookshelf = new Bookshelf();
        CommonGoalCard card = null;
        for (CommonGoalCard c : deck.getDeck().keySet()) {
            card = c;
            break;
        }
        switch (card.getId()) {
            case 1 -> bookshelf.setBookshelfMatrix(stair);
            case 2 -> bookshelf.setBookshelfMatrix(eightPieces);
            case 3 -> bookshelf.setBookshelfMatrix(square);
            case 4 -> bookshelf.setBookshelfMatrix(square);
            case 5 -> bookshelf.setBookshelfMatrix(diagonal);
            case 6 -> bookshelf.setBookshelfMatrix(x);
            case 7 -> bookshelf.setBookshelfMatrix(test15);
            case 8 -> bookshelf.setBookshelfMatrix(getGroupOfFour);
            case 9 -> bookshelf.setBookshelfMatrix(colMax3Types);
            case 10 -> bookshelf.setBookshelfMatrix(rowMax3Types);
            case 11 -> bookshelf.setBookshelfMatrix(colMax3Types);
            case 12 -> bookshelf.setBookshelfMatrix(rowMax3Types);
        }
        player.setBookshelf(bookshelf);

        int result = deck.getScore(player);
        assert(result == 8 || result == 16);

    }
}