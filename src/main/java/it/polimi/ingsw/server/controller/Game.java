package it.polimi.ingsw.server.controller;

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
import java.util.concurrent.ArrayBlockingQueue;

import static it.polimi.ingsw.distributed.events.controllerEvents.EventController.*;


public class Game {
    private final GameModel gameModel;
    private final HashMap<EventController, EventManager> eventHandlers;


    public Game( GameModel model) {
        this.gameModel = model;
        eventHandlers = new HashMap<>();
        initEventHandler();
    }

    //TODO ADD MORE EVENTS
    private void initEventHandler() {
        eventHandlers.put(SELECT_COORDINATES , this::selectCoordinate);
        eventHandlers.put(DESELECT_COORDINATES, this::deselectCoordinates);
        eventHandlers.put(PICK_TILES, this::pickCoordinates);
        eventHandlers.put(NEW_ORDER, this::newOrderTiles);
        eventHandlers.put(SELECT_COLUMN, this::selectColumn);
        eventHandlers.put(NEW_MESSAGE_CHAT, this::addMessageToChat);
        eventHandlers.put(NEW_MESSAGE_TO, this::addPrivateMessage);
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
        eventHandlers.get(eventType).performAction( message);
    }

    public void selectCoordinate(String message) {
        String[] coordinates = message.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] selectedCoordinates = new int[] {x, y};
        gameModel.selectCoordinates(selectedCoordinates);
    }

    public void deselectCoordinates(String message ) {
        String[] coordinates = message.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] selectedCoordinates = new int[] {x, y};
        gameModel.deselectCoordinates(selectedCoordinates);
    }

    private void pickCoordinates(String message) {
        System.out.println(gameModel.getCurrentPlayer().getNickname() + " picked the selected tiles from the board.");
        gameModel.pickTiles();
    }

    private void newOrderTiles(String message) {
        String[] strArr = message.split(" ");
        int[] newOrder = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            newOrder[i] = Integer.parseInt(strArr[i]);
        }
        gameModel.rearrangeSelectedItemTiles(newOrder);
    }

    private void selectColumn(String message ) {
        int col = Integer.parseInt(message);
        gameModel.selectColumn(col);
    }

    private void addMessageToChat(String message) {
        gameModel.addMessageToChat(message);
    }

    private void addPrivateMessage(String message) {
        String[] splittedMessage = message.split(" ");
        String username = splittedMessage[0];
        String reassembledMessage = splittedMessage[1];
        for (int i = 2; i < splittedMessage.length; i++) {
            reassembledMessage = reassembledMessage + " " + splittedMessage[i];
        }
        gameModel.addPrivateMessageToMap(username, reassembledMessage);
    }

    public ArrayBlockingQueue<String> getChatMessages() {
        return gameModel.getChat();
    }

}

@FunctionalInterface
interface EventManager {
    void performAction( String message);
}