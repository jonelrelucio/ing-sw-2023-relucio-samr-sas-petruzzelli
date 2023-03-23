package model;

import java.util.HashMap;
import java.util.Stack;

public class CommonGoalCardDeck {
    private HashMap<CommonGoalCard, Stack<Integer>> deck = new HashMap<>();
    public CommonGoalCardDeck(int numPlayer, CommonGoalCard card1, CommonGoalCard card2) {

    }

    public int getScoringToken(CommonGoalCard card) {
        return deck.get(card).pop();
    }


}
