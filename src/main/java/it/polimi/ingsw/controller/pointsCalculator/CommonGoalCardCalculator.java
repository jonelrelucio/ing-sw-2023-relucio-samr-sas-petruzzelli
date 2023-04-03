package it.polimi.ingsw.controller.pointsCalculator;

import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.commonGoalCard.*;

public class CommonGoalCardCalculator {

    /**
     * For each card in CommonGoalCardDeck: check if the current player's bookshelf match the scheme
     * if true: add the card to obtained common goal cards player list and add the scoring token value to the obtained common goal points
     * @param commonGoalCardDeck    commonGoalCardDeck with its stack of points
     * @param player                player to whom the points will be added
     * @return                      the obtainedCommonGoalPoints of the given player
     */
    public static int getScore(CommonGoalCardDeck commonGoalCardDeck, Player player) {
        ItemTile[][] bookshelf = player.getBookshelf().getBookshelfMatrix();

        for (CommonGoalCard card : commonGoalCardDeck.getDeck().keySet()) {
            if(!player.getObtainedCommonGoalCards().contains(card) && card.checkPattern(bookshelf)) {
                player.setObtainedCommonGoalCards(card);
                player.setScore(player.getObtainedCommonGoalPoints()+commonGoalCardDeck.getScoringToken(card));
            }
        }
        return player.getObtainedCommonGoalPoints();
    }



}
