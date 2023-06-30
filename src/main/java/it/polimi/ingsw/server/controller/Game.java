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

/**
 * This class represents the controller of the game,
 * it changes the game model according to the game rules and the inner logic
 */
public class Game {

    /**
     * the Reference to the game model
     */
    private final GameModel gameModel;

    /**
     * a hashmap of event handlers
     */
    private final HashMap<EventController, EventManager> eventHandlers;

    /**
     * Initialize Game
     * @param model
     */
    public Game( GameModel model) {
        this.gameModel = model;
        eventHandlers = new HashMap<>();
        initEventHandler();
    }

    /**
     * Fill the eventHandlers HashMap with the expected event that could be managed and the corresponding method's call
     */
    private void initEventHandler() {
        eventHandlers.put(SELECT_COORDINATES , this::selectCoordinate);
        eventHandlers.put(DESELECT_COORDINATES, this::deselectCoordinates);
        eventHandlers.put(PICK_TILES, this::pickCoordinates);
        eventHandlers.put(NEW_ORDER, this::newOrderTiles);
        eventHandlers.put(SELECT_COLUMN, this::selectColumn);
        eventHandlers.put(NEW_MESSAGE_CHAT, this::addMessageToChat);
        eventHandlers.put(NEW_MESSAGE_TO, this::addPrivateMessage);
    }

    /**
     * Create the player list, the game board and call the initGame method of gameModel that initialize it
     * @param usernameList
     */
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

    /**
     * Change the state of the game to 'MID'
     */
    public void start() {
        gameModel.setState(State.MID);
        System.out.println("Game has started.");
    }

    /**
     * Retrieve the event type and the attached message from the parameter 'messageEvent'
     * and call the performAction method of the event
     * @param messageEvent
     */
    public void handleEvent(MessageEvent messageEvent) {
        EventController eventType = messageEvent.getEventType();
        String message = messageEvent.getMessage();
        eventHandlers.get(eventType).performAction( message);
    }

    /**
     * Split the message and convert the two parts into the corresponding int value,
     * then call the selectCoordinates() method of gameModel
     * @param message
     */
    public void selectCoordinate(String message) {
        String[] coordinates = message.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] selectedCoordinates = new int[] {x, y};
        gameModel.selectCoordinates(selectedCoordinates);
    }

    /**
     * Split the message and convert the two parts into the corresponding int value,
     * then call the deselectCoordinates() method of gameModel
     * @param message
     */
    public void deselectCoordinates(String message ) {
        String[] coordinates = message.split(" ");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);
        int[] selectedCoordinates = new int[] {x, y};
        gameModel.deselectCoordinates(selectedCoordinates);
    }

    /**
     * Call the method pickTiles() of gameModel
     * @param message
     */
    private void pickCoordinates(String message) {
        System.out.println(gameModel.getCurrentPlayer().getNickname() + " picked the selected tiles from the board.");
        gameModel.pickTiles();
    }

    /**
     * Split the message into the corresponding int values and add them to an array of int,
     * then call the method rearrangeSelectedItemTiles() of gameModel
     * @param message
     */
    private void newOrderTiles(String message) {
        String[] strArr = message.split(" ");
        int[] newOrder = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            newOrder[i] = Integer.parseInt(strArr[i]);
        }
        gameModel.rearrangeSelectedItemTiles(newOrder);
    }

    /**
     * Convert the message to the corresponding int value of the selected column and call the method selectColumn of gameModel
     * @param message
     */
    private void selectColumn(String message ) {
        int col = Integer.parseInt(message);
        gameModel.selectColumn(col);
    }

    /**
     * Call the method addMessageToChat() of gameModel
     * @param message
     */
    private void addMessageToChat(String message) {
        gameModel.addMessageToChat(message);
    }

    /**
     * Retrieve the recipient player's nickname and reassembles the message,
     * then put it into the HashMap of the private messages where the nickname is the key and the message is its value
     * @param message
     */
    private void addPrivateMessage(String message) {
        String[] splittedMessage = message.split(" ");
        String username = splittedMessage[0];
        String reassembledMessage = splittedMessage[1];
        for (int i = 2; i < splittedMessage.length; i++) {
            reassembledMessage = reassembledMessage + " " + splittedMessage[i];
        }
        gameModel.addPrivateMessageToMap(username, reassembledMessage);
    }

    /**
     * Getter for the list of chat messages stored into the model
     * @return the list of the last 10 messages in the chat
     */
    public ArrayBlockingQueue<String> getChatMessages() {
        return gameModel.getChat();
    }

}

/**
 * Declares the 'performAction()' method
 */
@FunctionalInterface
interface EventManager {
    /**
     * Declaration of the method 'performAction()'
     * @param message
     */
    void performAction( String message);
}