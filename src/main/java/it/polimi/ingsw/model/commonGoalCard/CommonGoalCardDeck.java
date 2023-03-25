package it.polimi.ingsw.model.commonGoalCard;

import java.util.HashMap;
import java.util.Stack;

public class CommonGoalCardDeck {
    private final HashMap<CommonGoalCard, Stack<Integer>> deck = new HashMap<>();

    private static final int[][] scoringTokenArray = {
            {4, 8},
            {4, 6, 8},
            {2, 4, 6, 8}
    };

    private Stack<Integer> buildScoringStack(int key){
        Stack<Integer> scoringTokenStack = new Stack<>();
        for (int i = 0; i < scoringTokenArray[key-2].length; i++){
            scoringTokenStack.push(scoringTokenArray[key-1][i]);
        }
        return scoringTokenStack;
    }

    public CommonGoalCardDeck(int numOfPlayers, CommonGoalCard card1, CommonGoalCard card2) {
        deck.put(card1, buildScoringStack(numOfPlayers));
        deck.put(card2, buildScoringStack(numOfPlayers));
    }

    public HashMap<CommonGoalCard, Stack<Integer>> getDeck() {
        return deck;
    }



}