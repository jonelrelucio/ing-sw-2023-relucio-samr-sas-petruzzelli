package model;

import model.commonGoalCard.CommonGoalCardDeck;

enum State {
    INIT, MID, END
}

public class GameModel {
    private int numOfPlayer;
    private CircularArrayList<Player> playerList;
    private CommonGoalCardDeck commonGoalCardDeck;
    private Board board;
    private State state;
    private int numOfRounds;
    private Player currentPlayer;

    public GameModel() {
        this.playerList = new CircularArrayList<>();
        this.commonGoalCardDeck = new CommonGoalCardDeck();
        this.board = new Board();
    }
    public void setPersonalGoalCards() {
        for (int i = 0; i < numOfPlayer; i++ ){
            playerList.get(i).setPersonalGoalCard(new PersonalGoalCard());
        }
    }
    public Player checkWinner() {
        return new Player("string");
    }
    public Player getNextPlayer() {
        return new Player("string");
    }
    public int getNumOfPlayer() {
        return numOfPlayer;
    }

    public void setNumOfPlayer(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
    }

    public CircularArrayList<Player> getPlayerList() {
        return playerList;
    }
    public void setPlayerList(CircularArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public CommonGoalCardDeck getCommonGoalCardDeck() {
        return commonGoalCardDeck;
    }

    public void setCommonGoalCardDeck(CommonGoalCardDeck commonGoalCardDeck) {
        this.commonGoalCardDeck = commonGoalCardDeck;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
