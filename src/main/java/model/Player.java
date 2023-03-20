package model;

import java.util.ArrayList;
import model.commonGoalCard.*;

enum PlayerState {
    WAITING, PLAYING;
    @Override
    public String toString() {
        return super.toString();
    }
}

public class Player {

    private String nickname;
    private int score;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;
    private ArrayList<ItemTile> selectedTiles;
    private static final int ENDTOKENSCORE = 1;
    private int numOfRounds;
    private PlayerState playerState;

    public Player(String nickname){
        this.nickname = nickname;
        this.personalGoalCard = new PersonalGoalCard();
        this.bookshelf = new Bookshelf();
        selectedTiles = new ArrayList<>();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }

    public ArrayList<CommonGoalCard> getObtainedCommonGoalCards() {
        return obtainedCommonGoalCards;
    }

    public void setObtainedCommonGoalCards(ArrayList<CommonGoalCard> obtainedCommonGoalCards) {
        this.obtainedCommonGoalCards = obtainedCommonGoalCards;
    }

    public ArrayList<ItemTile> getSelectedTiles() {
        return selectedTiles;
    }

    public void setSelectedTiles(ArrayList<ItemTile> selectedTiles) {
        this.selectedTiles = selectedTiles;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public void setNumOfRounds(int numOfRounds) {
        this.numOfRounds = numOfRounds;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void getEndGameToken() {
        this.score += ENDTOKENSCORE;
    }
}
