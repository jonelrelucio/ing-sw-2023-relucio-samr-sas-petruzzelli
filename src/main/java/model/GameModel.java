package model;

enum State {
    INIT, MID, END
}

public class GameModel {
    private int numOfPlayer;
    private CircularArrayList<Player> playerList;
    private CommonGoalCardDeck commonGoalCardDeck;
    private Board board;
    private State state;
    private int getNumOfRounds;
    private Player currentPlayer;

    public void init() {}
    public void start() {

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

    public int getGetNumOfRounds() {
        return getNumOfRounds;
    }

    public void setGetNumOfRounds(int getNumOfRounds) {
        this.getNumOfRounds = getNumOfRounds;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
