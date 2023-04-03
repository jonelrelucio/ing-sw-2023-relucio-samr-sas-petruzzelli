package it.polimi.ingsw.model;

import java.util.ArrayList;

import it.polimi.ingsw.model.bag.PersonalGoalCardBag;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.personalGoalCard.PersonalGoalCard;
import it.polimi.ingsw.model.ItemTile.ItemTile;

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

    private String nickname;
    private int score;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<ItemTile> selectedTiles;
    private int numOfRounds;
    private PlayerState playerState;


    public Player(String nickname){
        this.nickname = nickname;
        this.bookshelf = new Bookshelf();
        selectedTiles = new ArrayList<>();
    }

    // Getters
    public String getNickname() { return nickname; }
    public int getScore() { return score; }
    public Bookshelf getBookshelf() { return bookshelf; }
    public PersonalGoalCard getPersonalGoalCard() { return personalGoalCard; }
    public ArrayList<ItemTile> getSelectedTiles() { return selectedTiles; }
    public int getNumOfRounds() { return numOfRounds; }
    public PlayerState getPlayerState() { return playerState; }

    // Setters
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setScore(int score) {this.score = score;}
    public void setBookshelf(Bookshelf bookshelf) {this.bookshelf = bookshelf;}
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {this.personalGoalCard = personalGoalCard;}
    public void setSelectedTiles(ArrayList<ItemTile> selectedTiles) {this.selectedTiles = selectedTiles;}
    public void setNumOfRounds(int numOfRounds) {this.numOfRounds = numOfRounds;}
    public void setPlayerState(PlayerState playerState) {this.playerState = playerState;}


    public boolean isWinner() { return winner; }
    public void setWinner() { this.winner = true; }
    public void getEndGameToken() {
        this.score += ENDTOKENSCORE;
    }

}
