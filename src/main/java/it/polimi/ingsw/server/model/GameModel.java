package it.polimi.ingsw.server.model;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.server.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.server.model.util.CircularArrayList;
import it.polimi.ingsw.util.Observable;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;

/**
 * This class represents all the game element and the states of the game
 */
public class GameModel extends Observable<EventView> {
    /**
     * Number of player in game, minimum 2 and maximum 4
     */
    private int numOfPlayer;
    /**
     * List of the players
     */
    private CircularArrayList<Player> playerList;
    /**
     * List of the two common goal card with their available points
     */
    private CommonGoalCardDeck commonGoalCardDeck;
    /**
     * The main board that represents the available item tile which the current player could take
     */
    private Board board;
    /**
     * The current state of the game
     */
    private State state;
    /**
     * The current number of played rounds
     */
    private int numOfRounds;
    /**
     * Flag that indicates that a player has filled its bookshelf and the last round will be played
     */
    private boolean endGame;
    /**
     * The current playing player
     */
    private Player currentPlayer;
    /**
     * List of the last 10 chat messages
     */
    private ArrayBlockingQueue<String> chat;
    /**
     * HashMap with players username as keys and a message as value
     */
    private HashMap<String, String> privateMessage;

    /**
     * Constructor
     * @param numOfPlayer   the number of players
     */
    public GameModel(int numOfPlayer) {
        if (numOfPlayer < 2 || numOfPlayer > 4 ) throw new IllegalArgumentException("Number of Player out of bounds");
        this.numOfPlayer = numOfPlayer;
        this.playerList = new CircularArrayList<>();
        this.commonGoalCardDeck = new CommonGoalCardDeck(numOfPlayer);
        this.board = new Board(numOfPlayer);
        this.numOfRounds = 0;
        this.state = State.INIT;
        PersonalGoalCardBag.reset();
    }

    /**
     * Initialize the GameModel
     */
    public GameModel(){
        this.numOfPlayer = 0;
        this.playerList = new CircularArrayList<>();
        this.numOfRounds = 0;
        this.state = State.INIT;
        PersonalGoalCardBag.reset();
        this.chat = new ArrayBlockingQueue<>(10, true);
        this.privateMessage = new HashMap<>();
    }

    /**
     * Setup the game once the players are ready to start the first turn
     * @param board
     * @param playerList
     * @see #setChangedAndNotifyObservers(EventView)
     */
    public void initGame(Board board, CircularArrayList<Player> playerList) {
        this.numOfPlayer = playerList.size();
        this.board = board;
        this.commonGoalCardDeck = new CommonGoalCardDeck(numOfPlayer);
        this.playerList = playerList;
        this.currentPlayer = playerList.get(0);
        setChangedAndNotifyObservers(NEW_TURN);
    }

    /**
     * @return the winner as Player
     */
    public Player getWinner() { if(!currentPlayer.isWinner()) throw new IllegalCallerException(); return currentPlayer; }

    /**
     * Set the current playing player
     */
    public void updateNextPlayer() {this.currentPlayer = playerList.get(playerList.indexOf(this.currentPlayer)+1);}

    /**
     * Increment the numOfRound field by 1
     */
    public void updateNumOfRounds() { this.numOfRounds++; }


    /**
     * Getter for the number of player
     * @return the number of player as int
     */
    public int getNumOfPlayer() {
        return numOfPlayer;
    }

    /**
     * Getter for the list of player
     * @return the list of player as {@code CircularArrayList<Player>}
     */
    public CircularArrayList<Player> getPlayerList() {return playerList;}

    /**
     * Getter for the list of two common goal card
     * @return the two common goal cards with their available points as CommonGoalCardDeck
     */
    public CommonGoalCardDeck getCommonGoalCardDeck() {
        return commonGoalCardDeck;
    }

    /**
     * Getter for the main game board
     * @return the game board as Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter for the current game state
     * @return the state as State
     */
    public State getState() { return state;}

    /**
     * Getter for the current number of rounds
     * @return the number of played rounds
     */
    public int getNumOfRounds() { return numOfRounds;}

    /**
     * Getter for the current playing player
     * @return current player
     */
    public Player getCurrentPlayer() { return currentPlayer;}

    /**
     * Getter for the list of chat messages
     * @return the last 10 messages in the chat
     */
    public ArrayBlockingQueue<String> getChat() { return chat; }

    /**
     * Getter for the list of the private messages
     * @return an HashMap that contains a player username as key and its last private message as value
     */
    public HashMap<String, String> getPrivateMessageMap() { return privateMessage; }


    /**
     * Set the number of player
     * @param numOfPlayer
     */
    public void setNumOfPlayer(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
    }

    /**
     * Set the list of players
     * @param playerList
     */
    public void setPlayerList(CircularArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * Set the list of the two common goal card with their initial available points
     * @param commonGoalCardDeck
     */
    public void setCommonGoalCardDeck(CommonGoalCardDeck commonGoalCardDeck) {
        this.commonGoalCardDeck = commonGoalCardDeck;
    }

    /**
     * Set the main board of the game
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Set the current state of the game
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Set the numOfRounds field
     * @param numOfRounds
     */
    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }

    /**
     * Set the currentPlayer field
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Add the message to the list of the last 10 chat messages and notify the clients
     * @param message
     */
    public void addMessageToChat(String message) {
        if (this.chat.remainingCapacity() == 0) {
            this.chat.poll();
        }
        this.chat.add(message);
        setChangedAndNotifyObservers(UPDATE_CHAT);
    }

    /**
     * Add the received message to the hashmap 'privateMessage' to the corresponding key (recipient username) and notify the clients
     * @param username
     * @param message
     * @see #setChangedAndNotifyObservers(EventView)
     */
    public void addPrivateMessageToMap(String username, String message) {
        if (playerList.stream().anyMatch(p -> p.getNickname().equals(username))) {
            this.privateMessage.put(username, message);
        }
        setChangedAndNotifyObservers(UPDATE_PRIVATE_CHAT);
    }

    /**
     * Updates the score of the current player
     */
    public void updateCurrentPlayerScore() {
        int score = 0;
        score += currentPlayer.getBookshelf().getScore();
        score += commonGoalCardDeck.getScore(currentPlayer);
        score += currentPlayer.getPersonalGoalCard().getScore(currentPlayer.getBookshelf().getBookshelfMatrix());
        if (currentPlayer.isFirstToFillBookshelf()) score += currentPlayer.getEndGameToken();
        System.out.println(currentPlayer.getNickname() + " has " + score + " points.");
        currentPlayer.setScore(score);
    }

    /**
     * Picks the tiles event
     */
    public void pickTiles(){
        currentPlayer.pickSelectedItemTiles();
        setChangedAndNotifyObservers(PICK_TILES_SUCCESS);
    }

    /**
     * Selects the tiles event
     * @param selectedCoordinates   the selected coordinates
     */
    public void selectCoordinates(int[] selectedCoordinates) {
        EventView event = currentPlayer.selectCoordinates(selectedCoordinates);
        setChangedAndNotifyObservers(event);
    }

    /**
     * deselect tiles event
     * @param selectedCoordinates   the tiles to be deselected
     */
    public void deselectCoordinates(int[] selectedCoordinates) {
        EventView event = currentPlayer.deselectCoordinates(selectedCoordinates);
        setChangedAndNotifyObservers(event);
    }

    /**
     * new order event
     * @param newOrder  the array of new order
     */
    public void rearrangeSelectedItemTiles(int[] newOrder) {
        EventView event = currentPlayer.rearrangeSelectedItemTiles(newOrder);
        setChangedAndNotifyObservers(event);
    }

    /**
     * the select column event
     * @param col   the column to be selected
     */
    public void selectColumn(int col) {
        EventView event = currentPlayer.putItemsInSelectedColumn(col);
        if (board.checkRefill()) board.refill();
        if (event.equals(NEW_TURN)) {
            if (currentPlayer.getBookshelf().isFull()){
                currentPlayer.setFirstToFillBookshelf();
                endGame = true;
            }
            if ( endGame && (playerList.indexOf(this.currentPlayer)+1)%playerList.size() == 0 ) {
                updateCurrentPlayerScore();
                event = EventView.END_GAME;
            }
            else {
                updateCurrentPlayerScore();
                updateNextPlayer();
            }
        }
        setChangedAndNotifyObservers(event);
    }

    /**
     * notifies its observer of the changes in the game model view
     * @param arg   the event that will notify the observer
     */
    private void setChangedAndNotifyObservers(EventView arg) {
        setChanged();
        notifyObservers(arg);
    }

}
