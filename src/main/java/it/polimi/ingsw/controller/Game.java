package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.CLI;

public class Game {
    GameModel model;
    CLI view;

    public Game(GameModel model, CLI view) {
        this.model = model;
        this.view = view;
    }

    public void play() {
        initgame();
        midgame();

    }

    private void initgame() {

    }

    public void midgame() {

    }

    /**
     * Updates the score of the current player
     */
    public void updateCurrentPlayerScore() {
        int score = 0;
        score += model.getCurrentPlayer().getBookshelf().getScore();
        score += model.getCommonGoalCardDeck().getScore(model.getCurrentPlayer());
        score += model.getCurrentPlayer().getPersonalGoalCard().getScore(model.getCurrentPlayer().getBookshelf().getBookshelfMatrix());
        if (model.getCurrentPlayer().isWinner()) score += model.getCurrentPlayer().getEndGameToken();
        model.getCurrentPlayer().setScore(score);
    }
}
