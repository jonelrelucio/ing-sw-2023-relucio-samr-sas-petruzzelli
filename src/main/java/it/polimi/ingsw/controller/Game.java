package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.events.NewGame;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.controller.events.GameEvent;

public class Game {
    GameModel model;

    public Game(GameModel model) {
        this.model = model;
    }

    public void eventHandler(GameEvent event) {
        String eventName = event.getEventName();
        switch (eventName){
            case "NEW_GAME" -> createNewGame(event);
            case "UPDATE_PLAYER_SCORE" -> updateCurrentPlayerScore();
        }
    }

    public void createNewGame(GameEvent x) {
        if (!(x instanceof NewGame event) ) throw new RuntimeException("Game Event is not A FirstPlayer instance");
        model.initGame(event.getNumOfPlayers(), event.getPlayerName());
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
