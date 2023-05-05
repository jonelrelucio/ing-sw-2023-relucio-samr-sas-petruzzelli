package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.distributed.events.controllerEvents.Event;
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

import static it.polimi.ingsw.distributed.events.controllerEvents.Event.*;


public class Game {
    private final GameModel gameModel;
    private HashMap<Event, EventManager> eventHandlers;


    public Game(GameModel model) {
        this.gameModel = model;
        initEventHandler();
    }

    //TODO ADD MORE EVENTS
    private void initEventHandler() {
        eventHandlers = new HashMap<>();
        eventHandlers.put(SELECT_COORDINATES ,new SelectCoordinates());
        eventHandlers.put(DESELECT_COORDINATES, new DeselectCoordinates());

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
        Event eventType = messageEvent.getEventType();
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
        System.out.println(gameModel.getCurrentPlayer().getNickname() + " selected coordinates from the board.");
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

    }
}
