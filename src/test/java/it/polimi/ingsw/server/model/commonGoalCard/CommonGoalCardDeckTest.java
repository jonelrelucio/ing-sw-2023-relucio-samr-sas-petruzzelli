package it.polimi.ingsw.server.model.commonGoalCard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCardDeckTest {

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
}