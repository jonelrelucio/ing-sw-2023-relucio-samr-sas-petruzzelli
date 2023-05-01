package it.polimi.ingsw.controller;

import it.polimi.ingsw.distributed.events.GameEvent;
import it.polimi.ingsw.distributed.events.NewGame;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.State;

public class Game {
    GameModel model;

    public Game(GameModel model) {
        this.model = model;
    }

    public void eventHandler(GameEvent event) {
        String eventName = event.getEventName();
        switch (eventName){
            case "NEW_GAME" -> createNewGame(event);
            case "" -> createNewGame(event);
            case "UPDATE_PLAYER_SCORE" -> updateCurrentPlayerScore();
        }
    }

    public void createNewGame(GameEvent x) {
        if (!(x instanceof NewGame event) ) throw new RuntimeException("Game Event is not a FirstPlayer instance");
        System.out.print("Received game event");
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


    /**
     * Manages the player turns
     */
    public void stateManager() {
        if (model.getState() == State.INIT) {
            if (model.getPlayerList().size() == model.getNumOfPlayer()) {
                model.setState(State.MID);
                nextTurn();
            }
        }
        else if (model.getState() == State.MID) {
            nextTurn();
        } else if (model.getState() == State.END) {
            // calculate the winner
            System.out.println("Qualcuno ha vinto!");
        }
    }

    public void nextTurn() {
        if (!model.getEndGame()) {
            model.updateNextPlayer();
        } else {
            if (model.getCurrentPlayer() != model.getPlayerList().get(model.getNumOfPlayer() - 1)) {
                model.updateNextPlayer();
            } else {
                // fine partita
                model.setState(State.END);
            }
        }
    }

}
