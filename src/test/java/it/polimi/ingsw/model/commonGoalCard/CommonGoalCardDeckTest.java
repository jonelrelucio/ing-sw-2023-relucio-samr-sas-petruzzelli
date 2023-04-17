package it.polimi.ingsw.model.commonGoalCard;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCardDeckTest {

    @Test
    void commonGoalCardDeck() {


        CommonGoalCardDeck builtTest = null;
        try {
            builtTest = new CommonGoalCardDeck(2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (CommonGoalCard card : builtTest.getDeck().keySet()) {
            System.out.println(card.getClass().getSimpleName() + ", scoring tokens: " + builtTest.getDeck().get(card).toString().replaceAll("\\[", "").replaceAll("]", ""));
        }
    }

    @Test
    void getDeck() {
    }

    @Test
    void getScoringToken() {
    }
}