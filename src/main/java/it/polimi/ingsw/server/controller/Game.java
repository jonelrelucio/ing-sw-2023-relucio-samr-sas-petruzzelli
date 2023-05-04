package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.PersonalGoalCard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Game {
    GameModel gameModel;
    private boolean gameStarted;


    public Game(GameModel model) {
        this.gameModel = model;
    }

    public void initGameModel(ArrayList<String> usernameList) {
        CircularArrayList<Player> playerList = new CircularArrayList<>();
        Board board = new Board(usernameList.size());
        for (String username : usernameList) {
            playerList.add(new Player(username, PersonalGoalCardBag.drawPersonalGoalCard(usernameList.size()), board ));
        }
        gameModel.setPlayerList(playerList);
    }

    public void start() {
        System.out.println("Game Started.");
    }

//    TODO Manage game events
//    public void eventHandler( GameEvent event) {
//        String eventName = event.getEventName();
//        switch (eventName){
//            case "NEW_GAME" -> createNewGame(event);
//            case "ADD_PLAYER" -> addNewPlayer(event);
//            case "START_GAME" -> startGame();
//            case "SELECT_COORDINATES" -> selectCoordinates(event);
//        }
//    }
//
//    private void selectCoordinates(GameEvent x) {
//        if (!(x instanceof SelectCoordinatesEvent event) ) throw new RuntimeException("Game Event is not a SelectCoordinates instance");
//        model.getCurrentPlayer().selectCoordinates(new int[] {event.getX(), event.getY()});
//        model.setChangedAndNotifyObservers(new UpdateCanBeSelectedTilesEvent(model.getBoard().getCanBeSelectedCoordinates(), model.getBoard().getSelectedCoordinates()));
//    }
//
//    public void createNewGame(GameEvent x) {
//        if (!(x instanceof NewGameEvent event) ) throw new RuntimeException("Game Event is not a FirstPlayer instance");
//        System.out.print("Received game event");
//        model.initGame(event.getNumOfPlayers(), event.getPlayerName());
//    }
//
//    public void addNewPlayer(GameEvent x) {
//        if (!(x instanceof AddPlayer event) ) throw new RuntimeException("Game Event is not a AddPlayer instance");
//        model.addNewPlayer(event.getUsername());
//    }
//
//    public void startGame() {
//        if(!gameStarted){
//            gameStarted = true;
//            Collections.shuffle(model.getPlayerList());
//            model.initCurrentPlayer();
//        }
//    }
//





//
//    /**
//     * Manages the player turns
//     */
//    public void stateManager() {
//        if (model.getState() == State.INIT) {
//            if (model.getPlayerList().size() == model.getNumOfPlayer()) {
//                model.setState(State.MID);
//                nextTurn();
//            }
//        }
//        else if (model.getState() == State.MID) {
//            nextTurn();
//        } else if (model.getState() == State.END) {
//            // calculate the winner
//            System.out.println("Qualcuno ha vinto!");
//        }
//    }

//    public void nextTurn() {
//        if (!model.getEndGame()) {
//            model.updateNextPlayer();
//        } else {
//            if (model.getCurrentPlayer() != model.getPlayerList().get(model.getNumOfPlayer() - 1)) {
//                model.updateNextPlayer();
//            } else {
//                // fine partita
//                model.setState(State.END);
//            }
//        }
//    }

}
