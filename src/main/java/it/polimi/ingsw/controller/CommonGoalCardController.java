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


}
