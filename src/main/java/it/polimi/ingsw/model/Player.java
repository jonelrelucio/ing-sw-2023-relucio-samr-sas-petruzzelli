package it.polimi.ingsw.model;

import java.util.ArrayList;

import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ItemTile.ItemTile;
import it.polimi.ingsw.model.factory.PersonalGoalCardBag;

enum PlayerState {
    WAITING, PLAYING;
    @Override
    public String toString() {
        return super.toString();
    }
}

public class Player {

    private static final int ENDTOKENSCORE = 1;
    private boolean winner;

    private final String nickname;
    private int score;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<ItemTile> selectedTiles;
    private int numOfRounds;
    private PlayerState playerState;
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;
    private int obtainedCommonGoalPoints;



    public Player(String nickname, PersonalGoalCard personalGoalCard){
        this.nickname = nickname;
        this.personalGoalCard = personalGoalCard;
        this.bookshelf = new Bookshelf();
        selectedTiles = new ArrayList<>();
        obtainedCommonGoalCards = new ArrayList<>();
    }

    // Getters
    public String getNickname() { return nickname; }
    public int getScore() { return score; }
    public Bookshelf getBookshelf() { return bookshelf; }
    public PersonalGoalCard getPersonalGoalCard() { return personalGoalCard; }
    public ArrayList<ItemTile> getSelectedTiles() { return selectedTiles; }
    public int getNumOfRounds() { return numOfRounds; }
    public PlayerState getPlayerState() { return playerState; }
    public ArrayList<CommonGoalCard> getObtainedCommonGoalCards() { return obtainedCommonGoalCards;}
    public int getObtainedCommonGoalPoints() { return obtainedCommonGoalPoints; }

    // Setters
    public void setScore(int score) {this.score = score;}
    public void setBookshelf(Bookshelf bookshelf) {this.bookshelf = bookshelf;}
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {this.personalGoalCard = personalGoalCard;}
    public void setSelectedTiles(ArrayList<ItemTile> selectedTiles) {this.selectedTiles = selectedTiles;}
    public void setNumOfRounds(int numOfRounds) {this.numOfRounds = numOfRounds;}
    public void setPlayerState(PlayerState playerState) {this.playerState = playerState;}
    public void setObtainedCommonGoalCards(CommonGoalCard card) { this.obtainedCommonGoalCards.add(card);}
    public void setObtainedCommonGoalPoints(int obtainedCommonGoalPoints) { this.obtainedCommonGoalPoints = obtainedCommonGoalPoints; }


    public boolean isWinner() { return winner; }
    public void setWinner() { this.winner = true; }
    public int getEndGameToken() {
        if(!isWinner()) throw new IllegalCallerException("Can't call EndGameToken if player is not the winner");
        return ENDTOKENSCORE;
    }


}
