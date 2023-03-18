package model;

import java.util.ArrayList;
import java.util.Stack;

public class Player {
    private String nickname;
    private int score;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;
    private Stack<ItemTile> selectedTiles;
    private static final int ENDTOKENSCORE = 1;

    public Player(String nickname){
        setNickname(nickname);
        setPersonalGoalCard(new PersonalGoalCard());
        setBookshelf();
        setSelectedTiles();
    }
    public void setObtainedCommonGoalCards(ArrayList<CommonGoalCard> obtainedCommonGoalCards){
        this.obtainedCommonGoalCards = obtainedCommonGoalCards;
    }

    public ArrayList<CommonGoalCard> getObtainedCommonGoalCards() { return obtainedCommonGoalCards; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getNickname() {
        return nickname;
    }
    public void setBookshelf() { bookshelf = new Bookshelf(); }
    public Bookshelf getBookshelf() { return bookshelf; }

    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }
    public PersonalGoalCard getPersonalGoalCard() { return personalGoalCard; }

    public void setScore() {this.score = 0; }
    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
    public void setSelectedTiles() { this.selectedTiles = new Stack<>(); }
    public void setSelectedTiles(Stack<ItemTile> selectedTiles) { this.selectedTiles = selectedTiles; }
    public Stack<ItemTile> getSelectedTiles() { return selectedTiles; }

    public void updateSelectedTiles(Stack<ItemTile> selectedTiles) { this.selectedTiles = selectedTiles; }

    public void getEndGameToken() { this.score += ENDTOKENSCORE; }


}
