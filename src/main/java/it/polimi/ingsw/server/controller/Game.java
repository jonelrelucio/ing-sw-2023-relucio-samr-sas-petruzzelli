package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.distributed.Client;
import it.polimi.ingsw.distributed.events.controllerEvents.EventController;
import it.polimi.ingsw.distributed.events.controllerEvents.MessageEvent;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.State;
import it.polimi.ingsw.server.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.server.model.util.CircularArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;


public class Game {
    private final GameModel gameModel;
    private HashMap<EventController, EventManager> eventHandlers;


    public Game( GameModel model) {
        this.gameModel = model;
        initEventHandler();
    }

    //TODO ADD MORE EVENTS
    private void initEventHandler() {
        eventHandlers = new HashMap<>();
        eventHandlers.put(SELECT_COORDINATES ,new SelectCoordinates());
        eventHandlers.put(DESELECT_COORDINATES, new DeselectCoordinates());
        eventHandlers.put(PICK_TILES, new PickCoordinates());
        eventHandlers.put(NEW_ORDER, new NewOrderTiles());
        eventHandlers.put(SELECT_COLUMN, new SelectColumn());
    }

    public void initGameModel(ArrayList<String> usernameList) {
        System.out.println("Game is being created.");
        CircularArrayList<Player> playerList = new CircularArrayList<>();
        Board board = new Board(usernameList.size());
        for (String username : usernameList) {
            playerList.add(new Player(username, PersonalGoalCardBag.drawPersonalGoalCard(usernameList.size()), board ));
        }
        Collections.shuffle(playerList);
        gameModel.initGame(board, playerList);
    }

    public void start() {
        gameModel.setState(State.MID);
        System.out.println("Game has started.");
    }

    public void handleEvent(MessageEvent messageEvent) {
        EventController eventType = messageEvent.getEventType();
        String message = messageEvent.getMessage();
        eventHandlers.get(eventType).performAction(gameModel, message);
    }

}


interface EventManager {

    void performAction(GameModel gameModel, String message);
}


class SelectCoordinates implements EventManager{

    @Override
    public void performAction(GameModel gameModel, String message) {
        String[] coordinates = message.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] selectedCoordinates = new int[] {x, y};
        gameModel.selectCoordinates(selectedCoordinates);
    }
}

class DeselectCoordinates implements EventManager{


    @Override
    public void performAction(GameModel gameModel, String message) {
        String[] coordinates = message.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] selectedCoordinates = new int[] {x, y};
        gameModel.deselectCoordinates(selectedCoordinates);
    }
}

class PickCoordinates implements EventManager{

    @Override
    public void performAction(GameModel gameModel, String message) {
        System.out.println(gameModel.getCurrentPlayer().getNickname() + " picked the selected tiles from the board.");
        gameModel.pickTiles();
    }
}

class NewOrderTiles implements EventManager {

    @Override
    public void performAction(GameModel gameModel, String message) {
        String[] strArr = message.split(" "); // split the string at spaces
        int[] newOrder = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            newOrder[i] = Integer.parseInt(strArr[i]); // parse each substring as integer
        }
        gameModel.rearrangeSelectedItemTiles(newOrder);
    }
}

class SelectColumn implements  EventManager {

    @Override
    public void performAction(GameModel gameModel, String message) {
        int col = Integer.parseInt(message);
        gameModel.selectColumn(col);
    }
}