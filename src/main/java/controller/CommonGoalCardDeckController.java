package controller;

import model.*;

import java.util.Collections;
import java.util.Stack;

public class CommonGoalCardDeckController {
    private CommonGoalCardDeck modelCommonGoalCardDeck;
    private GameModel gameModel;

    public CommonGoalCardDeckController(CommonGoalCardDeck modelCommonGoalCardDeck, GameModel gameModel) {
        this.modelCommonGoalCardDeck = modelCommonGoalCardDeck;
        this.gameModel = gameModel;
    }

    // draw two common goal cards from the complete deck of 12 cards and add them to the deck with their scoring tokens
    public void drawCommonGoalCard() {
        Stack<CommonGoalCard> completeDeck = new Stack<>();

        CommonGoalCard card1 = new CommonGoalCard1();
        CommonGoalCard card2 = new CommonGoalCard2();
        CommonGoalCard card3 = new CommonGoalCard3();
        CommonGoalCard card4 = new CommonGoalCard4();
        CommonGoalCard card5 = new CommonGoalCard5();
        CommonGoalCard card6 = new CommonGoalCard6();
        CommonGoalCard card7 = new CommonGoalCard7();
        CommonGoalCard card8 = new CommonGoalCard8();
        CommonGoalCard card9 = new CommonGoalCard9();
        CommonGoalCard card10 = new CommonGoalCard10();
        CommonGoalCard card11 = new CommonGoalCard11();
        CommonGoalCard card12 = new CommonGoalCard12();

        completeDeck.push(card1);
        completeDeck.push(card2);
        completeDeck.push(card3);
        completeDeck.push(card4);
        completeDeck.push(card5);
        completeDeck.push(card6);
        completeDeck.push(card7);
        completeDeck.push(card8);
        completeDeck.push(card9);
        completeDeck.push(card10);
        completeDeck.push(card11);
        completeDeck.push(card12);

        Collections.shuffle(completeDeck);
        modelCommonGoalCardDeck.setCommonGoalCardDeck(completeDeck.pop(), addScoringToken(gameModel.getNumOfPlayer()));

        Collections.shuffle(completeDeck);
        modelCommonGoalCardDeck.setCommonGoalCardDeck(completeDeck.pop(), addScoringToken(gameModel.getNumOfPlayer()));
    }

    // generates stack of scoring tokens according to the number of players
    public Stack<Integer> addScoringToken(int numPlayer) {

        int[][] scoringTokenList =
                {       {4, 8},
                        {4, 6, 8},
                        {2, 4, 6, 8}};

        Stack<Integer> scoringToken = new Stack<>();

        for (int i = 0; i < scoringTokenList[numPlayer - 2].length; i++) {
            scoringToken.push(scoringTokenList[numPlayer - 2][i]);
        }

        return scoringToken;
    }

    // check if the player obtained the common goal cards and add those cards with their scoring token to the player obtained cards list
    public boolean checkCommonGoalCard() {
        boolean found = false;

        for (CommonGoalCard card : modelCommonGoalCardDeck.getCommonGoalCardDeck().keySet()) {
            if (card.checkPattern(gameModel.getCurrentPlayer().getBookshelf().getBookshelf()) && !gameModel.getCurrentPlayer().getObtainedCommonGoalCards().contains(card)) {
                modelCommonGoalCardDeck.getScoringToken(card);
                found = true;
            }
        }

        return found;
    }
}
