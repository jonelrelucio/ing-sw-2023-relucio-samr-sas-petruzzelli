package it.polimi.ingsw.server.model;

import it.polimi.ingsw.distributed.events.ViewEvents.EventView;
import it.polimi.ingsw.distributed.events.ViewEvents.GameModelView;
import it.polimi.ingsw.server.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.server.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.server.model.util.CircularArrayList;
import it.polimi.ingsw.util.Observable;

import static it.polimi.ingsw.distributed.events.ViewEvents.EventView.*;

public class GameModel extends Observable<EventView> {

    private int numOfPlayer;
    private CircularArrayList<Player> playerList;
    private CommonGoalCardDeck commonGoalCardDeck;
    private Board board;
    private State state;
    private int numOfRounds; // what is numOfRounds??? does it increment every player change or does it increment every loop of the playerlist
    private int lastRound;
    private Player currentPlayer;

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

    public GameModel(){
        //if (numOfPlayer < 2 || numOfPlayer > 4 ) throw new IllegalArgumentException("Number of Player out of bounds");
        this.numOfPlayer = 0;
        this.playerList = new CircularArrayList<>();
        this.numOfRounds = 0;
        this.state = State.INIT;
        PersonalGoalCardBag.reset();
    }

    public void initGame(Board board, CircularArrayList<Player> playerList) {
        this.numOfPlayer = playerList.size();
        this.board = board;
        this.commonGoalCardDeck = new CommonGoalCardDeck(numOfPlayer);
        this.playerList = playerList;
        this.currentPlayer = playerList.get(0);
        setChangedAndNotifyObservers(NEW_TURN);
    }

    public Player getWinner() { if(!currentPlayer.isWinner()) throw new IllegalCallerException(); return currentPlayer; }
    public void updateNextPlayer() {this.currentPlayer = playerList.get(playerList.indexOf(this.currentPlayer)+1);}
    public void updateNumOfRounds() { this.numOfRounds++; }

    // Getters
    public int getNumOfPlayer() {
        return numOfPlayer;
    }
    public CircularArrayList<Player> getPlayerList() {return playerList;}
    public CommonGoalCardDeck getCommonGoalCardDeck() {
        return commonGoalCardDeck;
    }
    public Board getBoard() {
        return board;
    }
    public State getState() { return state;}
    public int getNumOfRounds() { return numOfRounds;}
    public Player getCurrentPlayer() { return currentPlayer;}

    // Setters
    public void setNumOfPlayer(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
    }
    public void setPlayerList(CircularArrayList<Player> playerList) {
        this.playerList = playerList;
    }
    public void setCommonGoalCardDeck(CommonGoalCardDeck commonGoalCardDeck) {
        this.commonGoalCardDeck = commonGoalCardDeck;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Updates the score of the current player
     */
    public void updateCurrentPlayerScore() {
        int score = 0;
        score += currentPlayer.getBookshelf().getScore();
        score += commonGoalCardDeck.getScore(currentPlayer);
        score += getCurrentPlayer().getPersonalGoalCard().getScore(currentPlayer.getBookshelf().getBookshelfMatrix());
        if (currentPlayer.isWinner()) score += currentPlayer.getEndGameToken();
        currentPlayer.setScore(score);
    }

    public void pickTiles(){
        currentPlayer.pickSelectedItemTiles();
        setChangedAndNotifyObservers(PICK_TILES_SUCCESS);
    }

    public void selectCoordinates(int[] selectedCoordinates) {
        EventView event = currentPlayer.selectCoordinates(selectedCoordinates);
        setChangedAndNotifyObservers(event);
    }

    public void deselectCoordinates(int[] selectedCoordinates) {
        EventView event = currentPlayer.deselectCoordinates(selectedCoordinates);
        setChangedAndNotifyObservers(event);
    }

    public void rearrangeSelectedItemTiles(int[] newOrder) {
        EventView event = currentPlayer.rearrangeSelectedItemTiles(newOrder);
        setChangedAndNotifyObservers(event);
    }

    public void selectColumn(int col) {
        EventView event = currentPlayer.putItemsInSelectedColumn(col);
        updateNextPlayer();
        setChangedAndNotifyObservers(event);
    }

    //TODO could be private
    public void setChangedAndNotifyObservers(EventView arg) {
        setChanged();
        notifyObservers(arg);
    }

}
