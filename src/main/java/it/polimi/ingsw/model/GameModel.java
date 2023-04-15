package it.polimi.ingsw.model;

import it.polimi.ingsw.model.bag.CommonGoalCardBag;
import it.polimi.ingsw.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCardDeck;
import it.polimi.ingsw.model.util.CircularArrayList;

enum State {
    INIT, MID, END
}

public class GameModel {
    private int numOfPlayer;
    private CircularArrayList<Player> playerList;
    private CommonGoalCardDeck commonGoalCardDeck;
    private Board board;
    private State state;
    private int numOfRounds; // what is numOfRounds??? does it increment every player change or does it increment every loop of the playerlist
    private Player currentPlayer;

    public GameModel(int numOfPlayer) {
        if (numOfPlayer < 2 || numOfPlayer > 4 ) throw new IllegalArgumentException("Number of Player out of bounds");
        this.playerList = new CircularArrayList<>();
        this.commonGoalCardDeck = CommonGoalCardBag.commonGoalCardDeckBuilder(numOfPlayer);
        this.board = new Board(numOfPlayer);
        this.numOfRounds = 0;
        this.state = State.INIT;
        PersonalGoalCardBag.resetBag();
    }

    public Player getWinner() { return currentPlayer; }
    public void updateNextPlayer() { this.currentPlayer = playerList.get(playerList.indexOf(this.currentPlayer)+1); }
    public void updateNumOfRounds() { this.numOfRounds++; }

    // Getters
    public int getNumOfPlayer() {
        return numOfPlayer;
    }
    public CircularArrayList<Player> getPlayerList() {
        return playerList;
    }
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
    public void setNumOfPlayer(int numOfPlayer) { this.numOfPlayer = numOfPlayer; }
    public void setPlayerList(CircularArrayList<Player> playerList) { this.playerList = playerList; }
    public void setCommonGoalCardDeck(CommonGoalCardDeck commonGoalCardDeck) { this.commonGoalCardDeck = commonGoalCardDeck; }
    public void setBoard(Board board) { this.board = board; }
    public void setState(State state) { this.state = state; }
    public void setNumOfRounds(int numOfRounds) { this.numOfRounds = numOfRounds;}
    public void setCurrentPlayer(Player currentPlayer) { this.currentPlayer = currentPlayer; }


    /**
     * Updates the score of the current player
     */
    public void updateCurrentPlayerScore() {
        int score = 0;
        score += currentPlayer.getBookshelf().getScore();
        score += commonGoalCardDeck.getScore(currentPlayer);
        score += currentPlayer.getPersonalGoalCard().getScore(currentPlayer.getBookshelf());
        if (currentPlayer.isWinner()) score += currentPlayer.getEndGameToken();
        currentPlayer.setScore(score);
    }

}
