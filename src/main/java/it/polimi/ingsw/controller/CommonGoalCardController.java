package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.commonGoalCard.*;

import java.util.Collections;
import java.util.Stack;

public class CommonGoalCardController {
    private final GameModel model;

    public CommonGoalCardController(GameModel model) {
        this.model = model;
    }

    public boolean checkCommonGoal() {
        ItemTile[][] bookshelf = model.getCurrentPlayer().getBookshelf().getBookshelfMatrix();
        boolean found = false;

        // for each card in CommonGoalCardDeck: check if the current player's bookshelf match the scheme
        // if yes: add the card to obtained common goal cards player list and add the scoring token value to the player score
        for (CommonGoalCard card : model.getCommonGoalCardDeck().getDeck().keySet()) {
            if(!model.getCurrentPlayer().getObtainedCommonGoalCards().contains(card) && card.checkPattern(bookshelf)) {
                model.getCurrentPlayer().setObtainedCommonGoalCards(card);
                model.getCurrentPlayer().setScore(model.getCommonGoalCardDeck().getScoringToken(card));
                found = true;
            }
        }

        return found;
    }

    public CommonGoalCardDeck commonGoalCardDeckBuilder(int numOfPlayer) {
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
