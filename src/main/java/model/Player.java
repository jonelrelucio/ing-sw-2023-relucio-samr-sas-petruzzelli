package model;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private int score;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<CommonGoalCard> obtainedCommonGoalCards;

    public Player(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void selectTiles() {
        //?????
    }

    public void getEndGameToken() {
        //????
    }


}
