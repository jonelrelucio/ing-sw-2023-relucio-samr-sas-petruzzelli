package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.pointsCalculator.BookshelfPointsCalculator;
import it.polimi.ingsw.controller.pointsCalculator.CommonGoalCardCalculator;
import it.polimi.ingsw.controller.pointsCalculator.PersonalGoalCardCalculator;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.View;

public class GameController{
    private final GameModel gameModel;
    private final View view;

    public GameController(GameModel model, View view){
        this.gameModel = model;
        this.view = view;
    }

    /**
     * Updates the score of the current Player
     * It checks the score obtained by the bookshelf calculator
     * it checks the score obtained by the common goal card
     * it checks the score obtained by the personal goal card
     */
    public void updateCurrentPlayerScore() {
        int score = 0;
        score += BookshelfPointsCalculator.getScore(gameModel.getCurrentPlayer().getBookshelf());
        score += CommonGoalCardCalculator.getScore(gameModel.getCommonGoalCardDeck(), gameModel.getCurrentPlayer());
        score += PersonalGoalCardCalculator.getScore(gameModel.getCurrentPlayer().getPersonalGoalCard(), gameModel.getCurrentPlayer().getBookshelf());
        if (gameModel.getCurrentPlayer().isWinner()) score += gameModel.getCurrentPlayer().getEndGameToken();
        gameModel.getCurrentPlayer().setScore(score);
    }

}