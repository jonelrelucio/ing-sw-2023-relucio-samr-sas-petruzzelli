package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.Player;

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

    public int getScoringToken(CommonGoalCard card) {
        return deck.get(card).pop();
    }



    /**
     * For each card in CommonGoalCardDeck: check if the current player's bookshelf match the scheme
     * if true: add the card to obtained common goal cards player list and add the scoring token value to the obtained common goal points
     * @param player                player to whom the points will be added
     * @return                      the obtainedCommonGoalPoints of the given player
     */
    public int getScore(Player player) {
        ItemTile[][] bookshelf = player.getBookshelf().getBookshelfMatrix();

        for (CommonGoalCard card : deck.keySet()) {
            if(!player.getObtainedCommonGoalCards().contains(card) && card.checkPattern(bookshelf)) {
                player.setObtainedCommonGoalCards(card);
                player.setScore(player.getObtainedCommonGoalPoints()+getScoringToken(card));
            }
        }
        return player.getObtainedCommonGoalPoints();
    }
}