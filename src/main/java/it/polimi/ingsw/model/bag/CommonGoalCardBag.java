package it.polimi.ingsw.model.bag;

import it.polimi.ingsw.model.commonGoalCard.*;

import java.util.Collections;
import java.util.Stack;

public class CommonGoalCardBag {


    public static CommonGoalCardDeck commonGoalCardDeckBuilder(int numOfPlayer) {
        Stack<CommonGoalCard> completeDeck = new Stack<>();

        completeDeck.push(new CommonGoalCard1());
        completeDeck.push(new CommonGoalCard2());
        completeDeck.push(new CommonGoalCard3());
        completeDeck.push(new CommonGoalCard4());
        completeDeck.push(new CommonGoalCard5());
        completeDeck.push(new CommonGoalCard6());
        completeDeck.push(new CommonGoalCard7());
        completeDeck.push(new CommonGoalCard8());
        completeDeck.push(new CommonGoalCard9());
        completeDeck.push(new CommonGoalCard10());
        completeDeck.push(new CommonGoalCard11());
        completeDeck.push(new CommonGoalCard12());

        Collections.shuffle(completeDeck);
        CommonGoalCard card1 = completeDeck.pop();

        Collections.shuffle(completeDeck);
        CommonGoalCard card2 = completeDeck.pop();

        return new CommonGoalCardDeck(numOfPlayer, card1, card2);
    }
}
