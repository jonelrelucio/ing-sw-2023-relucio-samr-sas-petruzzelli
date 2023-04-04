package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.pointsCalculator.BookshelfPointsCalculator;
import it.polimi.ingsw.controller.pointsCalculator.CommonGoalCardCalculator;
import it.polimi.ingsw.controller.pointsCalculator.PersonalGoalCardCalculator;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.View;

public class GameController{
    private final GameModel gameModel;

    public GameController(GameModel model){
        this.gameModel = model;
    }

    /**
     * Updates the score of the current Player
     * It gets the score obtained from the bookshelf calculator
     * it gets the score obtained from the common goal card
     * it gets the score obtained from the personal goal card
     */
    public void updateCurrentPlayerScore() {
        int score = 0;
        score += BookshelfPointsCalculator.getScore(gameModel.getCurrentPlayer().getBookshelf());
        score += CommonGoalCardCalculator.getScore(gameModel.getCommonGoalCardDeck(), gameModel.getCurrentPlayer());
        score += PersonalGoalCardCalculator.getScore(gameModel.getCurrentPlayer());
        if (gameModel.getCurrentPlayer().isWinner()) score += gameModel.getCurrentPlayer().getEndGameToken();
        gameModel.getCurrentPlayer().setScore(score);
    }

}
